package contactmanager;

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

	/**
	 * Needed for Serializable to throw an error if decoding a previous
	 * incompatible version. Need to increment if this class is changed so it is
	 * not backwards compatible with its previous version.
	 */
	private static final long serialVersionUID = 2L;
	/**
	 * list of Contact 's that have been registered using addNewContact.
	 */
	private List<Contact> contacts;
	/**
	 * list of future meetings that have been added using addFutureMeetings.
	 */
	private List<FutureMeeting> futureMeetings;
	/**
	 * list of pastMeetings created by addMeetingNotes on a FutureMeeting or by
	 * addNewPastMeeting.
	 */
	private List<PastMeeting> pastMeetings;
	/**
	 * Normally the real system date time will be used to check whether a
	 * meeting is in the future or past. However if pretendNow is not null then
	 * this date time will be used instead. Intended for testing.
	 */
	private Calendar pretendNow = null;
	/**
	 * deals with saving state, fileName, the method, and the methods to do it
	 */
	private FileSaveRetrieve fileSR = new FileSaveRetrieveImpl();

	/**
	 * construct a brand new ContactManager with no attempt to read from a save
	 * state file. Call to {@link #flush flush()} will save state to the default
	 * filename "contacts.txt".
	 */
	public ContactManagerImpl() {
		contacts = new ArrayList<>();
		futureMeetings = new ArrayList<>();
		pastMeetings = new ArrayList<>();
	}

	/**
	 * Construct the contact manager with data from a save-state-file. N.B. this
	 * file will normally be overwritten when {@link #flush flush()} method is
	 * later called to save the state. Note that the retrieved file name got
	 * from the save-state-file is ignored and the argument fileName will be
	 * used.
	 * 
	 * @param fileName
	 *            the save-state-file name
	 * 
	 * @throws RuntimeException
	 *             - if there is a problem in opening the file, reading from it
	 *             or decoding the object
	 */
	public ContactManagerImpl(String fileName) {
		fileSR.setFileName(fileName);
		ContactManagerImpl retrieve = (ContactManagerImpl) fileSR
				.retrieveFromFile();
		contacts = retrieve.contacts;
		futureMeetings = retrieve.futureMeetings;
		pastMeetings = retrieve.pastMeetings;
		pretendNow = retrieve.pretendNow;
	}

	/**
	 * "{@inheritDoc}"
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
		// problem. Problem includes if a clone is supplied.
		checkContacts(contacts.toArray(new Contact[0]));

		FutureMeeting thisFM = new FutureMeetingImpl(contacts, date);
		futureMeetings.add(thisFM);
		return thisFM.getId(); // the ID of the meeting
	}

	/**
	 * "{@inheritDoc}"
	 * 
	 * @throws IllegalArgumentException
	 *             "if there is a meeting with that ID happening in the future"
	 *             (i.e. stored as a future meeting)
	 */
	@Override
	public PastMeeting getPastMeeting(int id) {
		// check whether there is a past meeting
		if (getFutureMeetingNoThrow(id) != null)
			throw new IllegalArgumentException(
					"Check for past meeting with id of a future= " + id);
		return getPastMeetingNoThrow(id);
	}

	/**
	 * "{@inheritDoc}"
	 * 
	 * @throws IllegalArgumentException
	 *             "if there is a meeting with that ID happening in the past"
	 *             (i.e. stored as a past meeting)
	 */
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// check whether there is a past meeting
		if (getPastMeetingNoThrow(id) != null)
			throw new IllegalArgumentException(
					"Check for future meeting with id of a past= " + id);
		return getFutureMeetingNoThrow(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Meeting getMeeting(int id) {
		// use the other "NoThrow" methods (to avoid exceptions) and cast result
		Meeting retMeet = (Meeting) getFutureMeetingNoThrow(id);
		if (retMeet == null) {
			retMeet = (Meeting) getPastMeetingNoThrow(id);
		}
		return retMeet;
	}

	/**
	 * "{@inheritDoc}"
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

	/**
	 * "{@inheritDoc}"
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		/*
		 * Must select meetings:
		 * "that are scheduled for, or that took place on, the specified date"
		 * this presumably means that we need to be able to compare dates
		 * ignoring the time of date do so in CalendarUtils.sameDate()
		 */

		// select future meetings on required date
		List<Meeting> matchingMs = futureMeetings.stream()
				.filter(fm -> CalendarUtils.sameDate(date, fm.getDate()))
				.collect(Collectors.toList());

		// select meetings from past
		List<Meeting> matchingPasts = pastMeetings.stream()
				.filter(fm -> CalendarUtils.sameDate(date, fm.getDate()))
				.collect(Collectors.toList());

		// make joint list (could have meetings from both)
		matchingMs.addAll(matchingPasts);

		// First sort byID and then by again by "Date" in this case time of day
		Collections.sort(matchingMs, MeetingImpl::orderByID);
		Collections.sort(matchingMs, MeetingImpl::orderByDate);

		return matchingMs;
	}

	/**
	 * "{@inheritDoc}"
	 * 
	 * @param contact
	 *            one of the user’s contacts
	 * @return the list of past meeting(s) scheduled with this contact (maybe
	 *         empty).
	 * @throws IllegalArgumentException
	 *             if the contact does not exist
	 */
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// simply crib procedure from getFutureMeetingList(Contact)
		// and alter futureMeetings to pastMeetings...
		checkContacts(contact);
		List<PastMeeting> matchingMs = pastMeetings.stream()
				.filter(fm -> fm.getContacts().contains(contact))
				.collect(Collectors.toList());
		Collections.sort(matchingMs, MeetingImpl::orderByID);
		Collections.sort(matchingMs, MeetingImpl::orderByDate);
		return matchingMs;
	}

	/**
	 * "{@inheritDoc}"
	 * 
	 * @throws IllegalArgumentException
	 *             if the list of contacts is empty, or any of the contacts does
	 *             not exist
	 * @throws NullPointerException
	 *             if any of the arguments is null
	 * @throws IllegalStateException
	 *             if meeting is set for the future (N.B. this is missing from
	 *             Interface specification but needs to be done for
	 *             consistency).
	 * 
	 */
	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {

		// N.B. constructor throws required NullPointerException exception
		PastMeeting pastM = new PastMeetingImpl(contacts, date, text);

		// check that all supplied contacts are legitimate appearing in
		// this.contacts, throws a IllegalArgumentException if there is a
		// problem.
		checkContacts(contacts.toArray(new Contact[0]));
		if (checkDateIsFuture(date))
			throw new IllegalStateException(
					"Cannot add  a past meeting with a future date.");

		pastMeetings.add(pastM);
	}

	/**
	 * "{@inheritDoc}"
	 * 
	 * N.B. if used to add notes to a past meeting, the new notes replace the
	 * existing ones (rather than being appended).
	 * 
	 * @throws IllegalArgumentException
	 *             if the meeting does not exist
	 * @throws IllegalStateException
	 *             if the meeting is set for a date in the future
	 * @throws NullPointerException
	 *             if the notes are null
	 */
	@Override
	public void addMeetingNotes(int id, String text) {

		if (getMeeting(id) == null)
			throw new IllegalArgumentException("No meeting with id " + id);

		// search for id in future meetings
		for (FutureMeeting itFM : futureMeetings) {
			if (itFM.getId() == id) {
				// check the date of the meeting itFM is not in the future
				if (checkDateIsFuture(itFM.getDate()))
					throw new IllegalStateException(
							"Cannot add notes to a meeting in the future.");
				/*
				 * create a new past meeting with data from itFM and the
				 * supplied text. N.B. if 'text' is null the constructor throws
				 * NullPointerException as required.
				 */
				PastMeeting pastM = new PastMeetingImpl(id, itFM.getContacts(),
						itFM.getDate(), text);
				pastMeetings.add(pastM);

				// remove meeting from futureMeetings
				futureMeetings.remove(itFM);
				return; // done job
			}

		}
		// add notes to a past meeting at a later date
		PastMeetingImpl pastMI = (PastMeetingImpl) getPastMeeting(id);
		// have to cast to PastMeetingImpl because addNotes is not an interface
		// method
		pastMI.addNotes(text);

	}

	/**
	 * "{@inheritDoc}"
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
	 * "{@inheritDoc}"
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

	/**
	 * 
	 * "{@inheritDoc}"
	 * 
	 * Note that the file name and method to be saved are set in whatever
	 * constructor was used and at present cannot be altered.
	 * 
	 * @throws RuntimeException
	 *             - if there is an error in encoding the contactManager or in
	 *             opening the file or writing to it.
	 */
	@Override
	public void flush() {
		fileSR.saveToFile(this);
	}

	/*
	 * extra methods from interface ContactManagerPlus follow:
	 */

	@Override
	public List<Contact> getAllContacts() {
		return contacts;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<FutureMeeting> getAllFutureMeetings() {
		// must be sorted chronologically (pre-sort by id)
		Collections.sort(futureMeetings, MeetingImpl::orderByID);
		Collections.sort(futureMeetings, MeetingImpl::orderByDate);
		return futureMeetings;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PastMeeting> getAllPastMeetings() {
		// treat exactly the same as the future version
		Collections.sort(pastMeetings, MeetingImpl::orderByID);
		Collections.sort(pastMeetings, MeetingImpl::orderByDate);
		return pastMeetings;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPretendNow(Calendar pretendNow) {
		this.pretendNow = pretendNow;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Calendar getPretendNow() {
		return pretendNow;
	}

	/*
	 * private methods
	 */

	/**
	 * check whether a date is in the future N.B. checks against current
	 * date/time unless {@link #overrideDateNow(Calendar) overrideDateNow} has
	 * been used to set a "pretendNow"
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
	 * Checks the contacts are all recognised and appear in stored contacts
	 * 
	 * @param checkContacts
	 *            the single contact or array of contacts to be checked.
	 * @throws IllegalArgumentException
	 *             if any of the contacts does not appear, is supplied as a
	 *             clone or if the contact array is empty.
	 */
	private void checkContacts(Contact... checkContacts) {
		if (checkContacts.length == 0)
			throw new IllegalArgumentException("Empty contact set.");
		for (Contact itCon : checkContacts) {
			// is itCon in this.contacts?
			if (!contacts.contains(itCon))
				throw new IllegalArgumentException("Unknown contact: " + itCon);
			// check that it is not a clone
			Contact original = contacts.get(contacts.indexOf(itCon));
			if (!(original == itCon))
				throw new IllegalArgumentException("Cloned contact: " + itCon
						+ " (this is forbidden).");
		}
	}

	/**
	 * Returns the PAST meeting with the requested ID, or null if it there is
	 * none. Like {@link #getPastMeeting(int) getPastMeeting} except does not
	 * throw an exception if supplied with id of future meeting.
	 *
	 * @param id
	 *            the ID for the meeting
	 * @return the meeting with the requested ID, or null if it there is none.
	 */
	private PastMeeting getPastMeetingNoThrow(int id) {
		// simple crib of future version!
		List<PastMeeting> matchingPMs = pastMeetings.stream()
				.filter(fm -> fm.getId() == id).collect(Collectors.toList());
		if (matchingPMs.size() > 1) // belt and braces
			throw new RuntimeException("Programming Error. "
					+ "Have two past meetings with matching ids: "
					+ matchingPMs);
		return (matchingPMs.size() == 1) ? matchingPMs.get(0) : null;
	}

	/**
	 * Returns the FUTURE meeting with the requested ID, or null if there is
	 * none. Like {@link #getFutureMeeting(int) getFutureMeeting} except that
	 * does not throw exception if supplied with id of a past meeting.
	 *
	 * @param id
	 *            the ID for the meeting
	 * @return the meeting with the requested ID, or null if it there is none.
	 */
	private FutureMeeting getFutureMeetingNoThrow(int id) {
		// Select meetings that matches the id
		// use a stream lambda expression rather than for loop
		// http://stackoverflow.com/questions/22694884/filter-java-stream-to-1-and-only-1-element
		List<FutureMeeting> matchingFMs = futureMeetings.stream()
				.filter(fm -> fm.getId() == id).collect(Collectors.toList());
		if (matchingFMs.size() > 1) // belt and braces
			throw new RuntimeException("Programming Error. "
					+ "Have two future meetings with matching ids: "
					+ matchingFMs);
		// if there is one matching future meeting return it otherwise return
		// null
		return (matchingFMs.size() == 1) ? matchingFMs.get(0) : null;
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

		ret.append("\n\tpast meetings: \n");
		for (PastMeeting itFM : pastMeetings)
			ret.append("\t\t" + itFM + "\n");

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
	public int hashCode() { // generated by eclipse
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result
				+ ((futureMeetings == null) ? 0 : futureMeetings.hashCode());
		result = prime * result
				+ ((pastMeetings == null) ? 0 : pastMeetings.hashCode());
		result = prime * result
				+ ((pretendNow == null) ? 0 : pretendNow.hashCode());
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
		if (futureMeetings == null) {
			if (other.futureMeetings != null)
				return false;
		} else if (!futureMeetings.equals(other.futureMeetings))
			return false;
		if (pastMeetings == null) {
			if (other.pastMeetings != null)
				return false;
		} else if (!pastMeetings.equals(other.pastMeetings))
			return false;
		if (pretendNow == null) {
			if (other.pretendNow != null)
				return false;
		} else if (!pretendNow.equals(other.pretendNow))
			return false;
		return true;
	}

}
