@echo off
SET install_dir=D:\slide\

REM start mongodb
SET mongodb_dir=C:\Program Files\MongoDB\Server\3.4\bin
cd /D "%mongodb_dir%"
START mongod
REM exit /b 0

cd /D "%install_dir%\bin"

SET glassfish_dir=C:\Program Files\glassfish-4.1.1\glassfish\bin
CALL "%glassfish_dir%\asadmin.bat" start-domain
CALL "%glassfish_dir%\asadmin.bat" deploy --force=true "%install_dir%lib\VTBox.war"

REM asadmin stop-domain domain1