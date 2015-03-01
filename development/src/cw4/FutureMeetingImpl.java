package cw4;

import java.util.Calendar;
import java.util.Set;

/**
 * "A meeting to be held in the future"
 * 
 * "No methods here, this is just a naming interface 
 * (i.e. only necessary for type checking and/or downcasting)"
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 28 February 2015
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
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
		return  super.toString();
	}
	
}
