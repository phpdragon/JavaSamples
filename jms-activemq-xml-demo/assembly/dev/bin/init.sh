#!/bin/bash

if [ $(id -u) -ne 0 ];then
    echo "only root can execute"
	exit 1
fi

cd $(dirname $0)

typeset BIN_DIR DEPLOY_DIR APP_CONFIG_DIR COMMON_JARS_DIR LIB_DIR
typeset PIDS SERVER_NAME LOGS_FILE STDOUT_FILE
typeset JAVA_OPTS JAVA_MEM_OPTS JAVA_DEBUG_OPTS JAVA_JMX_OPTS
typeset COMMON_CONFIG_DIR LOGS_DIR DUMP_DIR DATE_DIR
typeset -i START_TIMEOUT STOP_TIMEOUT

export BIN_DIR=$(pwd)
export DEPLOY_DIR=$(dirname $BIN_DIR)
export APP_CONFIG_DIR=$DEPLOY_DIR/conf
export LIB_DIR=$DEPLOY_DIR/lib
export LOGS_DIR=$DEPLOY_DIR/logs
export DUMP_DIR=$DEPLOY_DIR/logs/dump
export DATE_DIR=$DUMP_DIR/"$(date +%Y%m%d%H%M%S)"

typeset APP_CONF_FILE=$APP_CONFIG_DIR/app.properties

export LOGS_FILE=`sed '/application.log4j.file/!d;s/.*=//' $APP_CONF_FILE | tr -d '\r'`
export MAIN_CLASS=`sed '/application.main/!d;s/.*=//' $APP_CONF_FILE | tr -d '\r'`

SERVER_NAME=$(sed '/application.name/!d;s/.*=//' $APP_CONF_FILE| tr -d '\r')
if [ -z "$SERVER_NAME" ]; then
	SERVER_NAME=$(hostname)
fi
export SERVER_NAME=$SERVER_NAME

function init_stdout_file(){
    if [ -n "$LOGS_FILE" ]; then
        LOGS_DIR=$(dirname $LOGS_FILE)
    else
        LOGS_DIR=$DEPLOY_DIR/logs
    fi

    if [ ! -d $LOGS_DIR ]; then
        mkdir $LOGS_DIR
    fi

    STDOUT_FILE=$LOGS_DIR/stdout.log
}

function init_dump_dir(){
	if [ ! -d $DUMP_DIR ]; then
		mkdir $DUMP_DIR
	fi

	if [ ! -d $DATE_DIR ]; then
		mkdir $DATE_DIR
	fi
}

function set_pids(){
    PIDS=$(ps -ef | grep java | grep "$APP_CONFIG_DIR" | grep -v grep | awk '{print $2}')
}

function check_is_started(){
    set_pids

    if [ -n "$PIDS" ]; then
        echo "ERROR: The $SERVER_NAME already started!"
        echo "PID: $PIDS"
        exit 1
    fi
}

function check_server_status(){
    set_pids

    if [ -z "$PIDS" ]; then
        echo "The $SERVER_NAME stopped!"
        exit 0
    else
        echo "The $SERVER_NAME running!"
        exit 0
    fi
}

function check_startup_status(){
    typeset -i count=0
    typeset -i time_out_count=0

    if [ $START_TIMEOUT -lt 0 ]; then
        START_TIMEOUT = 10
    fi

    while [ $count -lt 1 ]; do
        if [ $time_out_count -gt $START_TIMEOUT ]; then
            echo -e "\n start time out\n"
            exit 1
        fi

        echo -e ".\c"
        sleep 1

        count=$(ps -f | grep java | grep "$DEPLOY_DIR" | grep -v grep | awk '{print $2}' | wc -l)

		if [ $count -gt 0 ]; then
			break
		fi

		((time_out_count++))
	done

	echo "OK!"

	set_pids

	echo "PID: $PIDS"
	echo "STDOUT: $STDOUT_FILE"
}

function check_is_not_started(){
    set_pids

    if [ -z "$PIDS" ]; then
        echo "ERROR: The $SERVER_NAME does not started!"
        exit 1
    fi
}

function check_is_stopped(){
    set_pids

    if [ $STOP_TIMEOUT -lt 0 ]; then
        STOP_TIMEOUT = 10
    fi

	typeset -i count=0
    typeset -i time_out_count=0
	while [ $count -lt 1 ]; do
        if [ $time_out_count -gt $STOP_TIMEOUT ]; then
            echo -e "\n start time out\n"
            exit 1
        fi

    	echo -e ".\c"
    	sleep 1
    	count=1
    	for PID in $PIDS ; do
			PID_EXIST=$(ps -ef | grep $PID |grep -v grep)
			if [ -n "$PID_EXIST" ]; then
            	count=0
            	break
        	fi
    	done

    	((time_out_count++))
	done

	echo "OK!"
	echo "PID: $PIDS"

	cd $BIN_DIR
}

