#!/bin/sh
#$1=keyfile $2=ipaddr $3=shell
#run as sh ec2-wildfly.sh <path-to-pem> <instance-ip>
ssh -i $1 ec2-user@$2 'bash -s' < ./letsencrypt.sh