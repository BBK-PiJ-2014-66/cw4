package test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cw4.Contact;
import cw4.ContactImpl;
import cw4.ContactManager;
import cw4.ContactManagerImpl;
import cw4.ContactManagerPlus;
import cw4.FutureMeeting;
import cw4.Meeting;
import cw4.PastMeeting;

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