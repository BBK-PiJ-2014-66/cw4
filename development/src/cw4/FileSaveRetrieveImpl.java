package cw4;

import java.io.Serializable;

import serialencoder.SerialEncoder;
import serialencoder.SerialEncoderImplXSTREAMXML;

/**
 * 
 * Deals with saving {@link ContactManagerImpl} state to a string and or an
 * external text file.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 25 February 2015
 * 
 */
public class FileSaveRetrieveImpl implements FileSaveRetrieve, Serializable {

	/**
	 * Needed for Serializable to throw an error if asked to decoded a previous
	 * incompatible version. Need to increment if this class is changed as it is
	 * not backwards compatible with its previous version.
	 */
	private static final long serialVersionUID = 3L;

	/**
	 * name of the file to retrieve-state-from and to save-state-to. The default
	 * value "contacts.txt" is that quoted on CourseWork assignment. Set by
	 * {@link #setFileName(String)}, Get by {@link #getFileName()}.
	 */
	private String fileName = "contacts.txt";

	/**
	 * The encoding to be used for the same to file. This is hard coded here.
	 * 
	 * For development version use XSTREAMXML (better)
	 * 
	 * For "production" (master to-be-marked) version use JOSBASE64
	 * 
	 * See <a href=
	 * "https://github.com/BBK-PiJ-2014-66/cw4/wiki/Storing-data-to-string-file%3A-What-I-found-out-in-implementation"
	 * >Project wiki page</a> for a comparison of methods
	 */
	private final SerialEncoder serialEncoder =
	/* development version: uncomment next line */
	new SerialEncoderImplXSTREAMXML();

	/* production version: uncomment next line */
	// new SerialEncoderImplJOSBASE64();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFileName() {
		return fileName;
	}

	/**
	 * Saves the contactManager to the current {@link #fileName}
	 * 
	 * @param contactManager
	 *            the ContactManager to save
	 * @throws RuntimeException
	 *             if there is an error in encoding the contactManager
	 * @throws java.io.UncheckedIOException
	 *             if there is a problem opening or writing to the file
	 */
	@Override
	public void saveToFile(ContactManagerPlus contactManager) {
		serialEncoder.saveToFile(contactManager, fileName);
	}

	/**
	 * decodes ContactManagerPlus from the current {@link #fileName}, that must
	 * have been written by
	 * {@link #saveToFile(ContactManagerPlus contactManager) saveToFile}.
	 * 
	 * @return the contactManagerPlus encoded in the input string
	 * @throws java.io.UncheckedIOException
	 *             if there is a problem opening or reading from the file
	 * @throws RuntimeException
	 *             if there is a problem in opening the file, reading from it or
	 *             decoding the object
	 */
	@Override
	public ContactManagerPlus retrieveFromFile() {
		Object objRestore = serialEncoder.retreiveFromFile(fileName);
		ContactManagerPlus restore;
		try { // check for casting exception (belt and braces)
			restore = (ContactManagerPlus) objRestore;
		} catch (ClassCastException ex) {
			throw new RuntimeException("Problem casting decoded object.\n "
					+ "Details: " + ex);
		}		
		/*
		 * tidy up after restoration by register all id's read to make sure they
		 * will not be issued twice. This is difficult to unit test as it
		 * depends on the state of the singleton IdGenerator
		 */
		if (restore == null) {
			throw new RuntimeException("retrieveFromFile resulted in"
					+ " null ContactManagerPlus.");
		} else {
			for (Contact itCon : restore.getAllContacts()) {
				int id = itCon.getId();
				IdGenerator.CONTACT.registerExistingID(id);
			}
			for (FutureMeeting itFM : restore.getAllFutureMeetings()) {
				IdGenerator.MEETING.registerExistingID(itFM.getId());
			}
			for (PastMeeting itPM : restore.getAllPastMeetings()) {
				IdGenerator.MEETING.registerExistingID(itPM.getId());
			}
		}
		return restore;
	}
}