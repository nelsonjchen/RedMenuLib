package com.mindflakes.TeamRED.menuUtils;


import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mindflakes.TeamRED.MenuXML.Reader;
import com.mindflakes.TeamRED.menuClasses.FoodItem;
import com.mindflakes.TeamRED.menuClasses.MealMenu;
import com.mindflakes.TeamRED.menuClasses.Venue;

public class SearchQueryTests {
	private static ArrayList<MealMenu> menus;
	private MealMenuSearchQuery query;
	private ArrayList<MealMenu> results;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		menus = Reader.readFile(new Scanner(new File("tests/testInput.xml")));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		query = new MealMenuSearchQuery(menus);
		results = null;
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSearchForWords(){
		results = query.findFoodItem("BBQ fried").returnResults();
		assertTrue(results.size()!=0);
		for(MealMenu menu:results){
			boolean contains = false;
			for(Venue ven:menu.getVenues()){
				for(FoodItem food:ven.getFoodItems()){
					if(food.getName().toLowerCase().contains("bbq") || food.getName().toLowerCase().contains("fried")){
						contains = true;
					}
				}
			}
			assertTrue(contains);
		}
	}
	
	@Test
	public void testSearchForWordsWithPair(){
		results = query.findFoodItem("BBQ \"fried rice\"").returnResults();
		assertTrue(results.size()!=0);
		for(MealMenu menu:results){
			boolean contains = false;
			for(Venue ven:menu.getVenues()){
				for(FoodItem food:ven.getFoodItems()){
					if(food.getName().toLowerCase().contains("bbq") || food.getName().toLowerCase().contains("fried rice")){
						contains = true;
					}
				}
			}
			assertTrue(contains);
		}
	}	
	@Test
	public void testSearchForWordsGibberish(){
		results = query.findFoodItem("asdfasdf").returnResults();
		assertTrue(results.size()==0);
	}
	
	@Test
	public void testSearchForPair(){
		results = query.findFoodItem("\"fried rice\"").returnResults();
		assertTrue(results.size()!=0);
		for(MealMenu menu:results){
			boolean contains = false;
			for(Venue ven:menu.getVenues()){
				for(FoodItem food:ven.getFoodItems()){
					if(food.getName().toLowerCase().contains("fried rice")){
						contains = true;
					}
				}
			}
			assertTrue(contains);
		}
	}
	
	@Test
	public void testSearchForVegan(){
		results = query.findVegan().returnResults();
		assertTrue(results.size()!=0);
		for(MealMenu menu:results){
			for(Venue ven:menu.getVenues()){
				for(FoodItem food:ven.getFoodItems()){
					assertTrue(food.isVegan()&&food.isVegetarian());
				}
			}
		}
	}
	
	@Test
	public void testSearchForVegetarian(){
		results = query.findVegetarian().returnResults();
		assertTrue(results.size()!=0);
		for(MealMenu menu:results){
			for(Venue ven:menu.getVenues()){
				for(FoodItem food:ven.getFoodItems()){
					assertTrue(food.isVegetarian());
				}
			}
		}
	}

	@Test
	public void testSearchForCommonsCarrillo(){
		results = query.findCommons("Carrillo").returnResults();
		assertTrue(results.size()!=0);
		for(MealMenu menu:results){
			assertTrue(menu.getCommonsName().equals("Carrillo"));
		}
	}
	
	@Test
	public void testSearchForCommonsInvalid(){
		results = query.findCommons("Carrillo Commons").returnResults();
		assertTrue(results.size()==0);
	}
	
	@Test
	public void testFindEndingAtDateTimePass(){
		DateTime time = new DateTime(new DateTime(1274909400000l));
		results = query.findEndingAt(time).returnResults();
		assertTrue(results.size()!=0);
		for(MealMenu menu:results){
			assertTrue(menu.getMealInterval().getEnd().isEqual(time));
		}
	}
	
	@Test
	public void testFindEndingAtDateTimeFail(){
		DateTime time = new DateTime(new DateTime(1274909412345l));
		results = query.findEndingAt(time).returnResults();
		assertTrue(results.size()==0);
	}
	
	@Test
	public void testFindStartingAtDateTimePass(){
		DateTime time = new DateTime(new DateTime(1274896800000l));
		results = query.findStartingAt(time).returnResults();
		assertTrue(results.size()!=0);
		for(MealMenu menu:results){
			assertTrue(menu.getMealInterval().getStart().isEqual(time));
		}
	}
	
