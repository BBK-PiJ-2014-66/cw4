import java.util.Calendar;
import java.util.Set;

/**
 * "A meeting that was held in the past. It includes your notes about what
 * happened and what was agreed."
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 28 February 2015
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	private String notes;

	/**
	 * 
	 * Constructor for Meeting
	 * 
	 * @param contacts
	 *            list of the participating contact or contacts
	 * @param date
	 *            the scheduled date of the meeting (or when it occurred).
	 * @param notes
	 *            notes about what happened and what was agreed
	 *
	 * @throws NullPointerException
	 *             if any input parameter is null.
	 * @throws IllegalArgumentException
	 *             if supplied with an empty set of contacts
	 */
	public PastMeetingImpl(Set<Contact> contacts, Calendar date, String notes) {
		super(contacts, date);
		if (notes == null)
			throw new NullPointerException("null notes is not allowed.");
		this.notes = notes;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	/**
	 * Add notes to an existing past meeting
	 * 
	 * @param notes
	 *            detailing what happened and what was agreed
	 */
	public void addNotes(String notes) {
		//this.notes = notes;
	}

}
