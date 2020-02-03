#!/usr/bin/env bash
export username=admin
export password=password
mvn clean package spring-boot:run

#if tests needs to be skipped
#mvn clean package -Dmaven.test.skip=true spring-boot:run