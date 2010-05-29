package com.mindflakes.TeamRED.MenuXML;

import java.util.ArrayList;

import org.xml.sax.helpers.DefaultHandler;

import com.mindflakes.TeamRED.menuClasses.MealMenu;


/**
 * Based off of example as seen at
 * <a href=http://tinyurl.com/ykvykfp>this article</a>.
 *
 * @author Nelson Chen
 * 
 */
public class MenuXMLHandler extends DefaultHandler{
	private ArrayList<MealMenu> menus;
	
	public ArrayList<MealMenu> getMenus(){
		return this.menus;
	}
	
	
	
	

}
