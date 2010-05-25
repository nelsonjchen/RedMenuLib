package com.mindflakes.TeamRED.menuUtils;

import org.joda.time.DateTime;
import org.joda.time.Interval;

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


	/**
	 * Searches for MealMenu which start specifically at the time specified.
	 * @param startDate the specific time at which the meal starts
	 * @return a new MealMenuSearchQuery with only MealMenus which start exactly at the startDate
	 */
	public MealMenuSearchQuery findStartingAt(DateTime startDate){
		ArrayList<MealMenu> result = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			if(menu.getMealInterval().getStart().isEqual(startDate)) result.add(menu);
		}
		return new MealMenuSearchQuery(result);
	}

	/**
	 * Searches for MealMenu which start during the Interval specified.
	 * @param startInterval the time interval at which the meal starts
	 * @return a new MealMenuSearchQuery with only MealMenus which start during the interval
	 */
	public MealMenuSearchQuery findStartingAt(Interval startInterval){
		ArrayList<MealMenu> result = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			if(startInterval.contains(menu.getMealInterval().getStart())) result.add(menu);
		}
		return new MealMenuSearchQuery(result);
	}

	/**
	 * Searches for MealMenu which end exclusively before the time specified.
	 * @param startDate the specific time at which the meal ends before
	 * @return a new MealMenuSearchQuery with only MealMenus which end exclusively before the startDate
	 */
	public MealMenuSearchQuery findStartingBefore(DateTime startDate){
		ArrayList<MealMenu> result = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			if(menu.getMealInterval().getEnd().isBefore(startDate)) result.add(menu);
		}
		return new MealMenuSearchQuery(result);
	}

	/**
	 * Searches for MealMenu which end specifically before the time specified.
	 * @param startDate the specific time at which the meal ends before
	 * @return a new MealMenuSearchQuery with only MealMenus which end inclusively before or at the startDate
	 */
	public MealMenuSearchQuery findStartingBeforeOrAt(DateTime startDate){
		ArrayList<MealMenu> result = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			if(menu.getMealInterval().getEnd().isBefore(startDate) ||
					menu.getMealInterval().getEnd().isEqual(startDate)) result.add(menu);
		}
		return new MealMenuSearchQuery(result);
	}

	/**
	 * Searches for MealMenu which end specifically at the time specified.
	 * @param endDate the specific time at which the meal ends
	 * @return a new MealMenuSearchQuery with only MealMenus which end exactly at the endDate
	 */
	public MealMenuSearchQuery findEndingAt(DateTime endDate){
		ArrayList<MealMenu> result = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			if(menu.getMealInterval().getEnd().isEqual(endDate)) result.add(menu);
		}
		return new MealMenuSearchQuery(result);
	}

	/**
	 * Searches for MealMenu which end during the Interval specified.
	 * @param endInterval the time interval at which the meal ends
	 * @return a new MealMenuSearchQuery with only MealMenus which end during the interval
	 */
	public MealMenuSearchQuery findEndingAt(Interval endInterval){
		ArrayList<MealMenu> result = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			if(endInterval.contains(menu.getMealInterval().getEnd())) result.add(menu);
		}
		return new MealMenuSearchQuery(result);
	}
	/**
	 * Searches for MealMenu which end exclusively before the time specified.
	 * @param endDate the specific time at which the meal ends before
	 * @return a new MealMenuSearchQuery with only MealMenus which end exclusively before the endDate
	 */
	public MealMenuSearchQuery findEndingBefore(DateTime endDate){
		ArrayList<MealMenu> result = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			if(menu.getMealInterval().getEnd().isBefore(endDate)) result.add(menu);
		}
		return new MealMenuSearchQuery(result);
	}

	/**
	 * Searches for MealMenu which end specifically before the time specified.
	 * @param endDate the specific time at which the meal ends before
	 * @return a new MealMenuSearchQuery with only MealMenus which end inclusively before or at the endDate
	 */
	public MealMenuSearchQuery findEndingBeforeOrAt(DateTime endDate){
		ArrayList<MealMenu> result = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			if(menu.getMealInterval().getEnd().isBefore(endDate) ||
					menu.getMealInterval().getEnd().isEqual(endDate)) result.add(menu);
		}
		return new MealMenuSearchQuery(result);
	}

	/**
	 * Searches for MealMenu which start after or at the start of the specified interval,
	 * and end at or before the end of the specified interval
	 * @param interval the time interval at which the meal ends
	 * @return a new MealMenuSearchQuery with only MealMenus which end during the interval
	 */
	public MealMenuSearchQuery findEntirelyDuring(Interval interval){
		ArrayList<MealMenu> result = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			if(interval.contains(menu.getMealInterval())) result.add(menu);
		}
		return new MealMenuSearchQuery(result);
	}

	/** Searches for an exact match between the input string and the commons field in each MealMenu in the query
	 * @param commonsName the commonsName that will be compared with those of the MealMenus
	 * @return a new MealMenuSearchQuery containing only the MealMenus with an exact match in the commons name
	 */
	public MealMenuSearchQuery findCommons(String commonsName){
		commonsName=commonsName.toLowerCase();
		ArrayList<MealMenu> returnMenus = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			if(menu.getCommonsName().toLowerCase().equals(commonsName)) returnMenus.add(menu);
		}
		return new MealMenuSearchQuery(returnMenus);
	}

	/**
	 * Searches for all the Vegan FoodItems in the MealMenu and returns a new MealMenu containing only those items
	 * @return a MealMenu containing only Vegan items.
	 */
	public MealMenuSearchQuery findVegan(){
		ArrayList<MealMenu> veganMenus = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			menu=menu.newMealMenuFromVegan();
			if(menu.getVenues().size()>0) veganMenus.add(menu);
		}
		return new MealMenuSearchQuery(veganMenus);
	}

	/**
	 * Searches for all the Vegetarian FoodItems in the MealMenu and returns a new MealMenu containing only those items
	 * @return a MealMenu containing only Vegetarian items.
	 */
	public MealMenuSearchQuery findVegetarian(){
		ArrayList<MealMenu> vgtMenus = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			menu=menu.newMealMenuFromVegetarian();
			if(menu.getVenues().size()>0) vgtMenus.add(menu);
		}
		return new MealMenuSearchQuery(vgtMenus);
	}

	/**
	 * Searches for all FoodItems containing a word the search string. Words can be grouped together as a single
	 * word if they are surrounded by quotation marks ("). Search results are not prioritized by frequency of number of word matches
	 * @param search
	 * @return
	 */
	public MealMenuSearchQuery findFoodItem(String search){
		ArrayList<String> searchParts = splitSearch(search);
		ArrayList<MealMenu> searchMenus = new ArrayList<MealMenu>();
		for(MealMenu menu:menus){
			boolean foundFoodInThisMenu = false;
			//			try{
			for(Venue venue:menu.getVenues()){
				if(foundFoodInThisMenu) break;
				for(FoodItem food:venue.getFoodItems()){
					if(foundFoodInThisMenu) break;
					for(String s: searchParts){
						if(food.getName().toLowerCase().contains(s)){
							foundFoodInThisMenu=true;
							break;
						}
					}
				}
			}
			//			}catch(NullPointerException e){
			//				searchMenus.add(menu);
			//			}
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
					splitStrings.add(search.substring(0,search.indexOf('\"')).trim());
				}
				search=search.substring(search.indexOf('\"')+1);
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
			}
		}
		for(int i = 0; i<splitStrings.size();i++){
			if(splitStrings.get(i).trim().compareTo("")==0){
				splitStrings.remove(i);
			}
		}
		return splitStrings;
	}

	/**
	 * Returns the MealMenus that this SearchQuery contains. If called directly after a constructor, will return the input array.
	 * @return an ArrayList of MealMenus
	 */
	public ArrayList<MealMenu> returnResults(){
		return menus;
	}

}