#!/bin/sh
echo "------update------"
sudo yum -y update
echo "------upgrade------"
sudo yum -y upgrade
echo "------java------"
sudo yum -y install java-1.8.0
echo "------user jboss------"
sudo groupadd jboss
sudo useradd jboss -g jboss
sudo usermod -a -G jboss ec2-user
echo "------download wildfly-15------"
curl -O https://download.jboss.org/wildfly/15.0.0.Final/wildfly-15.0.0.Final.tar.gz && tar xf wildfly-15.0.0.Final.tar.gz 
echo "------mkdir jboss------"
sudo mkdir -p /opt/jboss
echo "------mv wildfly------"
sudo mv ./wildfly-15.0.0.Final /opt/jboss/wildfly
sudo rm wildfly-15.0.0.Final.tar.gz
echo "------chown/chmod wildfly-----"
sudo chown -R jboss:jboss /opt/jboss/wildfly
sudo chmod -R g+rw /opt/jboss/wildfly
echo "------mkdir chown/chmod text-analysis-twitter-----"
sudo mkdir -p /etc/opt/text-analysis-project/text-analysis-twitter/
sudo chown -R jboss:jboss /etc/opt/text-analysis-project/text-analysis-twitter/
sudo chmod g+rwx /etc/opt/text-analysis-project/text-analysis-twitter/
echo "------nginx------"
sudo amazon-linux-extras install -y nginx1.12