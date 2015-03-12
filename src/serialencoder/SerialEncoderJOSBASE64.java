package serialencoder;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * 
 * Uses * Java serialization using {@link java.io.ObjectOutputStream} and
 * {@link java.io.ObjectInputStream} put through {@link java.util.Base64} to
 * serialize to TXT format. Note that it requires the Object and underlying
 * Objects to implement {@link java.io.Serializable}.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 12 March 2015
 * 
 */
public class SerialEncoderJOSBASE64 extends SerialEncoder {

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
		throw new RuntimeException("not yet implemented"); // TODO
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
		throw new RuntimeException("not yet implemented"); // TODO
	}

}
