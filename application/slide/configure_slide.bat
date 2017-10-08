@echo off

SET /P java_dir=Please Provide Path to Java Installation Directory (For instance if the path to javac.exe on your system is C:\Program Files\Java\jdk1.8.0_102\bin\javac.exe, provide the Java Installation Path as C:\Program Files\Java\jdk1.8.0_102):

REM Check if java.exe can be found in speicifed installation directory
SET file_to_find="%java_dir%\bin\java.exe"
IF NOT EXIST %file_to_find% (
	echo %ERRORLEVEL%
	echo SLIDE configuration failed.
	echo %file_to_find%
	echo Could not find java.exe in %java_dir%\bin. Please ensure that the Path to Java Installation Directory provided is correct.
	EXIT /B
)

SET /P glassfish_dir=Please Provide Path to Glassfish Server Installation Directory: 

REM Check if asadmin.bat can be found in speicifed installation directory
SET file_to_find="%glassfish_dir%\bin\asadmin.bat"
IF NOT EXIST %file_to_find% (
	echo %ERRORLEVEL%
	echo SLIDE configuration failed.
	echo %file_to_find%
	echo Could not find asadmin.bat in %glassfish_dir%\bin. Please ensure that the Path to Glassfish Server Installation Directory provided is correct.
	EXIT /B
)

SET /P mongodb_dir=Please Provide Path to the "bin" folder in MongoDB Installation Directory:

REM Check if mongod.exe and mongoimport.exe can be found in speicifed installation directory
SET file_to_find="%mongodb_dir%\mongo.exe"
IF NOT EXIST %file_to_find% (
	echo %ERRORLEVEL%
	echo SLIDE configuration failed.
	echo %file_to_find%
	echo Could not find mongo.exe in %mongodb_dir%. Please ensure that the Path to the "bin" folder in MongoDB Installation Directory provided is correct.
	EXIT /B
)
SET file_to_find="%mongodb_dir%\mongoimport.exe"
IF NOT EXIST %file_to_find% (
	echo %ERRORLEVEL%
	echo SLIDE configuration failed.
	echo %file_to_find%
	echo Could not find mongoimport.exe in %mongodb_dir%. Please ensure that the Path to the "bin" folder in MongoDB Installation Directory provided is correct.
	EXIT /B
)

SET /P python_dir=Please Provide Path to Python Installation Directory:

REM Check if python.exe can be found in speicifed installation directory
SET file_to_find="%python_dir%\python.exe"
IF NOT EXIST %file_to_find% (
	echo %ERRORLEVEL%
	echo SLIDE configuration failed.
	echo %file_to_find%
	echo Could not find python.exe in %python_dir%. Please ensure that the Path to Python Installation Directory provided is correct.
	EXIT /B
)

SET install_dir=%~dp0
@echo %install_dir%

SET description_str=#install-dir
@echo %description_str%> %install_dir%config/slide-config.txt
@echo %install_dir%>> %install_dir%config/slide-config.txt

SET description_str=#java-dir
@echo %description_str%>> %install_dir%config/slide-config.txt
@echo %java_dir%>> %install_dir%config/slide-config.txt

SET glassfish_dir=%glassfish_dir%\bin
SET description_str=#glassfish-dir
@echo %description_str%>> %install_dir%config/slide-config.txt
@echo %glassfish_dir%>> %install_dir%config/slide-config.txt

SET description_str=#mongodb-dir
@echo %description_str%>> %install_dir%config/slide-config.txt
@echo %mongodb_dir%>> %install_dir%config/slide-config.txt

SET description_str=#python-dir
@echo %description_str%>> %install_dir%config/slide-config.txt
@echo %python_dir%>> %install_dir%config/slide-config.txt

REM Update slide-web-config.txt in VTBox/WEB-INF

set "install_dir_c=%install_dir:\=/%"
set "mongodb_dir_c=%mongodb_dir:\=/%"
set "glassfish_dir_c=%glassfish_dir:\=/%"
"%java_dir%\bin\java" -jar  configure_slide.jar "%install_dir_c%" "%mongodb_dir_c%" "%glassfish_dir_c%"

