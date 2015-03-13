package serialencodertest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import serialencoder.SerialEncoder;
import serialencoder.SerialEncoderImplJOSBASE64;
import serialencoder.SerialEncoderImplXSTREAMXML;

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
		Object[][] data = new Object[][] {
				{ new SerialEncoderImplXSTREAMXML() },
				{ new SerialEncoderImplJOSBASE64() } };
		return Arrays.asList(data);
	}

	@Parameter
	public SerialEncoder sEncoder; // the Parameter

	private TestPerson testperson = new TestPerson("John", 75);
	private String fileName = "testfiles/serialencoder_test.txt";

	/**
	 * test for normal behaviour encode to string and decode check we get back
	 * what we put in
	 */
	@Test
	public void testEncodeDecode() {
		String encoded = sEncoder.encode(testperson);
		TestPerson decoded = (TestPerson) sEncoder.decode(encoded);
		assertThat("\nencode testperson to a string and then decode this\n"
				+ "must get back the same person information as was put in",
				decoded.toString(), is(testperson.toString()));
	}

	/**
	 * Test that we get a RuntimeException after restoring from nonsense string.
	 * Furthermore it should have a meaningful exception message it should
	 * contain "string to contactManager" as it should have been recast.
	 */
	@Test
	public void attemptRestoreFromNonsenseString() {
		// The re-throw of exception includes "Details"
		String require = "Details";
		try {
			sEncoder.decode("This string does not encode anything!");
			fail("Attempt to restore Object from nonsense string\n"
					+ "failed to produce a RuntimeException");

		} catch (RuntimeException ex) {
			assertTrue("Attempt to restore Object from nonsense string\n"
					+ "Failure of test that exception message contains '"
					+ require + "'\n" + "Exception message is:\n " + ex, ex
					.toString().contains(require));
		}

	}

	/**
	 * Save state to fileName
	 * 
	 * want to be able to run separately from
	 * {@link #retrieveStateFromFileName()} so that can examine / copy file
	 * created
	 */
	@Test
	public void saveStateToFileName() {
		File file = new File(fileName);

		if (file.isFile()) { // file already exists
			// remove the file
			try {
				Files.delete(file.toPath());
			} catch (IOException ex) {
				fail("\nTest failed because could not delete file: " + file
						+ "\n" + "Exception detail: " + ex);
			}
		}
		// save our test contact manage object to the file
		sEncoder.saveToFile(testperson, fileName);
		assertTrue("\n.saveToFile(testperson) has failed to create\n"
				+ " the save-state file: '" + fileName + "'", file.isFile());
	}

	/**
	 * retrieve state from file fileName
	 * 
	 */
	@Test
	public void retrieveStateFromFileName() {
		saveStateToFileName(); // run previous test to create the file
		TestPerson getBack = (TestPerson) sEncoder.retreiveFromFile(fileName);
		assertThat("\nsave testperson to a file and then retrieve to getBack\n"
				+ "getBack must have the same person information as was put in.",
				getBack.toString(), is(testperson.toString()));
	}

}

/**
 * Little toy class for tests. A person has a name and an age.
 *
 * Serializable needed for JOSBASE64 but not required for XStreamXML
 */
class TestPerson implements Serializable {
	private static final long serialVersionUID = 1L; // a must for Serializable
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