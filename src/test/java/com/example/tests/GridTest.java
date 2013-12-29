package com.example.tests;

import com.thoughtworks.selenium.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class GridTest {
	private Selenium selenium;
	private HttpCommandProcessor proc;

	@Before
	public void setUp() throws Exception {
		proc = new HttpCommandProcessor("localhost", 4444, "*firefox",
				"http://smartclient.com/");
		selenium = new DefaultSelenium(proc);
		selenium.start();
	}

	@Test
	public void testGrid() throws Exception {
		// maximize the window
		selenium.windowMaximize();

		selenium.open("/#columnOrder");
		// selenium.waitForGridDone("scLocator=//ListGrid[ID=\"countryList\"]");
		executeCommand("waitForGridDone", "scLocator=//ListGrid[ID=\"countryList\"]");
		
		// verify the texts in the first row
		assertEquals("United States", selenium.getTable("scLocator=//ListGrid[ID=\"countryList\"].0.1"));
		assertEquals("North America", selenium.getTable("scLocator=//ListGrid[ID=\"countryList\"].0.2"));
		
		// selenium.waitForElementClickable("scLocator=//FeatureExplorer[ID=\"featureExplorer\"]/exampleViewer/exampleViewPane[Class=ExampleViewPane||index=0||length=1||classIndex=0||classLength=1]/viewContainer/child[Class=IButton||index=1||length=3||classIndex=0||classLength=2||roleIndex=0||roleLength=2||title=Show%20Capitals||scRole=button]/");
		waitForElementClickable("scLocator=//FeatureExplorer[ID=\"featureExplorer\"]/exampleViewer/exampleViewPane[Class=ExampleViewPane||index=0||length=1||classIndex=0||classLength=1]/viewContainer/child[Class=IButton||index=1||length=3||classIndex=0||classLength=2||roleIndex=0||roleLength=2||title=Show%20Capitals||scRole=button]/");
		// click Show Capitals button
		selenium.click("scLocator=//FeatureExplorer[ID=\"featureExplorer\"]/exampleViewer/exampleViewPane[Class=ExampleViewPane||index=0||length=1||classIndex=0||classLength=1]/viewContainer/child[Class=IButton||index=1||length=3||classIndex=0||classLength=2||roleIndex=0||roleLength=2||title=Show%20Capitals||scRole=button]/");
		
		// selenium.waitForGridDone("scLocator=//ListGrid[ID=\"countryList\"]");
		executeCommand("waitForGridDone", "scLocator=//ListGrid[ID=\"countryList\"]");
		// verify the texts in the first row again
		assertEquals("United States", selenium.getTable("scLocator=//ListGrid[ID=\"countryList\"].0.1"));
		assertEquals("Washington, DC", selenium.getTable("scLocator=//ListGrid[ID=\"countryList\"].0.2"));
		assertEquals("North America", selenium.getTable("scLocator=//ListGrid[ID=\"countryList\"].0.3"));
		
		// selenium.waitForElementClickable("scLocator=//ListGrid[ID=\"countryList\"]/header/headerButton[fieldName=countryName]/");
		waitForElementClickable("scLocator=//ListGrid[ID=\"countryList\"]/header/headerButton[fieldName=countryName]/");
		// apply a sort opration
		selenium.click("scLocator=//ListGrid[ID=\"countryList\"]/header/headerButton[fieldName=countryName]/");
		// selenium.waitForGridDone("scLocator=//ListGrid[ID=\"countryList\"]");
		executeCommand("waitForGridDone", "scLocator=//ListGrid[ID=\"countryList\"]");
		
		// verify the texts in the first row again
		assertEquals("Brazil", selenium.getTable("scLocator=//ListGrid[ID=\"countryList\"].0.1"));
		assertEquals("Brasilia", selenium.getTable("scLocator=//ListGrid[ID=\"countryList\"].0.2"));
		assertEquals("South America", selenium.getTable("scLocator=//ListGrid[ID=\"countryList\"].0.3"));
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
	
	void waitForElementClickable(String locator) {
		executeCommand("waitForElementClickable", locator);
	}
	
	void executeCommand(String cmd, String locator) {
	    String[] locatorArg = {locator};
	    proc.doCommand(cmd, locatorArg);   
	}
}
