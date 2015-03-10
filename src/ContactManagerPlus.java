package cw4;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Class to Manage contacts and Meetings including methods that are missing from
 * {@link ContactManager} needed for both JUnit testing and a functional
 * program.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 28 February 2015
 * 
 */
public interface ContactManagerPlus extends ContactManager, Serializable {

	/**
	 * Used to set a false current date in place of the real actual date/time
	 * "now". Useful for testing and may be for the program.
	 * 
	 * @param pretendNow
	 *            the date/time to treat as "now".
	 */
	void overrideDateNow(Calendar pretendNow);

	/**
	 * Getter for pretendNow - the overriding date for "now".
	 * 
	 * @return the date/time to treat as "now" or null if no pretend is set (so
	 *         real system time will be used).
	 */
	Calendar getPretendNow();

	/**
	 * Getter for fileSR used for saving the ContactManager to file
	 * 
	 * @return the FileSaveRetrieve object - useful to alter the file name to
	 *         save to and/or the method used
	 */
	FileSaveRetrieve getFileSR();

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