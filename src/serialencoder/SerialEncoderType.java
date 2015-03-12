package serialencoder;

/**
 * 
 * Types of serialization provided
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 11 March 2015
 * 
 */
public enum SerialEncoderType {
	/**
	 * Java serialization using {@link java.io.ObjectOutputStream} and
	 * {@link java.io.ObjectInputStream} put through {@link java.util.Base64}
	 */
	JOSBASE64,
	/**
	 * XML created using XStream <a
	 * href="http://xstream.codehaus.org/">http://xstream.codehaus.org/</a>
	 */
	XSTREAMXML
}
