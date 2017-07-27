#!/bin/bash

if [ $(id -u) -ne 0 ];then
    echo "only root can execute"
	exit 1
fi

cd $(dirname $0)
case $1 in 
	start) 
		./start.sh
        ;; 
    stop) 
		./stop.sh         
		;;
	debug)
		./start.sh debug
		;;
	restart)
		./restart.sh
		;;
	dump)
		./dump.sh
		;; 
	status)
		./status.sh
		;;
    *) 
    echo "Usage: $0 start|stop|debug|restart|dump|status" 
    ;; 
esac
