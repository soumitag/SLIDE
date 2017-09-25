@echo off

SET /P java_dir=Please Provide Path to Java Installation Directory:
SET /P python_dir=Please Provide Path to Python Installation Directory:
SET /P glassfish_dir=Please Provide Path to the "bin" Directory in Glassfish Server Installation:
SET /P mongodb_dir=Please Provide Path to the "bin" Directory in MongoDB Installation:

SET install_dir=install-dir::%~dp0
SET temp_dir=temp-dir::%~dp0temp

@echo %install_dir%> config/slide-config.txt

SET java_string=java-dir::%java_dir%
@echo %java_string%>> config/slide-config.txt

SET glassfish_dir=glassfish-dir::%glassfish_dir%
@echo %glassfish_dir%>> config/slide-config.txt

SET mongodb_dir=mongodb-dir::%mongodb_dir%
@echo %mongodb_dir%>> config/slide-config.txt

SET python_dir=python-dir::%python_dir%
@echo %python_dir%>> config/slide-config.txt

@echo %install_dir%> lib/slide-web-config.txt
@echo %temp_dir%>> lib/slide-web-config.txt

set PATH=%PATH%;%java_dir%\bin
cd lib
jar uf VTBox2.war slide-web-config.txt
del slide-web-config.txt
cd ..

@echo SLIDE Configuration is complete.
@echo To start SLIDE run %~dp0bin\start-slide.bat
@echo To stop SLIDE run %~dp0bin\stop-slide.bat

REM Set Java Path in glassfish domain1 config
CALL "%glassfish_dir%\asadmin.bat" start-domain
CALL "%glassfish_dir%\asadmin.bat" set "server.java-config.java-home=%java_dir%"
CALL "%glassfish_dir%\asadmin.bat" deploy --force=true "%install_dir%lib\VTBox.war"

REM CALL "%glassfish_dir%\asadmin.bat" stop-domain domain1