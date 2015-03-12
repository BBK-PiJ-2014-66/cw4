package serialencodertest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import serialencoder.SerialEncoder;
import serialencoder.SerialEncoderXStreamXML;

/**
 * 
 * JUnit tests for SerialEncoderXStreamXML and SerialEncoderJOSBase64
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 12 March 2015
 * 
 */
@RunWith(Parameterized.class)
public class SerialEncoder_Test {

	
	// Run same tests on the two classes
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { new SerialEncoderXStreamXML() },
				 };
		return Arrays.asList(data);
	}
	@Parameter
	public SerialEncoder sEncoder; // the Parameter
	
	private TestPerson testperson = new TestPerson("John", 75);

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