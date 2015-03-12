package serialencoder;

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
public class SerialEncoderXStreamXML extends SerialEncoder {

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
		throw new RuntimeException("encode method not yet implemented"); // TODO
	}

	/**
	 * 
	 * Decodes a string containing <a
	 * href="http://en.wikipedia.org/wiki/XML">XML</a> encoding created by
	 * {@link #encode(Object obj)} recovering the Object.
	 * 
	 * @param str
	 *            the String to decode
	 * @return the object encoded in str.
	 * @throws RuntimeException
	 *             if there is any problem decoding the string
	 *
	 */
	@Override
	public Object decode(String str) {
		throw new RuntimeException("decode method not yet implemented"); // TODO
	}

}
