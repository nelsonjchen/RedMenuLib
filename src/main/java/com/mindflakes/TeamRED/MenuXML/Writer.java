package com.mindflakes.TeamRED.MenuXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

import com.mindflakes.TeamRED.menuClasses.FoodItem;
import com.mindflakes.TeamRED.menuClasses.MealMenu;
import com.mindflakes.TeamRED.menuClasses.Venue;

/**
 * This class is used to write a set of MealMenu objects to a tagged XML format which is easier for applications to read.
 * Writes using xml version 1.0, and UTF-8 encoding.
 * @author Johan Henkens
 *
 */
public class Writer {
	
	/** Writes the ArrayList of MealMenu objects to the PrintStream. For use with files, start a new PrintStream from
	 * a FileOutputStream and pass it in.
	 * @param ps the PrintStream the XML document will be written to.
	 * @param menus the menus that will be written to an XML format.
	 */
	public static void writeToStream(PrintStream ps,ArrayList<MealMenu> menus){
		ps.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		ps.println("<MealMenus>");
		for(MealMenu menu : menus){
			ps.println("<MealMenu>");
			ps.println("<CommonsName>"+prepareForXML(menu.getCommonsName())+"</CommonsName>");
			ps.println("<MealName>"+prepareForXML(menu.getMealName())+"</MealName>");
			ps.println("<StartMillis>"+menu.getMealInterval().getStartMillis()+"</StartMillis>");
			ps.println("<EndMillis>"+menu.getMealInterval().getEndMillis()+"</EndMillis>");
			ps.println("<ModMillis>"+menu.getModDate().getMillis()+"</ModMillis>");
			writeVenuesToStream(ps,menu.getVenues());
			ps.println("</MealMenu>");
		}
		ps.println("</MealMenus>");
		ps.close();
	}
	
	private static String prepareForXML(String s){
		return s.replaceAll("&","&amp;").replaceAll("<", "&lt;").replaceAll(">","&gt;").replaceAll("\'","&apos;").replaceAll("\"", "&quot;");
	}
	
	private static void writeVenuesToStream(PrintStream ps, ArrayList<Venue> venues){
		ps.println("<Venues>");
		for(Venue ven : venues){
			ps.println("<Venue>");
			ps.println("<VenueName>"+prepareForXML(ven.getName())+"</VenueName>");
			writeFoodsToStream(ps,ven.getFoodItems());
			ps.println("</Venue>");
		}
		ps.println("</Venues>");
	}
	
	private static void writeFoodsToStream(PrintStream ps, ArrayList<FoodItem> foods){
		ps.println("<FoodItems>");
		for(FoodItem food: foods){
			ps.println("<FoodItem>");
			ps.println("<FoodName>"+prepareForXML(food.getName())+"</FoodName>");
			ps.println("<FoodProperties>"+((food.isVegan())?"Vegan":
				((food.isVegetarian())?"Vegetarian":"None"))+"</FoodProperties>");
			ps.println("</FoodItem>");
		}
		ps.println("</FoodItems>");
	}
	
	/*
	 * Code used from:
	 * http://www.java-tips.org/java-se-tips/java.util.zip/how-to-compress-a-file-in-the-gip-format.html
	 * 
	 */
	public static File compressFile(File inFile){
	    try {
	        // Create the GZIP output stream
	    	File out = new File(inFile.toString()+".gz");
	        GZIPOutputStream outFile = new GZIPOutputStream(new FileOutputStream(out));
	    
	        // Open the input file
	        FileInputStream in = new FileInputStream(inFile);
	    
	        // Transfer bytes from the input file to the GZIP output stream
	        byte[] buf = new byte[1024];
	        int len;
	        while ((len = in.read(buf)) > 0) {
	        	outFile.write(buf, 0, len);
	        }
	        in.close();
	    
	        // Complete the GZIP file
	        outFile.finish();
	        outFile.close();
	        return out;
	    } catch (IOException e) {
	    	System.err.println("Error while compressing.");
	    	e.printStackTrace();
	    	return null;
	    }
	}	
	
	public static boolean writeSerialized(ArrayList<MealMenu> menus, FileOutputStream ofs){
		try {
			ObjectOutputStream objectOut = new ObjectOutputStream(ofs);
			objectOut.writeObject(menus);
			objectOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
