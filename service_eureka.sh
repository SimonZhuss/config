#!/bin/sh

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# -----------------------------------------------------------------------------
# Start Script for the CATALINA Server
# -----------------------------------------------------------------------------
# Better OS/400 detection: see Bugzilla 31132
## java env
#export JAVA_HOME=/usr/java/jdk1.7.0_79
#export JRE_HOME=${JAVA_HOME}/jre

J_HOME="/usr/java/jdk1.8.0_91/bin/java"
SRC_JAR_HOME="/data/www"
SRC_JAR_NAME="eureka-server"
JAR_HOME="/home/eureka-server"
JAR_NAME="eureka-server"

## step 1 stop

echo "step 1 stop "

echo "kill -15 ${JAR_NAME}"

ps -ef | grep java | grep ${JAR_HOME} | awk '{print $2}' | xargs kill -15

sleep 5s

echo "top success"
## step remove old jar 2
## echo "step 2 remove old jar"
## rm -rf ${JAR_HOME}/${JAR_NAME}.jar

## step 3 copy new jar
## echo "step 3 copy new jar "
## cp ${SRC_JAR_HOME}/${SRC_JAR_NAME}.jar  ${JAR_HOME}/${JAR_NAME}.jar
## step 4 start jar
echo "step 4 start jar"
nohup ${J_HOME} -jar ${JAR_HOME}/${JAR_NAME}.jar --server.port=8761 > ${JAR_HOME}/nohup.out 2>&1 &
