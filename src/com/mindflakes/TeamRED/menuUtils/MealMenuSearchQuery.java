package com.mindflakes.TeamRED.menuUtils;

import org.joda.time.DateTime;
import com.mindflakes.TeamRED.menuClasses.*;
import java.util.ArrayList;

/**
 * This class is used to generate MealMenu searches from the DataStore given a date range.
 * Searches are designed to be cascaded. For example, one might do <code>query.findCommons.findVegan.returnResutls</code>
 * @author Johan Henkens
 *
 */
public class MealMenuSearchQuery {
	private ArrayList<MealMenu> menus;
	

	/** Starts a new MealMenuSearchQuery from the ArrayList of MealMenus.
	 * @param menus MealMenus which will be filtered using the search queries
	 */
	public MealMenuSearchQuery(ArrayList<MealMenu> menus){
		this.menus = menus;
	}
	
	/** Searches for an exact match between the input string and the commons field in each MealMenu in the query
	 * @param commonsName the commonsName that will be compared with those of the MealMenus
	 * @return a new MealMenuSearchQuery containing only the MealMenus with an exact match in the commons name
	 */
	public MealMenuSearchQuery findCommons(String commonsName){
//		System.out.println("searching for commons: "+commonsName);
		commonsName=commonsName.toLowerCase();
		ArrayList<MealMenu> returnMenus = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			if(menu.getCommonsName().toLowerCase().equals(commonsName)) returnMenus.add(menu);
		}
		return new MealMenuSearchQuery(returnMenus);
	}
	
	/**
	 * @return
	 */
	public MealMenuSearchQuery findVegan(){
//		System.out.println("Searching for vegan");
		ArrayList<MealMenu> veganMenus = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			menu=menu.newMealMenuFromVegan();
			if(menu.getVenues().size()>0) veganMenus.add(menu);
		}
		return new MealMenuSearchQuery(veganMenus);
	}
	
	public MealMenuSearchQuery findVegetarian(){
//		System.out.println("Searching for vegetarian");
		ArrayList<MealMenu> vgtMenus = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			menu=menu.newMealMenuFromVegetarian();
			if(menu.getVenues().size()>0) vgtMenus.add(menu);
		}
		return new MealMenuSearchQuery(vgtMenus);
	}
	
	public MealMenuSearchQuery findFoodItem(String search){
		ArrayList<String> searchParts = splitSearch(search);
		ArrayList<MealMenu> searchMenus = new ArrayList<MealMenu>();
//		System.out.print("Searchin for...: ");
//		for(String s: searchParts){
//			System.out.println(s+" ");
//		}
//		System.out.println();
		for(MealMenu menu:menus){
			boolean foundFoodInThisMenu = false;
			try{
				for(Venue venue:menu.getVenues()){
					if(foundFoodInThisMenu) break;
					for(FoodItem food:venue.getFoodItems()){
						//					if(food.getName().contains("Focaccia")) System.out.println(food.getName());
						if(foundFoodInThisMenu) break;
						for(String s: searchParts){
							if(food.getName().toLowerCase().contains(s)){
								foundFoodInThisMenu=true;
								break;
							}
						}
					}
				}
			}catch(NullPointerException e){
				searchMenus.add(menu);
			}
			if(foundFoodInThisMenu) searchMenus.add(menu);
		}
		return new MealMenuSearchQuery(searchMenus);	
	}
	
	private ArrayList<String> splitSearch(String search){
		System.out.println("splitting: " + search);
		ArrayList<String> splitStrings = new ArrayList<String>();
		search=search.toLowerCase();
		while(search.indexOf('\"')!=-1){
			if(search.indexOf('\"',search.indexOf('\"')+1)!=-1){
				if(search.indexOf('\"')!=0){
//					System.out.println("adding: "+search.substring(0,search.indexOf('\"')).trim());
					splitStrings.add(search.substring(0,search.indexOf('\"')).trim());
				}
				search=search.substring(search.indexOf('\"')+1);
//				System.out.println("adding:" +search.substring(0,search.indexOf('\"')).trim());
				splitStrings.add(search.substring(0,search.indexOf('\"')).trim());
				search=search.substring(search.indexOf('\"')+1);
			} else{
				String tmp = search.substring(0,search.indexOf('\"')).trim();
				if(tmp.length()!=0) splitStrings.add(tmp);
				search = search.substring(search.indexOf('\"')+1).trim();
				break;
			}
		}
		for(String s:search.split(" ")){
			if(s.length()!=0){
				splitStrings.add(s);
//				System.out.println("adding: "+s);
			}
		}
		for(int i = 0; i<splitStrings.size();i++){
			if(splitStrings.get(i).trim().compareTo("")==0){
				splitStrings.remove(i);
			}
		}
		return splitStrings;
	}
	
	public ArrayList<MealMenu> returnResults(){
		return menus;
	}
	
}