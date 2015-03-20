package uk.fictitiousurl.contactmanager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * "A meeting to be held in the future"
 * 
 * "No methods here, this is just a naming interface (i.e. only necessary for
 * type checking and/or downcasting)"
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 28 February 2015
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting,
		Serializable {

	/**
	 * Needed for Serializable to throw an error if decoding a previous
	 * incompatible version. Need to increment if this class is changed so it is
	 * not backwards compatible with its previous version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for FutureMeeting
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
	public FutureMeetingImpl(Set<Contact> contacts, Calendar date) {
		super(contacts, date);
	}

	// thats all no methods

	/**
	 * use Meeting method
	 */
	@Override
	public String toString() {
		return super.toString();
	}

}
