import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to Manage contacts and Meetings
 * 
 * @see ContactManager
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 24 February 2015
 * 
 */
public class ContactManagerImpl implements ContactManager {

	List<Contact> contacts;

	/**
	 * construct a brand new ContactManager
	 */
	ContactManagerImpl() {
		contacts = new ArrayList<>();
	}

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNewContact(String name, String notes) {
		// ContactImpl constructor will throw required NullPointerException
		// if name or notes are null
		Contact newContact = new ContactImpl(name, notes);
		contacts.add(newContact);
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Contact> getContacts(String name) {
		// TODO return NullPointerException if name is null..
		Set<Contact> retContacts = new HashSet<>();
		for (Contact itContact : contacts) {
			if (itContact.getName().equals(name))
				retContacts.add(itContact);
		}
		return retContacts;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

}
