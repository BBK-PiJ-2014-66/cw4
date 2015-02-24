/**
 * A contact is a person we are making business with or may do in the future.
 * Contacts have an ID (unique), a name (probably unique, but maybe not), and
 * notes that the user may want to save about them.
 * 
 * @see Contact
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 24 February 2015
 * 
 */
public class ContactImpl implements Contact {

	private String name;
	private String notes;
	private int id;
	private static int maxId = 0; // maximum ID seen

	/**
	 * Constructor supplied with just a name
	 * 
	 * @param name
	 *            the contact's name
	 * @throws NullPointerException
	 *             if name is null.
	 */
	ContactImpl(String name) {
		this(name, "");
	}

	/**
	 * Constructor: supply both name and notes
	 * 
	 * @param name
	 *            the contact's name
	 * @param notes
	 *            notes about the contact (email...?)
	 * @throws NullPointerException
	 *             if name or notes are null.
	 */
	ContactImpl(String name, String notes) {
		this(++maxId, name, notes); // increment maxId and use this
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
	 * 
	 * @throws NullPointerException
	 *             if name or notes are null.
	 */
	ContactImpl(int id, String name, String notes) {
		if (name == null)
			throw new NullPointerException("name cannot be null");
		if (notes == null)
			throw new NullPointerException("notes cannot be null");
		this.name = name;
		this.notes = notes;
		this.id = id;
		if (id > maxId) // is this the biggest ID seen so far
			maxId = id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNotes() {
		return notes;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * N.B. The added notes replace any existing notes.
	 * 
	 * @throws NullPointerException
	 *             if note is null.
	 */
	@Override
	public void addNotes(String note) {
		if (note == null)
			throw new NullPointerException("added note cannot be null");
		this.notes = note;
	}

}
