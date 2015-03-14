

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * A class to represent meetings.
 *
 * Meetings have unique IDs, scheduled date and a list of participating
 * contacts: so a pretty simple class single constructor and three getters.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 28 February 2015
 *
 */
public class MeetingImpl implements Meeting, Serializable {

	/**
	 * Needed for Serializable to throw an error if decoding a previous
	 * incompatible version. Need to increment if this class is changed so it is
	 * not backwards compatible with its previous version.
	 */
	private static final long serialVersionUID = 2L;

	private int id; // unique ID
	private Calendar date;
	/**
	 * although we are supplied contacts as a set the Contact objects are
	 * mutable so so storing as a set is problematic. Instead store as a list
	 * and convert
	 */
	private List<Contact> contacts;

	/**
	 * Constructor for Meeting
	 * 
	 * @param contacts
	 *            list of the participating contact or contacts
	 * @param date
	 *            the scheduled date of the meeting (or when it occurred).
	 * @throws NullPointerException
	 *             if contacts or date is null.
	 * @throws IllegalArgumentException
	 *             if supplied with an empty set of contacts
	 */
	public MeetingImpl(Set<Contact> contacts, Calendar date) {
		// call the 3 argument constructor with a new unique ID
		this(IdGenerator.MEETING.nextID(), contacts, date);
	}

	public MeetingImpl(int id, Set<Contact> contacts, Calendar date) {
		if (contacts == null || date == null)
			throw new NullPointerException(
					"null contacts or dates not allowed.");
		if (contacts.size() == 0)
			throw new IllegalArgumentException("empty contact set supplied."
					+ " A meeting must have at least one contact.");
		this.id = id;
		this.contacts = new ArrayList<Contact>(contacts);
		this.contacts.sort(ContactImpl::orderByID); // sort by ID after input
		this.date = date;

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
	public Calendar getDate() {
		return date;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Contact> getContacts() {
		return new HashSet<Contact>(contacts);
	}

	/**
	 * string representation meeting in human readable form (single line).
	 */
	@Override
	public String toString() {
		/*
		 * Contacts should be sorted by ID
		 */
		this.contacts.sort(ContactImpl::orderByID); // sort by ID after input
		return "id=" + id + ", date=" + date.getTime() + ", contacts="
				+ contacts;
	}

	/**
	 * Compares two meetings and returns a value compatible with
	 * {@link java.util.Comparator} to order the meetings by date (Used because
	 * we cannot make Meeting Comparable)
	 * 
	 * @param first
	 *            a meeting
	 * @param second
	 *            a meeting
	 * @return "a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second."
	 */
	public static int orderByDate(Meeting first, Meeting second) {
		return first.getDate().compareTo(second.getDate());
	}

	/**
	 * Compares two meetings and returns a value compatible with
	 * {@link java.util.Comparator} to order the meetings by ID number.
	 * 
	 * @param first
	 *            a meeting
	 * @param second
	 *            a meeting
	 * @return "a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second."
	 */
	public static int orderByID(Meeting first, Meeting second) {
		return first.getId() - second.getId();
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
		result = prime * result
				+ ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * Checks whether Object obj is a MeetingImpl that equals this Meeting Impl.
	 * 
	 * Meetings are equal if they have the same information in id, date and
	 * contacts. Note that clones are .equal there is no requirement for the
	 * object or its content to the the same object - just that the information
	 * be equivalent.
	 * 
	 * @param obj
	 *            the Object to check.
	 * @return true if obj is a MeetingImpl that meets the equality criterion
	 *         above
	 */
	@Override
	public boolean equals(Object obj) { // generated by eclipse
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MeetingImpl))
			return false;
		MeetingImpl other = (MeetingImpl) obj;
		if (contacts == null) {
			if (other.contacts != null)
				return false;
		} else if (!contacts.equals(other.contacts))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	/*
	 * NOTE: would be nice to make Meeting Comparable but we cannot alter the
	 * interface can make MeetingImpl Comparable but to use this in practice
	 * involves nasty casting issues. Keep commented out Comparable stuff for
	 * the record:
	 */
	// public class MeetingImpl implements Meeting, Comparable<Meeting> {
	// /*
	// * Order meetings according to their date. If dates are exactly the same
	// * order by id number.
	// *
	// * @see java.lang.Comparable#compareTo(java.lang.Object)
	// */
	// @Override
	// public int compareTo(Meeting other) {
	// int retval = this.getDate().compareTo(other.getDate());
	// if (retval == 0)
	// retval = this.getId() - other.getId();
	// return retval;
	// }

}