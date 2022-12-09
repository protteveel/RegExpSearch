import java.io.File;
import java.util.Collections;
import java.util.Vector;

public class FileCollector {
	
	public static String FILE_PATH = "~/Documents/Rotteveel/Projects/ACME/logFiles/*.txt*";

	// The path to search for files
	private String filePath;
	
	// Track everything is OK
	private Boolean allOK = false;
	
	// Collection of files, which match the specification
	Vector<String> matchingFiles =new Vector<String>();
	
	// Constructor to see if there is at least one file in the directory
	FileCollector(String newFilePath) {
		// Do we have a file path?
		if((newFilePath != null) && (newFilePath.length() > 0 )) {
			// Copy the file path
			filePath = newFilePath;
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
	
	// Get the directory from a path
	private String getDirectory(String thePath ) {
		// Set the default return value
		String retVal = thePath;
		// Do we have a path?
		if((thePath != null) && (thePath.length() > 0 )) {
			// Split the path based on the file separator
			String dirParts[] = thePath.split(File.separator);
			// Do we have some parts?
			if((dirParts != null) && (dirParts.length > 0)) {
				// Copy the first part
				retVal = dirParts[0];
				// Concatenate the part
				for(int i = 1; i < dirParts.length - 1; i++) {
					// Put the directory back together
					retVal = retVal + File.separator + dirParts[i];
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
	
	// Get the name from a file name, which is the first part in front of the first '.'
	private String getName(String fileName) {
		// Set the default return value
		String retVal = fileName;
		// Do we have a filename?
		if((fileName != null) && (fileName.length() > 0 )) {
			// Split the file name based on
			String fileParts[] = fileName.split("\\.");
			// Do we have some parts?
			if((fileParts != null) && (fileParts.length > 1)) {
				// Get the first part of the file name
				retVal = fileParts[0];
			}
		}
		// Return the result
		return retVal;
	}
	
	// Get the extension from a file name
	private String getExtension(String fileName) {
		// Set the default return value
		String retVal = "";
		// Do we have a filename?
		if((fileName != null) && (fileName.length() > 0 )) {
			// Split the file name based on
			String fileParts[] = fileName.split("\\.");
			// Do we have some parts?
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
		// Return the result
		return retVal;
	}
	
	// Does the file matched the provided specification?
	private Boolean fileMatch(String fileName, String nameSpec, String extSpec) {
//		System.out.println("\nFile name: " + fileName);		
		// Set the default return value
		Boolean retVal = false;
		// Replace a '*' with the regular expression for any character '.'
		nameSpec = nameSpec.replaceAll("\\*", ".\\*");
//		System.out.println("Name spec: " + nameSpec);
		// Replace a '*' with the regular expression for any character '.'
		extSpec = extSpec.replaceAll("\\*", ".\\*");
//		System.out.println("Ext spec:  " + extSpec);
		// Get the file name
		String namePart = getName(fileName);
//		System.out.println("Name part: " + namePart);
		// Get the extension
		String extPart = getExtension(fileName);
//		System.out.println("Ext. part: " + extPart);
		// Does the name part match the name specification and the extension part matched the extension specification
		if(namePart.matches(nameSpec)) {
			if(extPart.matches(extSpec)) {
				// It is a match
				retVal = true;
			} else {
//				System.out.println("Extension does not match");
			}
		} else {
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
			// Get a file handle for the new file path
			File dirHandle = new File(thePath);
			// Get the directory
			String dirName = getDirectory(dirHandle.getPath());
			// Get the file name
			String fileName = getName(dirHandle.getName());
			// Get the extension
			String extension = getExtension(dirHandle.getName());
			// Let's try to read the directory
			dirHandle = new File(dirName);
			// Could the directory be opened?
			if(dirHandle != null) {
    			// Get the list of files in directory
    			File fileList[] = dirHandle.listFiles();
    			// Do we have some files?
    			if((fileList != null) && (fileList.length > 0 )) {
    				// Parse through the list of files
    				for(int i=0; i<fileList.length; i++) {
        				// Did we found a file?
        				if(fileList[i].isFile()) {
        					// Does the file matched the specification?
        					if(fileMatch(fileList[i].getName(), fileName, extension)) {
            					// Add the file to the list of matching files
        						matchingFiles.add(dirName + fileList[i].getName());
        					}
        				} else {
        					// Did we find a directory?
            				if(fileList[i].isDirectory()) {
            					// Parse the directory
            					collectFiles(dirName + fileList[i].getName() + File.separator + fileName + "." + extension);
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
	String[] vectorToArray() {
		// Set the default return value
		String[] retVal = new String[matchingFiles.size()];
		// Copy the strings one by one
		for(int i=0; i<matchingFiles.size(); i++ ) {
			// Copy the string
			retVal[i] = matchingFiles.elementAt(i);
		}
		// Return the results
		return retVal;

	}

	// Get the list of matching files
	protected String[] getMatchingFiles() {
		// Set the return value
		String[] retVal = null;
		// Sort the list alphabetically
		Collections.sort(matchingFiles);
		// Convert the vector to an array of strings
		retVal = vectorToArray();
		// Return the list of matching files
		return retVal;
	}
}