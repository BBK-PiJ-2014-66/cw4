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
	 * Java ObjectOutputStream ObjectInputStream encoded put through Base64 
	 */
	JOSBASE64,
	/**
	 * XML created using <a href="http://xstream.codehaus.org/">XStream</a>
	 */
	XSTREAMXML
}
