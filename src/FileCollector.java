import java.io.File;
import java.util.Vector;

public class FileCollector {
	
	// The path to search for files
	private String filePath;
	
	// The file name specification
	private String fileSpec;
	
	// The extension specification
	private String extSpec;
	
	// Track everything is OK
	private Boolean allOK = false;
	
	// Collection of files, which match the specification
	Vector<String> matchingFiles = null;
	
	// Make sure the path provided is correct
	private String checkPath(String newPath) {
		// Does the path start with a ~?
		if(newPath.startsWith("~")) {
			// Get the user's home directory
			String homeDir = System.getProperty("user.home");
			// Do we have a home directory?
			if((homeDir != null) && (homeDir.length() > 0)) {
				// Replace the ~ with the user's home directory
				newPath = homeDir + newPath.substring(1, newPath.length());
			}
		}
		// Return the results
		return newPath;
	}
	
	// Constructor to see if there is at least one file in the directory
	FileCollector(String newFilePath) {
		// Do we have a file path?
		if((newFilePath != null) && (newFilePath.length() > 0 )) {
			// Check the path
			filePath = checkPath(newFilePath);
			// Get a file handle for the new file path
			File dirHandle = new File(filePath);
			// Get the directory
			String dirName = getDirectory(dirHandle.getPath());
			// Let's try to read the directory
			dirHandle = new File(dirName);
			// Could the directory be opened?
			if(dirHandle != null) {
    			// Get the list of files in directory
    			File fileList[] = dirHandle.listFiles();
    			// Do we have some files?
    			if((fileList != null) && (fileList.length > 0 )) {
    				// All is OK so far
    				setAllOK(true);
    				// Create a new vector to hold the matching files
    				matchingFiles = new Vector<String>();
    				// Get the file specification
    				fileSpec = getName(filePath);
    				// Get the extension specification
    				extSpec = getExtension(filePath);
    			} else {
    				// Can not we read the directory?
    				if(!dirHandle.canRead()) {
    					System.out.println("Cannot read directory: \"" + dirName + "\"");
    				}
    			}
			}
		}
	}
	
	// Keep track if everything is OK
	private void setAllOK(Boolean newAllOk) {
		// Copy the value
		allOK = newAllOk;
	}

	// Return the all OK status
	protected Boolean getAllOK() {
		// Return the current state
		return allOK;
	}
	
	// Replace a single backslash character (\) with a double backslash character (\\)
	private String escapeRegExp(String regExp) {
		// Default return value
		String retVal = "";
		// Do we have a regular expression?
		if((regExp != null) && (regExp.length() > 0)) {
			// Parse the string
			for(int i=0; i<regExp.length(); i++) {
				// Get the character
				char theKey = regExp.charAt(i);
				// Is it a backslash
				if(theKey == '\\') {
					// Add a double backslash
					retVal += "\\\\";
				} else {
					// Just add the character
					retVal += theKey;
				}
			}
		}
		// Return the result
		return retVal;
	}
	
	// Get the directory from a path
	private String getDirectory(String thePath ) {
		// Set the default return value
		String retVal = thePath;
		// Do we have a path?
		if((thePath != null) && (thePath.length() > 0 )) {
			// Is the path a directory?
			File theDir = new File(thePath);
			// Did we got a handle?
			if(theDir != null) {
				// Is not it a directory?
				if(!theDir.isDirectory()) {
					// Split the path based on the file separator
					String dirParts[] = thePath.split(escapeRegExp(File.separator));
					// Do we have some parts?
					if((dirParts != null) && (dirParts.length > 0)) {
						// Copy the first part
						retVal = dirParts[0];
						// Concatenate the remaining part
						for(int i = 1; i < dirParts.length - 1; i++) {
							// Put the directory back together
							retVal = retVal + File.separator + dirParts[i];
						}
					} else {
						// Inform the user
						System.out.println("\tCould not split the path.");
					}
				}
			}
		}
		// Does not the directory ends with a file separator?
		if(!retVal.endsWith(File.separator)) {
			// Add the file separator
			retVal += File.separator;
		}
		// Return the result
		return retVal;
	}
	
	// Get the file name from a path, which is the first part in front of the first '.'
	private String getName(String filePath) {
		// Set the default return value
		String retVal = "*";
		// Do we have a file path?
		if((filePath != null) && (filePath.length() > 0 )) {
			// Split the path based on the file separator
			String pathParts[] = filePath.split(escapeRegExp(File.separator));
			// Do we have some path parts?
			if((pathParts != null) && (pathParts.length > 0)) {
				// We assume the last part is the file name
				String fileName = pathParts[pathParts.length-1];
				// Split the file name based on the dot
				String fileParts[] = fileName.split("\\.");
				// Do we have some file parts?
				if((fileParts != null) && (fileParts.length > 1)) {
					// We assume the first part is the file name
					retVal = fileParts[0];
					// Do not we have a name specification?
					if((retVal == null) || (retVal.length() == 0)) {
						// Use a wild card instead
						retVal = "*";
					}
				}
			}
		}
		// Return the result
		return retVal;
	}
	
