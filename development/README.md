# Development branch

Why a separate branch? See [wiki page](https://github.com/BBK-PiJ-2014-66/cw4/wiki/Packages,-the-use-of-%60development%60-and-branching)

## Interfaces
* As supplied on coursework document:
  * [ContactManager](src/cw4/ContactManager.java)
  * [Contact](src/cw4/Contact.java)
  * [Meeting](src/cw4/Meeting.java)
  * [PastMeeting](src/cw4/PastMeeting.java)
  * [FutureMeeting](src/cw4/FutureMeeting.java)
* Own interfaces:
  * [ContactManagerPlus](src/cw4/ContactManagerPlus.java) extends [ContactManager](src/cw4/ContactManager.java) 
     adding methods that are needed for both JUnit testing and to produce a functional final program.
  * [FileSaveRetrieve](src/cw4/FileSaveRetrieve.java) interface

## Implementation
* [ContactImpl](src/cw4/ContactImpl.java) tested by  [ContactImplTest](src/test/ContactImplTest.java)
* [ContactManagerImpl](src/cw4/ContactManagerImpl.java) tested by  [ContactManagerImplTest](src/test/ContactManagerImplTest.java) 
**N.B., often tests use [ContactManagerPlus](src/cw4/ContactManagerPlus.java) rather than simple  [ContactManager](src/cw4/ContactManager.java) because the interface lacks simple getters and writing tests without these would be painful in the extreme.**
* [MeetingImpl](src/cw4/MeetingImpl.java) tested by [MeetingImplTest](src/test/MeetingImplTest.java).
* [PastMeetingImpl](src/cw4/PastMeetingImpl.java) tested by [PastMeetingImplTest](src/test/PastMeetingImplTest.java).
* [FutureMeetingImpl](src/cw4/FutureMeetingImpl.java) tested by [FutureMeetingImplTest](src/test/FutureMeetingImplTest.java).
* [IdGenerator](src/cw4/IdGenerator.java) uses `Enum` singleton objects for unique ID's. 
Nice thing as same code can provide separate ID's for Contacts and Meetings.
* [CalendarUtils](src/cw4/CalendarUtils.java) utility functions tested by [CalendarUtilsTest](src/test/CalendarUtilsTest.java)
* [FileSaveRetrieveImpl](src/cw4/FileSaveRetrieveImpl.java) tested by [FileSaveRetrieveTest](src/test/FileSaveRetrieveTest.java) *see below*





