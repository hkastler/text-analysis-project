#!/bin/sh
ssh -i $1 ec2-user@$2 'bash -s' < ./wildfly-start.sh