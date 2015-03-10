# Development branch

Why a separate branch? See [wiki page](https://github.com/BBK-PiJ-2014-66/cw4/wiki/Packages,-the-use-of-%60development%60-and-branching)

## Interfaces
* As supplied on coursework document, except in package `cw4`:
  * [ContactManager](development/src/cw4/ContactManager.java)
  * [Contact](development/src/cw4/Contact.java)
  * [Meeting](development/src/cw4/Meeting.java)
  * [PastMeeting](development/src/cw4/PastMeeting.java)
  * [FutureMeeting](development/src/cw4/FutureMeeting.java)
* Own interfaces:
  * [ContactManagerPlus](development/src/cw4/ContactManagerPlus.java) extends [ContactManager](development/src/cw4/ContactManager.java) 
     adding methods that are needed for both JUnit testing and to produce a functional final program.
  * [FileSaveRetrieve](development/src/cw4/FileSaveRetrieve.java) interface

## Implementation
* [ContactImpl](development/src/cw4/ContactImpl.java) tested by  [ContactImplTest](development/src/test/ContactImplTest.java)
* [ContactManagerImpl](development/src/cw4/ContactManagerImpl.java) tested by  [ContactManagerImplTest](development/src/test/ContactManagerImplTest.java) 
**N.B., often tests use [ContactManagerPlus](development/src/cw4/ContactManagerPlus.java) rather than simple  [ContactManager](development/src/cw4/ContactManager.java) because the interface lacks simple getters and writing tests without these would be painful in the extreme.**
* [MeetingImpl](development/src/cw4/MeetingImpl.java) tested by [MeetingImplTest](development/src/test/MeetingImplTest.java).
* [PastMeetingImpl](development/src/cw4/PastMeetingImpl.java) tested by [PastMeetingImplTest](development/src/test/PastMeetingImplTest.java).
* [FutureMeetingImpl](development/src/cw4/FutureMeetingImpl.java) tested by [FutureMeetingImplTest](development/src/test/FutureMeetingImplTest.java).
* [IdGenerator](development/src/cw4/IdGenerator.java) uses `Enum` singleton objects for unique ID's. 
Nice thing as same code can provide separate ID's for Contacts and Meetings.
* [CalendarUtils](development/src/cw4/CalendarUtils.java) utility functions tested by [CalendarUtilsTest](development/src/test/CalendarUtilsTest.java)
* [FileSaveRetrieveImpl](development/src/cw4/FileSaveRetrieveImpl.java) tested by [FileSaveRetrieveTest](development/src/test/FileSaveRetrieveTest.java) *see below*
Please see the [project wiki](https://github.com/BBK-PiJ-2014-66/cw4/wiki) for explanations on the approach taken.

# Development branch

Why a separate branch? See [wiki page](https://github.com/BBK-PiJ-2014-66/cw4/wiki/Packages,-the-use-of-%60development%60-and-branching)

## Interfaces
* As supplied on coursework document:
  * [ContactManager](development/src/cw4/ContactManager.java)
  * [Contact](development/src/cw4/Contact.java)
  * [Meeting](development/src/cw4/Meeting.java)
  * [PastMeeting](development/src/cw4/PastMeeting.java)
  * [FutureMeeting](development/src/cw4/FutureMeeting.java)
* Own interfaces:
  * [ContactManagerPlus](development/src/cw4/ContactManagerPlus.java) extends [ContactManager](development/src/cw4/ContactManager.java) 
     adding methods that are needed for both JUnit testing and to produce a functional final program.
  * [FileSaveRetrieve](development/src/cw4/FileSaveRetrieve.java) interface

## Implementation
* [ContactImpl](development/src/cw4/ContactImpl.java) tested by  [ContactImplTest](development/src/test/ContactImplTest.java)
* [ContactManagerImpl](development/src/cw4/ContactManagerImpl.java) tested by  [ContactManagerImplTest](development/src/test/ContactManagerImplTest.java) 
**N.B., often tests use [ContactManagerPlus](development/src/cw4/ContactManagerPlus.java) rather than simple  [ContactManager](development/src/cw4/ContactManager.java) because the interface lacks simple getters and writing tests without these would be painful in the extreme.**
* [MeetingImpl](development/src/cw4/MeetingImpl.java) tested by [MeetingImplTest](development/src/test/MeetingImplTest.java).
* [PastMeetingImpl](development/src/cw4/PastMeetingImpl.java) tested by [PastMeetingImplTest](development/src/test/PastMeetingImplTest.java).
* [FutureMeetingImpl](development/src/cw4/FutureMeetingImpl.java) tested by [FutureMeetingImplTest](development/src/test/FutureMeetingImplTest.java).
* [IdGenerator](development/src/cw4/IdGenerator.java) uses `Enum` singleton objects for unique ID's. 
Nice thing as same code can provide separate ID's for Contacts and Meetings.
* [CalendarUtils](development/src/cw4/CalendarUtils.java) utility functions tested by [CalendarUtilsTest](development/src/test/CalendarUtilsTest.java)
* [FileSaveRetrieveImpl](development/src/cw4/FileSaveRetrieveImpl.java) tested by [FileSaveRetrieveTest](development/src/test/FileSaveRetrieveTest.java) *see below*
