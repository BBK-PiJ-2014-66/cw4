package cw4;

import java.util.Calendar;
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
public class MeetingImpl implements Meeting {

	private int id; // unique ID
	private Calendar date;
	private Set<Contact> contacts;

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
		this.contacts = contacts;
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
		return contacts;
	}

	/**
	 * string representation meeting in human readable form (single line).
	 */
	@Override
	public String toString() {
		return "id=" + id + ", date=" + date.getTime() + ", contacts="
				+ contacts;
	}

	
	/*
	 * NOTE: would be nice to make Meeting Comparable but we cannot alter the
	 * interface can make MeetingImpl Comparable but to use this in practice
	 * involves nasty casting issues. Keep commented out Comparable stuff for the
	 * record:
	 */
// public class MeetingImpl implements Meeting, Comparable<Meeting> {
//	/*
//	 * Order meetings according to their date. If dates are exactly the same
//	 * order by id number.
//	 * 
//	 * @see java.lang.Comparable#compareTo(java.lang.Object)
//	 */
//	@Override
//	public int compareTo(Meeting other) {
//		int retval = this.getDate().compareTo(other.getDate());
//		if (retval == 0)
//			retval = this.getId() - other.getId();
//		return retval;
//	}

}
