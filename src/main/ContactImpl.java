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
		System.out.println("debug call to ContactImpl constructor id="+ id + " name=" + name + " notes=" + notes);
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

	/**
	 * Customised toString 
	 */
	@Override
	public String toString() {
		return "name='" + name + "', notes='" + notes + "', id=" + id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() { // generated by eclipse
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) { // generated by eclipse
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ContactImpl))
			return false;
		ContactImpl other = (ContactImpl) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		return true;
	}

}
