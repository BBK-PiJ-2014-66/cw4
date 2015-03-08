package cw4;

/**
 * 
 * Supported formats to save the state of the contact manager to
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 28 February 2015
 * 
 */
public enum FileSaveRetrieveMethod {
	/**
	 * XML created using <a href="http://xstream.codehaus.org/">XStream</a>
	 */
	XML, // (development only)
	/**
	 * base64 encoded Serialization
	 */
	SERIALIZATION // (development and final submission)
}
