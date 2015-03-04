package cw4;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

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
public class FileSaveRetrieve { 

	private FileSaveRetrieve() {
		throw new UnsupportedOperationException("Uninstantiable class");
	}

	/**
	 * writes the ContactManagerImpl information to a file (so that than it
	 * be read back in by {@link retrieveFromfile})
	 * 
	 * @param fileName
	 *            name of the file to save to
	 * @param contactManager
	 *            the contactManager to save
	 * @throws IOException
	 *             if there is an error opening or writing to the file
	 */
	public static void saveToFile(String fileName,
			ContactManagerImpl contactManager) throws IOException {
		// use XML format (for now)
		String xml = saveToString(contactManager);
		System.out.println("Debug need to write to file " + fileName
				+ " xml=\n" + xml);
		// TODO writing to file
	}
	
	/**
	 * reads ContactManagerImpl state information from a file (written by
	 * {@link saveTofile})
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
	public static ContactManagerImpl retrieveFromFile(String fileName)
			throws FileNotFoundException, IOException {
		// TODO write method
		return null;
	}

	/**
	 * Converts the supplied contactManager to a single XML format string using
	 * XStream
	 * 
	 * @param contactManager
	 *            the contactManager to encode
	 * @return XML encoding of the contactManager object
	 */
	public static String saveToString(ContactManagerImpl contactManager) {
		XStream xstream = new XStream(new StaxDriver());
		String xml = xstream.toXML(contactManager);
		return xml;
	}

	/**
	 * reads ContactManagerImpl from an XML string written by
	 * {@link toXMLString}
	 * 
	 * @param xml
	 *            the XML string
	 * @return the contactManagerImpl encoded
	 * @throws someexception???? on error???? //TODO
	 */
	public static ContactManagerImpl retrieveFromString(String xml) {
		XStream xstream = new XStream(new StaxDriver());
		ContactManagerImpl restore = (ContactManagerImpl) xstream.fromXML(xml);
		// TODO ensure that ID's are unique
		// register all id's read so they will not be reissued.
		for (Contact itCon: restore.getAllContacts()) {
			int id = itCon.getId();
			IdGenerator.CONTACT.registerExistingID(id);
		}
		return restore;
	}



}
