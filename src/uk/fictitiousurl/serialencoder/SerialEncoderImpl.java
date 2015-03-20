package uk.fictitiousurl.serialencoder;

/**
 * 
 * Provides methods to encode an Object to a String or a file and to decode it
 * back using some form of serialization.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 11 March 2015
 * 
 */
public abstract class SerialEncoderImpl implements SerialEncoder {
	/**
	 * Converts the supplied obj to a string so that it can be subsequently
	 * recovered by {@link #decode(String str)}.
	 * 
	 * @param obj
	 *            the object to encode (may need to implement
	 *            {@link java.io.Serializable})
	 * @return encoded String containing information about the object
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
	@Override
	public abstract Object decode(String str);

	/**
	 * saves the object to a file, so that it can be read at a later time by
	 * {@link retreiveFromFile}
	 * 
	 * @param obj
	 *            the Object to be saved (may need to be Serializable)
	 * @param outFileName
	 *            the name for the output file
	 * @throws java.io.UncheckedIOException
	 *             if there is a problem opening or writing to the file
	 * @throws RuntimeException
	 *             if there is a problem in encoding the object
	 */
	@Override
	public void saveToFile(Object obj, String outFileName) {
		String encoded = encode(obj); // implemented in daughter class.
		FileUtils.stringToFile(encoded, outFileName);
	}

	/**
	 * retrieves an object from a file created by {@link saveToFile}
	 * 
	 * @param inFileName
	 *            the name for the input file (must exist)
	 * @return the Object encoded in the file
	 * @throws java.io.UncheckedIOException
	 *             if there is a problem opening or reading from the file
	 * @throws RuntimeException
	 *             if there is a problem in decoding the object

	 */
	@Override
	public Object retreiveFromFile(String inFileName) {
		String strFromFile = FileUtils.fileContentsToString(inFileName);
		return decode(strFromFile);
	}

}
