package com.mahindra.mobile;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class Framework {
	
	static AndroidDriver<WebElement> driver;

	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub
		
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "d4a4d1d2"); // Example: "emulator-5554" or your device's ID
		capabilities.setCapability("platformVersion", "11"); // Android version on the device
		capabilities.setCapability("appPackage", "com.miui.calculator"); // Package name of the app
		capabilities.setCapability("appActivity","com.miui.calculator.cal.CalculatorActivity"); // Main activity of the app
		
		URL url = new URL("http://0.0.0.0:4723/wd/hub");
		
		driver = new AndroidDriver<WebElement>(url, capabilities);
		
		


	}

}
