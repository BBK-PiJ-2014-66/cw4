import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FileSaveRetrieveTest {

	/**
	 * Test saving state to a string and creating a new ContactManagerImpl from
	 * that object
	 */
	@Test
	public void testSaveToXmlStringAndRestore() {
		ContactManagerImpl testCM = new ContactManagerImpl();
		List<String> names = Arrays.asList("John Smith", "Jane Doe",
				"Adam Ant", "Jason Hippo");
		for (String name : names) {
			testCM.addNewContact(name, "<email>");
		}
		String xml = FileSaveRetrieve.toXMLSting(testCM);
		fail("test not yet implemented xml= \n" + xml);
	}

}
