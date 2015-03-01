import java.util.List;

/**
 * Class to Manage contacts and Meetings including methods that are missing from 
 * {@link ContactManager} needed for both JUnit testing and a functional program.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 28 February 2015
 * 
 */
public interface ContactManagerPlus extends ContactManager {

	/**
	 * A simple getter for all contacts. Not an interface method but I would add
	 * it because it would be really useful in actually writing a program!
	 * 
	 * @return list of all the contacts
	 */
	public List<Contact> getAllContacts();

	/**
	 * A simple getter for all future meetings.
	 * 
	 * @return a list of meetings that is chronologically sorted.
	 */
	public List<FutureMeeting> getAllFutureMeetings();

	/**
	 * A simple getter for all past meetings
	 * 
	 * @return a list of meetings that is chronologically sorted.
	 */
	public List<PastMeeting> getAllPastMeetings();

}
