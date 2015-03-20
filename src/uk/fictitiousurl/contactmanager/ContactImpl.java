package uk.fictitiousurl.contactmanager;

import java.io.Serializable;

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
public class ContactImpl implements Contact, Serializable {

	/**
	 * Needed for Serializable to throw an error if decoding a previous
	 * incompatible version. Need to increment if this class is changed so it is
	 * not backwards compatible with its previous version.
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String notes;
	private int id;

	/**
	 * Constructor supplied with just a name
	 * 
	 * @param name
	 *            the contact's name
	 * @throws NullPointerException
	 *             if name is null.
	 */
	public ContactImpl(String name) {
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
	public ContactImpl(String name, String notes) {
		if (name == null)
			throw new NullPointerException("name cannot be null");
		if (notes == null)
			throw new NullPointerException("notes cannot be null");
		this.name = name;
		this.notes = notes;
		id = IdGenerator.CONTACT.nextID(); // issues a unique ID
	}

	/**
	 * Three parameter constructor (useful in testing to make a clone)
	 *
	 * @param name
	 *            the contact's name
	 * @param notes
	 *            notes about the contact (email...?)
	 * @param id
	 *            id number
	 * @throws NullPointerException
	 *             if name or notes are null.
	 */
	public ContactImpl(String name, String notes, int id) {
		this(name, notes);
		this.id = id;
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
	 * Compares two contacts and returns a value compatible with
	 * {@link java.util.Comparator} to order the contacts by ID number.
	 * 
	 * @param first
	 *            a contact
	 * @param second
	 *            another contact
	 * @return "a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second."
	 */
	public static int orderByID(Contact first, Contact second) {
		return first.getId() - second.getId();
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

	/**
	 * Checks whether this ContactImpl is equal to Object obj.
	 * 
	 * @param obj
	 *            the object to check
	 * 
	 * @return true if obj is a ContactImpl that has the same id, equal names
	 *         and equal notes; false other. Note that a "clone" will be satisfy
	 *         .equals (does not have to be same object).
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
