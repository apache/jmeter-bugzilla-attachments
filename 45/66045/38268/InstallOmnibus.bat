@ECHO OFF

:main
cls
@ECHO...........................
@ECHO.......................
@ECHO..................
@ECHO Installing Kovair Omnibus in Jmeter
@ECHO Copy or Replace ApacheJMeter_core.jar to JmeterInstallationDir\lib\ext
@ECHO..................
@ECHO.......................
@ECHO...........................


set /p HOMEDIR="Enter Jmeter installation directory path: "
set LIBDIR=%HOMEDIR%
set SRCDIR=%~dp0



	IF EXIST %HOMEDIR% (
		@ECHO.****************************************************
		@ECHO File copying begins....
		@ECHO Source directory path: %SRCDIR%Installomnibus\
		@ECHO Target directory path: %LIBDIR%\lib\ext
		
		xcopy /s "%SRCDIR%Installomnibus\ApacheJMeter_core.jar" "%LIBDIR%\lib\ext"
		
		@ECHO File copied successfully.
		@ECHO.****************************************************
		
		@ECHO.........
		@ECHO.....
		@ECHO Kovair Omnibus Installation completed.
		@ECHO.....
		@ECHO.........
		pause > nul
		Exit
		
	) ELSE (

		@ECHO.........
		@ECHO.....
		@ECHO ERROR:Directory does not exist!
		@ECHO.....
		@ECHO.........
		@ECHO Do you want to retry? (y/n)
		set INPUT=
		set /P INPUT=Enter your choice: %=%
		If /I "%INPUT%"=="y" goto main
		If /I "%INPUT%"=="n" exit
		
		
	)








