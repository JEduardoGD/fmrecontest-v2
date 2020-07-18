#!/bin/bash

MAVEN_HOME=/home/geduardo/sft/maven/apache-maven-3.6.1
JAVA_HOME=/home/geduardo/sft/java/jdk1.8.0_221

export PATH=$PATH:$MAVEN_HOME/bin:$JAVA_HOME/bin

mvn -v 

mvn clean package -Dmaven.test.skip=true