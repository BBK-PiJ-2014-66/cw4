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
public abstract class SerialEncoder {
	/**
	 * Converts the supplied obj to a string so that it can be subsequently
	 * recovered by {@link #decode(String str)}.
	 * 
	 * @param obj
	 *            the object to encode (may need to be Serializable)
	 * @return encoded form of the object
	 * @throws RuntimeException
	 *             if there is any problem encoding the object
	 */
	public abstract String encode(Object obj);

	/**
	 * Decodes a string created by {@link #encode(Object obj)} recovering the
	 * Object.
	 * 
	 * @param str
	 *            the String to decode
	 * @return the object
	 * @throws RuntimeException
	 *             if there is any problem decoding the string
	 */
	public abstract Object decode(String str);

	/**
	 * saves the object to a file, so that it can be read at a later time by
	 * {@link retreiveFromFile}
	 * 
	 * @param obj the Object to be saved (may need to be Serializable)
	 * @param outFileName the name for the output file
	 * @throws RuntimeException
	 *             if there is any problem opening the file or encoding the
	 *             object
	 */
	public void saveToFile(Object obj, String outFileName) {
		throw new RuntimeException("not yet implemented"); // TODO
	}

	/**
	 * retrieves an object from a file created by {@link saveToFile}
	 * 
	 * @param inFileName the name for the input file (must exist)
	 * @return the Object encoded in the file
	 */
	public Object retreiveFromFile(String inFileName) {
		throw new RuntimeException("not yet implemented"); // TODO
	}

}
