#!/bin/bash
# ┌───────────────────────────────────────────────────────────┐"
# │ Name:        RegExpSearch.sh                              │"
# │ Description: Script to test the RegExpSearch application. │"
# │ History:     1.0.0 - Fri Dec  9, 2022 - initial version.  │"
# │              1.1.0 - Thu Dec 15, 2022 - updated version.  │"
# │              1.1.1 - Sun Dec 18, 2022 - updated version.  │"
# └───────────────────────────────────────────────────────────┘"

@echo off
clear

# Remove the old results file
rm ~/workspace/RegExpSearch/RegExpSearch.txt

# Go into the working directory
pushd ~/workspace/RegExpSearch/bin

# Execute the search
java -jar ./RegExpSearch.jar -f "~/workspace/RegExpSearch/logFiles/*.txt*" -c ~/workspace/RegExpSearch/RegExpSearch.conf -o ~/workspace/RegExpSearch/RegExpSearch.txt

# Review the results
more ~/workspace/RegExpSearch/RegExpSearch.txt

# Go back where we came from
popd