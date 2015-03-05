package cw4;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * 
 * Deals with saving {@link ContactManagerImpl} state to a string and or an
 * external text file.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 25 February 2015
 * 
 */
public class FileSaveRetrieve {

	private FileSaveRetrieveMethod method = FileSaveRetrieveMethod.SERIALIZATION;

	// private String saveFileName = "contacts.txt";

	/**
	 * Sets the
	 * 
	 * @param method
	 *            the method to save to string/file
	 */
	public void setMode(FileSaveRetrieveMethod method) {
		this.method = method;
	}

	/**
	 * Converts the supplied contactManager to string so that its data can be
	 * recovered by {@link #retrieveFromString(String)}.
	 * 
	 * @param contactManager
	 *            the contactManager to encode
	 * @return encoded form of the contactManager object
	 */
	public String saveToString(ContactManagerPlus contactManager) {
		if (method == FileSaveRetrieveMethod.XML)
			return saveToStringXML(contactManager);
		return null; // TODO implement serialization
	}

	/**
	 * Converts the supplied contactManager to a string in XML format using
	 * XStream
	 * 
	 * @param contactManager
	 *            the contactManager to encode
	 * @return XML encoding of the contactManager object
	 */
	private String saveToStringXML(ContactManagerPlus contactManager) {
		XStream xstream = new XStream(new StaxDriver());
		String xml = xstream.toXML(contactManager);
		return xml;
	}

	/**
	 * reads ContactManagerPlus from an XML string written by
	 * {@link toXMLString}
	 * 
	 * @param xml
	 *            the XML string
	 * @return the contactManagerPlus encoded
	 * @throws someexception
	 *             ???? on error???? //TODO
	 */
	public ContactManagerPlus retrieveFromString(String string) {
		if (method == FileSaveRetrieveMethod.XML)
			return retrieveFromStringXML(string);
		return null; // TODO implement serialization

	}

	private ContactManagerPlus retrieveFromStringXML(String xml) {
		XStream xstream = new XStream(new StaxDriver());
		ContactManagerPlus restore = (ContactManagerPlus) xstream.fromXML(xml);
		// register all id's read so they will not be reissued.
		for (Contact itCon : restore.getAllContacts()) {
			int id = itCon.getId();
			IdGenerator.CONTACT.registerExistingID(id);
		}
		// TODO need to register meeting id's as well. 
		return restore;
	}

}
