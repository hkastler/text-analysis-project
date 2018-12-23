#!/bin/sh
#$1=keyfile
#$2=ipaddr
scp -i $1 /c/etc/opt/text-analysis-project/text-analysis-twitter/twitter_sentiment_app_properties ec2-user@$2:/etc/opt/text-analysis-project/text-analysis-twitter/.
scp -i $1 /c/etc/opt/text-analysis-project/text-analysis-twitter/twitter_sentiment_training_data.train ec2-user@$2:/etc/opt/text-analysis-project/text-analysis-twitter/.
scp -i $1 ../../text-analysis-webapp/target/text-analysis-webapp/index.html ec2-user@$2:/opt/jboss/wildfly/wildfly-15.0.0.Final/welcome-content/
