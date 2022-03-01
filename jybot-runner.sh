#!/bin/sh

echo "JY Bot 실행 스크립트 시작"

echo "실행중인 JY Bot 프로세스 탐색" 

processInfo=$(ps -ef | grep 'JYBot')
echo "프로세스 정보: ${processInfo}"

pid=$(echo ${processInfo} | cut -d " " -f2)
echo "프로세스 ID: ${pid}"

if [ -n "${pid}" ]
then 
	echo "실행중인 JY Bot 프로세스 존재"
	echo "실행중인 JY Bot 프로세스 종료"
	kill ${pid}
	sleep 5
	echo "실행중인 JY Bot 종료 완료"
else
	echo "실행중인 JY Bot 프로세스 없음"
fi

echo "JY Bot 실행"
nohup java -Xms1024m -Xmx2048m -jar JYBot-1.0-SNAPSHOT.jar --spring.profiles.active=prod 2>&1 &
sleep 10

newProcessInfo=$(ps -ef | grep 'JYBot')
echo "새로 시작된 프로세스 정보: ${newProcessInfo}"

newPid=$(echo ${newProcessInfo} | cut -d " " -f2)
echo "새로 시작된 프로세스 ID: ${newPid}"
