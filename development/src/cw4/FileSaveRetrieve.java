package cw4;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

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
	 * subsequently recovered by {@link #retrieveFromString(String)
	 * retrieveFromString}.
	 * 
	 * @param contactManager
	 *            the contactManager to encode
	 * @return encoded form of the contactManager object
	 * @throws RuntimeException
	 *             if there is a problem encoding the contactManager
	 */
	public String saveToString(ContactManagerPlus contactManager) {
		String retStr = null;
		if (method == FileSaveRetrieveMethod.XML)
			retStr = saveToStringXML(contactManager);
		else if (method == FileSaveRetrieveMethod.SERIALIZATION)
			retStr = saveToStringSerialization(contactManager);
		return retStr; // TODO implement serialization
	}

	/**
	 * Converts the supplied contactManager to a string in XML format using
	 * XStream.
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
	 * Converts the supplied contactManager to a string using serialization.
	 * 
	 * @param contactManager
	 *            the contactManager to encode
	 * @return serialized encoding of the contactManager object
	 * @throws RuntimeException
	 *             if there is a problem serializing the contactManager
	 */
	private String saveToStringSerialization(ContactManagerPlus contactManager) {
		/*
		 * originally followed:
		 * 
		 * http://stackoverflow.com/questions/8887197/reliably
		 * -convert-any-object-to-string-and-then-back-again
		 * 
		 * but this fails. So use base64 encoding as shown by
		 * 
		 * http://stackoverflow.com/questions/134492/how-to-serialize-an-object-into
		 * -a-string
		 * 
		 * except use ava8 base64 encoding procedure following
		 * 
		 * http://blog.eyallupu.com/2013/11/base64-encoding-in-java-8.html
		 */
		String str = "";
		// serialize the object
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(contactManager);
			oos.close();
			str = new String(Base64.getEncoder().encodeToString(
					baos.toByteArray()));
		} catch (IOException ex) {
			// recast Exception to Runtime.
			throw new RuntimeException(
					"Error in serialization of contactManager to a String: "
							+ ex);
		}
		return str;
	}

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
	public ContactManagerPlus retrieveFromString(String string) {
		ContactManagerPlus restore = null;
		if (method == FileSaveRetrieveMethod.XML)
			restore = retrieveFromStringXML(string);
		else if (method == FileSaveRetrieveMethod.SERIALIZATION)
			restore = retrieveFromStringSerialization(string);
		// tidy up after restoration //TODO
		return restore;

	}

	/**
	 * decodes ContactManagerPlus from an XML string written by
	 * {@link #saveToStringXML(ContactManagerPlus contactManager)
	 * saveToStringXML}
	 * 
	 * @param xml
	 *            the XML string
	 * @return the contactManagerPlus encoded in the input string
	 * @throws RuntimeException
	 *             if there is a problem decoding the string
	 */
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

/**
	 * decodes ContactManagerPlus from an string written by
	 * {@link #saveToStringSerialization(ContactManagerPlus contactManager)
	 * saveToStringSerialization}
	 * 
	 * @param string written by {@link #saveToStringSerialization(ContactManagerPlus contactManager)
	 * @return the contactManagerPlus encoded in the input string
	 * @throws RuntimeException
	 *             if there is a problem decoding the string
	 */
	private ContactManagerPlus retrieveFromStringSerialization(String string) {
		/*
		 * see saveToStringSerialization for method!
		 */
		ContactManagerPlus restore = null;

		try {
			byte data[] = Base64.getDecoder().decode(string);
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bais);
			restore = (ContactManagerPlus) ois.readObject();
		} catch (ClassNotFoundException | IOException ex) {
			throw new RuntimeException(
					"Error in deserialization of string to contactManager. "
							+ ex);
		}

		return restore;
	}

}
