# RegExpSearch
Search through one or more files, based on regular expressions.

## Usage

> java -jar ./RegExpSearch.jar -f [file location(s)] -c [configuration file location] -o [output file location]

## Example

> java -jar ./RegExpSearch.jar -f ./logs/\*.txt\* -c ./RegExpSearch.conf -o ./RegExpSearch.txt

## Parameters
The application takes the following three parameters
- File location(s)
- Location configuration file
- Location output file

## Test
The following is a sample Bash script to test the application:

```console
#!/bin/bash
@echo off
clear

# Move into the working directory:
cd ~/workspace/RegExpSearch

# Execute the regular expression search:
java -jar ./bin/RegExpSearch.jar -f "~/Documents/Rotteveel/Projects/ACME/logFiles/*.txt*" -c ./RegExpSearch.conf -o ./RegExpSearch.txt

# Review the results:
more ./RegExpSearch.txt
```

### File locations
Use this parameter to specify which files to search through.

The format for this parameter:

> -f [file location(s)]

- This can be an absolute path. E.g.: <code>-f /Users/percyrotteveel/workspace/RegExpSearch/logs/\*.log</code>
- This can be a relative path. E.g.: <code>-f ./logs/\*.txt</code>
- This can be a path relative to the home folder. E.g.: <code>-f ~/Documents/Rotteveel/Projects/ACME/logFiles/\*.txt\*</code>
- For the extension, any standard OS wild card can be used. E.g.: <code>-f ./logs/\*.txt\*</code>

The application will inspect all the files in the specified directory, including the ones in any sub-directories, which match the specification. E.g.: <code>-f ./\*Logs\*</code>, could results in the inspection of the following files:

- <code>./WorkflowLogs.log</code>
- <code>./WorkflowLogs.txt</code>
- <code>./WorkflowLogs.txt.1</code>
- <code>./WorkflowLogs.txt.2</code>
- <code>./logs/PreProdLogs.txt</code>
- <code>./logs/StagingLogs.txt</code>
- <code>./logs/TestingLogs.txt</code>

### Location configuration file
Use this parameter to specify where the configuration file can be found.

The format for this parameter:

> -c [configuration file location]

- This can be an absolute path. E.g.: <code>-c /Users/percyrotteveel/workspace/RegExpSearch/RegExpSearch.conf</code>
- This can be a relative path. E.g.: <code>-c ./RegExpSearch.conf</code>
- This can be a path relative to the home folder. E.g.: <code>-c ~/workspace/RegExpSearch/RegExpSearch.conf</code>
- Any standard OS file location specification can be used.

#### Format
For the configuration file, use the following directions:

- One specification per line
- A specification is only used if the line starts with the letter 'Y'; any other characters are ignored; this makes it really easy to test one or more specifications
- Format: [Use the specification (Y/N)?]<code>tab</code>[name for the regular expression]<code>tab</code>[regular expression]
- E.g. <code>Y	DOB	.\*\[d|D]\[o|O][b|B].\*([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])).\*</code>
- The regular expression above searched for any form of spelling for date of birth (DOB), followed by a date (YYYY-MM-DD)

**Note:**

- Every regular expression is used on every line, in every file that matches the specification.

### Location output file
Use this parameter to specify the file where the output has to be written to.

The format for this parameter:

> -o [output file location]

- This can be an absolute path. E.g.: <code>-o /Users/percyrotteveel/workspace/RegExpSearch/RegExpSearch.txt</code>
- This can be a relative path. E.g.: <code>-o ./RegExpSearch.txt</code>
- This can be a path relative to the home folder. E.g.: <code>-o ~/workspace/RegExpSearch/RegExpSearch.txt</code>
- Any standard OS file location specification can be used.

During execution, as soon as the application finds a match for any of the regular expressions, it will write the filename, the line number and the line where the match was found, and which regular expression was a match. E.g.:

> ./logFiles/WorkflowLogs.txt

> 5918	   "patientDOB" : "1982-12-12",

>	DOB

In the aforementioned example:

- The the file ./logFiles/WorkflowLogs.txt
- On line 5918: "patientDOB" : "1982-12-12",
- There was a match for DOB

**Note:**

- If the output file exists, it will be overwritten with each execution.

## References

- A good source for finding regular expressions is the [Regular Expression Library](https://regexlib.com "A great place to search for regular expressions").
- A good place for testing regular expressions online is the [Regular expression tester](https://regexr.com "RegExr: Learn, Build, & Test RegEx").