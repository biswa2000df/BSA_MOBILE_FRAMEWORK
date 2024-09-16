package com.mahindra.mobile;

import java.util.List;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class Function extends ConnectToDataSheet {

	public static WebElement element;
	public static List<WebElement> elements;

	Function() {
		element = LocatorManager.webElement;
		elements = LocatorManager.webElements;
	}

	public static void ActionRDS() throws Exception {

		if (Action.equalsIgnoreCase("Monitoring_Properties")) {
			// start time count
		}

		else if (Action.equalsIgnoreCase("START_APPLICATION")) {
			MobileConfiguration.mobileConfigurationSheet();
			if (ConnectToMainController.ExecutionType.equalsIgnoreCase("local")) {
				Android_IOS_Driver.InitialisationDriverLocal();
			} else if (ConnectToMainController.ExecutionType.equalsIgnoreCase("remote")) {
				Android_IOS_Driver.InitialisationDriverRemote();
			}
		}

		else if (Action.equalsIgnoreCase("InstallAndStartApplication")) {
			MobileConfiguration.mobileConfigurationSheet();
		}

		else if (Action.equalsIgnoreCase("QUIT")) {
			driver.quit();
		}

		else if (Action.equalsIgnoreCase("KeyBoardSendKeys")) {
			driver.getKeyboard().sendKeys(dataSheet2Value);
		}

		else if (Action.equalsIgnoreCase("CLICK")) {
			element.click();
		}

		else if (Action.equalsIgnoreCase("SendKeys")) {
			element.sendKeys(dataSheet2Value);
		}

		else if (Action.equalsIgnoreCase("StartDriver")) {

//			Android_IOS_Driver.InitialisationDriverLocalActivateApp();
			MobileConfiguration.mobileConfigurationSheet();
		}

		else if (Action.equalsIgnoreCase("OpenApp_UsingOnlyAppPackage")) {
			// MobileConfiguration.mobileConfigurationSheet();
			driver.activateApp(MobileConfiguration.App_PackageName);
//			driver.activateApp("com.mahindra.fosqa");
		}

		else if (Action.contains("WAIT")) {
			String input = Action;
			String digits = input.replaceAll("\\D+", "");
			Thread.sleep(Integer.parseInt(digits));

		}

	}

}
