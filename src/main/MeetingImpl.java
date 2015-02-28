import java.util.Calendar;
import java.util.Set;

/**
 * 
 * A class to represent meetings
 *
 * Meetings have unique IDs, scheduled date and a list of participating
 * contacts: so a pretty simple class single constructor and three getters.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 28 February 2015
 *
 */
public class MeetingImpl implements Meeting {

	/**
	 * Constructor
	 * 
	 * @param contacts
	 *            list of the participating contact or contacts
	 * @param date
	 *            the scheduled date of the meeting (or when it occurred).
	 */
	MeetingImpl(Set<Contact> contacts, Calendar date) {
		// TODO write the constructor
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
	public Calendar getDate() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Contact> getContacts() {
		// TODO Auto-generated method stub
		return null;
	}

}
