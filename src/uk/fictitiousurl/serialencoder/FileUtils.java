package uk.fictitiousurl.serialencoder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UncheckedIOException;

/**
 * Static methods to save a string to a file and vice-versa. package private as
 * not intended for general use.
 * 
 * @author Oliver Smart {@literal <osmart01@dcs.bbk.ac.uk>}
 * @since 13 March 2015
 * 
 */
class FileUtils {

	private FileUtils() {
		throw new UnsupportedOperationException("Uninstantiable class");
	}

	/**
	 * writes out the string to a file.
	 * 
	 * @param inStr
	 *            the string to write
	 * @param outFile
	 *            the file to write to
	 * @throws java.io.UncheckedIOException
	 *             if there is an error creating the file
	 */
	static void stringToFile(String inStr, String outFile) {
		// Use PrintWriter based on PiJ Day 16 sheet I/O 1.3.2
		try (PrintWriter pwout = new PrintWriter(outFile)) {
			pwout.write(inStr);
		} catch (FileNotFoundException ex) {
			throw new UncheckedIOException(ex);
		}
	}

	/**
	 * Reads the complete contents of a file to a string - preserving line
	 * breaks
	 * 
	 * @param inFile
	 *            the file of the file
	 * @return the contents of the file as a single string
	 * @throws java.io.UncheckedIOException
	 *             if there is an error opening the file
	 */
	static String fileContentsToString(String inFile) {
		// adapted from sdp cw1 Translator (after bug fix) and Eckle
		// "Thinking Java" p 927 using scanner but this gives newline strip
		// problems.
		//
		// then adapted to use BufferedReader .read()
		// to read the file character by character from
		// http://stackoverflow.com/questions/811851/how-do-i-read-input-character-by-character-in-java
		StringBuilder stringBuilder = new StringBuilder();
		// try with resources to avoid cleanup close, final stuff.
		try (FileInputStream fis = new FileInputStream(inFile);
				InputStreamReader isr = new InputStreamReader(fis);
				Reader reader = new BufferedReader(isr)) {
			int anInt; // an integer
			while ((anInt = reader.read()) != -1) { // EOF is -1
				char asChar = (char) anInt;
				stringBuilder.append(asChar);
			}

		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}
		return stringBuilder.toString();
	}
}
