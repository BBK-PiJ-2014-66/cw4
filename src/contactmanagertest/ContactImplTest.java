package contactmanagertest;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Test;

import contactmanager.Contact;
import contactmanager.ContactImpl;

/**
 * JUnit tests for ContactImpl implementation of Contact. 
 * 
 * All the tests now pass.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 24 February 2015
 *
 */
public class ContactImplTest {

	@Test
	public void testGetBackName() {
		String supplyName = "Joe Bloggs";
		Contact testContact = new ContactImpl(supplyName);
		String backName = testContact.getName();
		assertThat("Use ContactImpl constructor with just a name, "
				+ "check that getName returns the same.", backName,
				is(supplyName));
	}

	@Test
	public void testGetBackNotes() {
		String supplyNotes = "email: joe.bloggs@fictious.com";
		Contact testContact = new ContactImpl("Joe Bloggs", supplyNotes);
		String backNotes = testContact.getNotes();
		assertThat("Use ContactImpl constructor name & notes, "
				+ "check that .getNotes() returns the supplied notes.",
				backNotes, is(supplyNotes));
	}

	@Test
	public void testAddNotes() {
		Contact testContact = new ContactImpl("Joe Bloggs");
		String supplyNotes = "email: joe.bloggs@fictious.com";
		testContact.addNotes(supplyNotes);
		String backNotes = testContact.getNotes();
		assertThat("Supply notes with .addNotes & "
				+ "check that .getNotes() returns the supplied.", backNotes,
				is(supplyNotes));
	}

	@Test
	public void testTwoContactsHaveDistinctIDs() {
		// get ID of two new contacts
		int idA = new ContactImpl("Joe Bloggs").getId();
		int idB = new ContactImpl("Jane Doe").getId();
		assertThat("ID of two contacts should be different.", idA, not(idB));
	}

	/*
	 * supplying a null string as a name should result in a NullPointerException
	 * For method see
	 * http://stackoverflow.com/questions/156503/how-do-you-assert
	 * -that-a-certain -exception-is-thrown-in-junit-4-tests
	 */
	@Test(expected = NullPointerException.class)
	public void testSupplyNullName() {
		new ContactImpl(null);
	}

	/*
	 * supplying null notes should result in a NullPointerException
	 */
	@Test(expected = NullPointerException.class)
	public void testSupplyNullNotes() {
		new ContactImpl("Joe Bloggs", null);
	}

	/*
	 * adding null notes should result in a NullPointerException
	 */
	@Test(expected = NullPointerException.class)
	public void testAddNullNotes() {
		Contact testContact = new ContactImpl("Joe Bloggs");
		testContact.addNotes(null);
	}

}
