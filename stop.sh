#! /bin/bash

appName=$1

KILLNAME=`java -jar ${}`
kill_process(){
    ps_var=$(ps -ef |grep -v grep | grep $1)
    if [ "$ps_var" = "" ]
    then
        return 0
    fi
    set $ps_var
    echo "`date` "
    echo " killing process"
    echo " $ps_var "
    ps ax | grep "${KILLNAME}" | grep -v grep | awk '{print $1}' | xargs kill
    return 0
}

echo "Stop"
ps -ef | grep "${KILLNAME}" | grep -v grep
runCheck=`ps -ef | grep "${KILLNAME}" | grep -v grep | wc -l`
echo "running process count = [${runCheck}]"
if [ $runCheck -ge 1 ]
then
kill_process ${KILLNAME}
fi