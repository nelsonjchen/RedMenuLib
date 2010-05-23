package com.mindflakes.TeamRED.MenuXML;

import java.io.PrintStream;
import java.util.ArrayList;

import com.mindflakes.TeamRED.menuClasses.*;

public class Writer {
	
	public static void writeToStream(PrintStream ps,ArrayList<MealMenu> menus){
		ps.println("<MealMenus>");
		for(MealMenu menu : menus){
			ps.println("<MealMenu>");
			ps.println("<CommonsName>"+menu.getCommonsName()+"</CommonsName>");
			ps.println("<MealName>"+menu.getMealName()+"</MealName>");
			ps.println("<StartMillis>"+menu.getMealInterval().getStartMillis()+"</StartMillis>");
			ps.println("<EndMillis>"+menu.getMealInterval().getEndMillis()+"</EndMillis>");
			ps.println("<ModMillis>"+menu.getModDate().getMillis()+"</ModMillis>");
			writeVenuesToStream(ps,menu.getVenues());
			ps.println("</MealMenu>");
		}
		ps.println("</MealMenus>");
		
	}
	
	private static void writeVenuesToStream(PrintStream ps, ArrayList<Venue> venues){
		ps.println("<Venues>");
		for(Venue ven : venues){
			ps.println("<Venue>");
			ps.println("<VenueName>"+ven.getName()+"</VenueName>");
			writeFoodsToStream(ps,ven.getFoodItems());
			ps.println("</Venue>");
		}
		ps.println("</Venues>");
	}
	
	private static void writeFoodsToStream(PrintStream ps, ArrayList<FoodItem> foods){
		ps.println("<FoodItems>");
		for(FoodItem food: foods){
			ps.println("<FoodItem>");
			ps.println("<FoodName>"+food.getName()+"</FoodName>");
			ps.println("<FoodProperties>"+((food.isVegan())?"Vegan":
				((food.isVegetarian())?"Vegetarian":"None"))+"</FoodProperties>");
			ps.println("</FoodItem>");
		}
		ps.println("</FoodItems>");
	}

}
