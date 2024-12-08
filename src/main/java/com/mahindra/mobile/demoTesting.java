package com.mahindra.mobile;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class demoTesting {
	
	
	public static void scroll(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 1500)", "");
	}
	

}