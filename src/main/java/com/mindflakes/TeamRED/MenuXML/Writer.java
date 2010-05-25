package com.mindflakes.TeamRED.MenuXML;

import java.io.PrintStream;
import java.util.ArrayList;

import com.mindflakes.TeamRED.menuClasses.*;

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

}
