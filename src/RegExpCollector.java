import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class RegExpCollector {

	// Location of the configuration file
	public static final String CONFIG_FILE_PATH = "/Users/percyrotteveel/workspace/RegExSearch/RegExSearch.conf";
	
	// The path to the configuration file
	private String confFilePath;
	
	// Track everything is OK
	private Boolean allOK = false;

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

	// Collection of regular expressions
	Vector<TestRegExp> regExVector =new Vector<TestRegExp>();

	// Constructor to see if there is a configuration file we can read
	RegExpCollector(String newConfFilePath) {
		// Set everything is not OK
		setAllOK(false);
		// Do we have a configuration file name
		if((newConfFilePath != null) && (newConfFilePath.length() > 0)) {
			// Copy the configuration file name
			confFilePath = newConfFilePath;
			// Try to open the file?
			File fileHandle = new File(confFilePath);
			// The file could be opened?
			if((fileHandle != null) && (fileHandle.canRead())) {
				// All is good
				setAllOK(true);
			}
		}
	}
	
	private Boolean collectRegExp(String confFilePath) {
		// Is all OK so far?
		if(getAllOK()) {
			// Set the overall status not OK
			setAllOK(false);
			// Try to open the file?
			File fileHandle = new File(confFilePath);
			// The file could be opened?
			if((fileHandle != null) && (fileHandle.canRead())) {
				// Try t0 create a buffer reader
				try {
					// Open a buffer reader
					BufferedReader reader = new BufferedReader(new FileReader(fileHandle));
					// The line to read
					String line;
					// The first character to test
					char key;
					// Read till the end of file
					while((line = reader.readLine()) != null) {
						// Do we have a line?
						if((line != null) && (line.length() > 0 )) {
							// Get the first character
							key = line.charAt(0);
							// Is the first character a 'Y' or 'y'?
							if((key == 'Y') || (key == 'y')) {
								// Split the string by tabs 
								String[] parts = line.split("\t");
								// Do we have enough parts?
								if((parts != null) && (parts.length >= 3)) {
									// Create a new regular expression object
									TestRegExp theTestRegExp = new TestRegExp(parts[1], parts[2]);
									// Was the object creates successfully?
									if(theTestRegExp != null) {
										// Add it to the list of regular expression tests
										regExVector.add(theTestRegExp);
									}
								}
							}
						}
					}
					// Close the buffer reader
					reader.close();
					// Do we have at least one regular expression to test with?
					if ((regExVector != null) && (regExVector.size() > 0)) {
						// All is OK
						setAllOK(true);
					}
				} catch (FileNotFoundException e) {
					// Inform the user could not read the file
					System.out.println("Could not open the configuyration file: \"" + confFilePath + "\"");
				} catch (IOException e) {
					// Inform the user could not read the file
					System.out.println("Could not read the configuyration file: \"" + confFilePath + "\"");
				}
			}
		}
		// Return the results
		return getAllOK();
	}
	
	// Get the list of regular expression to test
	protected TestRegExp[] getRegExpTests() {
		// Create an empty array
		TestRegExp[] retVal = null;
		// Is everything OK?
		if(getAllOK()) {
			// Do we have at least one regular expression to test with?
			if ((regExVector != null) && (regExVector.size() > 0)) {
				// Initialize the array
				retVal = new TestRegExp[regExVector.size()];
				// Parse through the list
				for(int i=0; i<regExVector.size(); i++) {
					// Get the regular expression to test
					TestRegExp theTestRegExp = regExVector.elementAt(i);
					// Do we have a regular expression to test?
					if(theTestRegExp != null) {
						// Add it to the array
						retVal[i] = theTestRegExp;
					}
				}
			}
		}
		// Return the results
		return retVal;
	}
	
	// Print the list of regular expression tests
	protected void print() {
		// Is everything OK?
		if(getAllOK()) {
			// Do we have at least one regular expression to test with?
			if ((regExVector != null) && (regExVector.size() > 0)) {
				for(int i=0; i<regExVector.size(); i++) {
					// Get the regular expression to test
					TestRegExp theTestRegExp = regExVector.elementAt(i);
					// Do we have a regular expression to test?
					if(theTestRegExp != null) {
						// Print the regular expression to test
						System.out.println(theTestRegExp.getName() + "\t" + theTestRegExp.getRegExp());
					}
				}
			}
		}
	}
	
	// Collect the list of regular expressions
	protected Boolean run() {
		// Return the results
		return collectRegExp(confFilePath);
	}
}