	// Get the extension from a path, which are all the parts after the first '.'
	private String getExtension(String filePath) {
		// Set the default return value
		String retVal = "*";
		// Do we have a file path?
		if((filePath != null) && (filePath.length() > 0 )) {
			// Split the path based on the file separator
			String pathParts[] = filePath.split(escapeRegExp(File.separator));
			// Do we have some path parts?
			if((pathParts != null) && (pathParts.length > 0)) {
				// We assume the last part is the file name
				String fileName = pathParts[pathParts.length-1];
				// Split the file name based on the dot
				String fileParts[] = fileName.split("\\.");
				// Do we have some file parts?
				if((fileParts != null) && (fileParts.length > 1)) {
					// Start with the first part after the first '.'
					retVal = fileParts[1];
					// Put the rest of the extension together
					for(int i=2; i < fileParts.length; i++) {
						// Concatenate the next part
						retVal = retVal + "." + fileParts[i];
					}
				}
			}
		}
		// Return the result
		return retVal;
	}
	
	// Does the file matched the provided specification?
	private Boolean fileMatch(String fileName, String nameSpec, String extSpec) {
		// Set the default return value
		Boolean retVal = false;
		// Do we have a name specification?
		if((nameSpec != null) && (nameSpec.length() > 0)) {
			// Replace a '*' with the regular expression for any character '.'
			nameSpec = nameSpec.replaceAll("\\*", ".\\*");
		} else {
			// Any name will do
			nameSpec = ".*";
		}
		// Do we have an extension specification?
		if((extSpec != null) && (extSpec.length() > 0)) {
			// Replace a '*' with the regular expression for any character '.'
			extSpec = extSpec.replaceAll("\\*", ".\\*");
		} else {
			// Any extension will do
			extSpec = ".*";
		}
		// Get the file name
		String namePart = getName(fileName);
		// Get the extension
		String extPart = getExtension(fileName);
		// Does the name part match the name specification and the extension part matched the extension specification
		if(namePart.matches(nameSpec)) {
			if(extPart.matches(extSpec)) {
				// It is a match
				retVal = true;
			} else {
				// This is only for debugging
//				System.out.println("Extension does not match");
			}
		} else {
			// This is only for debugging
//			System.out.println("Name does not match");
		}
		// Return the result
		return retVal;
	}
	
	// Collect all files that match the provide path
	private Boolean collectFiles(String thePath) {
		// Set the default return value
		Boolean retVal = true;
		// Do we have a file path?
		if((thePath != null) && (thePath.length() > 0 )) {
			// Get the directory name
			String dirName = getDirectory(thePath);
			// Get a file handle for the new file path
			File dirHandle = new File(dirName);
			// Could the directory be opened?
			if(dirHandle != null) {
    			// Get the list of files in directory
    			File fileList[] = dirHandle.listFiles();
    			// Do we have some files?
    			if((fileList != null) && (fileList.length > 0 )) {
    				// Parse through the list of files
    				for(int i=0; i<fileList.length; i++) {
    					// Get the name of the item (file or directory)
    					String newName = fileList[i].getName();
        				// Did we found a file?
        				if(fileList[i].isFile()) {
        					// Does the file matched the specification?
        					if(fileMatch(newName, fileSpec, extSpec)) {
            					// Add the file to the list of matching files
        						matchingFiles.add(dirName + newName);
        					}
        				} else {
        					// Did we find a directory?
            				if(fileList[i].isDirectory()) {
            					// Create the new path
            					String newPath = dirName + newName + File.separator + fileSpec + "." + extSpec;
            					// Parse the directory
            					collectFiles(newPath);
        					}
        				}
    				}
    			} else {
    				// Can not we read the directory?
    				if(!dirHandle.canRead()) {
    					System.out.println("Cannot read directory: \"" + dirName + "\"");
    				}
    			}
			}
		}
		// Return the results
		return retVal;
	}
	
	// Collect the list of files
	protected Boolean run() {
		// Return the results
		return collectFiles(filePath);
	}
	
	// Get the list of matching files in an array
	public String[] getMatchingFiles() {
		// Set the default return value
		String[] retVal = null;

		// Do we have matching files?
		if((matchingFiles != null) && (matchingFiles.size() > 0)) {
			// Create the string array
			retVal = new String[matchingFiles.size()];
			// Copy the strings one by one
			for(int i=0; i<matchingFiles.size(); i++ ) {
				// Copy the string
				retVal[i] = matchingFiles.elementAt(i);
			}
		}
		// Return the results
		return retVal;

	}
	
	public static void main(String[] args) {
		// Do we have some arguments?
		if((args != null) && (args.length > 0 )) {
			// Create the object
			FileCollector fileCollector = new FileCollector(args[0]);
			// Do we have an object?
			if((fileCollector != null) && (fileCollector.getAllOK())){
				// Collect all matching files
				if( fileCollector.run()) {
					// Get the list of matching files
					String[] fileList = fileCollector.getMatchingFiles();
					// Do we have a list of matching files?
					if((fileList != null) && (fileList.length > 0 )) {
						// Print the path
						System.out.println("\nPATH: \"" + args[0] + "\"\n");
						// Print the list
						for(int i=0; i<fileList.length; i++) {
							// Print the matching file
							System.out.println("fileList[" + i + "]: \"" + fileList[i] + "\"");
						}
					} else {
						// Inform the user
						System.out.println("ERROR: Could not find any match files.");
						System.out.println("Path:  \"" + args[0] + "\"");
					}
				} else {
					// Inform the user
					System.out.println("ERROR: Could not access the directory.");
					System.out.println("Path:  \"" + args[0] + "\"");
				}
			} else {
				// Inform the user
				System.out.println("ERROR: Could not create a file collector.");
				System.out.println("Path:  \"" + args[0] + "\"");
			}
		} else {
			// Inform the user
			System.out.println("ERROR: Please supply a file location.");
			System.out.println("E.g.:  ~/Documents/logFiles/*.txt*");
		}
	}
}