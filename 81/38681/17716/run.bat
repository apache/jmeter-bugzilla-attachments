@echo off

set JAVA_HOME=D:\jmeter\Java\j2sdk1.4.2_07
set JM_HOME=D:\jmeter\jakarta-jmeter-2.1.1
set JM_TESTS=D:\jmeter\tests\test


set HEAP=-Xms256m -Xmx256m
set NEW=-XX:NewSize=128m -XX:MaxNewSize=128m
set SURVIVOR=-XX:SurvivorRatio=8 -XX:TargetSurvivorRatio=50%
set TENURING=-XX:MaxTenuringThreshold=2
set EVACUATION=-XX:MaxLiveObjectEvacuationRatio=20%
set RMIGC=-Dsun.rmi.dgc.client.gcInterval=600000 -Dsun.rmi.dgc.server.gcInterval=600000
set PERM=-XX:PermSize=64m -XX:MaxPermSize=64m
set DEBUG=-verbose:gc -XX:+PrintTenuringDistribution

set ARGS=%HEAP% %NEW% %SURVIVOR% %TENURING% %EVACUATION% %RMIGC% %PERM% %DEBUG% %DDRAW%


cd %JM_TESTS%
d:
%JAVA_HOME%\bin\javaw.exe %ARGS% -jar %JM_HOME%\bin\ApacheJMeter.jar -n -t script1.jmx 
