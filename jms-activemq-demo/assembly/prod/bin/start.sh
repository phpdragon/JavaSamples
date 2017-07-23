#!/bin/bash
cd $(dirname $0)
source ./init.sh

COMMON_CONFIG_DIR="/home/project/java_server_config"
COMMON_JARS_DIR='/opt/jars/1.8'

START_TIMEOUT=15

#set java options
JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "

JAVA_MEM_OPTS=" -server -Xms256m -Xmx512m -XX:SurvivorRatio=2 -XX:+UseParallelGC "

JAVA_DEBUG_OPTS=""

JAVA_JMX_OPTS=""

startup $1