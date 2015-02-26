import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FileSaveRetrieveTest {

	/**
	 * Test saving state to a string and creating a new ContactManagerImpl from
	 * that object (now passes).
	 */
	@Test
	public void testSaveToXmlStringAndRestore() {
		ContactManagerImpl origCM = new ContactManagerImpl();
		List<String> names = Arrays.asList("John Smith", "Jane Doe",
				"Adam Ant", "Jason Hippo");
		for (String name : names) {
			origCM.addNewContact(name, "<email>");
		}
		String xml = FileSaveRetrieve.toXMLSting(origCM);

		ContactManagerImpl restoreCM = FileSaveRetrieve.fromXMLString(xml);
		assertNotNull(".fromXMLString() should return null!",
				restoreCM);
		assertThat("Restored ContactManager equals original", restoreCM, is(origCM));
	}

}
