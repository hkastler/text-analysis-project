#!/bin/sh
#$1=keyfile $2=ipaddr
#run as sh ec2-wildfly-full.sh <path-to-pem> <instance-ip>
ssh -o "StrictHostKeyChecking no" -i $1 ec2-user@$2 'bash -s' < ./image-setup.sh && sh ./scp-configs.sh $1 $2