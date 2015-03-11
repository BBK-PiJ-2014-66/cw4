package serialencoder;

/**
 * 
 * Provides methods to encode an Object to a String and to decode it back using
 * some form of serialization.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 11 March 2015
 * 
 */
public interface SerialEncoder {
	/**
	 * Converts the supplied obj to a string so that it can be subsequently
	 * recovered by {@link #???}.
	 * 
	 * @param obj
	 *            the object to encode
	 * @return encoded form of the object
	 * @throws RuntimeException
	 *             if there is any problem encoding the object
	 */
	public String encode(Object obj);

	/**
	 * Decodes a string created by {@link #encode(Object obj} recovering the
	 * Object.
	 * 
	 * @param str
	 *            the String to decode
	 * @return the object
	 * @throws RuntimeException
	 *             if there is any problem decoding the string
	 */
	public Object decode(String str);
}
