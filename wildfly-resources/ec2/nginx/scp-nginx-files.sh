#!/bin/sh
#$1=keyfile $2=ipaddr
scp -i $1 ./nginx.conf ec2-user@$2:~/. &&
scp -i $1 ./jboss.conf ec2-user@$2:~/. &&
ssh -i $1 ec2-user@$2 'bash -s' < ./nginx-install.sh
