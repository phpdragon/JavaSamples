#!/bin/bash

if [ $(id -u) -ne 0 ];then
    echo "only root can execute"
	exit 1
fi

cd $(dirname $0)
./stop.sh
./start.sh
