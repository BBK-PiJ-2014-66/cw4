package uk.fictitiousurl.serialencoder;

import java.io.Serializable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * Uses XStream <a
 * href="http://xstream.codehaus.org/">http://xstream.codehaus.org/</a> to
 * serialize to <a href="http://en.wikipedia.org/wiki/XML">XML</a> format. Note
 * that XStream does not require the Object to implement
 * {@link java.io.Serializable}.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 12 March 2015
 * 
 */
public class SerialEncoderImplXSTREAMXML extends SerialEncoderImpl implements
		Serializable {

	/**
	 * Needed for Serializable to throw an error if asked to decoded a previous
	 * incompatible version. Need to increment if this class is changed as it is
	 * not backwards compatible with its previous version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Uses <a href="http://xstream.codehaus.org/">XStream</a> and <a
	 * href="http://en.wikipedia.org/wiki/XML">XML</a> to convert the supplied
	 * obj to a string so that it can be subsequently recovered by
	 * {@link #decode(String str)}.
	 * 
	 * @param obj
	 *            the object to encode (does NOT need to implement
	 *            {@link java.io.Serializable})).
	 * @return encoded String containing information about the object in XML
	 *         format.
	 * @throws RuntimeException
	 *             if there is any problem encoding the object
	 */
	@Override
	public String encode(Object obj) {
		XStream xstream = new XStream(new StaxDriver());
		String xml = xstream.toXML(obj);
		return xml;
	}

	/**
	 * 
	 * Decodes a string containing <a
	 * href="http://en.wikipedia.org/wiki/XML">XML</a> encoding created by
	 * {@link #encode(Object obj)} recovering the Object.
	 * 
	 * @param xml
	 *            the String to decode
	 * @return the object encoded in str.
	 * @throws RuntimeException
	 *             if there is any problem decoding the string
	 *
	 */
	@Override
	public Object decode(String xml) {
		try {
			XStream xstream = new XStream(new StaxDriver());
			Object restore = xstream.fromXML(xml);
			return restore;
		} catch (Exception ex) {
			// complex procedures! So catch any exception (a bit naughty) and
			// recast it to Runtime with a meaningful prefix
			throw new RuntimeException(
					"Error in decodingXML string to an Object. Details: " + ex);
		}
	}

}
