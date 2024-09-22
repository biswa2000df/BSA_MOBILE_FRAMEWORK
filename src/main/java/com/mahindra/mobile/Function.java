package com.mahindra.mobile;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Function extends ConnectToDataSheet {

	public static WebElement element;
	public static List<WebElement> elements;
	static UtilsActivity utilsActivity;
	public static boolean ActualResult;
	public static long executionStartTime;
	static TouchAction touchAction;

	Function() {
		element = LocatorManager.webElement;
		elements = LocatorManager.webElements;
	}

	public static void ActionRDS() throws Exception {
		
		utilsActivity = new UtilsActivity();

		try {

			if (Action.equalsIgnoreCase("Monitoring_Properties")) {
				// start time count
				executionStartTime = System.nanoTime();
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
				if (dataSheet2Value.equals("Y")) {
					element.click();
				} else {
					System.out.println("Please do Sheet2 proceed value 'Y' ");
				}
			}

			else if (Action.equalsIgnoreCase("SendKeys")) {
				element.sendKeys(dataSheet2Value);
			}

			else if (Action.equalsIgnoreCase("StartDriver")) {
				MobileConfiguration.mobileConfigurationSheet();
			}

			else if (Action.equalsIgnoreCase("OpenApp_UsingOnlyAppPackage")) {
				driver.activateApp(MobileConfiguration.App_PackageName);
			}

			else if (Action.contains("WAIT")) {
				String input = Action;
				String digits = input.replaceAll("\\D+", "");
				Thread.sleep(Integer.parseInt(digits));

			}
			
			else if(Action.equalsIgnoreCase("ScrollDown")) {
				// Get the size of the screen
				int screenHeight = driver.manage().window().getSize().getHeight();
				int screenWidth = driver.manage().window().getSize().getWidth();
//				System.out.println("screenHeight = " + screenHeight);
//				System.out.println("screenWidth = " + screenWidth);
				
				
				// Calculate the start and end points for the swipe
				int startX = screenWidth / 2; // Horizontal center of the screen
				int startY = (int) (screenHeight * 0.8); // Start from the top 80% of the screen
				int endY = (int) (screenHeight * 0.3); // Swipe down to 30% of the screen height

				// Perform the swipe gesture for pull down to refresh
			    touchAction = new TouchAction(driver);
				touchAction
				    .press(PointOption.point(startX, startY))
				    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))) // Hold for a moment
				    .moveTo(PointOption.point(startX, endY))
				    .release()
				    .perform();
			}
			
			else if (Action.equalsIgnoreCase("CheckVisibility")) {

				Boolean Verify = Boolean.parseBoolean(dataSheet2Value);

				if (Verify) {
					try {
//						System.out.println(element);
						ActualResult =element.isDisplayed();
//						System.out.println("ActualResultpass========="+ActualResult);
						if (ActualResult) {
							ConnectToDataSheet.status="PASS";
							utilsActivity.passTestCase();
//							System.out.println("webElement is Display");

						}
					} catch (Exception e) {
						ActualResult = false;
						System.out.println("ActualResultFail======"+ActualResult);
						ConnectToDataSheet.status="FAIL";
						ConnectToDataSheet.failedValidations++;
						utilsActivity.failTestCase();
//						System.out.println("webElement is not Display");
					}
				}

			}
		} catch (Exception e) {
//			System.out.println(e);
		}

	}

}
