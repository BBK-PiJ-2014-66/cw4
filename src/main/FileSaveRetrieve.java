import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 * Deals with saving {@link ContactManagerImpl} state to an external file.
 * Although this functionality could be included in ContactManagerImpl the
 * interface has 13 public methods so structure programming considerations make
 * separation really desirable.
 * 
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 25 February 2015
 * 
 */
class FileSaveRetrieve { // class is package-private

	private FileSaveRetrieve() {
		throw new UnsupportedOperationException("Uninstantiable class");
	}

	/**
	 * writes the ContactManagerImpl information to an XML file (so that than it
	 * be read back in by {@link saveToXMLfile})
	 * 
	 * @param fileName
	 *            name of the file to save to
	 * @param contactManager
	 *            the contactManager to save
	 * @throws IOException
	 *             if there is an error opening or writing to the file
	 */
	protected static void saveToXMLfile(String fileName,
			ContactManagerImpl contactManager) throws IOException {
		// TODO write method

	}

	/**
	 * reads ContactManagerImpl state information from an XML file (written by
	 * {@link saveToXMLfile})
	 * 
	 * @param fileName
	 *            the name of the input file (must already exist).
	 * @return a new ContactManagerImpl object based on the information from
	 *         file
	 * @throws FileNotFoundException
	 *             if file cannot be opened
	 * @throws IOException
	 *             if there is an error reading the file
	 */
	protected static ContactManagerImpl retrieveFromXMLfile(String fileName)
			throws FileNotFoundException, IOException {
		// TODO write method
		return null;
	}

}
