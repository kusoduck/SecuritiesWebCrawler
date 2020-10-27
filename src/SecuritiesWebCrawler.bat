@echo off
rem Batch file(based on SM-14) to install Windows service with LineWorks Service Manager
rem =====================================================================================
echo Start Report Template Migration Processor

if "%JAVA_HOME%" == "" goto nojavahome

rem **********************************
rem include project specific jar files
rem **********************************
set APP_HOME=%~dp0
rem remove trailing slash
IF %APP_HOME:~-1%==\ set APP_HOME=%APP_HOME:~0,-1%

set CP=
set CP=%CP%;${dependency.cp.windows}
set CP=%CP%;lib\${project.build.finalName}.${project.packaging}

rem *********************************
rem run class, command line options
rem *********************************


"%JAVA_HOME%"\bin\java.exe -cp %CP% com.kusoduck.securities.SecuritiesWebCrawler

goto done

:nojavahome
echo.
echo ERROR: No JAVA_HOME environment variable set.
goto done

:done
echo bye...
