@echo off
:start
set /p userinp=(U/D):
set userinp=%userinp:~0,1%
if "%userinp%"  NEQ  "U" if "%userinp%"  NEQ  "u" if "%userinp%"  NEQ  "D" if "%userinp%"  NEQ  "d" (
	echo  wrong input, please input again!
) else (
	java -jar db_scripts.jar %userinp%
)
:end