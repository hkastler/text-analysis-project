#!/bin/sh
sudo yum -y update -y 
sudo yum -y install java-1.8.0
sudo groupadd jboss
sudo useradd jboss -g jboss
sudo usermod -a -G jboss ec2-user
curl -O https://download.jboss.org/wildfly/15.0.0.Final/wildfly-15.0.0.Final.tar.gz && tar xf wildfly-15.0.0.Final.tar.gz 
sudo mkdir -p /opt/jboss/wildfly
sudo mv ./wildfly-15.0.0.Final /opt/jboss/wildfly
sudo rm wildfly-15.0.0.Final.tar.gz
sudo chown -R jboss:jboss /opt/jboss/wildfly
sudo chmod -R g+rw /opt/jboss/wildfly
sudo mkdir -p /etc/opt/text-analysis-project/text-analysis-twitter/
sudo chown -R jboss:jboss /etc/opt/text-analysis-project/text-analysis-twitter/
sudo chmod g+rwx  /etc/opt/text-analysis-project/text-analysis-twitter/
sudo amazon-linux-extras -y install nginx1.12