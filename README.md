# Contact Manager
## _to-be-marked-lite_ version

* This is the version as prepared to be assessed. It differs from the [development branch](https://github.com/BBK-PiJ-2014-66/cw4/tree/development) in two ways:
  * it has the `contactmanager` and `contactmanagertest` packages collapsed into the default package to conform to the interfaces in the coursework document.
  * The code to produce XML format save-state file has been removed to avoid having a dependency on [XStream](http://xstream.codehaus.org/) library. Java Serialization with Base64 encoding is used instead.

* Please see the [project wiki](https://github.com/BBK-PiJ-2014-66/cw4/wiki) for explanations on the approach taken.
* Why a separate branch? See [wiki page](https://github.com/BBK-PiJ-2014-66/cw4/wiki/Packages,-the-use-of-%60development%60-and-branching)

## JavaDoc

[Online version of JavaDoc for project](http://bbk-pij-2014-66.github.io/cw4/javadoc/), public interfaces for the [development](https://github.com/BBK-PiJ-2014-66/cw4/tree/development) version rather than the _to-be-marked-lite_ version

## Guide to source code

### default package 

#### Interfaces
* As supplied on coursework document, with some JavaDoc clarifications 
  * [ContactManager](src/ContactManager.java)
  * [Contact](src/Contact.java)
  * [Meeting](src/Meeting.java)
  * [PastMeeting](src/PastMeeting.java)
  * [FutureMeeting](src/FutureMeeting.java)
* Own interfaces:
  * [ContactManagerPlus](src/ContactManagerPlus.java) extends [ContactManager](src/ContactManager.java) 
     adding methods that are useful for both JUnit testing and to produce a functional final program.
  * [FileSaveRetrieve](src/FileSaveRetrieve.java) interface

#### Implementation & Tests
* [ContactImpl](src/ContactImpl.java) tested by  [ContactImplTest](src/ContactImplTest.java)
* [ContactManagerImpl](src/ContactManagerImpl.java) tested by  [ContactManagerImplTest](src/ContactManagerImplTest.java) 
**N.B., often tests use [ContactManagerPlus](src/ContactManagerPlus.java) rather than simple  [ContactManager](src/ContactManager.java) because the interface lacks simple getters and writing tests without these would be painful in the extreme.**
* [MeetingImpl](src/MeetingImpl.java) tested by [MeetingImplTest](src/MeetingImplTest.java).
* [PastMeetingImpl](src/PastMeetingImpl.java) tested by [PastMeetingImplTest](src/PastMeetingImplTest.java).
* [FutureMeetingImpl](src/FutureMeetingImpl.java) tested by [FutureMeetingImplTest](src/FutureMeetingImplTest.java).
* [IdGenerator](src/IdGenerator.java) uses `Enum` singleton objects for unique ID's. 
Nice thing as same code can provide separate ID's for Contacts and Meetings.
 
* [FileSaveRetrieveImpl](src/FileSaveRetrieveImpl.java) tested by [FileSaveRetrieveTest](src/FileSaveRetrieveTest.java)


### package `timetools`
* [NowOrPretend](src/uk/fictitiousurl/timetools/NowOrPretend.java) an `Enum` that provides a singleton class supplying either the real system time for now or a "pretend" time. (Tested in the `contactmanager` tests)
* [CalendarUtils](src/uk/fictitiousurl/timetools/CalendarUtils.java) utility function tested by [CalendarUtilsTest](src/uk/fictitiousurl/timetoolstest/CalendarUtilsTest.java)

### package `serialencoder` & `serialencodertest`

#### Interface & Abstract Class
* Interface [SerialEncoder](src/uk/fictitiousurl/serialencoder/SerialEncoder.java) 
* Abstract Class [SerialEncoderImpl](src/uk/fictitiousurl/serialencoder/SerialEncoderImpl.java)

#### Implementation & Tests
* [SerialEncoderImplJOSBASE64](src/uk/fictitiousurl/serialencoder/SerialEncoderImplJOSBASE64.java) uses Java serialization using `ObjectOutputStream` and `ObjectInputStream` put through Base64.
* ~~[SerialEncoderImplXSTREAMXML](src/uk/fictitiousurl/serialencoder/SerialEncoderImplXSTREAMXML.java) uses  [XStream](http://xstream.codehaus.org/) to serialize to XML format.~~ _removed for the_to-be-marked-lite version_
* Both implementations are tested by parameterized test [SerialEncoder_Test](src/uk/fictitiousurl/serialencodertest/SerialEncoder_Test.java).

