package uk.fictitiousurl.contactmanager;

/**
 * 
 * Deals with saving {@link ContactManagerImpl} state to a string and or an
 * external text file.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @version 3.0.01
 * @since 1.0
 */
public interface FileSaveRetrieve {

	/**
	 * Setter for the name of the file to read and write contactManager to/from.
	 * 
	 * @param fileName
	 *            the name of the file
	 */
	void setFileName(String fileName);

	/**
	 * Getter for the name of the file to read and write contactManager to
	 * 
	 * @return name of the file
	 */
	String getFileName();

	/**
	 * Saves the contactManager to the current fileName (set by
	 * {@link #setFileName(String)}).
	 * 
	 * @param contactManager
	 *            the ContactManager to save
	 * @throws RuntimeException
	 *             if there is an error in encoding the contactManager
	 * @throws java.io.UncheckedIOException
	 *             if there is a problem opening or writing to the file
	 */
	void saveToFile(ContactManagerPlus contactManager);

	/**
	 * decodes ContactManagerPlus from the current fileName, that must have been
	 * written by {@link #saveToFile(ContactManagerPlus contactManager)
	 * saveToFile}.
	 * 
	 * @return the contactManagerPlus encoded in the input string
	 * @throws java.io.UncheckedIOException
	 *             if there is a problem opening or reading from the file
	 * @throws RuntimeException
	 *             if there is a problem in opening the file, reading from it or
	 *             decoding the object
	 */
	ContactManagerPlus retrieveFromFile();

}