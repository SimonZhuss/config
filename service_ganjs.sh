#/bin/bash
source /etc/profile
if [ $# -lt 1 ];
then
  echo "USAGE: $0 classname opts"
  exit 1
fi

INSTANCE=""
#-----------------------------需要修改的----------------------------------#
#服务名称
#SERVICE_NAME="qjs_customer_service"
#jar地址
#SERVICE_JAR=customer-web-1.0.0-SNAPSHOT.jar
#eureka地址
#EUREKA_ADDR="192.168.227.215:8761"
#服务端口
#INSTANCE_PORT="8080"
#-----------------------------需要修改的----------------------------------#
#-----------------------------需要修改的----------------------------------#
#服务名称
SERVICE_NAME=$2
#jar地址
SERVICE_JAR=$3
#eureka地址
EUREKA_ADDR=$4
#服务端口
INSTANCE_PORT=$5
#-----------------------------需要修改的----------------------------------#

#设定关键字，用来查找服务，stop时关闭服务
echo $SERVICE_JAR
INSTANCE_IP=`ifconfig eth0 | grep "inet addr" | awk -F"[ :]+" '{print $4}'`
INSTANCE_ADDR="$INSTANCE_IP:$INSTANCE_PORT"
KEYWORD=$SERVICE_JAR

# 进程号存放文件,和xxx-xxx.jar同目录
PID_FILE="$SERVICE_NAME.pid"
# EUREKA启动实例ID存放文件,和xxx-xxx.jar同目录
INSTANCE_FILE="instance.id"
BASE_DIR=$(dirname $0)
 
#如果没有设置JAVA_HOME环境变量，此处指定JAVA_HOME
if [ -z "$JAVA_HOME" ]; then
  JAVA_HOME=/opt/jdk1.8.0_131
fi
echo JAVA_HOME
echo ${JAVA_HOME} 
JAVA_OPTS="-server -Xms512M -Xmx512M -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=128M -XX:+UseFastAccessorMethods -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
JAVA="$JAVA_HOME/bin/java"

#nohup $JAVA $JAVA_OPTS $KEYWORD >> server_nohup.log 2>&1 & echo $! > $pidfile
 
# Returns 0 if the process with PID $1 is running.
function checkProcessIsRunning {
   local pid="$1"
   ps -ef | grep java | grep $pid | grep "$KEYWORD" | grep -q --binary -F java
   if [ $? -ne 0 ]; then return 1; fi
   return 0;
}
 
# Returns 0 when the service is running and sets the variable $pid to the PID.
function getServicePID {
   if [ ! -f $PID_FILE ]; then return 1; fi
   pid="$(<$PID_FILE)"
   checkProcessIsRunning $pid || return 1
   return 0; }

#重启服务时设置instanceId
function setInstanceId(){
	if [ ! -f $INSTANCE_FILE ]; then
       INSTANCE=""
	else
	   INSTANCE="$(<$INSTANCE_FILE)"
	 fi
}   
function startServiceProcess(){
   touch $PID_FILE
   rm -rf nohup.log
   rm -rf $INSTANCE_FILE
   if [ ! -z $INSTANCE ]; then
	 echo $INSTANCE > $INSTANCE_FILE
	 /usr/bin/nohup $JAVA $JAVA_OPTS -jar $SERVICE_JAR $KEYWORD --spring.profiles.active=peer$INSTANCE >> nohup.log 2>&1 & echo $! > $PID_FILE
   else
	 /usr/bin/nohup $JAVA $JAVA_OPTS -jar $SERVICE_JAR $KEYWORD >> nohup.log 2>&1 & echo $! > $PID_FILE
   fi
   
   sleep 0.1
   pid="$(<$PID_FILE)"
   if checkProcessIsRunning $pid; then :; else
      echo "$SERVICE_NAME start failed, see nohup.log."
      return 1
   fi
   return 0;
}
 
function stopServiceProcess {
   STOP_DATE=`date +%Y%m%d%H%M%S`
   kill $pid || return 1
   for ((i=0; i<10; i++)); do
      checkProcessIsRunning $pid
      if [ $? -ne 0 ]; then
         rm -f $PID_FILE
         return 0
         fi
      sleep 1
      done
   echo "\n$SERVICE_NAME did not terminate within 10 seconds, sending SIGKILL..."
   curl -X DELETE http://$EUREKA_ADDR/eureka/apps/$SERVICE_NAME/$INSTANCE_ADDR
   kill -s KILL $pid || return 1
   local killWaitTime=15
   for ((i=0; i<10; i++)); do
      checkProcessIsRunning $pid
      if [ $? -ne 0 ]; then
         rm -f $PID_FILE
         return 0
         fi
      sleep 1
      done
   echo "Error: $SERVICE_NAME could not be stopped within 10 + 10 seconds!"
   return 1;
}
 
function startService(){
   getServicePID
   if [ $? -eq 0 ]; then echo "$SERVICE_NAME is already running"; RETVAL=0; return 0; fi
   echo -n "Starting $SERVICE_NAME..."
   startServiceProcess
   if [ $? -ne 0 ]; then RETVAL=1; echo "failed"; return 1; fi
   COUNT=0
   while [ $COUNT -lt 1 ]; do
    for (( i=0;  i<60;  i=i+1 )) do
        STR=`grep "Started Application" nohup.log`
        if [ ! -z "$STR" ]; then
            echo "PID=$pid"
            echo "Server start OK in $i seconds."
            break;
        fi
       echo -e ".\c"
       sleep 1
   done
   break
    done
echo "OK!"
#START_PIDS=`ps  --no-heading -C java -f --width 1000 | grep "$DEPLOY_HOME" |awk '{print $2}'`
#echo "PID: $START_PIDS"
#   echo "started PID=$pid"
   RETVAL=0
   return 0;
}
 
function stopService {
   getServicePID
   if [ $? -ne 0 ]; then echo -n "$SERVICE_NAME is not running"; RETVAL=0; echo ""; return 0; fi
   echo "Stopping $SERVICE_NAME... "
   stopServiceProcess
   if [ $? -ne 0 ]; then RETVAL=1; echo "failed"; return 1; fi
   echo "stopped PID=$pid"
   RETVAL=0
   return 0;
}
 
function checkServiceStatus {
   echo -n "Checking for $SERVICE_NAME: "
   if getServicePID; then
   echo "running PID=$pid"
   RETVAL=0
   else
   echo "stopped"
   RETVAL=3
   fi
   return 0;
}
 
function main {
   RETVAL=0
   case "$1" in
      start)
         startService
         ;;
      stop)
         stopService
         ;;
      restart)
         stopService && setInstanceId && startService
         ;;
      status)
         checkServiceStatus
         ;;
      *)
         echo "Usage: $0 {start|stop|restart|status}"
         exit 1
         ;;
      esac
   exit $RETVAL
}
 
main $1
