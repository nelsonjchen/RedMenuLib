package com.mindflakes.TeamRED.MenuXML;

import java.util.ArrayList;

import org.xml.sax.helpers.DefaultHandler;

import com.mindflakes.TeamRED.menuClasses.MealMenu;


public class MenuXMLHandler extends DefaultHandler{
	private ArrayList<MealMenu> menus;
	
	public ArrayList<MealMenu> getMenu(){
		return this.menus;
	}

}
