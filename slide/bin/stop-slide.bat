@echo off

taskkill /f /im mongod.exe

SET glassfish_dir=C:\Program Files\glassfish-4.1.1\glassfish\bin
CALL "%glassfish_dir%\asadmin.bat" stop-domain domain1