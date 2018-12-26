#!/bin/sh
sh ec2-wildfly-full.sh $1 $2 && 
cd nginx
sh nginx-full.sh $1 $2