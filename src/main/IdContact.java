/**
 * Singleton class used to issue unique ID numbers for contacts.
 * 
 * Written after looking at information from:
 * http://www.javalobby.org/java/forums/t17491.html
 * http://stackoverflow.com/questions
 * /15029445/java-static-variable-for-auto-increment-userid-objectoutputstream
 * http://www.tutorialspoint.com/design_pattern/singleton_pattern.htm
 *
 */
class IdContact {
	private static final IdContact INSTANCE = new IdContact();
	private int maxID = 0; // the maximum ID seen so far

	private IdContact() {
	};

	/**
	 * Used to get the single IdContact object
	 * 
	 * @return the singleton IdContact object
	 */
	public static IdContact getInstance() {
		return INSTANCE;
	}

	/**
	 * provides a unique integer ID for contacts.
	 * 
	 * @return the new ID number that is one larger than any ID issued so far
	 */
	public int nextID() {
		return ++maxID;
	}

	/**
	 * Used to register contacts that have been read in from a file to make sure
	 * no new ID issued conflicts with it.
	 * 
	 * @param existingID
	 *            the existing ID read in from file.
	 */
	public void registerExistingID(int existingID) {
		if (existingID > maxID)
			maxID = existingID;
	}

}
