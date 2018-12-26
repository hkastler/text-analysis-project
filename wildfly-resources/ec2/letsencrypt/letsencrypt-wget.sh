#!/bin/sh
sudo service nginx stop
cd /tmp
wget -O epel.rpm –nv \
https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
sudo yum install -y ./epel.rpm

sudo yum -y install python2-certbot-apache.noarch

sudo service nginx stop

sudo certbot -i ngnix -a manual \
--preferred-challenges dns -d www.hkstlr.com

sudo service nginx start