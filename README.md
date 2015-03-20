# Development branch

Please see the [project wiki](https://github.com/BBK-PiJ-2014-66/cw4/wiki) for explanations on the approach taken.


Why a separate branch? See [wiki page](https://github.com/BBK-PiJ-2014-66/cw4/wiki/Packages,-the-use-of-%60development%60-and-branching)

## JavaDoc

[Online version of JavaDoc for project](http://bbk-pij-2014-66.github.io/cw4/javadoc/) (public interfaces).


## Guide to source code

### package `contactmanager` & `contactmanagertest`

#### Interfaces
* As supplied on coursework document (except for the `package` statement)
  * [ContactManager](src/uk/fictitiousurl/contactmanager/ContactManager.java)
  * [Contact](src/uk/fictitiousurl/contactmanager/Contact.java)
  * [Meeting](src/uk/fictitiousurl/contactmanager/Meeting.java)
  * [PastMeeting](src/uk/fictitiousurl/contactmanager/PastMeeting.java)
  * [FutureMeeting](src/uk/fictitiousurl/contactmanager/FutureMeeting.java)
* Own interfaces:
  * [ContactManagerPlus](src/uk/fictitiousurl/contactmanager/ContactManagerPlus.java) extends [ContactManager](src/uk/fictitiousurl/contactmanager/ContactManager.java) 
     adding methods that are useful for both JUnit testing and to produce a functional final program.
  * [FileSaveRetrieve](src/uk/fictitiousurl/contactmanager/FileSaveRetrieve.java) interface

#### Implementation & Tests
* [ContactImpl](src/uk/fictitiousurl/contactmanager/ContactImpl.java) tested by  [ContactImplTest](src/uk/fictitiousurl/contactmanagertest/ContactImplTest.java)
* [ContactManagerImpl](src/uk/fictitiousurl/contactmanager/ContactManagerImpl.java) tested by  [ContactManagerImplTest](src/uk/fictitiousurl/contactmanagertest/ContactManagerImplTest.java) 
**N.B., often tests use [ContactManagerPlus](src/uk/fictitiousurl/contactmanager/ContactManagerPlus.java) rather than simple  [ContactManager](src/uk/fictitiousurl/contactmanager/ContactManager.java) because the interface lacks simple getters and writing tests without these would be painful in the extreme.**
* [MeetingImpl](src/uk/fictitiousurl/contactmanager/MeetingImpl.java) tested by [MeetingImplTest](src/uk/fictitiousurl/contactmanagertest/MeetingImplTest.java).
* [PastMeetingImpl](src/uk/fictitiousurl/contactmanager/PastMeetingImpl.java) tested by [PastMeetingImplTest](src/uk/fictitiousurl/contactmanagertest/PastMeetingImplTest.java).
* [FutureMeetingImpl](src/uk/fictitiousurl/contactmanager/FutureMeetingImpl.java) tested by [FutureMeetingImplTest](src/uk/fictitiousurl/contactmanagertest/FutureMeetingImplTest.java).
* [IdGenerator](src/uk/fictitiousurl/contactmanager/IdGenerator.java) uses `Enum` singleton objects for unique ID's. 
Nice thing as same code can provide separate ID's for Contacts and Meetings.
 
* [FileSaveRetrieveImpl](src/uk/fictitiousurl/contactmanager/FileSaveRetrieveImpl.java) tested by [FileSaveRetrieveTest](src/uk/fictitiousurl/contactmanagertest/FileSaveRetrieveTest.java)


### package `timetools`
* [NowOrPretend](src/uk/fictitiousurl/timetools/NowOrPretend.java) an `Enum` that provides a singleton class supplying either the real system time for now or a "pretend" time.
* [CalendarUtils](src/uk/fictitiousurl/timetools/CalendarUtils.java) utility function (tested in development but altered to package-private so test removed).

### package `serialencoder` & `serialencodertest`

#### Interface & Abstract Class
* Interface [SerialEncoder](src/uk/fictitiousurl/serialencoder/SerialEncoder.java) 
* Abstract Class [SerialEncoderImpl](src/uk/fictitiousurl/serialencoder/SerialEncoderImpl.java)

#### Implementation & Tests
* [SerialEncoderImplJOSBASE64](src/uk/fictitiousurl/serialencoder/SerialEncoderImplJOSBASE64.java) uses Java serialization using `ObjectOutputStream` and `ObjectInputStream` put through Base64.
* [SerialEncoderImplXSTREAMXML](src/uk/fictitiousurl/serialencoder/SerialEncoderImplXSTREAMXML.java) uses  [XStream](http://xstream.codehaus.org/) to serialize to XML format.
* Both implementations are tested by parameterized test [SerialEncoder_Test](src/uk/fictitiousurl/serialencodertest/SerialEncoder_Test.java).

