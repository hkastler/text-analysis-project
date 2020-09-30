#!/bin/sh
#$1=keyfile
#$2=ipaddr
echo "-----scp-configs-----"
scp -i $1 /c/etc/opt/text-analysis-project/text-analysis-twitter/twitter_sentiment_app_properties ec2-user@$2:/etc/opt/text-analysis-project/text-analysis-twitter/.
scp -i $1 /c/etc/opt/text-analysis-project/text-analysis-twitter/twitter_sentiment_training_data.train ec2-user@$2:/etc/opt/text-analysis-project/text-analysis-twitter/.
scp -i $1 ./wildfly.service ec2-user@$2:~/.
scp -i $1 ./etc-default-wildfly ec2-user@$2:~/.
scp -i $1 ./wildfly-launch.sh ec2-user@$2:~/.
scp -i $1 ./standalone.conf ec2-user@$2:~/.

echo "-----ssh chown mv-----"
ssh -i $1 -T ec2-user@$2 "sudo chown root:root ~/wildfly.service"
ssh -i $1 -T ec2-user@$2 "sudo mv ~/wildfly.service /etc/systemd/system/."
echo "-----ssh chown mv-----"
ssh -i $1 -T ec2-user@$2 "sudo chown root:root ~/etc-default-wildfly"
ssh -i $1 -T ec2-user@$2 "sudo mv ~/etc-default-wildfly /etc/default/wildfly"
echo "-----ssh chown mv-----"
ssh -i $1 -T ec2-user@$2 "sudo chown jboss:jboss ~/wildfly-launch.sh"
ssh -i $1 -T ec2-user@$2 "sudo mv ~/wildfly-launch.sh /opt/jboss/wildfly/bin/launch.sh"
ssh -i $1 -T ec2-user@$2 "sudo chmod 755 /opt/jboss/wildfly/bin/launch.sh"
echo "-----ssh chown mv-----"
ssh -i $1 -T ec2-user@$2 "sudo chown jboss:jboss ~/standalone.conf"
ssh -i $1 -T ec2-user@$2 "sudo mv /opt/jboss/wildfly/bin/standalone.conf /opt/jboss/wildfly/bin/standalone.conf.bak"
ssh -i $1 -T ec2-user@$2 "sudo mv ~/standalone.conf /opt/jboss/wildfly/bin/standalone.conf"
echo "-----systemctl wildfly-----"
ssh -i $1 -T ec2-user@$2 "sudo systemctl daemon-reload"
ssh -i $1 -T ec2-user@$2 "sudo systemctl start wildfly"
ssh -i $1 -T ec2-user@$2 "sudo systemctl enable wildfly"
echo "-----admin user wildfly-----"
ssh -i $1 -T ec2-user@$2 "sudo -u jboss /opt/jboss/wildfly/bin/add-user.sh admin 11admin00 --silent"