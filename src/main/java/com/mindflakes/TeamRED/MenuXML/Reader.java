package com.mindflakes.TeamRED.MenuXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.mindflakes.TeamRED.menuClasses.FoodItem;
import com.mindflakes.TeamRED.menuClasses.MealMenu;
import com.mindflakes.TeamRED.menuClasses.Venue;

/**
 * This class contains a reader that parses an XML file that is properly
 * formatted for the RedMenuLib and generates MealMenu's from its contents.
 * 
 * @author Johan Henkens
 * 
 */
public class Reader {

	/**
	 * Reads the file contained in the scanner and, if there are no errors,
	 * returns an ArrayList of MealMenu's. If the file is improperly formatted,
	 * the readFile method will return an empty, but non-null ArrayList. The
	 * scanner should point to the start of the XML file ("<?xml version.....")
	 * 
	 * @param sc
	 *            The scanner containing the file to be parsed
	 * @return the MealMenu's generated from the file, or an empty but non-null
	 *         ArrayList if there were errors.
	 */
	public static ArrayList<MealMenu> readFile(Scanner sc) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		ArrayList<MealMenu> result;
		try {
			SAXParser parser = factory.newSAXParser();
			MenuXMLHandler handler = new MenuXMLHandler();
			StringBuilder s = new StringBuilder();
			while(sc.hasNext()) {
				s.append(sc.next());
			}
			parser.parse(s.toString(), handler);
			result = handler.getMenus();
		} catch(Exception e){
			result = null;
			throw new RuntimeException();
		} finally {
			sc.close();
		}
		
		if (result == null)
			return new ArrayList<MealMenu>();
		else
			return result;
	}

	/*
	 * Adapted from
	 * :http://www.java-tips.org/java-se-tips/java.util.zip/how-to-uncompress
	 * -a-file-in-the-gip-format.html
	 */
	public static File uncompressFile(File inFile) {
		String result;
		if (!inFile.toString().endsWith(".gz")) {
			if (inFile.toString().indexOf('.') != -1) {
				result = inFile.toString();
				result = result.substring(0, result.indexOf('.'))
						+ ".unzip"
						+ result.substring(result.indexOf('.'), result
								.indexOf(".unz") + 1);
			} else {
				result = inFile.toString() + ".unzip";
			}
		} else {
			result = inFile.toString().substring(0,
					inFile.toString().length() - 3);
		}
		return uncompressFile(inFile, new File(result));

	}

	/*
	 * Adapted from
	 * :http://www.java-tips.org/java-se-tips/java.util.zip/how-to-uncompress
	 * -a-file-in-the-gip-format.html
	 */
	public static boolean uncompressFile(FileInputStream input,
			FileOutputStream out) {
		try {
			// Open the compressed file
			GZIPInputStream in = new GZIPInputStream(input);

			// Transfer bytes from the compressed file to the output file
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			// Close the file and stream
			in.close();
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/*
	 * Adapted from
	 * :http://www.java-tips.org/java-se-tips/java.util.zip/how-to-uncompress
	 * -a-file-in-the-gip-format.html
	 */
	public static File uncompressFile(File inFile, File outFile) {
		try {
			return (uncompressFile(new FileInputStream(inFile),
					new FileOutputStream(outFile))) ? outFile : null;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<MealMenu> readSerialized(FileInputStream is) {
		ObjectInputStream objectIn;
		try {
			objectIn = new ObjectInputStream(is);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		try {
			ArrayList<MealMenu> result = (ArrayList<MealMenu>) objectIn.readObject();
			objectIn.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<MealMenu> readXML(FileInputStream is) {
		ObjectInputStream objectIn;
		try {
			objectIn = new ObjectInputStream(is);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		try {
			ArrayList<MealMenu> result = (ArrayList<MealMenu>) objectIn.readObject();
			objectIn.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
}