package cw4;

/**
 * Two singleton classes used to issue unique ID numbers for contacts and for
 * meetings.
 *
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 27 February 2015
 * 
 */
public enum IdGenerator {
	CONTACT, // Separate Unique ID for Contact
	MEETING; // and for Meeting

	/*
	 * Written after looking at information from:
	 * 
	 * http://stackoverflow.com/questions/427902/what-is-the-best-approach-for-using
	 * -an-enum-as-a-singleton-in-java
	 * http://www.javalobby.org/java/forums/t17491.html
	 * http://stackoverflow.com/questions
	 * /15029445/java-static-variable-for-auto
	 * -increment-userid-objectoutputstream
	 * http://www.tutorialspoint.com/design_pattern/singleton_pattern.htm
	 */

	private int maxID = 0; // the maximum ID seen so far

	/**
	 * provides a unique integer ID.
	 * 
	 * @return the new ID number that is one larger than any ID issued so far
	 */
	public int nextID() {
		return ++maxID;
	}

	/**
	 * Used to register ID that have been read in from a file to make sure no
	 * new ID issued conflicts with it.
	 * 
	 * @param existingID
	 *            the existing ID read in from file.
	 */
	public void registerExistingID(int existingID) {
		if (existingID > maxID)
			maxID = existingID;
	}

}
