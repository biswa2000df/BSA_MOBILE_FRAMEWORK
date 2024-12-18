package com.mahindra.mobile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;


public class Android_IOS_Driver {

	static WebDriver driver;
	static DesiredCapabilities capabilities;
	static URL url;
	static Map<String, Object> browserstackOptions;
	
	public static String browserDriverFolderPath;
	public static String browserDriverPath;
	public static String OS;

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
	

		
		
		public static void Initialisation(String browser) throws MalformedURLException {

			if (browser.equalsIgnoreCase("chrome")) {
				BrowserDriverFolder(browser);
				System.setProperty("webdriver.chrome.driver", browserDriverPath);
				ChromeOptions option = new ChromeOptions();
				option.addArguments("--remote-allow-origins=*");
				option.addArguments("--no-sandbox");
//				option.addArguments("--incognito");
				option.addArguments("--disable-notifications");
				option.addArguments("--disable-cache");
				option.addArguments("--disable-popup-blocking");

				
				option.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//				option.addArguments("--headless");

				driver = new ChromeDriver(option);
				driver.manage().window().maximize();
				 driver.manage().timeouts().implicitlyWait(Long.parseLong(ConnectToMainController.ImplicityWait), TimeUnit.SECONDS);
				

			}

			else if (browser.equalsIgnoreCase("edge")) {
			/*	BrowserDriverFolder(browser);
				WebDriverManager.edgedriver().setup();
				EdgeOptions option = new EdgeOptions();
				option.addArguments("--remote-allow-origins=*");
				driver = new EdgeDriver(option);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);*/
				System.exit(0);
			}
			
			else if (browser.equalsIgnoreCase("BrowserStack")) {
				/* DesiredCapabilities caps = new DesiredCapabilities();

			        Map<String, Object> browserstackOptions = new HashMap<String, Object>();
			        browserstackOptions.put("os", "Windows");
			        browserstackOptions.put("osVersion", "11");
			        browserstackOptions.put("browserName", "Chrome");
//			        browserstackOptions.put("browserVersion", "lattest");
			        browserstackOptions.put("browserVersion", "120");
			        browserstackOptions.put("projectName", "BrowserStack Test Project");
			        browserstackOptions.put("buildName", "IShine Automate");
			        browserstackOptions.put("sessionName", "Daily EOD");

			        caps.setCapability("bstack:options", browserstackOptions);
			        

			        
			        // Initialize the remote WebDriver using BrowserStack URL
			        driver = new RemoteWebDriver(new URL(URL), caps);
			        driver.manage().window().maximize();
			        sessionId = ((RemoteWebDriver) driver).getSessionId().toString();  */
			} 
			
			else {
				System.out.println("SORRY!!! U Choose InvalidBrowser");
				System.exit(0);
			}

		}
		
		public static void setWindowsSize(String size) {
			String part[] = size.split("x");
			if(part.length != 2) {
				 throw new IllegalArgumentException("Size string must be in the format 'widthxheight', e.g., '800x600'.");
			}
			int width = Integer.parseInt(part[0]);
			int height = Integer.parseInt(part[1]);
			
			Dimension dimension = new Dimension(width, height);
			driver.manage().window().setSize(dimension);
		}

//			driver.get("https://mail.apmosys.com/webmail/#sign-in");

		public static void BrowserDriverFolder(String Browser) {

			browserDriverFolderPath = System.getProperty("user.dir") + File.separator + "BrowserDriver";
			File BrowserDriverFolderPath = new File(browserDriverFolderPath);

			try {
				//check browser driver folder exist or not
				BrowserDriverFolderPath.exists();

				OS = System.getProperty("os.name");
				System.out.println("OS = " + OS); // Windows 11

				if (OS.equalsIgnoreCase("Linux")) {
					// calling to the linux and ubuntu browser file
					LinuxAndUbuntuBrowserDriver(Browser); 
				} else {
					// calling to the windowsbrowser .exe file
					WindowBrowserDriver(Browser);
				}

				File BrowserDriverPath = new File(browserDriverPath);
				try {
					//check driver path are exist or not
					BrowserDriverPath.exists();
				} catch (Exception e) {
					System.out.println("SORRY!!!  " + Browser + "Driver.exe File is Not Present");
					System.exit(1);
				}

			} catch (Exception e) {
				System.out.println("SORRY!!!  'BrowserDriver' folder is Not Present");
				System.exit(1);
			}
		}

		public static void WindowBrowserDriver(String BrowserEXEFile) {
			switch (BrowserEXEFile) {
			case "CHROME":
				browserDriverPath = System.getProperty("user.dir") + File.separator + "BrowserDriver" + File.separator
						+ "chromedriver.exe";
				break;
			case "EDGE":
				browserDriverPath = System.getProperty("user.dir") + File.separator + "BrowserDriver" + File.separator
						+ "edgedriver.exe";
				break;
			case "chrome":
				browserDriverPath = System.getProperty("user.dir") + File.separator + "BrowserDriver" + File.separator
						+ "chromedriver.exe";
				break;
			case "edge":
				browserDriverPath = System.getProperty("user.dir") + File.separator + "BrowserDriver" + File.separator
						+ "edgedriver.exe";
				break;
			case "Chrome":
				browserDriverPath = System.getProperty("user.dir") + File.separator + "BrowserDriver" + File.separator
						+ "chromedriver.exe";
				break;
			case "Edge":
				browserDriverPath = System.getProperty("user.dir") + File.separator + "BrowserDriver" + File.separator
						+ "edgedriver.exe";
				break;
			default:
				System.out.println("SORRY!!! Your OS = " + OS + ", But You select Invalid Driver");
				System.exit(0);

			}
		}

		public static void LinuxAndUbuntuBrowserDriver(String BrowserEXEFile) {
			switch (BrowserEXEFile) {
			case "CHROME":
				browserDriverPath = System.getProperty("user.dir") + File.separator + "BrowserDriver" + File.separator
						+ "chromedriver";
				break;
			case "EDGE":
				browserDriverPath = System.getProperty("user.dir") + File.separator + "BrowserDriver" + File.separator
						+ "edgedriver";
				break;
			case "chrome":
				browserDriverPath = System.getProperty("user.dir") + File.separator + "BrowserDriver" + File.separator
						+ "chromedriver";
				break;
			case "edge":
				browserDriverPath = System.getProperty("user.dir") + File.separator + "BrowserDriver" + File.separator
						+ "edgedriver";
				break;
			case "Chrome":
				browserDriverPath = System.getProperty("user.dir") + File.separator + "BrowserDriver" + File.separator
						+ "chromedriver";
				break;
			case "Edge":
				browserDriverPath = System.getProperty("user.dir") + File.separator + "BrowserDriver" + File.separator
						+ "edgedriver";
				break;
			default:
				System.out.println("SORRY!!! Your OS  = " + OS + ", But You select Invalid Driver");
				System.exit(0);

			}
		}


}
