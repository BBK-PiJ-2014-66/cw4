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
	MeetingImpl(Set<Contact> contacts, Calendar date) {
		// TODO throw exception on dodgy input
		id = IdGenerator.MEETING.nextID(); // issues an ID unique to meetings
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

}
