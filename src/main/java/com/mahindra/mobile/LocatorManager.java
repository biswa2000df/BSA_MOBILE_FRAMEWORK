package com.mahindra.mobile;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;

public class LocatorManager extends ConnectToDataSheet {

	AppiumDriver<WebElement> driver;
	public static WebElement webElement;
	public static List<WebElement> webElements;
	
	LocatorManager(){
		driver = Android_IOS_Driver.driver;
	}

	public void mapToLocator() {
		
		By by = getLocatorByType(PropertyName, PropertyValue);
		System.out.println(by);
		System.out.println("go inside the locator class");
	/*	try {
			WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(ConnectToMainController.ExplicityWait));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			webElement = driver.findElement(by);
			webElements = driver.findElements(by);
		} catch (Exception e) {
			System.out.println(e);
		}*/
	}

	private By getLocatorByType(String propertyName, String propertyValue) {
		if (propertyName != null && !propertyName.isEmpty() && propertyValue != null && !propertyValue.isEmpty()) {
			switch (propertyName.toLowerCase()) {
			case "id":
				return By.id(propertyValue);
			case "name":
				return By.name(propertyValue);
			case "classname":
				return By.className(propertyValue);
			case "tagname":
				return By.tagName(propertyValue);
			case "linktext":
				return By.linkText(propertyValue);
			case "partiallinktext":
				return By.partialLinkText(propertyValue);
			case "css":
				return By.cssSelector(propertyValue);
			case "xpath":
				return By.xpath(propertyValue);
			default:
				throw new IllegalArgumentException("Unsupported PropertyName type: " + propertyName);
			}
		}
		return null;
	}

}
