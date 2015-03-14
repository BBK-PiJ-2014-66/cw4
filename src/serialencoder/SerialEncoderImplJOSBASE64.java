package serialencoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

/**
 * 
 * Uses Java serialization using {@link java.io.ObjectOutputStream} and
 * {@link java.io.ObjectInputStream} put through {@link java.util.Base64} to
 * serialize to TXT format. Note that it requires the Object and underlying
 * Objects to implement {@link java.io.Serializable}.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 12 March 2015
 * 
 */
public class SerialEncoderImplJOSBASE64 extends SerialEncoderImpl implements
		Serializable {
	
	/**
	 * Needed for Serializable to throw an error if asked to decoded a previous
	 * incompatible version. Need to increment if this class is changed as it is
	 * not backwards compatible with its previous version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Uses Java serialization using {@link java.io.ObjectOutputStream} and
	 * {@link java.util.Base64} to convert the supplied obj to a text string so
	 * that it can be subsequently recovered by {@link #decode(String str)}.
	 * 
	 * @param obj
	 *            the object to encode. N.B. obj MUST implement
	 *            {@link java.io.Serializable}.
	 * @return encoded String containing information about the object in TXT
	 *         format.
	 * @throws RuntimeException
	 *             if there is any problem encoding the object
	 */
	@Override
	public String encode(Object obj) {
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
			oos.writeObject(obj);
			oos.close();
			str = Base64.getEncoder().encodeToString(baos.toByteArray());
		} catch (Exception ex) {
			// complex procedures! So catch any exception (a bit naughty) and
			// recast it to Runtime with a meaningful prefix
			throw new RuntimeException(
					"Error in JOSBASE64 serialization of Object to a String. Details: "
							+ ex);
		}
		return str;
	}

	/**
	 * 
	 * Decodes a string containing JOSBASE64 encoding created by
	 * {@link #encode(Object obj)} recovering the Object.
	 * 
	 * @param txt
	 *            the String to decode
	 * @return the object encoded in txt.
	 * @throws RuntimeException
	 *             if there is any problem decoding the string
	 *
	 */
	@Override
	public Object decode(String txt) {
		Object restore = null;
		try {
			byte data[] = Base64.getDecoder().decode(txt);
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ObjectInputStream ois = new ObjectInputStream(bais);
			restore = ois.readObject();
		} catch (Exception ex) {
			// complex procedures! So catch any exception (a bit naughty) and
			// recast it to Runtime with a meaningful prefix
			throw new RuntimeException(
					"Error in JOSBASE64 deserialization of string to Object. Details: "
							+ ex);

		}
		return restore;
	}

}
