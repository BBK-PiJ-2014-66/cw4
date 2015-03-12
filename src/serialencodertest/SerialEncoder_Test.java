package serialencodertest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import serialencoder.SerialEncoder;
import serialencoder.SerialEncoderXStreamXML;

/**
 * 
 * JUnit tests for SerialEncoderXStreamXML and SerialEncoderJOSBase64
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 12 March 2015
 * 
 */public class SerialEncoder_Test {

	private TestPerson testperson = new TestPerson("John", 75);
	private SerialEncoder sEncoder = new SerialEncoderXStreamXML();

	@Test
	public void testEncodeDecode() {
		String encoded = sEncoder.encode(testperson);
		System.out
				.println("debug info" + testperson + " encoded is " + encoded);
		TestPerson decoded = (TestPerson) sEncoder.decode(encoded);
		assertThat(decoded.toString(), is(testperson.toString()));
	}

}

/**
 * Little toy class for tests. A person has a name and an age.
 *
 */
class TestPerson {
	private String name;
	private int age;

	TestPerson(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "TestPerson [name=" + name + ", age=" + age + "]";
	}
}