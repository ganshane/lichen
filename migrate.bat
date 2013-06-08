@echo off
:start
set /p userinp=(U/D):
set userinp=%userinp:~0,1%
if "%userinp%"  EQU  "U" goto execute
if "%userinp%"  EQU  "u" goto execute 
if "%userinp%"  EQU  "D" goto execute
if "%userinp%"  EQU  "d" goto execute	
if "%userinp%"  NEQ  "d" goto error			
:execute
java -jar db_scripts.jar %userinp%
echo  finished,and could be continued!
goto start
:error
echo  wrong input, please input again!
goto start