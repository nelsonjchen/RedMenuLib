package com.mindflakes.TeamRED.MenuXML;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mindflakes.TeamRED.menuClasses.FoodItem;
import com.mindflakes.TeamRED.menuClasses.MealMenu;
import com.mindflakes.TeamRED.menuClasses.Venue;


/**
 * Based off of example as seen at
 * <a href=http://tinyurl.com/ykvykfp>this article</a>.
 *
 * @author Nelson Chen
 * 
 */
public class MenuXMLHandler extends DefaultHandler{
	private StringBuilder builder;
	private ArrayList<MealMenu> menus;
	private ArrayList<Venue> venues;
	private ArrayList<FoodItem> fooditems;
	
	private String commonsname;
	private String mealname;
	private long startmillis;
	private long modmillis; 
	private long endmillis;
	
	private String venuename;
	
	private String foodname;
	private String foodproperties;
	
	
	static final String MEALMENUS = "MealMenus";
	static final String MEALMENU = "MealMenu";
	static final String COMMONSNAME = "CommonsName";
	static final String MEALNAME = "MealName";
	static final String STARTMILLIS = "StartMillis";
	static final String ENDMILLIS = "EndMillis";
	static final String MODMILLIS = "ModMillis";
	
	static final String VENUES = "Venues";
	static final String VENUE = "Venue";
	static final String VENUENAME = "VenueName";
	static final String FOODITEMS = "FoodItems";
	static final String FOODITEM = "FoodItem";
	static final String FOODNAME = "FoodName";
	static final String FOODPROPERTIES = "FoodProperties";

	
	public ArrayList<MealMenu> getMenus(){
		return this.menus;
	}
	
	@Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }
	
    @Override
    public void endElement(String uri, String localName, String name)
    throws SAXException {
    	super.endElement(uri, localName, name);
    	
    	if (name.equalsIgnoreCase(COMMONSNAME)) {
    		commonsname = builder.toString();
    	} else if (name.equalsIgnoreCase(MEALNAME)) {
    		mealname = builder.toString();
    	} else if (name.equalsIgnoreCase(STARTMILLIS)) {
    		startmillis = Long.parseLong(builder.toString());
    	} else if (name.equalsIgnoreCase(ENDMILLIS)) {
    		endmillis = Long.parseLong(builder.toString());
    	} else if (name.equalsIgnoreCase(MODMILLIS)) {
    		modmillis = Long.parseLong(builder.toString());
    	} else if (name.equalsIgnoreCase(VENUENAME)) {
    		venuename = builder.toString();
    	} else if (name.equalsIgnoreCase(FOODNAME)) {
    		foodname = builder.toString();
    	} else if (name.equalsIgnoreCase(FOODPROPERTIES)) {
    		foodproperties = builder.toString();
    	} else if (name.equalsIgnoreCase(FOODITEM)) {
    		boolean vegan = false;
    		boolean vegetarian = false;
    		if (foodproperties.equalsIgnoreCase("Vegan")) {
    			vegan = true;
    			vegetarian = true;
    		} else if (foodproperties.equalsIgnoreCase("Vegetarian")) {
    			vegetarian = true;
    		}
    		fooditems.add(new FoodItem(foodname, vegan, vegetarian));
    	} else if (name.equalsIgnoreCase(FOODITEMS)) {
    		venues.add(new Venue(venuename, fooditems));
    	} else if (name.equalsIgnoreCase(MEALMENU)) {
    		menus.add(new MealMenu(commonsname, startmillis, endmillis, modmillis, venues, mealname));
    	}
    	
    	builder.setLength(0);    

    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        builder = new StringBuilder();
        menus = new ArrayList<MealMenu>();
        venues = new ArrayList<Venue>();
        fooditems = new ArrayList<FoodItem>();
    }

    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (name.equalsIgnoreCase(VENUES)){
        	venues = new ArrayList<Venue>();
        }
        if (name.equalsIgnoreCase(FOODITEMS)){
        	fooditems = new ArrayList<FoodItem>();
        }
    }
}




