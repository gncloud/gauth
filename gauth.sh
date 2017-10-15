#!/bin/bash

# joonwoo write

# oracle java install
#JAVA_HOME=/usr/jdk
#JAVA_RUN_FILE=$JAVA_HOME/bin/java

# openJdk install
JAVA_RUN_FILE=/bin/java

SERVICE_NAME=gauth
SERVICE_HOME=/app/gauth
PATH_TO_JAR=$SERVICE_HOME/target/gauth.war
PID_PATH_NAME=/tmp/$SERVICE_NAME-pid

LOGBACK_FILE=$SERVICE_HOME/conf/logback.xml
CONFIG_FILE=$SERVICE_HOME/conf/application.properties

case $1 in
    start)
        echo "Starting $SERVICE_NAME ..."
        if [ ! -f $PID_PATH_NAME ]; then
	    if [ -f $LOGBACK_FILE ]; then
		LOGBACK_FILE=--logging.pattern.file=$LOGBACK_FILE
	    else
		LOGBACK_FILE=""
	    fi
	    if [ -f $CONFIG_FILE ]; then
		CONFIG_FILE=--spring.config.location=$CONFIG_FILE
	    else
		CONFIG_FILE=""
	    fi
            echo "SERVICE RUN COMMAND : $JAVA_RUN_FILE -jar $PATH_TO_JAR $LOGBACK_FILE $CONFIG_FILE"
	    nohup $JAVA_RUN_FILE -jar $PATH_TO_JAR $LOGBACK_FILE $CONFIG_FILE  2> errors.txt < /dev/null & PID=$!
	    echo $PID > $PID_PATH_NAME
            echo $! > $PID_PATH_NAME
	    $(pwd)/$SERVICE_NAME status
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is already running ... PID[$(cat $PID_PATH_NAME)]"
        fi
    ;;
    stop)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stoping ..."
            kill $PID;
            echo "$SERVICE_NAME stopped ..."
            rm $PID_PATH_NAME
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
	echo "$SERVICE_NAME is Restarting...."
	$(pwd)/$SERVICE_NAME stop
	sleep 0.5
	$(pwd)/$SERVICE_NAME start
    ;;
    status)
	if [ -f $PID_PATH_NAME ]; then
	    echo "$SERVICE_NAME is Status ..."
	    PID=$(cat $PID_PATH_NAME)
	    ps $PID
	else
	   echo "$SERVICE_NAME is not running ..."
	fi
    ;;
    *)
	echo "Usage: service $SERVICE_NAME {start|stop|restart|status}"
	exit 1
esac
exit 0