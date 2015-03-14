package test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import contactmanager.ContactManagerImpl;
import contactmanager.ContactManagerPlus;

/**
 * Single test run in isolation:
 * 
 * idea test constructor load from "testfiles/contacts.txt" this is written as
 * file "fileSaveRetrieveTest.txt" by the test
 * {@link test.FileSaveRetrieveTest#saveStateToFileName()}
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 24 February 2015
 *
 */
public class RetrieveContactsDotTxtTest {
	/**
	 * Date for nowCal in contacts.txt
	 */
	private Calendar nowCal = new GregorianCalendar(2014, Calendar.MARCH, 13,
			10, 00);

	@Test
	public void testContactManagerFrom_Test_ContactsDotTxt() {
		String testFile = "testfiles/contacts.txt";
		ContactManagerPlus retrieveCMP = new ContactManagerImpl(testFile);
		// just check number of contacts, # meetings and override date
		assertThat("retrieve contact manager from '" + testFile + "\n"
				+ "This has three contacts.", retrieveCMP.getAllContacts()
				.size(), is(3));
		assertThat("retrieve contact manager from '" + testFile + "\n"
				+ "This has two past meetings.", retrieveCMP
				.getAllPastMeetings().size(), is(2));
		assertThat("retrieve contact manager from '" + testFile + "\n"
				+ "This has three future meetings.", retrieveCMP
				.getAllFutureMeetings().size(), is(3));
		assertThat("retrieve contact manager from '" + testFile + "\n"
				+ "This has pretendNow 13 Mar 2014.", retrieveCMP
				.getPretendNow().getTime(), is(nowCal.getTime()));
		// check for problems in the id generation by adding 1000 contacts
		for (int cc = 0; cc < 1000; cc++)
			retrieveCMP.addNewContact("new contact #" + (cc+1), "notes");

	}

}