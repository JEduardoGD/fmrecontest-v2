#!/bin/bash

#declare modules to compile and push
MODULES="fmrecontest-evaluate fmrecontest-api fmrecontest-recibir"

#taking vars.env to set environments vars
export $(cat vars.env | grep -v '#' | awk '/=/ {print $1}')

#setting java and maven paths on path
PATH="$PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin"

#taking version from pom
VERSION=$(xml2 < pom.xml  | grep /project/version= | sed 's/.*=//')

#say your maven version
mvn -v

#say the environment vars
echo $ENVIRONMENT

#say the project version
echo $VERSION

#fetch every module
for module in $MODULES; do
	echo "BUILD ${module}..."

	#the image name will be username/module:version
	IMAGE_NAME="${DOCKERHUB_USERNAME}/${module}:${ENVIRONMENT}"

	#check if the image exists and store result in RES var
	RES=`docker images -q "$IMAGE_NAME" 2> /dev/null`

	#if docker image exists delte them
	if [[ $RES != "" ]]; then
		echo "delete ${RES}"
		docker rmi -f ${RES}
	fi

	#create docker image
	docker build -t ${IMAGE_NAME} ${module}

	#push docker image
	docker push ${IMAGE_NAME}
done
