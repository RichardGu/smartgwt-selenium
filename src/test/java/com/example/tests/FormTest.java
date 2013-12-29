package com.example.tests;

import com.thoughtworks.selenium.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class FormTest {
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
	public void testForm() throws Exception {
		// maximize the window
		selenium.windowMaximize();
		
		// open the test target page
		selenium.open("/#FSsingleSourceValidation");
		// waiting for the page to load
		waitForElementClickable("scLocator=//DynamicForm[ID=\"dynamicForm\"]/");
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (selenium.isElementPresent("scLocator=//IButton[ID=\"saveButton\"]/")) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		// verify that the value of Item input box  is empty or not
		assertEquals("", selenium.getValue("id=isc_2K"));
		// verify that the value of SKU input box is "my SKU" or not
		assertEquals("my SKU", selenium.getValue("id=isc_2N"));
		// verify that the value of Description input box  is empty or not
		assertEquals("", selenium.getValue("id=isc_2Q"));
		// verify that the value of Category input box  is empty or not
		assertEquals("", selenium.getValue("id=isc_2T"));
		// verify that the Units drowdown list is selected nothing
		assertEquals("", selenium.getText("id=isc_31"));
		// verify that the value of Unit Cost input box is "-1.234"
		assertEquals("-1.234", selenium.getValue("id=isc_33"));
		// verify that the In Stock check box is unchecked
		assertEquals("", selenium.getText("id=isc_39"));
		
		// verify that there are no error messages.
		assertFalse(selenium.isElementPresent("id=isc_4I"));
		assertFalse(selenium.isElementPresent("id=isc_4L"));
		assertFalse(selenium.isElementPresent("id=isc_4G"));
		
		waitForElementClickable("scLocator=//IButton[ID=\"saveButton\"]/");
		// click the Save button
		selenium.click("scLocator=//IButton[ID=\"saveButton\"]/");
		
		// verify that the there are some error messages.
		assertTrue(selenium.isElementPresent("id=isc_4I"));
		assertTrue(selenium.isElementPresent("id=isc_4L"));
		assertTrue(selenium.isElementPresent("id=isc_4G"));
		
		waitForElementClickable("scLocator=//IButton[ID=\"clearErrorsButton\"]/");
		// click the Clear Errors button
		selenium.click("scLocator=//IButton[ID=\"clearErrorsButton\"]/");
		
		waitForElementClickable("scLocator=//DynamicForm[ID=\"dynamicForm\"]/");
		// fill the form
		selenium.type("id=isc_2K", "First Item");
		selenium.type("id=isc_2Q", "Here is the description of this item.");
		selenium.type("id=isc_2T", "C1");
		selenium.type("id=isc_33", "2.5");
		waitForElementClickable("scLocator=//IButton[ID=\"saveButton\"]/");
		// click the Save button again
		selenium.click("scLocator=//IButton[ID=\"saveButton\"]/");
		
		waitForElementClickable("scLocator=//DynamicForm[ID=\"dynamicForm\"]/");
		// verify that there are no error messages.
		assertFalse(selenium.isElementPresent("id=isc_4I"));
		assertFalse(selenium.isElementPresent("id=isc_4L"));
		assertFalse(selenium.isElementPresent("id=isc_4G"));
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
	
	void waitForElementClickable(String locator) {
	    String[] locatorArg = {locator};
	    proc.doCommand("waitForElementClickable", locatorArg);   
	}

}
