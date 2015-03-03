package cw4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
	Calendar pretendNow = null;

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
		// I would add a check that the meeting time/date is not the same as
		// another meeting maybe +/- 5 mins but as this is not in the spec!
		if (!checkDateIsFuture(date))
			throw new IllegalArgumentException(
					"Future meetings cannot have past dates."
							+ " Supplied date =" + date.getTime());

		// check that all supplied contacts are legitimate appearing in
		// this.contacts, throws a IllegalArgumentException if there is a
		// problem.
		checkContacts(contacts.toArray(new Contact[0]));

		// TODO there might be a normalisation problem where supplied contacts
		// TODO are different objects with same value? Check this latter.

		FutureMeeting thisFM = new FutureMeetingImpl(contacts, date);
		futureMeetings.add(thisFM);
		return thisFM.getId(); // the ID of the meeting
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Meeting getMeeting(int id) {
		// use the other methods and cast result
		Meeting retMeet = (Meeting) getFutureMeeting(id);
		// TODO if null look in past meetings....
		return retMeet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *             if the contact does not exist
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// N.B. do not understand "will not contain duplicates"?
		// A meeting has a unique ID so cannot be a duplicate ever.
		// Can have two meetings at the same time with the same or different
		// contacts but filtering these out seems a silly idea.
		// I would prevent two meetings being added at the same (+/- 5 minutes)
		// but we have to implement what is on the spec.

		// check the contact is legit if not throws IllegalArgumentException
		checkContacts(contact);

		// use lambda expression to select all meeting in futureMeetings that
		// involves the contact
		List<Meeting> matchingMs = futureMeetings.stream()
				.filter(fm -> fm.getContacts().contains(contact))
				.collect(Collectors.toList());

		// sort matchingMs chronologically.
		/*
		 * First coded here
		 * 
		 * Comparator<Meeting> byDate = (m1, m2) -> { return
		 * m1.getDate().compareTo(m2.getDate()); };
		 * 
		 * Collections.sort(matchingMs, byDate);
		 */
		// but split off Comparator to a static functions for neatness and unit
		// testing. First sort byID and then by again by Date.
		Collections.sort(matchingMs, MeetingImpl::orderByID);
		Collections.sort(matchingMs, MeetingImpl::orderByDate);
		return matchingMs;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO need to select meetings:
		// "that are scheduled for, or that took place on, the specified date"
		// this presumably means that we need to be able to compare dates
		// ignoring the time of day. Write a static method
		// sameDate(Calendar,Calendar)
		List<Meeting> matchingMs = futureMeetings.stream()
				.filter(fm -> CalendarUtils.sameDate(date,fm.getDate()))
				.collect(Collectors.toList());
		
		
		return matchingMs;
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

	/*
	 * extra methods from interface ContactManagerPlus follow:
	 */

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void overrideDateNow(Calendar pretendNow) {
		this.pretendNow = pretendNow;
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

	/*
	 * private methods
	 */

	/**
	 * check whether a date is in the future N.B. checks against current
	 * date/time unless {@link overrideDateNow} has been used to set a
	 * "pretendNow"
	 * 
	 * @param date
	 *            the date to check
	 * @return whether the date is in the future.
	 */
	private boolean checkDateIsFuture(Calendar date) {
		Calendar now = (pretendNow != null) ? pretendNow : Calendar
				.getInstance();
		return date.after(now);
	}

	/**
	 * Checks the contacts are all recognised and appear in this.contacts
	 * 
	 * @param contacts
	 *            the single contact or array of contacts to be checked.
	 * @throws IllegalArgumentException
	 *             if any of the contacts does not appear or the contact array
	 *             is empty.
	 */
	private void checkContacts(Contact... contacts) {
		if (contacts.length == 0)
			throw new IllegalArgumentException("Empty contact set.");
		for (Contact itCon : contacts) {
			// is itCon in this.contacts?
			if (!this.contacts.contains(itCon))
				throw new IllegalArgumentException("Unknown contact: " + itCon);
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
		if (pretendNow != null)
			ret.append("\n\tN.B. Overriding current date/time ('now') to: "
					+ pretendNow.getTime() + "\n");
		return ret.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {// generated by eclipse TODO update for new fields
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
	public boolean equals(Object obj) { // generated by eclipse TODO update for
										// new fields
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
