package cw4;

import java.io.Serializable;

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
	 *             if there is an error in encoding the contactManager or in
	 *             opening the file or writing to it.
	 */
	@Override
	public void saveToFile(ContactManagerPlus contactManager) {
		throw new RuntimeException("saveToFile() needs new implementation"); // TODO
	}

	/**
	 * decodes ContactManagerPlus from the current {@link #fileName}, that must
	 * have been written by
	 * {@link #saveToFile(ContactManagerPlus contactManager) saveToFile}.
	 * 
	 * @return the contactManagerPlus encoded in the input string
	 * @throws RuntimeException
	 *             if there is a problem in opening the file, reading from it or
	 *             decoding the object
	 */
	@Override
	public ContactManagerPlus retrieveFromFile() {
		throw new RuntimeException("retriveToFile() needs new implementation"); // TODO
	}
}