mkdir %install_dir%lib\WEB-INF
SET install_dir_wo_end_slash=%install_dir:~0,-1%
@echo %install_dir_wo_end_slash%> %install_dir%lib\WEB-INF\slide-web-config.txt
cd /D %install_dir%lib
"%java_dir%\bin\java" -jar uf VTBox.war WEB-INF\slide-web-config.txt
rmdir /s /q WEB-INF
cd ..

REM Check if fastcluster is present
"%python_dir%\python" "%install_dir%src\dependency_checks.py" "%install_dir%src\import_check.txt"
SET /P import_check=<%install_dir%src\import_check.txt
IF NOT %import_check% == 1 (
	IF %import_check% == -1 (
		echo SLIDE configuration failed.
		echo Python package fastcluster is not installed.
	) ELSE IF %import_check% == -2 (
		echo SLIDE configuration failed.
		echo Python package numpy is not installed.
	) ELSE IF %import_check% == -3 (
		echo SLIDE configuration failed.
		echo Python package scipy is not installed.
	) ELSE IF %import_check% == -4 (
		echo SLIDE configuration failed.
		echo Python package scipy.cluster.hierarchy is not installed.
	)
	EXIT /B
)
del %install_dir%src\import_check.txt

REM Import Data Into MongoDB
@echo Importing data into MongoDB
CALL %install_dir%bin\start-mongo
"%mongodb_dir%\mongoimport" --db geneVocab_HomoSapiens --collection HS_geneMap2 --drop --file "%install_dir%db\HS_geneMap2.json"
"%mongodb_dir%\mongoimport" --db geneVocab_HomoSapiens --collection HS_aliasEntrezMap --drop --file "%install_dir%db\HS_aliasEntrezMap.json"
"%mongodb_dir%\mongoimport" --db geneVocab_HomoSapiens --collection HS_entrezAliasMap --drop --file "%install_dir%db\HS_entrezAliasMap.json"
"%mongodb_dir%\mongoimport" --db geneVocab_HomoSapiens --collection HS_goMap2 --drop --file "%install_dir%db\HS_goMap2.json"
"%mongodb_dir%\mongoimport" --db geneVocab_HomoSapiens --collection HS_pathwayMap --drop --file "%install_dir%db\HS_pathwayMap.json"
"%mongodb_dir%\mongoimport" --db geneVocab_MusMusculus --collection MM_aliasEntrezMap --drop --file "%install_dir%db\MM_aliasEntrezMap.json"
"%mongodb_dir%\mongoimport" --db geneVocab_MusMusculus --collection MM_entrezAliasMap --drop --file "%install_dir%db\MM_entrezAliasMap.json"
"%mongodb_dir%\mongoimport" --db geneVocab_MusMusculus --collection MM_geneMap2 --drop --file "%install_dir%db\MM_geneMap2.json"
"%mongodb_dir%\mongoimport" --db geneVocab_MusMusculus --collection MM_goMap2 --drop --file "%install_dir%db\MM_goMap2.json"
"%mongodb_dir%\mongoimport" --db geneVocab_MusMusculus --collection MM_pathwayMap --drop --file "%install_dir%db\MM_pathwayMap.json"
timeout 3
taskkill /f /im mongod.exe

REM Finally Done
@echo SLIDE Configuration is complete.
@echo To start SLIDE run %install_dir%start-slide.bat
@echo To stop SLIDE run %install_dir%stop-slide.bat

REM Set Java Path in glassfish domain1 config
REM CALL "%glassfish_dir%\asadmin.bat" start-domain
REM CALL "%glassfish_dir%\asadmin.bat" set "server.java-config.java-home=%java_dir%"
REM CALL "%glassfish_dir%\asadmin.bat" deploy --force=true "%install_dir%lib\VTBox.war"

REM CALL "%glassfish_dir%\asadmin.bat" stop-domain domain1