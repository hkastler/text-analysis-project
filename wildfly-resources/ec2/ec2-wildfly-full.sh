#!/bin/sh
#$1=keyfile $2=ipaddr
#run as sh ec2-wildfly.sh <path-to-pem> <instance-ip>
ssh -i $1 ec2-user@$2 'bash -s' < ./image-setup.sh && sh ./scp-configs.sh $1 $2 && sh ./ssh-wildfly-start.sh $1 $2