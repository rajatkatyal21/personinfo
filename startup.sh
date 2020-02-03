#!/usr/bin/env bash
export username=admin
export password=password
export port=8000
mvn clean package spring-boot:run

#if tests needs to be skipped
#mvn clean package -Dmaven.test.skip=true spring-boot:run