function stop_server(){
    set_pids

	echo -e "Stopping the $SERVER_NAME ...\c"

	for PID in $PIDS ; do
    	kill $PID > /dev/null 2>&1
	done
}

function start_server(){
    typeset LIB_JARS CLASSPATH

    # set lib
    LIB_JARS=$(ls $LIB_DIR | grep .jar | grep -v grep | awk '{print "'$LIB_DIR'/"$0}' | tr "\n" ":")

    #set out jars dir
    if [ -d $COMMON_JARS_DIR ];then
		COMMON_JARS_DIR=$(ls $COMMON_JARS_DIR | grep .jar| grep -v grep | awk '{print "'$COMMON_JARS_DIR'/"$0}' | tr "\n" ":")
	fi

    if [ -n "$JAVA_OPTS" ]; then
        JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
    fi

	if [ -n "$JAVA_MEM_OPTS" ]; then
        JAVA_MEM_OPTS=" -server -Xms128m -Xmx256m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
    fi

    if [ "$1" = "debug" ]; then
        JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
    fi

    if [ "$1" = "jmx" ]; then
        JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
    fi

    CLASSPATH=$APP_CONFIG_DIR:$LIB_JARS
    if [ -n "$COMMON_CONFIG_DIR" ]; then
        CLASSPATH="$CLASSPATH:$COMMON_CONFIG_DIR"
    fi

    if [ -n "$COMMON_JARS_DIR" ]; then
        CLASSPATH="$CLASSPATH:$COMMON_JARS_DIR"
    fi

    #start server
    cd $DEPLOY_DIR

    echo -e "Starting the $SERVER_NAME ...\c"

    nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -classpath $CLASSPATH $MAIN_CLASS > $STDOUT_FILE 2>&1 &

    cd $BIN_DIR
}

function dump(){
	echo -e "Dumping the $SERVER_NAME ...\c"
	for PID in $PIDS ; do
		jstack $PID > $DATE_DIR/jstack-$PID.dump 2>&1
		echo -e ".\c"
		jinfo $PID > $DATE_DIR/jinfo-$PID.dump 2>&1
		echo -e ".\c"
		jstat -gcutil $PID > $DATE_DIR/jstat-gcutil-$PID.dump 2>&1
		echo -e ".\c"
		jstat -gccapacity $PID > $DATE_DIR/jstat-gccapacity-$PID.dump 2>&1
		echo -e ".\c"
		jmap $PID > $DATE_DIR/jmap-$PID.dump 2>&1
		echo -e ".\c"
		jmap -heap $PID > $DATE_DIR/jmap-heap-$PID.dump 2>&1
		echo -e ".\c"
		jmap -histo $PID > $DATE_DIR/jmap-histo-$PID.dump 2>&1
		echo -e ".\c"
		if [ -r /usr/sbin/lsof ]; then
		/usr/sbin/lsof -p $PID > $DATE_DIR/lsof-$PID.dump
		echo -e ".\c"
		fi
	done

	if [ -r /bin/netstat ]; then
		/bin/netstat -an > $DATE_DIR/netstat.dump 2>&1
		echo -e ".\c"
	fi
	if [ -r /usr/bin/iostat ]; then
		/usr/bin/iostat > $DATE_DIR/iostat.dump 2>&1
		echo -e ".\c"
	fi
	if [ -r /usr/bin/mpstat ]; then
		/usr/bin/mpstat > $DATE_DIR/mpstat.dump 2>&1
		echo -e ".\c"
	fi
	if [ -r /usr/bin/vmstat ]; then
		/usr/bin/vmstat > $DATE_DIR/vmstat.dump 2>&1
		echo -e ".\c"
	fi
	if [ -r /usr/bin/free ]; then
		/usr/bin/free -t > $DATE_DIR/free.dump 2>&1
		echo -e ".\c"
	fi
	if [ -r /usr/bin/sar ]; then
		/usr/bin/sar > $DATE_DIR/sar.dump 2>&1
		echo -e ".\c"
	fi
	if [ -r /usr/bin/uptime ]; then
		/usr/bin/uptime > $DATE_DIR/uptime.dump 2>&1
		echo -e ".\c"
	fi

	echo "OK!"
	echo "DUMP: $DATE_DIR"
}

function start_dump(){
    check_is_not_started
	init_stdout_file
	init_dump_dir
	dump
}

function startup(){
	check_is_started
	init_stdout_file
	start_server $1
	check_startup_status
}

function shutdown(){
    check_is_not_started
	stop_server
	check_is_stopped
}