import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Singleton class to ensure that unique contactID's are kept
 * 
 * Written after looking at information from:
 * http://www.javalobby.org/java/forums/t17491.html
 * http://stackoverflow.com/questions
 * /15029445/java-static-variable-for-auto-increment-userid-objectoutputstream
 * http://www.tutorialspoint.com/design_pattern/singleton_pattern.htm
 *
 */
class IdContact implements Serializable {
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
	 * provides a unique integer ID for contacts
	 * 
	 * @return the ID number
	 */
	public int nextID() {
		return ++maxID;
	}

	private Object readResolve() throws ObjectStreamException {
		// instead of the object we're on,
		// return the class variable INSTANCE
		return INSTANCE;
	}
}

/*
 * id = IdContact.getInstance.nextID();
 */
