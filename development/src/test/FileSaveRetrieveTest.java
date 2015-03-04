package test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cw4.Contact;
import cw4.ContactManagerImpl;
import cw4.FileSaveRetrieve;

public class FileSaveRetrieveTest {

	/**
	 * Test restoring state from a a hard coded string this is like a program
	 * starting up. Add a new contact and check whether there is a conflict.
	 * 
	 * Make this the first test to avoid altering state first.
	 */
	@Test
	public void restoreFromXmlStringAddNewContact() {		// TODO test fails!
		// hard coded XML string for a single contact "A" with notes "B"
		// Obtained from using following code and cut/paste:
		/*
		 * ContactManagerImpl getXML = new ContactManagerImpl();
		 * getXML.addNewContact("A","B"); System.out.println("Get xml=\n" +
		 * FileSaveRetrieve.toXMLSting(getXML));
		 */
		String xml = "<?xml version=\"1.0\" ?><ContactManagerImpl><contacts><ContactImpl><name>A</name><notes>B</notes><id>1</id></ContactImpl></contacts></ContactManagerImpl>";
		ContactManagerImpl testCM = FileSaveRetrieve.retrieveFromString(xml);
		// addNewContact now checks whether the new ID conflicts with any existing
		// and raises an RuntimeException if this happens.
		testCM.addNewContact("New Person", "new notes"); 

		// there should be two contacts
		List<Contact> contacts = testCM.getAllContacts();
		assertThat("Get one contact from initial XML and adding another,"
				+ " so there should be two contacts.", contacts.size(), is(2));
		// check id's are distinct
		int id0 = contacts.get(0).getId();
		int id1 = contacts.get(1).getId();
		assertThat("Get one contact from initial XML and adding another,"
				+ " IDs must not be the same.", id0, not(id1));
	}

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
		String xml = FileSaveRetrieve.saveToString(origCM);
		System.out.println(xml);
		ContactManagerImpl restoreCM = FileSaveRetrieve.retrieveFromString(xml);

		assertThat("Restored ContactManager equals original", restoreCM,
				is(origCM));
	}

}
