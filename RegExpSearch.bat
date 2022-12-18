REM ┌───────────────────────────────────────────────────────────┐"
REM │ Name:        RegExpSearch.bat                             │"
REM │ Description: Script to test the RegExpSearch application. │"
REM │ History:     1.0.0 - Fri Dec  9, 2022 - initial version.  │"
REM │              1.1.0 - Thu Dec 15, 2022 - updated version.  │"
REM │              1.1.1 - Sun Dec 18, 2022 - updated version.  │"
REM └───────────────────────────────────────────────────────────┘"

@ECHO OFF
CLS

REM Remove the old results file
DEL C:\Users\percy\Documents\RegExpSearch\RegExpSearch.txt

REM Go into the working directory
PUSHD C:\Users\percy\Documents\RegExpSearch

REM Execute the search
java -jar RegExpSearch.jar -f ~\Documents\logFiles\*.txt* -c C:\Users\percy\Documents\RegExpSearch\RegExpSearch.conf -o C:\Users\percy\Documents\RegExpSearch\RegExpSearch.txt

REM Review the results
ECHO C:\Users\percy\Documents\RegExpSearch\RegExpSearch.txt

REM Go back where we came from
POPD