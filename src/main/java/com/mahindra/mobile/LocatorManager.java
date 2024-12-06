package com.mahindra.mobile;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codoid.products.exception.FilloException;

public class LocatorManager extends ConnectToDataSheet {

	LocatorManager() {
		super();
	}

	public void mapToLocator() throws FilloException, InterruptedException, Exception {

		webElement = null;
		webElements = null;

		locatorManager = new LocatorManager();
		function = new Function();

		By by;

		if ((PropertyName != null && !PropertyName.isEmpty() && PropertyValue != null && !PropertyValue.isEmpty())) {

			if (PropertyName.equalsIgnoreCase("xpath")) {
				by = By.xpath(PropertyValue);
			} else if (PropertyName.equalsIgnoreCase("id")) {
				by = By.id(PropertyValue);
			} else if (PropertyName.equalsIgnoreCase("name")) {
				by = By.name(PropertyValue);
			} else if (PropertyName.equalsIgnoreCase("className")) {
				by = By.className(PropertyValue);
			} else if (PropertyName.equalsIgnoreCase("css")) {
				by = By.cssSelector(PropertyValue);
			} else if (PropertyName.equalsIgnoreCase("tagName")) {
				by = By.tagName(PropertyValue);
			} else if (PropertyName.equalsIgnoreCase("linkText")) {
				by = By.linkText(PropertyValue);
			} else if (PropertyName.equalsIgnoreCase("partialLinkText")) {
				by = By.partialLinkText(PropertyValue);
			} else {
				// Handle unsupported PropertyName type
				throw new IllegalArgumentException("Unsupported PropertyName type: " + PropertyName);
			}

			try {
				
				WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(ConnectToMainController.ExplicityWait));
		        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				
				webElement = driver.findElement(by);
				webElements = driver.findElements(by); 
				ConnectToDataSheet.extractTestData();   // call to sheet 2 data
				
			} catch (Exception e) {
				
					e.printStackTrace();
					ConnectToDataSheet.extractTestData();
					fail++;
				
			}
		} else {
			ConnectToDataSheet.extractTestData();
		}

	}

}
