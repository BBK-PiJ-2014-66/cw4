# PiJ Coursework Four
* The assignment document [cw4.pdf](cw4.pdf)
* Due **your github repo will be cloned on Sunday, 22nd March 2015 @ 23:59**

## Intefaces
* As supplied on coursework document:
  * [ContactManager](src/main/ContactManager.java)
  * [Contact](src/main/Contact.java)
  * [Meeting](src/main/Meeting.java)
  * [PastMeeting](src/main/PastMeeting.java)
  * [FutureMeeting](src/main/FutureMeeting.java)

## Implementation
* [ContactImpl](src/main/ContactImpl.java) tested by  [ContactImplTest](src/test/ContactImplTest.java)
* [ContactManagerImpl](src/main/ContactManagerImpl.java) tested by  [ContactManagerImplTest](src/test/ContactManagerImplTest.java)
* [IdGenerator](src/main/IdGenerator.java) uses Enum singleton objects for unique ID's.

## Storing data to file
The coursework document specifies 

**When the application is closed, all data must be stored in a text file called "contacts.txt". This file must be read at startup to recover all data introduced in a former session (the format of the file if up to you: you can use XML, comma-separated values (CSV), or any other format).**

### Possibility one **Java Serialisation**
On the Moodle Keith suggested that we look at the use of Java Serialisation particularly the
[Lars Vogel Tutorial](http://www.vogella.com/tutorials/JavaSerialization/article.html). This is attractively simple but has the disadvantage that that approach does not produce a *text* output as so does not strictly meet the spec (a binary file called *contacts.txt* would seem to be a really bad idea). To get around the problem one could encode the binary for instance using 64bit encoding. Getting ugly and in any case making the file human readable would seem to be a good idea. So reject for now but keep as a backup plan in case the use of 3rd party libraries is verboten.

### Possibility two **DIY file write**
Write code explicitly dump to a custom text format. Done too much of this in the past. This is a bit of a nightmare requiring how to encode user fields that include the delimiters.

### Possibility three **xml or json with DIY writer**
Use a decent human and other readable exchange format. XML is a good idea. JSON is another good (better like betamax). But once again I have waste a lot of time maintaining/extending DIY XML writer.

### Possibility four **use XSTREAM to read/write XML**
There are many many ways of writing XML in java. Looking through these the easiest for a newbie is XSTREAM.
Got this working easily. *But introducing a 3rd party Jar into the project* **So it is probably forbidden** May carry on and then strip back to **Possibility one Java Serialisation**

### Possibility five **use Java SE methods to read/write XML**
Useful [StackOverFlow contribution](http://stackoverflow.com/questions/9256669/java-built-in-data-parser-for-json-or-xml-or-else#9296657). Of the four possibilities [XMLStreamWriter](http://tutorials.jenkov.com/java-xml/stax-xmlstreamwriter.html) looks simplest to you (but one would end up with DIY writer that used XML mark-up).


