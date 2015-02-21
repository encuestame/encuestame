#!/bin/bash

mvn -pl enme-utils,enme-persistence/enme-model,enme-persistence/enme-dao,enme-core,enme-config,enme-business,enme-oauth,enme-social,enme-mvc/controllers,enme-mvc/json/json-api1,enme-mvc/json/json-api2,enme-mvc/websocket,enme-war/enme-config/,enme-war/web-app/jetty-webapp clean install -B -o -Dmaven.test.skip=true
