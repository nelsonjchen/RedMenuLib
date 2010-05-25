package com.mindflakes.TeamRED.menuUtils;

import java.util.ArrayList;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import com.mindflakes.TeamRED.menuClasses.FoodItem;
import com.mindflakes.TeamRED.menuClasses.MealMenu;
import com.mindflakes.TeamRED.menuClasses.Venue;

/**
 * Class used for various displaying techniques not necessarily used in final production. Good for visual debugging.
 * @author Johan Henkens
 * @author Nelson Chen
 *
 */
public  class MealMenuUtil {
	
    /** Prints each <code>MealMenu</code> in this Scraper to <code>System.out</code> in a clean format. 
     * @param menus ArrayList of MealMenus that will be printed to System.out
     */
    public static void printAll(ArrayList<MealMenu> menus){
    	for(MealMenu menu : menus){
    		System.out.println("Commons: " + menu.getCommonsName());
    		System.out.println("Start Time: " + DateTimeFormat.mediumDateTime().print(menu.getMealInterval().getStart()));
    		System.out.println("End Time: " + DateTimeFormat.mediumDateTime().print(menu.getMealInterval().getEnd()));
    		System.out.println("Mod Time: " + DateTimeFormat.mediumDateTime().print(menu.getModDate()));
    		System.out.println("Meal Name: " + menu.getMealName());
    		System.out.println("Venues: ");
    		for(Venue ven : menu.getVenues()){
    			System.out.println("    Venue Name: " + ven.getName());
    			System.out.println("    Food Items:");
    			for(FoodItem food : ven.getFoodItems()){
    				System.out.println("        " + food.getName()+" " + food.isVegan() +" "+ food.isVegetarian());
    			}
    		}
    	}
    }

	
	/**
	 * Generates an simple string from the MealMenu for quick displaying.
	 * @param menu MealMenu to be used to generate an simple string
	 * @return the generated simple string
	 */
	public static String mealMenuString(MealMenu menu) {
		String append = "*";
		append += menu.getCommonsName() + "*\n";
		for (Venue v : menu.getVenues()) {
			append += "\\" + v.getName() + "\n";
			for (FoodItem f: v.getFoodItems()) {
				append += "*" + f.getName() + "\n";
			}
		}
		return append;
	}
	
	/**
	 * Generates an html string from the MealMenu for quick displaying.
	 * @param menu MealMenu to be used to generate an html string
	 * @return the generated html string
	 */
	public static String mealMenuSimpleHTML(MealMenu menu) {
		String append = "<div class=\"meal\">";
		append += "<h2>" + menu.getCommonsName() + "</h2>\n";
		append += "<h3>" + menu.getMealName() + "</h3>\n";
		append += "<p>" + DateTimeFormat.mediumDateTime().withZone(DateTimeZone.forID("America/Los_Angeles"))
				.print(menu.getMealInterval().getStart())
				+ " to "
				+ DateTimeFormat.shortTime().withZone(DateTimeZone.forID("America/Los_Angeles"))
				.print(menu.getMealInterval().getEnd()) 
				
				+ "</p>\n";

		for (Venue v : menu.getVenues()) {
			append += "<h4>" + v.getName() + "</h4>\n";
			append += "<ul>\n";
			if(v.getFoodItems()!=null) for (FoodItem f: v.getFoodItems()) {
				if(f==null){
					append += "<li>null</li>\n";
				}
				append += "<li>" + f.getName() + "</li>\n";
			} else{
				append+=append += "<li>AllFoodItems are NULL!!! null</li>\n";
			}
			append += "</ul>\n";
		}


		append += "</div>";
		return append;
	}
	
	/**
	 * Generates an html string from the MealMenu for rss displaying.
	 * @param menu MealMenu to be used to generate an html string
	 * @return the generated html string
	 */
	public static String mealMenuSimpleRSSHTML(MealMenu menu) {
		String append = "";

		for (Venue v : menu.getVenues()) {
			append += "<h4>" + v.getName() + "</h4>\n";
			append += "<ul>\n";
			for (FoodItem f: v.getFoodItems()) {
				append += "<li>" + f.getName() + "</li>\n";
			}
			append += "</ul>\n";
		}
		return append;
	}
}
