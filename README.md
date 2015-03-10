# PiJ CW4 Contact Manager

* Please see the [project wiki](https://github.com/BBK-PiJ-2014-66/cw4/wiki) for explanation of the approach taken and 
a comparison between the different approaches to Serialization and JavaDoc Pages.

* Note that development was done in the `development` directory, so switch branch to see a file's git history.


## Interfaces
* As supplied on coursework pdf document:
  * [ContactManager](src/ContactManager.java)
  * [Contact](src/Contact.java)
  * [Meeting](src/Meeting.java)
  * [PastMeeting](src/PastMeeting.java)
  * [FutureMeeting](src/FutureMeeting.java)
* Own interfaces:
  * [ContactManagerPlus](src/ContactManagerPlus.java) extends [ContactManager](src/ContactManager.java) 
     adding methods that are needed for both JUnit testing and to produce a functional final program.
  * [FileSaveRetrieve](src/FileSaveRetrieve.java) interface

## Implementation
* [ContactImpl](src/ContactImpl.java) tested by  [ContactImplTest](junit/ContactImplTest.java)
* [ContactManagerImpl](src/ContactManagerImpl.java) tested by  [ContactManagerImplTest](junit/ContactManagerImplTest.java) 
**N.B., often tests use [ContactManagerPlus](src/ContactManagerPlus.java) rather than simple  [ContactManager](src/ContactManager.java) because the interface lacks simple getters and writing tests without these would be painful in the extreme.**
* [MeetingImpl](src/MeetingImpl.java) tested by [MeetingImplTest](junit/MeetingImplTest.java).
* [PastMeetingImpl](src/PastMeetingImpl.java) tested by [PastMeetingImplTest](junit/PastMeetingImplTest.java).
* [FutureMeetingImpl](src/FutureMeetingImpl.java) tested by [FutureMeetingImplTest](junit/FutureMeetingImplTest.java).
* [IdGenerator](src/IdGenerator.java) uses `Enum` singleton objects for unique ID's. 
Nice thing as same code can provide separate ID's for Contacts and Meetings.
* [CalendarUtils](src/CalendarUtils.java) utility functions tested by [CalendarUtilsTest](junit/CalendarUtilsTest.java)
* [FileSaveRetrieveImpl](src/FileSaveRetrieveImpl.java) tested by [FileSaveRetrieveTest](junit/FileSaveRetrieveTest.java) *see below*

Oliver Smart <osmart01@dcs.bbk.ac.uk>


