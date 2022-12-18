	// Class to hold one test regular expression
	public class TestRegExp {
		
		// The name for the regular expression
		private String theName = null;
		
		// The regular expression
		private String theRegExp = null;
		
		TestRegExp(String newName, String newRegEx) {
			// Set the name
			setName(newName);
			// Set the regular expression
			setRegExp(newRegEx);
		}
		
		// Set the name of the regular expression
		protected String setName(String newName) {
			// Do we have a name?
			if((newName != null) && (newName.length() >= 0)) {
				// Copy the name
				theName = newName;
			}
			// Return the result
			return getName();
		}
		
		// Get the name of the regular expression
		protected String getName() {
			// Return the name
			return theName;
		}
		
		// Set the regular expression
		protected String setRegExp(String newRegExp) {
			// Do we have a regular expression?
			if((newRegExp != null) && (newRegExp.length() >= 0)) {
				// Copy the regular expression
				theRegExp = newRegExp;
			}
			// Return the result
			return getRegExp();
		}
		
		// Get the regular expression
		protected String getRegExp() {
			// Return the regular expression
			return theRegExp;
		}

	}
