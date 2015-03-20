package uk.fictitiousurl.contactmanager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Class to Manage contacts and Meetings including methods that are missing from
 * {@link ContactManager} needed for both JUnit testing and a functional
 * program.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @version 3.0.01
 * @since 1.0
 */
public interface ContactManagerPlus extends ContactManager, Serializable {
	/**
	 * A simple getter for all contacts. Added it because it would be really
	 * useful in actually writing a program!
	 * 
	 * @return list of all the contacts
	 */
	List<Contact> getAllContacts();

	/**
	 * A simple getter for all future meetings.
	 * 
	 * @return a list of meetings that is chronologically sorted.
	 */
	List<FutureMeeting> getAllFutureMeetings();

	/**
	 * A simple getter for all past meetings
	 * 
	 * @return a list of meetings that is chronologically sorted.
	 */
	List<PastMeeting> getAllPastMeetings();
}
