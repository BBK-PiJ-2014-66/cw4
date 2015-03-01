package cw4;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class to Manage contacts and Meetings
 * 
 * @see ContactManager
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 24 February 2015
 * 
 */
public class ContactManagerImpl implements ContactManagerPlus {

	List<Contact> contacts;
	List<FutureMeeting> futureMeetings;

	/**
	 * construct a brand new ContactManager
	 */
	public ContactManagerImpl() {
		contacts = new ArrayList<>();
		futureMeetings = new ArrayList<>();
	}

	ContactManagerImpl(String fileName) {
		ContactManagerImpl readCM = null;
		try {
			readCM = FileSaveRetrieve.retrieveFromfile(fileName);
			contacts = readCM.contacts;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *             if the meeting is set for a time in the past, or if any
	 *             contact is not in list of current contacts
	 */
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		// TODO check that date supplied is in future and throw
		// IllegalArgumentException if not
		if (!checkDateIsFuture(date))
			throw new IllegalArgumentException(
					"Future meetings cannot have past dates."
							+ " Supplied date =" + date.getTime());

		// TODO check that all supplied contacts are legit and that there is at
		// least one
		FutureMeeting thisFM = new FutureMeetingImpl(contacts, date);
		futureMeetings.add(thisFM);
		return thisFM.getId(); // the ID of the meeting
	}

	private boolean checkDateIsFuture(Calendar date) {
		Calendar now = Calendar.getInstance(); 
		//TODO must allow now override to a supplied pretend time
		return date.after(now);
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *             "if there is a meeting with that ID happening in the past"
	 *             (i.e. stored as a past meeting)
	 */
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// TODO check past meetings for that ID - throw an exception on match

		// Select meetings that matches the id
		// use a stream lambda expression rather than for loop
		// http://stackoverflow.com/questions/22694884/filter-java-stream-to-1-and-only-1-element
		List<FutureMeeting> matchingFMs = futureMeetings.stream()
				.filter(fm -> fm.getId() == id).collect(Collectors.toList());
		if (matchingFMs.size() > 1) // belt and braces
			throw new RuntimeException("Programming Error. "
					+ "Have two future meeting with matching ids: "
					+ matchingFMs);
		// if there is one matching future meeting return it otherwise return
		// null
		return (matchingFMs.size() == 1) ? matchingFMs.get(0) : null;
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
	 * 
	 * @throws RuntimeException
	 *             if identical contact ID error occurs (this should be an
	 *             "impossible error")
	 */
	@Override
	public void addNewContact(String name, String notes) {
		// ContactImpl constructor will throw required NullPointerException
		// if name or notes are null
		Contact newContact = new ContactImpl(name, notes);
		// contacts are required to have new IDs - lets check that this is true
		for (Contact itCon : contacts) {
			if (itCon.getId() == newContact.getId())
				throw new RuntimeException(
						"programming error: identical contact IDs for " + itCon
								+ " and " + newContact);
		}

		contacts.add(newContact);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Contact> getContacts(int... ids) {
		// can never match no ids
		if (ids.length == 0)
			throw new IllegalArgumentException(
					"must supply at least one id to getContacts");
		Set<Contact> retContacts = new HashSet<>();
		for (int itID : ids) {
			boolean match = false;
			for (Contact itContact : contacts) {
				if (itContact.getId() == itID) {
					match = true;
					retContacts.add(itContact);
					break;
				}
			}
			if (!match)
				throw new IllegalArgumentException(
						"getContacts supplied with invalid contact id = "
								+ itID);
		}

		return retContacts;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Contact> getContacts(String name) {
		if (name == null)
			throw new NullPointerException("cannot search for null name.");
		Set<Contact> retContacts = new HashSet<>();
		for (Contact itContact : contacts) {
			if (itContact.getName().contains(name))
				retContacts.add(itContact);
		}
		return retContacts;
	}

	@Override
	public List<Contact> getAllContacts() {
		return contacts;
	}

	@Override
	public List<FutureMeeting> getAllFutureMeetings() {
		// TODO sort chronologically;
		return futureMeetings;
	}

	@Override
	public List<PastMeeting> getAllPastMeetings() {
		return null; // TODO write method
	}

	@Override
	public void flush() {
		try {
			FileSaveRetrieve.saveToFile("contacts.txt", this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Customised toString() N.B. produces output on multiple lines as this is
	 * useful in debugging.
	 */
	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer("\n\tcontacts: \n");
		for (Contact itCon : contacts)
			ret.append("\t\t" + itCon + "\n");
		ret.append("\n\tfuture meetings: \n");
		for (FutureMeeting itFM : futureMeetings)
			ret.append("\t\t" + itFM + "\n");

		return ret.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {// generated by eclipse
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contacts == null) ? 0 : contacts.hashCode());
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
		if (!(obj instanceof ContactManagerImpl))
			return false;
		ContactManagerImpl other = (ContactManagerImpl) obj;
		if (contacts == null) {
			if (other.contacts != null)
				return false;
		} else if (!contacts.equals(other.contacts))
			return false;
		return true;
	}

}