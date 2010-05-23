package com.mindflakes.TeamRED.MenuXML;
import java.util.ArrayList;
import java.util.Scanner;

import com.mindflakes.TeamRED.menuClasses.FoodItem;
import com.mindflakes.TeamRED.menuClasses.MealMenu;
import com.mindflakes.TeamRED.menuClasses.Venue;

public class Reader {
	
	public static ArrayList<MealMenu> readFile(Scanner sc){
		ArrayList<MealMenu> result = parseMealMenus(sc);
		if(result==null) return new ArrayList<MealMenu>();
		else return result;
	}
	
	private static String fixFromXML(String s){
		return s.replaceAll("&amp;","&").replaceAll("&lt;","<").replaceAll("&gt;",">").replaceAll("&apos;","\'").replaceAll("&quot;","\"");
	}
	
	
	private static ArrayList<MealMenu> parseMealMenus(Scanner sc){
		if(!sc.hasNext()||!sc.nextLine().equals("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")) return null;
		if(!sc.hasNext()||!sc.nextLine().equals("<MealMenus>")) 
			return null;
		ArrayList<MealMenu> result = new ArrayList<MealMenu>();
		String line;
		if(sc.hasNext()){
			line = sc.nextLine();
		} else 
			return null;
		while(line.equals("<MealMenu>")){
			if(!sc.hasNext()) 
				return null;
			MealMenu tmp = parseMealMenu(sc);
			if(tmp == null) return result;
			else result.add(tmp);
			if(!sc.hasNext()) 
				return null;
			line = sc.nextLine();
		}
		if(!line.equals("</MealMenus>")) 
			return null;
		return result;
	}

	private static MealMenu parseMealMenu(Scanner sc){
		String commonsName = sc.nextLine();
		if(!commonsName.startsWith("<CommonsName>") || !commonsName.endsWith("</CommonsName>") || !sc.hasNext()){
			return null;
		}
		String mealName = sc.nextLine();
		if(!mealName.startsWith("<MealName>") || !mealName.endsWith("</MealName>") || !sc.hasNext()){
			return null;
		}
		String startMillisString = sc.nextLine();		
		if(!startMillisString.startsWith("<StartMillis>") || !startMillisString.endsWith("</StartMillis>") || !sc.hasNext()){
			return null;
		}
		String endMillisString = sc.nextLine();		
		if(!endMillisString.startsWith("<EndMillis>") || !endMillisString.endsWith("</EndMillis>") || !sc.hasNext()){
			return null;
		}
		String modMillisString = sc.nextLine();		
		if(!modMillisString.startsWith("<ModMillis>") || !modMillisString.endsWith("</ModMillis>") || !sc.hasNext()){
			return null;
		}
		ArrayList<Venue> venues = parseVenues(sc);
		if(venues==null || !sc.hasNext() || !sc.nextLine().equals("</MealMenu>")) 
			return null;
		try{
			commonsName = commonsName.substring(commonsName.indexOf('>')+1,commonsName.lastIndexOf("</"));
			mealName = mealName.substring(mealName.indexOf('>')+1,mealName.lastIndexOf("</"));
			Long startMillis = Long.parseLong(startMillisString.substring(startMillisString.indexOf('>')+1,startMillisString.lastIndexOf("</")));
			Long endMillis = Long.parseLong(endMillisString.substring(endMillisString.indexOf('>')+1,endMillisString.lastIndexOf("</")));
			Long modMillis = Long.parseLong(modMillisString.substring(modMillisString.indexOf('>')+1,modMillisString.lastIndexOf("</")));
			return new MealMenu(fixFromXML(commonsName),startMillis,endMillis,modMillis,venues,fixFromXML(mealName));
		} catch(NumberFormatException e){
			return null;
		}
	}
	
	private static ArrayList<Venue> parseVenues(Scanner sc){
		if(!sc.hasNext() || !sc.nextLine().equals("<Venues>")) 
			return null;
		ArrayList<Venue> result = new ArrayList<Venue>();
		String line;
		if(sc.hasNext()){
			line = sc.nextLine();
		} else 
			return null;
		//Checks if scanner has a new line. Checks if that line is equal to "<Venue>", then checks there is still another line
		while(line.equals("<Venue>")){
			if(!sc.hasNext()) 
				return null;
			Venue tmp = parseVenue(sc);
			if(tmp==null) 
				return null;
			else result.add(tmp);
			
			if(!sc.hasNext()) 
				return null;
			line = sc.nextLine();
		}
		if(!line.equals("</Venues>") || !sc.hasNext()) 
			return null;
		return result;
	}
	
	private static Venue parseVenue(Scanner sc){
		String venueName = sc.nextLine();
		if(!sc.hasNext() || !venueName.startsWith("<VenueName>") || !venueName.endsWith("</VenueName>")) 
			return null;
		ArrayList<FoodItem> foods = parseFoods(sc);
		if(foods==null || !sc.hasNext() || !sc.nextLine().equals("</Venue>")) 
			return null;
	
		venueName = venueName.substring(venueName.indexOf('>')+1,venueName.lastIndexOf("</"));
		return new Venue(fixFromXML(venueName),foods);
	}
	
	private static ArrayList<FoodItem> parseFoods(Scanner sc){
		if(!sc.hasNext()|| !sc.nextLine().equals("<FoodItems>")) 
			return null;
		ArrayList<FoodItem> result = new ArrayList<FoodItem>();
		String line;
		if(sc.hasNext()){
			line = sc.nextLine();
		} else 
			return null;
		while(line.equals("<FoodItem>")){
			if(!sc.hasNext()) 
				return null;
			
			FoodItem tmp = parseFood(sc);
			if(tmp==null) 
				return null; 
			else result.add(tmp);
			
			if(!sc.hasNext()) 
				return null;
			line = sc.nextLine();
		}
		if(!line.equals("</FoodItems>") || !sc.hasNext()) 
			return null;
		return result;
	}
	
	private static FoodItem parseFood(Scanner sc){
		String foodName = sc.nextLine();
		if(!sc.hasNext() || !foodName.startsWith("<FoodName>") || !foodName.endsWith("</FoodName>")) 
			return null;
		String properties = sc.nextLine();
		if(!sc.hasNext() || !properties.startsWith("<FoodProperties>") || !properties.endsWith("</FoodProperties>")) 
			return null;
		
		foodName = foodName.substring(foodName.indexOf('>')+1,foodName.lastIndexOf("</"));
		properties = properties.substring(properties.indexOf('>')+1,properties.lastIndexOf("</"));
		if(!sc.hasNext()||!sc.nextLine().equals("</FoodItem>")) 
			return null;
		
		return new FoodItem(fixFromXML(foodName),
				(properties.equals("Vegan"))?true:false,
						(properties.equals("Vegetarian")||properties.equals("Vegan"))?true:false);
	}
}
