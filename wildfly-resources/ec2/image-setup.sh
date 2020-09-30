#!/bin/sh
WILDFLY_VERSION=20.0.0.Final
JAVA_VERSION=java-11-amazon-corretto
NGINX_VERSION=nginx1.12
echo "------update------"
sudo yum -y update
echo "------upgrade------"
sudo yum -y upgrade
echo "------java------"
sudo yum -y install $JAVA_VERSION
echo "------user jboss------"
sudo groupadd jboss
sudo useradd jboss -g jboss
sudo usermod -a -G jboss ec2-user
echo "------download $WILDFLY_VERSION------"
curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz && tar xf wildfly-$WILDFLY_VERSION.tar.gz 
echo "------mkdir jboss------"
sudo mkdir -p /opt/jboss
echo "------mv wildfly------"
sudo mv ./wildfly-$WILDFLY_VERSION /opt/jboss/.
sudo ln -s /opt/jboss/wildfly-$WILDFLY_VERSION/ /opt/jboss/wildfly
sudo rm wildfly-$WILDFLY_VERSION.tar.gz
echo "------chown/chmod wildfly-----"
sudo chown -R jboss:jboss /opt/jboss/wildfly
sudo chmod -R g+rw /opt/jboss/wildfly
echo "------mkdir chown/chmod text-analysis-twitter-----"
sudo mkdir -p /etc/opt/text-analysis-project/text-analysis-twitter/
sudo chown -R jboss:jboss /etc/opt/text-analysis-project/text-analysis-twitter/
sudo chmod g+rwx /etc/opt/text-analysis-project/text-analysis-twitter/
echo "------nginx------"
sudo amazon-linux-extras install -y $NGINX_VERSION

sudo rm -rf /var/cache/yum