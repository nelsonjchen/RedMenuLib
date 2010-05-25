package com.mindflakes.TeamRED.menuClasses;


import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mindflakes.TeamRED.MenuXML.Reader;

public class MealMenuTests {
	private static ArrayList<MealMenu> menus;
	private ArrayList<MealMenu> currentMenus;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		menus = Reader.readFile(new Scanner(new File("tests/testInput.xml")));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		currentMenus = menus;
	}
	
	/**
	 * Tests the Venue class's getVeganItems method
	 */
	@Test
	public void testVenueVegan(){
		for(MealMenu menu:currentMenus){
			for(Venue ven: menu.getVenues()){
				for(FoodItem food:ven.getVeganItems()){
					assertTrue(food.isVegan()
							&& food.isVegetarian());
				}
			}
		}
	}
	/**
	 * Tests the Venue class's getVegetarianItems method
	 */
	@Test
	public void testVenueVegetarian(){
		for(MealMenu menu:currentMenus){
			for(Venue ven: menu.getVenues()){
				for(FoodItem food:ven.getVegetarianItems()){
					assertTrue(food.isVegetarian());
				}
			}
		}
	}
	
	/**
	 * Tests the MealMenu class' getVeganVenues method
	 */
	@Test
	public void testVeganMealMenus(){
		for(MealMenu menu:currentMenus){
			for(Venue ven:menu.getVeganVenues()){
				for(FoodItem food:ven.getFoodItems()){
					assertTrue(food.isVegan()
							&& food.isVegetarian());		
				}
			}
		}
	}
	
	/**
	 * Tests the MealMenu class' getVegetarianVenues method.
	 */
	@Test
	public void testVegetarianMealMenus(){
		for(MealMenu menu:currentMenus){
			for(Venue ven:menu.getVegetarianVenues()){
				for(FoodItem food:ven.getFoodItems()){
					assertTrue(food.isVegetarian());		
				}
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		
	}

}
