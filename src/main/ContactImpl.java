/**
 * A contact is a person we are making business with or may do in the future.
 * Contacts have an ID (unique), a name (probably unique, but maybe not), and
 * notes that the user may want to save about them
 * 
 * @see Contact
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 24 February 2015
 * 
 */
public class ContactImpl implements Contact {

	/**
	 * Constructor supplied with just a name
	 * 
	 * @param name
	 *            the contact's name
	 */
	ContactImpl(String name) {
		// TODO write constructor
	}

	/**
	 * Constructor: supply both name and notes
	 * 
	 * @param name
	 *            the contact's name
	 * @param notes
	 *            notes about the contact (email...?)
	 */
	ContactImpl(String name, String notes) {
		// TODO write constructor
	}

	/**
	 * Constructor: supply id, name and notes (needed to recreate objects on
	 * file read)
	 * 
	 * @param id
	 *            the user id
	 * @param name
	 *            the contact's name
	 * @param notes
	 *            notes about the contact (email...?)
	 */
	ContactImpl(int id, String name, String notes) {
		// TODO write constructor
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNotes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNotes(String note) {
		// TODO Auto-generated method stub

	}

}
