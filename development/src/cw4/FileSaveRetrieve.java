package cw4;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
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
public class FileSaveRetrieve implements Serializable {

	/**
	 * Needed for Serializable to throw an error if decoding a previous
	 * incompatible version. Need to increment if this class is changed so it is
	 * not backwards compatible with its previous version.
	 */
	private static final long serialVersionUID = 1L;

	private FileSaveRetrieveMethod method = FileSaveRetrieveMethod.SERIALIZATION;

	/**
	 * name of the file to retrieve-state-from and to save-state-to. The default
	 * value "contacts.txt" is that quoted on CourseWork assignment. Set by
	 * {@link #setFileName(String)}, Get by {@link #getFileName()}.
	 */
	private String fileName = "contacts.txt";

	/**
	 * Sets the
	 * 
	 * @param method
	 *            the method to save to string/file
	 */
	public void setMethod(FileSaveRetrieveMethod method) {
		this.method = method;
	}

	/**
	 * Setter for the name of the file to read and write contactManager to/from.
	 * 
	 * @param fileName
	 *            the name of the file
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Getter for the name of the file to read and write contactManager to
	 * 
	 * @return name of the file
	 */
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
	public void saveToFile(ContactManagerPlus contactManager) {
		String cmStr = saveToString(contactManager);
		stringToFile(cmStr, fileName);
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
	public ContactManagerPlus retrieveFromFile() {
		String strFromFile = fileContentsToString(fileName);
		// decode string
		return retrieveFromString(strFromFile);
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
		return retStr;
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
		/*
		 * tidy up after restoration by register all id's read to make sure they
		 * will not be issued twice. This is difficult to unit test as it
		 * depends on the state of the singleton IdGenerator
		 */
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

		return restore;

	}

	/**
	 * Converts the supplied contactManager to a string in XML format using <a
	 * href="http://xstream.codehaus.org/">XStream</a>
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
		try {
			XStream xstream = new XStream(new StaxDriver());
			ContactManagerPlus restore = (ContactManagerPlus) xstream
					.fromXML(xml);
			return restore;
		} catch (Exception ex) {
			// complex procedures! So catch any exception (a bit naughty) and
			// recast it to Runtime with a meaningful prefix
			throw new RuntimeException(
					"Error in decodingXML string to contactManager. Details: "
							+ ex);
		}

	}

	/**
	 * Converts the supplied contactManager to a string using serialization and
	 * base64 encoding.
	 * 
	 * @param contactManager
	 *            the contactManager to encode
	 * @return serialized encoding of the contactManager object
	 * @throws RuntimeException
	 *             if there is a problem serializing the contactManager
	 */
	private String saveToStringSerialization(ContactManagerPlus contactManager) {
		/*
		 * serialize the object, originally followed:
		 * 
		 * http://stackoverflow.com/questions/8887197/reliably
		 * -convert-any-object-to-string-and-then-back-again
		 * 
		 * but this fails. So use base64 encoding instead as shown by
		 * 
		 * http://stackoverflow.com/questions/134492/how-to-serialize-an-object-into
		 * -a-string
		 * 
		 * except use Java8 base64 encoding procedure following
		 * 
		 * http://blog.eyallupu.com/2013/11/base64-encoding-in-java-8.html
		 */
		String str = "";
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(contactManager);
			oos.close();
			str = Base64.getEncoder().encodeToString(baos.toByteArray());
		} catch (Exception ex) {
			// complex procedures! So catch any exception (a bit naughty) and
			// recast it to Runtime with a meaningful prefix
			throw new RuntimeException(
					"Error in serialization of contactManager to a String. Details: "
							+ ex);
		}
		return str;
	}

	/**
	 * 
	 * decodes ContactManagerPlus from an string written by
	 * {@link #saveToStringSerialization(ContactManagerPlus contactManager)
	 * saveToStringSerialization}
	 * 
	 * @param string
	 *            written by saveToStringSerialization
	 * @return the contactManagerPlus encoded in the input string
	 * @throws RuntimeException
	 *             if there is a problem decoding the string
	 * 
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
		} catch (Exception ex) {
			// complex procedures! So catch any exception (a bit naughty) and
			// recast it to Runtime with a meaningful prefix
			throw new RuntimeException(
					"Error in deserialization of string to contactManager. Details: "
							+ ex);

		}

		return restore;
	}

	/**
	 * writes out the string to a file.
	 * 
	 * @param inStr
	 *            the string to write
	 * @param outFile
	 *            the file to write to
	 * @throws RuntimeException
	 *             if there is an error creating the file
	 */
	private static void stringToFile(String inStr, String outFile) {
		// Use PrintWriter based on PiJ Day 16 sheet I/O 1.3.2
		try (PrintWriter pwout = new PrintWriter(outFile)) {
			pwout.write(inStr);
		} catch (FileNotFoundException ex) {
			throw new RuntimeException("Error creating file '" + outFile
					+ "' to write to. Exception details: " + ex);
		}
	}

	/**
	 * Reads the complete contents of a file to a string - preserving line
	 * breaks
	 * 
	 * @param inFile
	 *            the file of the file
	 * @return the contents of the file as a single string
	 */
	private static String fileContentsToString(String inFile) {
		// adapted from sdp cw1 Translator (after bug fix) and Eckle
		// "Thinking Java" p 927 using scanner but this gives newline strip
		// problems.
		//
		// then adapted to use BufferedReader .read()
		// to read the file character by character from
		// http://stackoverflow.com/questions/811851/how-do-i-read-input-character-by-character-in-java
		StringBuilder stringBuilder = new StringBuilder();
		// try with resources to avoid cleanup close, final stuff.
		try (FileInputStream fis = new FileInputStream(inFile);
				InputStreamReader isr = new InputStreamReader(fis);
				Reader reader = new BufferedReader(isr)) {
			int anInt; // an integer
			while ((anInt = reader.read()) != -1) { // EOF is -1
				char asChar = (char) anInt;
				stringBuilder.append(asChar);
			}
		} catch (FileNotFoundException ex) {
			throw new RuntimeException("Error opening file '" + inFile
					+ "' to read. Exception details: " + ex);
		} catch (IOException ex) {
			throw new RuntimeException("Error in read from file '" + inFile
					+ "'. Exception details: " + ex);
		}
		return stringBuilder.toString();
	}

}
