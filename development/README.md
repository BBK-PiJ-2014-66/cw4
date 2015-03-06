# PiJ Coursework Four
## Development Work
*Doing development work in a separate directory to allow use of
packages* In the end will use to produce an "official-to-be-marked-submission".

* The assignment document [cw4.pdf](cw4.pdf)
* Due: **your github repo will be cloned on Sunday, 22nd March 2015 @ 23:59**

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
* [FileSaveRetrieve](src/cw4/FileSaveRetrieve.java) tested by [FileSaveRetrieveTest](src/test/FileSaveRetrieveTest.java) *see below*


## Storing data to file: initial ideas
The coursework document specifies :

**When the application is closed, all data must be stored in a text file called "contacts.txt". This file must be read at startup to recover all data introduced in a former session (the format of the file if up to you: you can use XML, comma-separated values (CSV), or any other format).**

### Possibility one **Java Serialization**
On the Moodle Keith suggested that we look at the use of Java Serialization particularly the
[Lars Vogel Tutorial](http://www.vogella.com/tutorials/JavaSerialization/article.html). This is attractively simple but has the disadvantage that that approach does not produce a *text* output as so does not strictly meet the spec (a binary file called *contacts.txt* would seem to be a really bad idea). To get around the problem one could encode the binary for instance using base64 encoding. Getting ugly and in any case making the file human readable would seem to be a good idea. So reject for now but keep as a backup plan in case the use of 3rd party libraries is verboten.

### Possibility two **DIY file write**
Write code explicitly dump to a custom text format. Done too much of this in the past. This is a bit of a nightmare requiring how to encode user fields that include the delimiters.

### Possibility three **XML or JSON with DIY writer**
Use a decent human and other readable exchange format. XML is a good idea. JSON is another good (better like betamax). But once again I have waste a lot of time maintaining/extending DIY XML writer.

### Possibility four **use XSTREAM to read/write XML**
There are many many ways of writing XML in java. Looking through these the easiest for a newbie is XSTREAM that uses reflection.
Got this working easily - amazingly simple to produce a meaningful XML file that could be easily transferred/read by other programs. *But it introduces a 3rd party Jar into the project* **So it is probably forbidden** May carry on and then strip back to **Possibility one Java Serialisation**

### Possibility five **use Java SE methods to read/write XML**
Useful [StackOverFlow contribution](http://stackoverflow.com/questions/9256669/java-built-in-data-parser-for-json-or-xml-or-else#9296657). Of the four possibilities [XMLStreamWriter](http://tutorials.jenkov.com/java-xml/stax-xmlstreamwriter.html) looks simplest to you (but one would end up with DIY writer that used XML mark-up).

## Storing data to string/file: What I found out in implementation
* It seemed sensible to split the job into two parts. First worked out how to encode the object to a String and to decode from it.
Then once this was coded/tested it was simple to use this to write to/read from a file.
* Using XSTREAM is really easy. XSTREAM uses reflection and was able to make a sensible, structured human readable XML file *without any modifications of the underlying classes*.
* **Serialization to string** was more difficult to get to work. It was necessary to change the underlying classes to use *Serializable*.
The initial implementation using the [procedure proposed on many web sites](http://stackoverflow.com/questions/8887197/reliably-convert-any-object-to-string-and-then-back-again) could encode but failed to decode.
Got an [alternative method](http://stackoverflow.com/questions/134492/how-to-serialize-an-object-into-a-string) that uses
[base64 encoding](http://en.wikipedia.org/wiki/Base64) worked (using 
[Java 8 base64 encoding](http://blog.eyallupu.com/2013/11/base64-encoding-in-java-8.html))
but necessitated more class changes after run time exceptions. XML storage seems a much better
solution for any real word application (could parse old versions if classes where changed there is some chance of debugging user problems).
