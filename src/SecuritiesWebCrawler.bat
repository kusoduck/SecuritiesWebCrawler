@echo off
echo path:%~dp0

::取得bat檔案所在的當前目錄
set base=%~dp0

set class=%base%\classes
set libs=%base%\libs

set class_path=%class%;%libs%;

java -cp %class_path% com.kusoduck.securities.SecuritiesWebCrawler
@pause