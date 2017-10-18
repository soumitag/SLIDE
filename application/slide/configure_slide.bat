@echo off

SET /P java_dir=Please Provide Path to Java Installation Directory (For instance if the path to java.exe on your system is C:\Program Files\Java\jdk1.8.0_102\bin\java.exe, provide the Java Installation Path as C:\Program Files\Java\jdk1.8.0_102\bin):

REM Check if java.exe can be found in speicifed installation directory
SET file_to_find="%java_dir%\java.exe"

IF NOT EXIST %file_to_find% (
	echo %ERRORLEVEL%
	echo SLIDE configuration failed.
	echo %file_to_find%
	echo Could not find java.exe in "%java_dir%\bin". Please ensure that the Path to Java Installation Directory provided is correct.
	EXIT /B
)

SET install_dir=%~dp0
SET "install_dir_c=%install_dir:\=/%"

SET "java_dir_c=%java_dir:\=/%"

"%java_dir_c%\java" -jar configure_slide.jar "%install_dir_c%" "%java_dir_c%" WINDOWS