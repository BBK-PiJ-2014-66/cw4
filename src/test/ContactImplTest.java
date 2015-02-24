import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;

/**
 * JUnit tests for ContactImpl implementation of Contact
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
				+ "check that getName returns the same.", backName, is(supplyName));
	}
	

	@Test
	public void testGetBackNotes() {
		String supplyName = "Joe Bloggs";
		String supplyNotes = "email: joe.bloggs@fictious.com";
		Contact testContact = new ContactImpl(supplyName, supplyNotes);
		String backNotes = testContact.getNotes();
		assertThat("Use ContactImpl constructor name & notes, "
				+ "check that getName returns the same.", backNotes, is(supplyNotes));
	}
	
	
	

}
