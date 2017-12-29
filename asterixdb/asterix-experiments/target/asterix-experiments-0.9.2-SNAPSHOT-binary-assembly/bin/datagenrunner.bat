@REM ----------------------------------------------------------------------------
@REM  Copyright 2001-2006 The Apache Software Foundation.
@REM
@REM  Licensed under the Apache License, Version 2.0 (the "License");
@REM  you may not use this file except in compliance with the License.
@REM  You may obtain a copy of the License at
@REM
@REM       http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM  Unless required by applicable law or agreed to in writing, software
@REM  distributed under the License is distributed on an "AS IS" BASIS,
@REM  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM  See the License for the specific language governing permissions and
@REM  limitations under the License.
@REM ----------------------------------------------------------------------------
@REM
@REM   Copyright (c) 2001-2006 The Apache Software Foundation.  All rights
@REM   reserved.

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup
set REPO=


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\lib

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\hyracks-api-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-ipc-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-util-0.3.2-SNAPSHOT.jar;"%REPO%"\jackson-databind-2.8.4.jar;"%REPO%"\commons-collections4-4.1.jar;"%REPO%"\hyracks-control-cc-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-http-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-control-common-0.3.2-SNAPSHOT.jar;"%REPO%"\netty-all-4.1.6.Final.jar;"%REPO%"\ini4j-0.5.4.jar;"%REPO%"\asterix-common-0.9.2-SNAPSHOT.jar;"%REPO%"\algebricks-common-0.3.2-SNAPSHOT.jar;"%REPO%"\algebricks-core-0.3.2-SNAPSHOT.jar;"%REPO%"\algebricks-data-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-dataflow-common-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-dataflow-std-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-storage-am-lsm-common-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-storage-am-common-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-storage-common-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-storage-am-btree-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-storage-am-bloomfilter-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-storage-am-rtree-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-storage-am-lsm-btree-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-storage-am-lsm-invertedindex-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-storage-am-lsm-rtree-0.3.2-SNAPSHOT.jar;"%REPO%"\log4j-1.2.17.jar;"%REPO%"\hyracks-data-std-0.3.2-SNAPSHOT.jar;"%REPO%"\jackson-core-2.8.4.jar;"%REPO%"\jackson-annotations-2.8.4.jar;"%REPO%"\asterix-app-0.9.2-SNAPSHOT.jar;"%REPO%"\hyracks-control-nc-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-comm-0.3.2-SNAPSHOT.jar;"%REPO%"\algebricks-compiler-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-client-0.3.2-SNAPSHOT.jar;"%REPO%"\asterix-algebra-0.9.2-SNAPSHOT.jar;"%REPO%"\algebricks-rewriter-0.3.2-SNAPSHOT.jar;"%REPO%"\asterix-om-0.9.2-SNAPSHOT.jar;"%REPO%"\asterix-metadata-0.9.2-SNAPSHOT.jar;"%REPO%"\asterix-transactions-0.9.2-SNAPSHOT.jar;"%REPO%"\asterix-replication-0.9.2-SNAPSHOT.jar;"%REPO%"\asterix-external-data-0.9.2-SNAPSHOT.jar;"%REPO%"\hyracks-hdfs-core-0.3.2-SNAPSHOT.jar;"%REPO%"\hyracks-hdfs-2.x-0.3.2-SNAPSHOT.jar;"%REPO%"\asterix-hivecompat-0.9.2-SNAPSHOT.jar;"%REPO%"\commons-logging-api-1.0.4.jar;"%REPO%"\hive-shims-common-0.13.0.jar;"%REPO%"\hive-common-0.13.0.jar;"%REPO%"\rome-fetcher-1.7.4.jar;"%REPO%"\rome-1.7.4.jar;"%REPO%"\rome-utils-1.7.4.jar;"%REPO%"\jdom2-2.0.6.jar;"%REPO%"\hive-serde-0.13.0.jar;"%REPO%"\hive-shims-0.13.0.jar;"%REPO%"\hive-shims-0.20-0.13.0.jar;"%REPO%"\hive-shims-common-secure-0.13.0.jar;"%REPO%"\hive-shims-0.20S-0.13.0.jar;"%REPO%"\hive-shims-0.23-0.13.0.jar;"%REPO%"\libthrift-0.9.0.jar;"%REPO%"\core-io-1.3.2.jar;"%REPO%"\rxjava-1.1.8.jar;"%REPO%"\hadoop-mapreduce-client-core-2.2.0.jar;"%REPO%"\hadoop-yarn-common-2.2.0.jar;"%REPO%"\hadoop-yarn-api-2.2.0.jar;"%REPO%"\guice-3.0.jar;"%REPO%"\javax.inject-1.jar;"%REPO%"\aopalliance-1.0.jar;"%REPO%"\jersey-test-framework-grizzly2-1.9.jar;"%REPO%"\jersey-test-framework-core-1.9.jar;"%REPO%"\javax.servlet-api-3.0.1.jar;"%REPO%"\jersey-client-1.9.jar;"%REPO%"\jersey-grizzly2-1.9.jar;"%REPO%"\grizzly-http-2.1.2.jar;"%REPO%"\grizzly-framework-2.1.2.jar;"%REPO%"\gmbal-api-only-3.0.0-b023.jar;"%REPO%"\management-api-3.0.0-b012.jar;"%REPO%"\grizzly-http-server-2.1.2.jar;"%REPO%"\grizzly-rcm-2.1.2.jar;"%REPO%"\grizzly-http-servlet-2.1.2.jar;"%REPO%"\javax.servlet-3.1.jar;"%REPO%"\jersey-guice-1.9.jar;"%REPO%"\guice-servlet-3.0.jar;"%REPO%"\netty-3.6.2.Final.jar;"%REPO%"\xml-apis-1.4.01.jar;"%REPO%"\asterix-lang-aql-0.9.2-SNAPSHOT.jar;"%REPO%"\asterix-events-0.9.2-SNAPSHOT.jar;"%REPO%"\zookeeper-3.4.5.jar;"%REPO%"\jline-0.9.94.jar;"%REPO%"\netty-3.2.2.Final.jar;"%REPO%"\hyracks-net-0.3.2-SNAPSHOT.jar;"%REPO%"\algebricks-runtime-0.3.2-SNAPSHOT.jar;"%REPO%"\asterix-lang-common-0.9.2-SNAPSHOT.jar;"%REPO%"\asterix-runtime-0.9.2-SNAPSHOT.jar;"%REPO%"\asterix-fuzzyjoin-0.9.2-SNAPSHOT.jar;"%REPO%"\asterix-lang-sqlpp-0.9.2-SNAPSHOT.jar;"%REPO%"\commons-lang-2.5.jar;"%REPO%"\guava-18.0.jar;"%REPO%"\hadoop-common-2.2.0.jar;"%REPO%"\hadoop-annotations-2.2.0.jar;"%REPO%"\commons-cli-1.2.jar;"%REPO%"\commons-math-2.1.jar;"%REPO%"\xmlenc-0.52.jar;"%REPO%"\commons-net-3.1.jar;"%REPO%"\servlet-api-2.5.jar;"%REPO%"\jetty-6.1.26.jar;"%REPO%"\jetty-util-6.1.26.jar;"%REPO%"\jersey-core-1.9.jar;"%REPO%"\jersey-json-1.9.jar;"%REPO%"\jettison-1.1.jar;"%REPO%"\stax-api-1.0.1.jar;"%REPO%"\jaxb-impl-2.2.3-1.jar;"%REPO%"\jackson-jaxrs-1.8.3.jar;"%REPO%"\jackson-xc-1.8.3.jar;"%REPO%"\jersey-server-1.9.jar;"%REPO%"\asm-3.1.jar;"%REPO%"\jasper-compiler-5.5.23.jar;"%REPO%"\jasper-runtime-5.5.23.jar;"%REPO%"\jsp-api-2.1.jar;"%REPO%"\commons-el-1.0.jar;"%REPO%"\jets3t-0.6.1.jar;"%REPO%"\commons-configuration-1.6.jar;"%REPO%"\commons-collections-3.2.1.jar;"%REPO%"\commons-digester-1.8.jar;"%REPO%"\commons-beanutils-1.7.0.jar;"%REPO%"\commons-beanutils-core-1.8.0.jar;"%REPO%"\slf4j-log4j12-1.7.5.jar;"%REPO%"\jackson-core-asl-1.8.8.jar;"%REPO%"\jackson-mapper-asl-1.8.8.jar;"%REPO%"\avro-1.7.4.jar;"%REPO%"\paranamer-2.3.jar;"%REPO%"\snappy-java-1.0.4.1.jar;"%REPO%"\protobuf-java-2.5.0.jar;"%REPO%"\hadoop-auth-2.2.0.jar;"%REPO%"\jsch-0.1.42.jar;"%REPO%"\commons-compress-1.4.1.jar;"%REPO%"\xz-1.0.jar;"%REPO%"\asterix-active-0.9.2-SNAPSHOT.jar;"%REPO%"\hadoop-hdfs-2.2.0.jar;"%REPO%"\commons-daemon-1.0.13.jar;"%REPO%"\asterix-tools-0.9.2-SNAPSHOT-tests.jar;"%REPO%"\httpclient-4.5.2.jar;"%REPO%"\commons-logging-1.1.1.jar;"%REPO%"\commons-codec-1.9.jar;"%REPO%"\httpcore-4.4.5.jar;"%REPO%"\commons-io-2.5.jar;"%REPO%"\commons-httpclient-3.0.1.jar;"%REPO%"\junit-4.12.jar;"%REPO%"\hamcrest-core-1.3.jar;"%REPO%"\commons-lang3-3.5.jar;"%REPO%"\args4j-2.33.jar;"%REPO%"\jaxb-api-2.2.12.jar;"%REPO%"\sshj-0.13.0.jar;"%REPO%"\bcpkix-jdk15on-1.51.jar;"%REPO%"\jzlib-1.1.3.jar;"%REPO%"\slf4j-api-1.7.7.jar;"%REPO%"\bcprov-jdk15on-1.51.jar;"%REPO%"\asterix-experiments-0.9.2-SNAPSHOT.jar

set ENDORSED_DIR=
if NOT "%ENDORSED_DIR%" == "" set CLASSPATH="%BASEDIR%"\%ENDORSED_DIR%\*;%CLASSPATH%

if NOT "%CLASSPATH_PREFIX%" == "" set CLASSPATH=%CLASSPATH_PREFIX%;%CLASSPATH%

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS%  -classpath %CLASSPATH% -Dapp.name="datagenrunner" -Dapp.repo="%REPO%" -Dapp.home="%BASEDIR%" -Dbasedir="%BASEDIR%" org.apache.asterix.experiment.client.SocketTweetGeneratorDriver %CMD_LINE_ARGS%
if %ERRORLEVEL% NEQ 0 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=%ERRORLEVEL%

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@REM If error code is set to 1 then the endlocal was done already in :error.
if %ERROR_CODE% EQU 0 @endlocal


:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
