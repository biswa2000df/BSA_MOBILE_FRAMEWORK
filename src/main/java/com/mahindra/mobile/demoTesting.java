package com.mahindra.mobile;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class demoTesting {
	
	
	public static void scrollUp(WebDriver driver, int Scroll) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		System.out.println(Scroll);
		js.executeScript("window.scrollBy(0, " + -Scroll + ")", "");
		
	}
	
	public static void scrollDown(WebDriver driver, int Scroll) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		System.out.println(Scroll);
		js.executeScript("window.scrollBy(0, " + Scroll + ")", "");
	}
	
	public static void ScrollwebElementUntilVisible(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}
	
	
	public static void ScrollUpAndDownwebElementUntilVisible(WebDriver driver, String element, int Scroll) {
		
		int maxScrollLimit = 20;
		int currentScrollCount = 1;
		boolean elementIsVisible = false;
		
		
		while(elementIsVisible) {
			
			  try {
		            WebElement scrollElement = driver.findElement(By.xpath(element));
		            if (scrollElement.isDisplayed()) {
//		                System.out.println("Element is visible!");
		                elementIsVisible = true;
		                break;
		            }
		        } catch (Exception e) {
		            // Element not found yet, continue scrolling
		        }
			  
			  
			  JavascriptExecutor js = (JavascriptExecutor) driver;
		        if (currentScrollCount < maxScrollLimit / 2) {
		            js.executeScript("window.scrollBy(0, " + Scroll + ")");
		        } else {
		            js.executeScript("window.scrollBy(0, " + -Scroll + ")");
		        }

		        currentScrollCount++;
		    }

		    if (!elementIsVisible) {
		        System.out.println("Ohh Sorry... Scrolling limit completed. Element not found.");
		    }
		
		
		
	}

}