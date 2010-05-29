package com.mindflakes.TeamRED.MenuXML;

import java.util.ArrayList;

import org.xml.sax.SAXException;
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
	private StringBuilder builder;
	
	public ArrayList<MealMenu> getMenus(){
		return this.menus;
	}
	
	@Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

	

}
