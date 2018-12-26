#!/bin/sh
#$1=keyfile
#$2=ipaddr
scp -i $1 ./hkstlr.com.conf ec2-user@$2:~/.