	@Test
	public void testFindStartingAtDateTimeFail(){
		DateTime time = new DateTime(new DateTime(1274909412345l));
		results = query.findStartingAt(time).returnResults();
		assertTrue(results.size()==0);
	}
	
	@Test
	public void testFindStartingInterval(){
		Interval inter = new Interval(new DateTime(1274896800000l), new DateTime(1274896900000l));
		results = query.findStartingAt(inter).returnResults();
		assertTrue(results.size()!=0);
		for(MealMenu menu:results){
			assertTrue(inter.contains(menu.getMealInterval().getStart()));
		}
	}
	
	@Test
	public void testFindEndingInterval(){
		Interval inter = new Interval(new DateTime(1274909400000l), new DateTime(1277909500000l));
		results = query.findEndingAt(inter).returnResults();
		assertTrue(results.size()!=0);
		for(MealMenu menu:results){
			assertTrue(inter.contains(menu.getMealInterval().getEnd()));
		}
	}
	
	@Test
	public void testFindStartingIntervalFail(){
		Interval inter = new Interval(new DateTime(1274909400000l), new DateTime(1274909400000l));
		results = query.findStartingAt(inter).returnResults();
		assertTrue(results.size()==0);
		for(MealMenu menu:results){
			assertTrue(inter.contains(menu.getMealInterval().getStart()));
		}
	}
	
	@Test
	public void testFindEndingIntervalFail(){
		Interval inter = new Interval(new DateTime(1274909400000l), new DateTime(1274909400000l));
		results = query.findEndingAt(inter).returnResults();
		assertTrue(results.size()==0);
		for(MealMenu menu:results){
			assertTrue(inter.contains(menu.getMealInterval().getEnd()));
		}
	}
	
	@Test
	public void testStartTimeSortIncreasing(){
		results = query.sortByStartTime(true).returnResults();
		assertTrue(results.size()!=0);
		for(int i = 1; i<results.size();i++){
			assertTrue(results.get(i-1).getMealInterval().getStartMillis()<=results.get(i).getMealInterval().getStartMillis());
		}
	}
	@Test
	public void testStartTimeSortDecreasing(){
		results = query.sortByStartTime(false).returnResults();
		assertTrue(results.size()!=0);
		for(int i = 1; i<results.size();i++){
			assertTrue(results.get(i-1).getMealInterval().getStartMillis()>=results.get(i).getMealInterval().getStartMillis());
		}
	}
	@Test
	public void testEndTimeSortIncreasing(){
		results = query.sortByEndTime(true).returnResults();
		assertTrue(results.size()!=0);
		for(int i = 1; i<results.size();i++){
			assertTrue(results.get(i-1).getMealInterval().getEndMillis()<=results.get(i).getMealInterval().getEndMillis());
		}
	}	
	@Test
	public void testEndTimeSortDecreasing(){
		results = query.sortByEndTime(false).returnResults();
		assertTrue(results.size()!=0);
		for(int i = 1; i<results.size();i++){
			assertTrue(results.get(i-1).getMealInterval().getEndMillis()>=results.get(i).getMealInterval().getEndMillis());
		}
	}
	@Test
	public void testCommonsNameSortIncreasing(){
		results = query.sortByCommonsName(true).returnResults();
		assertTrue(results.size()!=0);
		for(int i = 1; i< results.size();i++){
			assertTrue(results.get(i-1).getCommonsName().compareToIgnoreCase(results.get(i).getCommonsName())<=0);
		}
	}
	@Test
	public void testCommonsNameSortDecreasing(){
		results = query.sortByCommonsName(false).returnResults();
		assertTrue(results.size()!=0);
		for(int i = 1; i< results.size();i++){
			assertTrue(results.get(i-1).getCommonsName().compareToIgnoreCase(results.get(i).getCommonsName())>=0);
		}
	}	
	@Test
	public void testMealNameSortIncreasing(){
		results = query.sortByMealName(true).returnResults();
		assertTrue(results.size()!=0);
		for(int i = 1; i< results.size();i++){
			assertTrue(results.get(i-1).getMealName().compareToIgnoreCase(results.get(i).getMealName())<=0);
		}
	}
	@Test
	public void testMealNameSortDecreasing(){
		results = query.sortByMealName(false).returnResults();
		assertTrue(results.size()!=0);
		for(int i = 1; i< results.size();i++){
			assertTrue(results.get(i-1).getMealName().compareToIgnoreCase(results.get(i).getMealName())>=0);
		}
	}	
}
