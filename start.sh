#! /bin/bash

jarName= $1

RUNNANE=`java -jar ${jarName}`

runCheck=`ps -ef | grep -v grep | grep "${RUNNAME}" | wc -l`
if [ "${runCheck}" -ge 1 ]
then
    bash ./stop.sh
fi
nohup $RUNNANE &
runCheck=`ps -ef | grep -v grep | grep "${RUNNAME}" | wc -l`
echo "running process count = [${runCheck}]"