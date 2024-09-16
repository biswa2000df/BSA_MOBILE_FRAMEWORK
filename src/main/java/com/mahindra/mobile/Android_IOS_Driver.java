package com.mahindra.mobile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class Android_IOS_Driver {

	static AppiumDriver<WebElement> driver;
	static DesiredCapabilities capabilities;
	static URL url;
	static Map<String, Object> browserstackOptions;

	public static void InitialisationDriverRemote() throws Exception {

		final String BrowserStackUrl = "https://" + MobileConfiguration.UserName + ":" + MobileConfiguration.AccessKey
				+ "@hub-cloud.browserstack.com/wd/hub";

		browserstackOptions = new HashMap<String, Object>();

		capabilities = new DesiredCapabilities();

		if (MobileConfiguration.DevicePlatform.equalsIgnoreCase("Android")) {

			browserstackOptions.put("platformName", MobileConfiguration.DevicePlatform);
			browserstackOptions.put("deviceName", MobileConfiguration.DeviceName);
			browserstackOptions.put("platformVersion", MobileConfiguration.DevicePlatformVersion);
			browserstackOptions.put("name", MobileConfiguration.Process);

			if (MobileConfiguration.AppReset.equalsIgnoreCase("NO")) {
				browserstackOptions.put("noReset", true);
				browserstackOptions.put("fullReset", false);
			} else if (MobileConfiguration.AppReset.equalsIgnoreCase("YES")) {
				browserstackOptions.put("noReset", false);
				browserstackOptions.put("fullReset", true);
			} else if (MobileConfiguration.Pre_InstalledApp.equalsIgnoreCase("NO")) {
				browserstackOptions.put("APP", MobileConfiguration.AppPath);
			}

			browserstackOptions.put("appPackage", MobileConfiguration.App_PackageName);
			browserstackOptions.put("appActivity", MobileConfiguration.App_PackageActivityName);

			capabilities.setCapability("bstack:options", browserstackOptions);

			url = new URL(BrowserStackUrl);
			driver = new AndroidDriver<WebElement>(url, capabilities);
			driver.manage().timeouts().implicitlyWait(Long.parseLong(ConnectToMainController.ImplicityWait),
					TimeUnit.SECONDS);

		} else if (MobileConfiguration.DevicePlatform.equalsIgnoreCase("IOS")) {

			browserstackOptions.put("platformName", MobileConfiguration.DevicePlatform);
			browserstackOptions.put("deviceName", MobileConfiguration.DeviceName);
			browserstackOptions.put("platformVersion", MobileConfiguration.DevicePlatformVersion);
			browserstackOptions.put("name", MobileConfiguration.Process);

			if (MobileConfiguration.AppReset.equalsIgnoreCase("NO")) {
				browserstackOptions.put("noReset", true);
				browserstackOptions.put("fullReset", false);
			} else if (MobileConfiguration.AppReset.equalsIgnoreCase("YES")) {
				browserstackOptions.put("noReset", false);
				browserstackOptions.put("fullReset", true);
			} else if (MobileConfiguration.Pre_InstalledApp.equalsIgnoreCase("NO")) {
				browserstackOptions.put("APP", MobileConfiguration.AppPath);
			}

			browserstackOptions.put("appPackage", MobileConfiguration.App_PackageName);
			browserstackOptions.put("appActivity", MobileConfiguration.App_PackageActivityName);

			capabilities.setCapability("bstack:options", browserstackOptions);

			url = new URL(BrowserStackUrl);
			driver = new IOSDriver<WebElement>(url, capabilities);
			driver.manage().timeouts().implicitlyWait(Long.parseLong(ConnectToMainController.ImplicityWait),
					TimeUnit.SECONDS);

		}
	}

	public static void InitialisationDriverLocal() throws Exception {

		capabilities = new DesiredCapabilities();

		if (MobileConfiguration.DevicePlatform.equalsIgnoreCase("Android")) {

			capabilities.setCapability("platformName", MobileConfiguration.DevicePlatform);
			capabilities.setCapability("deviceName", MobileConfiguration.DeviceName);
			capabilities.setCapability("platformVersion", MobileConfiguration.DevicePlatformVersion);
			capabilities.setCapability("name", MobileConfiguration.Process);

			if (MobileConfiguration.AppReset.equalsIgnoreCase("NO")) {
				capabilities.setCapability("noReset", true);
				capabilities.setCapability("fullReset", false);
			} else if (MobileConfiguration.AppReset.equalsIgnoreCase("YES")) {
				capabilities.setCapability("noReset", false);
				capabilities.setCapability("fullReset", true);
			} else if (MobileConfiguration.Pre_InstalledApp.equalsIgnoreCase("NO")) {
				capabilities.setCapability("APP", MobileConfiguration.AppPath);
			}

			capabilities.setCapability("appPackage", MobileConfiguration.App_PackageName);
			capabilities.setCapability("appActivity", MobileConfiguration.App_PackageActivityName);

			url = new URL("http://" + MobileConfiguration.AppiumPort + "/wd/hub");
			driver = new AndroidDriver<WebElement>(url, capabilities);
			driver.manage().timeouts().implicitlyWait(Long.parseLong(ConnectToMainController.ImplicityWait),
					TimeUnit.SECONDS);

		} else if (MobileConfiguration.DevicePlatform.equalsIgnoreCase("IOS")) {

			capabilities.setCapability("platformName", MobileConfiguration.DevicePlatform);
			capabilities.setCapability("deviceName", MobileConfiguration.DeviceName);
			capabilities.setCapability("platformVersion", MobileConfiguration.DevicePlatformVersion);
			capabilities.setCapability("name", MobileConfiguration.Process);

			if (MobileConfiguration.AppReset.equalsIgnoreCase("NO")) {
				capabilities.setCapability("noReset", true);
				capabilities.setCapability("fullReset", false);
			} else if (MobileConfiguration.AppReset.equalsIgnoreCase("YES")) {
				capabilities.setCapability("noReset", false);
				capabilities.setCapability("fullReset", true);
			} else if (MobileConfiguration.Pre_InstalledApp.equalsIgnoreCase("NO")) {
				capabilities.setCapability("APP", MobileConfiguration.AppPath);
			}

			capabilities.setCapability("appPackage", MobileConfiguration.App_PackageName);
			capabilities.setCapability("appActivity", MobileConfiguration.App_PackageActivityName);

			url = new URL("http://" + MobileConfiguration.AppiumPort + "/wd/hub");
			driver = new IOSDriver<WebElement>(url, capabilities);
			driver.manage().timeouts().implicitlyWait(Long.parseLong(ConnectToMainController.ImplicityWait),
					TimeUnit.SECONDS);
		}

	}

}
