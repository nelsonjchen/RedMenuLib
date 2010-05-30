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
	private MealMenu menu;
	private ArrayList<Venue> venues;
	private Venue venue;
	private ArrayList<FoodItem> fooditems;
	private FoodItem fooditem;
	
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
    	
    	if (localName.equalsIgnoreCase(COMMONSNAME)) {
    		commonsname = builder.toString();
    	} else if (localName.equalsIgnoreCase(MEALNAME)) {
    		mealname = builder.toString();
    	} else if (localName.equalsIgnoreCase(STARTMILLIS)) {
    		startmillis = Long.parseLong(builder.toString());
    	} else if (localName.equalsIgnoreCase(ENDMILLIS)) {
    		endmillis = Long.parseLong(builder.toString());
    	} else if (localName.equalsIgnoreCase(MODMILLIS)) {
    		modmillis = Long.parseLong(builder.toString());
    	} else if (localName.equalsIgnoreCase(VENUENAME)) {
    		venuename = builder.toString();
    	} else if (localName.equalsIgnoreCase(FOODNAME)) {
    		foodname = builder.toString();
    	} else if (localName.equalsIgnoreCase(FOODPROPERTIES)) {
    		foodproperties = builder.toString();
    	} else if (localName.equalsIgnoreCase(FOODITEM)) {
    		boolean vegan = false;
    		boolean vegetarian = false;
    		if (foodproperties.equalsIgnoreCase("Vegan")) {
    			vegan = true;
    		} else if (foodproperties.equalsIgnoreCase("Vegetarian")) {
    			vegetarian = true;
    		}
    		fooditems.add(new FoodItem(foodname, vegan, vegetarian));
    	} else if (localName.equalsIgnoreCase(FOODITEMS)) {
    		venues.add(new Venue(venuename, fooditems));
    	} else if (localName.equalsIgnoreCase(VENUES)) {
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
        if (localName.equalsIgnoreCase(VENUES)){
        	venues = new ArrayList<Venue>();
        }
        if (localName.equalsIgnoreCase(FOODITEMS)){
        	fooditems = new ArrayList<FoodItem>();
        }
    }
}




