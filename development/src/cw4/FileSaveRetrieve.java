package cw4;

/**
 * 
 * Deals with saving {@link ContactManagerImpl} state to a string and or an
 * external text file.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 09 March 2015
 * 
 */
public interface FileSaveRetrieve {
	/**
	 * Setter for FileSaveRetrieveMethod
	 * 
	 * @param method
	 *            the method to save to string/file
	 */
	void setMethod(FileSaveRetrieveMethod method);

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
	 * Saves the contactManager to the current fileName
	 * 
	 * @param contactManager
	 *            the ContactManager to save
	 * @throws RuntimeException
	 *             if there is an error in encoding the contactManager or in
	 *             opening the file or writing to it.
	 */
	void saveToFile(ContactManagerPlus contactManager);

	/**
	 * decodes ContactManagerPlus from the current fileName, that must have been
	 * written by {@link #saveToFile(ContactManagerPlus contactManager)
	 * saveToFile}.
	 * 
	 * @return the contactManagerPlus encoded in the input string
	 * @throws RuntimeException
	 *             if there is a problem in opening the file, reading from it or
	 *             decoding the object
	 */
	ContactManagerPlus retrieveFromFile();

	/**
	 * Converts the supplied contactManager to string so that its data can be
	 * subsequently recovered by {@link #retrieveFromString(String)
	 * retrieveFromString}.
	 * 
	 * @param contactManager
	 *            the contactManager to encode
	 * @return encoded form of the contactManager object
	 * @throws RuntimeException
	 *             if there is a problem encoding the contactManager
	 */
	String saveToString(ContactManagerPlus contactManager);

	/**
	 * decodes ContactManagerPlus from an encoded string written by
	 * {@link #saveToString(ContactManagerPlus contactManager) saveToString}
	 * 
	 * @param string
	 *            the String
	 * @return the contactManagerPlus encoded in the input string
	 * @throws RuntimeException
	 *             if there is a problem decoding the string
	 */
	ContactManagerPlus retrieveFromString(String string);
}