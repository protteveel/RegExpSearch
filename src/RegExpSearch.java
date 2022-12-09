import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RegExpSearch {

    // The length of the help message
    private static int LEN_HELP_MSG = 97;
    
    // Possible parameters given to the application.
    private enum Parameter { FILE_PATH,       // Path to the file(s) to search.
    	                    CONFIG_FILE_PATH, // Path to configuration file.
    	                    OUTPUT_FILE_PATH, // Path to output file.
    	                    UNDEFINED };      // Not set yet.

    // Track the overall status
    private Boolean allOK = true;
    
    // List of matching files
    FileCollector fileCollector;
    
    // List of regular expression tests
    RegExpCollector regExCollector;
    	                    
    // Display how to use the program
    public void displayHelp(String msg) {
    	System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ RegExSearch, Search for text in files, using regular expressions from a configuration file.   ║");
        System.out.println("║                                                                                               ║");
        System.out.println("║ Logic flow: ┌──────────────────────┐                                                          ║");
        System.out.println("║             │ File(s) to search in │                                                          ║");
        System.out.println("║             └──────────────────────┘\\     ┌───────────────────┐    ┌─────────────┐            ║");
        System.out.println("║                                        => │ RegExSearch (App) │ => │ Output file │            ║");
        System.out.println("║             ┌──────────────────────┐/     └───────────────────┘    └─────────────┘            ║");
        System.out.println("║             │ Configuration file   │                                                          ║");
        System.out.println("║             └──────────────────────┘                                                          ║");
        System.out.println("║                                                                                               ║");
        System.out.println("║ Version: 1.0.0 - Fri Dec 9, 2022                                                              ║");
        System.out.println("║                                                                                               ║");
        System.out.println("║ Usage:   java -jar RegExSearch.jar -f <path to the file(s) to search>                         ║");
        System.out.println("║                                    -c <path to configuration file>                            ║");
        System.out.println("║                                    -o <path to output file>                                   ║");
        System.out.println("║                                                                                               ║");
        System.out.println("║ Example: java -jar RegExSearch.jar -f ./*.txt* -c ./RegExSearch.conf -o ./RegExSearch.txt     ║");
        System.out.println("║                                                                                               ║");
        System.out.println("║ Notes:   - It is expected all the file(s) is/are readable.                                    ║");
        System.out.println("║          - It is expected the config file is readable and is in the correct format.           ║");
        System.out.println("║          - It is expected the output file is writeable and can be overwritten.                ║");
        System.out.println("║          - Written by Percy Rotteveel.                                                        ║");
        System.out.println("║          - Nobody can be held liable for any errors, mistakes, omissions, etc.                ║");
        System.out.println("║          - USE AT YOUR OWN RISK!                                                              ║");
        System.out.println("║                                                                                               ║");
        System.out.println("║ Config:  - It is expected, the config file is a plain text file.                              ║");
        System.out.println("║          - It is expected, there is one \"detection rule\" per line.                            ║");
        System.out.println("║          - It is expected, a \"detection rule\" has the following format:                       ║");
        System.out.println("║            - <Use the rule (Y/N)?><tab><field identifier><tab><regular expression>. E.g.:     ║");
        System.out.println("║            - Y	email	^[A-Z0-9. _%+-]+@[A-Z0-9                                            ║");
        System.out.println("║          - The field identifiers can be any sequence of characters but no <tab>.              ║");
        System.out.println("║                                                                                               ║");
        if (( msg != null ) && ( msg.length() > 0 )) {
            System.out.println( msg ); 
        }
    	System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
    
    // Return the status of the object
    public Boolean getAllOK() {
    	return allOK;
    }
    
    // Set the status of the object
    protected void setAllOK(Boolean newAllOK) {
    	// Copy the value
    	allOK = newAllOK; 
    }
    
    // Format error to fit in the help message box
    private String createErrorMsg(String msg) {
    	// Set the return value
    	String retVal = "║ ERROR: " + msg;
    	// Make sure it is long enough, so it will fit nicely
    	while (retVal.length() < (LEN_HELP_MSG - 1)) {
    		// Add a space at the end
    		retVal += " ";
    	}
    	// Add the end character
    	retVal += "║";
    	// Return the result
    	return retVal;
    }

	 // Path to the log files
	 private String filesPath = "";
	
	 // Path to the configuration file
	 private String cnfFilePath = "";
	 
	 // Path to the output file
	 private String outFilePath = "";
	
	 // Convert a character to a parameter
	 private Parameter charToParameter(char theKey) {
		 // Set the default return vale
		 Parameter retVal = Parameter.UNDEFINED;
		 // Determine the parameter
		 switch (theKey) {
			 case 'f':
				 retVal = Parameter.FILE_PATH;
				 break;
			 case 'c':
				 retVal = Parameter.CONFIG_FILE_PATH;
				 break;
			 case 'o':
				 retVal = Parameter.OUTPUT_FILE_PATH;
				 break;
		 }		 
		 // Return the result
		 return retVal;
	 }
	 
	// Make sure the path provided is correct
	private String checkPath(String newPath) {
		// Does the path start with a ~?
		if(newPath.startsWith("~")) {
			// Get the user's home directory
			String homeDir = System.getProperty("user.home");
			// Do we have a home directory?
			if((homeDir != null) && (homeDir.length() > 0)) {
				// Replace the ~ with the user's home directory
				newPath = newPath.replaceFirst("~", homeDir);
			}
		}
		// Return the results
		return newPath;
	}
		
	 // Make sure all arguments were provided
	 RegExpSearch( String[] arguments ) {
		 // Track the character parameter
		 char theKey = '?';
		 // We haven't determined the parameter yet
		 Parameter inParameter = Parameter.UNDEFINED;
        // Do we have at least six arguments
        if (( arguments != null ) && ( arguments.length >= 6 )){
           // Get the parameters
       	for (int i = 0; i < arguments.length; i++ ) {
       		// Get the argument
       		String argument = arguments[i];
       		// Does it start with a '-'?
       		if( argument.startsWith("-")) {
       			// Get the parameter
       			theKey = argument.toLowerCase().charAt(1);
       			inParameter = charToParameter(  theKey );
       		} else {
       			// What parameter as we in?
       			switch (inParameter) {
        			case FILE_PATH:
        				// The next parameter is the path to the log files
        				filesPath = checkPath( arguments[i]);
        				break;
        			case CONFIG_FILE_PATH:
        				// The next parameter is the path to the configuration file
        				cnfFilePath = checkPath( arguments[i]);
        				break;
        			case OUTPUT_FILE_PATH:
        				// The next parameter is the path to the output file
        				outFilePath = checkPath( arguments[i]);
        				break;
        			default:
        				// Wrong parameter, display error message
        				displayHelp( createErrorMsg("Wrong argument: \"-" + theKey + " " + argument + "\""));
        				// Break out of the loop
        				i = arguments.length;
        				// The overall status is not OK
        				allOK = false;
        				break;
       			}
       		}
       	}
       } else {
       	// Display the help information
       	displayHelp( createErrorMsg("Wrong number of arguments (" + arguments.length + "); should be six."));
       	// The overall status is not OK
       	allOK = false;
       }
   }

	 // Create the list of log files
	 private Boolean getListOfFiles() {
		 // Set the default return value
		 Boolean retVal = true;
		 // Create a new file collector object
		 fileCollector = new FileCollector(filesPath);
		 // The file path provided was OK
		 if((fileCollector != null) && (fileCollector.getAllOK())) {
			 // Collect the files
			 retVal = fileCollector.run();
		 }
		 // Return the result
		 return retVal;
	 }
	 
	 // Create the list of regular expressions to test for
	 private Boolean getRegExTests() {
		 // Set the default return value
		 Boolean retVal = true;
		 // Create a new regular expression collector object
		 regExCollector = new RegExpCollector(cnfFilePath);
		 // The path to the configuration file was OK?
		 if((regExCollector != null) && (regExCollector.getAllOK())) {
			 // Collect the regular expression tests
			 retVal = regExCollector.run();
		 }
		 
		 // Return the result
		 return retVal;
	 }

	 // Initialize the object
	 private void init() {
		// So far all is OK?
		if(getAllOK()) {
			// Set all to not OK until all is done
			setAllOK(false);
			// Do we have a path to the log files?
			if ((filesPath != null) && (filesPath.length() > 0)) {
				// Get a list of matching files
				if (getListOfFiles()) {
					// Get list of regular expression tests
					if(getRegExTests()) {
						// All is OK
						setAllOK(true);
					}
				} else {
					// Inform the user
					displayHelp( createErrorMsg("Could not find any matching files"));
				}
			} else {
				// Inform the user
				displayHelp( createErrorMsg("There is no path to the log file(s) specified"));
			}
		}
	 }
	 
	 // Go through the file looking for matching regular expressions
	 private void findRegExp(PrintWriter writer, String filePath, TestRegExp[] regExpList) {
	 	// Track if the file name has been printed
		Boolean fileNamePrinted = false;
		// Try to open the file?
		File fileHandle = new File(filePath);
		// The file could be opened?
		if((fileHandle != null) && (fileHandle.canRead())) {
			// Try to create a buffer reader
			try {
				// Open a buffer reader
				BufferedReader reader = new BufferedReader(new FileReader(fileHandle));
				// The line to read
				String line;
				// Track the line number
				int lineNumber = 0;
				// Track the line has been printed
				Boolean linePrinted = false;
				// Read till the end of file
				while((line = reader.readLine()) != null) {
					// Line has not been printed
					linePrinted = false;
					// Increase the line number
					lineNumber++;
					// Do we have a line?
					if((line != null) && (line.length() > 0 )) {
						// Parse through the list of all the regular expressions
						for(int i=0; i<regExpList.length; i++) {
							// Get the name
							String name = regExpList[i].getName();
							// Get the regular expression
							String regExp = regExpList[i].getRegExp();
							// Does the line match the regular expression?
							if(line.matches(regExp)) {
								// Has not the file name been printed?
								if(!fileNamePrinted) {
									// Print the file name
									writer.println(filePath);
									// File name has been printed
									fileNamePrinted = true;
								}
								// Has the line been printed?
								if(!linePrinted) {
									// Print the line
									writer.println(lineNumber + "\t" + line);
									// Line has been printed
									linePrinted = true;
								}
								// Print what had been found
								writer.println("\t" + name);
							}
						}
					}
				}
				// Close the buffer reader
				reader.close();
			} catch (FileNotFoundException e) {
				// Inform the user
				displayHelp( createErrorMsg("Could not open the file: \"" + filePath + "\""));
			} catch (IOException e) {
				// Inform the user
				displayHelp( createErrorMsg("Could not read the file: \"" + filePath + "\""));
			}
		}
	 }
	 
	 // Run the scan
	 private void run() {
		 // Get the list of files to search through
		 String[] fileList = fileCollector.getMatchingFiles();
		 // Did we get a file list?
		 if((fileList != null) && (fileList.length > 0)) {
			 // Get the list of regular expressions
			 TestRegExp[] regExpList = regExCollector.getRegExpTests();
			 // Did we get a regular expression list?
			 if((regExpList != null) && (regExpList.length > 0)) {
				 // Try to create the output file
				 try {
					 // Create the output file
					 PrintWriter writer = new PrintWriter(outFilePath);
					 // Writer created successfully?
					 if(writer != null) {
						 // Parse through the file list
						 for(int i=0; i<fileList.length; i++ ) {
							 // Go through the file looking for matching regular expressions
							 findRegExp(writer, fileList[i], regExpList);
						 }
						 // Close the output writer
						 writer.close();
					 }
				} catch (FileNotFoundException e) {
					// Inform the user
					displayHelp( createErrorMsg("Could not create the output file"));
				}
			 }
		 }
	 }
	 
    // Main routine
    public static void main(String[] args) {
    	// Create the object
    	RegExpSearch regExSearch = new RegExpSearch(args);
    	// Could we create the object and all is OK?
    	if((regExSearch != null) && (regExSearch.getAllOK())) {
			// Initialize the object
    		regExSearch.init();
	        // Is all OK still?
			if (regExSearch.getAllOK()) {
				// Run the scan
				regExSearch.run();
			}
    	}
	}
}
