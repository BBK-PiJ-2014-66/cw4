# Development branch

Please see the [project wiki](https://github.com/BBK-PiJ-2014-66/cw4/wiki) for explanations on the approach taken.


Why a separate branch? See [wiki page](https://github.com/BBK-PiJ-2014-66/cw4/wiki/Packages,-the-use-of-%60development%60-and-branching)

## JavaDoc

[Online version of JavaDoc for project](http://bbk-pij-2014-66.github.io/cw4/javadoc/) (public interfaces).


## Guide to source code

### package `contactmanager` & `contactmanagertest`

#### Interfaces
* As supplied on coursework document (except for the `package` statement)
  * [ContactManager](src/contactmanager/ContactManager.java)
  * [Contact](src/contactmanager/Contact.java)
  * [Meeting](src/contactmanager/Meeting.java)
  * [PastMeeting](src/contactmanager/PastMeeting.java)
  * [FutureMeeting](src/contactmanager/FutureMeeting.java)
* Own interfaces:
  * [ContactManagerPlus](src/contactmanager/ContactManagerPlus.java) extends [ContactManager](src/contactmanager/ContactManager.java) 
     adding methods that are useful for both JUnit testing and to produce a functional final program.
  * [FileSaveRetrieve](src/contactmanager/FileSaveRetrieve.java) interface

#### Implementation & Tests
* [ContactImpl](src/contactmanager/ContactImpl.java) tested by  [ContactImplTest](src/contactmanagertest/ContactImplTest.java)
* [ContactManagerImpl](src/contactmanager/ContactManagerImpl.java) tested by  [ContactManagerImplTest](src/contactmanagertest/ContactManagerImplTest.java) 
**N.B., often tests use [ContactManagerPlus](src/contactmanager/ContactManagerPlus.java) rather than simple  [ContactManager](src/contactmanager/ContactManager.java) because the interface lacks simple getters and writing tests without these would be painful in the extreme.**
* [MeetingImpl](src/contactmanager/MeetingImpl.java) tested by [MeetingImplTest](src/contactmanagertest/MeetingImplTest.java).
* [PastMeetingImpl](src/contactmanager/PastMeetingImpl.java) tested by [PastMeetingImplTest](src/contactmanagertest/PastMeetingImplTest.java).
* [FutureMeetingImpl](src/contactmanager/FutureMeetingImpl.java) tested by [FutureMeetingImplTest](src/contactmanagertest/FutureMeetingImplTest.java).
* [IdGenerator](src/contactmanager/IdGenerator.java) uses `Enum` singleton objects for unique ID's. 
Nice thing as same code can provide separate ID's for Contacts and Meetings.
* [CalendarUtils](src/contactmanager/CalendarUtils.java) utility function (tested in development but altered to package-private so test removed). 
* [FileSaveRetrieveImpl](src/contactmanager/FileSaveRetrieveImpl.java) tested by [FileSaveRetrieveTest](src/contactmanagertest/FileSaveRetrieveTest.java)


### package `serialencoder` & `serialencodertest`

#### Interface & Abstract Class
* Interface [SerialEncoder](src/serialencoder/SerialEncoder.java) 
* Abstract Class [SerialEncoderImpl](src/serialencoder/SerialEncoderImpl.java)

#### Implementation & Tests


