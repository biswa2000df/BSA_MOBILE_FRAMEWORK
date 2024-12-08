package com.mahindra.mobile;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class Android_IOS_Driver {

	static WebDriver driver;
	static DesiredCapabilities capabilities;
	static URL url;
	static Map<String, Object> browserstackOptions;

	public static void InitialisationDriverRemote() throws Exception {
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String formattedDate = formatter.format(now);

		final String BrowserStackUrl = "https://" + MobileConfiguration.UserName + ":" + MobileConfiguration.AccessKey
				+ "@hub-cloud.browserstack.com/wd/hub";

		browserstackOptions = new HashMap<String, Object>();

		capabilities = new DesiredCapabilities();

		try {

			if (MobileConfiguration.DevicePlatform.equalsIgnoreCase("Android")) {

				capabilities.setCapability("platformName", MobileConfiguration.DevicePlatform);
				capabilities.setCapability("deviceName", MobileConfiguration.DeviceName);
				capabilities.setCapability("platformVersion",  MobileConfiguration.DevicePlatformVersion);
				capabilities.setCapability("name", MobileConfiguration.Process + " BISWAJIT SAHOO");

				if (MobileConfiguration.AppReset.equalsIgnoreCase("NO")) {
					capabilities.setCapability("noReset", true);
					capabilities.setCapability("fullReset", false);
				} else if (MobileConfiguration.AppReset.equalsIgnoreCase("YES")) {
					capabilities.setCapability("noReset", false);
					capabilities.setCapability("fullReset", true);
				} else if (MobileConfiguration.Pre_InstalledApp.equalsIgnoreCase("NO")) {
					capabilities.setCapability("APP", MobileConfiguration.AppPath);
				}

				capabilities.setCapability("app", "bs://a1a4c73044c410ff61b3d725e6f510b031088676");
				capabilities.setCapability("browserstack.enableCameraImageInjection", true);
				capabilities.setCapability("interactiveDebugging", true);
				capabilities.setCapability("build", "Udaan_" + MobileConfiguration.Process + " - SahooBiswajit");
				capabilities.setCapability("name", "UDAAN_"+ MobileConfiguration.Process + "_ANDROID_APP" + formattedDate);
				capabilities.setCapability("autoGrantPermissions", true);
				capabilities.setCapability("browserstack.debug", true);
				capabilities.setCapability("browserstack.video", true);
				capabilities.setCapability("acceptSslCerts", true);

//				capabilities.setCapability("appPackage", MobileConfiguration.App_PackageName);
//				capabilities.setCapability("appActivity", MobileConfiguration.App_PackageActivityName);

				url = new URL(BrowserStackUrl);
				driver = new AndroidDriver(url, capabilities);
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
				driver = new IOSDriver(url, capabilities);
				driver.manage().timeouts().implicitlyWait(Long.parseLong(ConnectToMainController.ImplicityWait),
						TimeUnit.SECONDS);

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	
	}

	public static void InitialisationDriverLocal() throws Exception {

		capabilities = new DesiredCapabilities();

		try {

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
				driver = new AndroidDriver(url, capabilities);
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
				driver = new IOSDriver(url, capabilities);
				driver.manage().timeouts().implicitlyWait(Long.parseLong(ConnectToMainController.ImplicityWait),
						TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	

	}
	
	public static void InitialisationWebDriverLocal() throws Exception {
		

		try {
			
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\biswa\\Downloads\\chromedriver-win64 (10)\\chromedriver-win64\\chromedriver.exe");

			ChromeOptions option = new ChromeOptions();
			option.addArguments("--remote-allow-origins=*");
			option.addArguments("--no-sandbox");
//			option.addArguments("--incognito");
			option.addArguments("--disable-notifications");
			option.addArguments("--disable-cache");
	        option.addArguments("--disable-popup-blocking");

			
			option.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//			option.addArguments("--headless");

			driver = new ChromeDriver(option);
			driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Long.parseLong(ConnectToMainController.ImplicityWait), TimeUnit.SECONDS);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	

	}


}
