package com.mahindra.mobile;

import java.awt.Robot;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Keyboard;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Function extends ConnectToDataSheet {

	public static WebElement element;
	public static List<WebElement> elements;
	static UtilsActivity utilsActivity;
	public static boolean ActualResult;
	public static long executionStartTime;
	static TouchAction touchAction;
	public static int randomNumber;

	Function() {
		element = LocatorManager.webElement;
		elements = LocatorManager.webElements;
	}

	public static void ActionRDS() throws Exception {
		
		 Robot robot;
		
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
			
			else if (Action.equalsIgnoreCase("CLEAR")) {
				element.clear();
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
			
			else if (Action.contains("cameraImageInjection")) {
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("browserstack_executor: {\"action\": \"cameraImageInjection\", \"arguments\": {\"imageUrl\": \"media://" + dataSheet2Value + "\"}}");


			}
			
			else if (Action.contains("GenerateRandomNumber")) {
				 int numDigits = Integer.parseInt(dataSheet2Value);

			        // Calculate the minimum and maximum values for the given number of digits
			        int min = (int) Math.pow(10, numDigits - 1);
			        int max = (int) Math.pow(10, numDigits) - 1;

			        // Generate the random number in the given range
			        Random random = new Random();
			        randomNumber = min + random.nextInt(max - min + 1);
			        System.out.println("Generating random Number = " + randomNumber);

			}
			
			else if (Action.contains("MPIN")) {
				String mpin = String.valueOf(randomNumber);
				element.sendKeys(mpin);
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

			} else if (Action.contains("ScrollUsingCoordinate")) {
				
				
				 String input = Action;

			        // Remove the part before '(' and after ')'
			        String numbersPart = input.substring(input.indexOf('(') + 1, input.indexOf(')'));

			        // Split the numbers into an array
			        String[] values = numbersPart.split(",");

			        // Parse and assign to individual variables
			        int startX = Integer.parseInt(values[0].trim());
			        int startY = Integer.parseInt(values[1].trim());
			        int endX = Integer.parseInt(values[2].trim());
			        int endY = Integer.parseInt(values[3].trim());
				
				// Define the start and end points for the swipe
//				int startX = 0; // X coordinate for the start point
//				int startY = 0; // Y coordinate for the start point
//				int endX = 0;   // X coordinate for the end point
//				int endY = 0;   // Y coordinate for the end point
				
//				String dotArray[] = dataSheet2Value.split("\\.");
				
				TouchAction touchAction = new TouchAction(driver);
				
//				startX = Integer.parseInt(dotArray[0]);
//				startY = Integer.parseInt(dotArray[1]);
//				endX = Integer.parseInt(dotArray[2]);
//				endY = Integer.parseInt(dotArray[3]);
			

				// Perform the swipe action
				touchAction.press(PointOption.point(startX, startY))
				            .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				            .moveTo(PointOption.point(endX, endY))
				            .release()
				            .perform();
			} else if(Action.equalsIgnoreCase("ScrollIntoElementIntoText")) {
				driver.executeScript("mobile: scroll", 
					    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + dataSheet2Value + "\"));");

			} else if(Action.equalsIgnoreCase("HideKeyBoard")) {
				driver.hideKeyboard();
			}  
			
			else if(Action.equalsIgnoreCase("ScrollIntoElementIntoContentDesc")) {
				MobileElement scrollElement = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
					    "new UiScrollable(new UiSelector().scrollable(true))" +
					    ".scrollIntoView(new UiSelector().description(\"" + dataSheet2Value + "\"))"));
			}
			
			else if(Action.equalsIgnoreCase("ScrollIntoElementAndClickUsingContentDesc")) {
				MobileElement scrollElement = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
					    "new UiScrollable(new UiSelector().scrollable(true))" +
					    ".scrollIntoView(new UiSelector().description(\"" + dataSheet2Value + "\"))"));
				scrollElement.click();
				
			}
			
			else if(Action.equalsIgnoreCase("ScrollDownLittleBit")) {
				

					// Get the size of the screen
					int screenHeight = driver.manage().window().getSize().getHeight();
					int screenWidth = driver.manage().window().getSize().getWidth();
//					System.out.println("screenHeight = " + screenHeight);
//					System.out.println("screenWidth = " + screenWidth);
					
					
					 int startX = screenWidth / 2; // Horizontal center of the screen
					 int startY = (int) (screenHeight * 0.6); // Start from 60% of the screen height
					 int endY = (int) (screenHeight * 0.5); // Swipe down to 50% of the screen height

					// Perform the swipe gesture for pull down to refresh
					TouchAction touchAction = new TouchAction(driver);
					touchAction
					    .press(PointOption.point(startX, startY))
					    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))) // Hold for a moment
					    .moveTo(PointOption.point(startX, endY))
					    .release()
					    .perform();
				
				
			}
			
			else if(Action.equalsIgnoreCase("ClickOnSearchApplication")) {
				driver.findElement(By.xpath("//android.view.View[contains(@content-desc,'ID: " + dataSheet2Value + "' )]")).click();
			}
			
			
			else if (Action.equalsIgnoreCase("UntilScrollElementView")) {

				boolean isElementVisible = false;

				while (!isElementVisible) {
					try {
						// Locate the element using content-desc
						
						String xpathExpression = "//android.view.View[contains(@content-desc,'" + dataSheet2Value + "')]";
						MobileElement element = (MobileElement) driver.findElement(By.xpath(xpathExpression));
						
						
						if (element.isDisplayed()) {
							isElementVisible = true; // Mark as found
//							element.click(); // Optional: Click the element
//							System.out.println("Element found and clicked!");
							break; // Exit the loop immediately
						}
					} catch (Exception e) {

						int screenHeight = driver.manage().window().getSize().getHeight();
						int screenWidth = driver.manage().window().getSize().getWidth();

						int startX = screenWidth / 2;
						int startY = (int) (screenHeight * 0.6);
						int endY = (int) (screenHeight * 0.5);

						// Perform the swipe gesture for pull down to refresh
						TouchAction touchAction = new TouchAction(driver);
						touchAction.press(PointOption.point(startX, startY))
								.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300))) // Hold for a moment
								.moveTo(PointOption.point(startX, endY)).release().perform();
					}

				}

			}
			
			else if (Action.equalsIgnoreCase("UntilScrollDownElementView")) {

				boolean isElementVisible = false;

				while (!isElementVisible) {
					try {
						// Locate the element using content-desc
						
						String xpathExpression = PropertyValue;
						MobileElement element = (MobileElement) driver.findElement(By.xpath(xpathExpression));
						
						
						if (element.isDisplayed()) {
							isElementVisible = true; // Mark as found
//							element.click(); // Optional: Click the element
//							System.out.println("Element found and clicked!");
							break; // Exit the loop immediately
						}
					} catch (Exception e) {

						int screenHeight = driver.manage().window().getSize().getHeight();
						int screenWidth = driver.manage().window().getSize().getWidth();

						int startX = screenWidth / 2;
						int startY = (int) (screenHeight * 0.6);
						int endY = (int) (screenHeight * 0.5);

						// Perform the swipe gesture for pull down to refresh
						TouchAction touchAction = new TouchAction(driver);
						touchAction.press(PointOption.point(startX, startY))
								.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300))) // Hold for a moment
								.moveTo(PointOption.point(startX, endY)).release().perform();
					}
				}
			}
			
			else if (Action.equalsIgnoreCase("UntilScrollUpElementView")) {

				boolean isElementVisible = false;

				while (!isElementVisible) {
					try {
						// Locate the element using content-desc
						
						String xpathExpression = PropertyValue;
						MobileElement element = (MobileElement) driver.findElement(By.xpath(xpathExpression));
						
						
						if (element.isDisplayed()) {
							isElementVisible = true; // Mark as found
//							element.click(); // Optional: Click the element
//							System.out.println("Element found and clicked!");
							break; // Exit the loop immediately
						}
					} catch (Exception e) {

						int screenHeight = driver.manage().window().getSize().getHeight();
						int screenWidth = driver.manage().window().getSize().getWidth();

						int startX = screenWidth / 2;
						int startY = (int) (screenHeight * 0.5);
						int endY = (int) (screenHeight * 0.6);

						// Perform the swipe gesture for pull down to refresh
						TouchAction touchAction = new TouchAction(driver);
						touchAction.press(PointOption.point(startX, startY))
								.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300))) // Hold for a moment
								.moveTo(PointOption.point(startX, endY)).release().perform();
					}

				}
			}
			
			else if (Action.equalsIgnoreCase("ScrollUpDownElementUnTillVisiable")) {

				boolean isElementVisible = false;
				boolean scrollDown = true; // Initial scroll direction
				int scrollAttempts = 0;
				int maxScrollAttempts = 10; // Set a limit to avoid infinite scrolling

				while (!isElementVisible && scrollAttempts < maxScrollAttempts) {
				    try {
				        // Locate the element using content-desc
				        String xpathExpression = PropertyValue;
				        MobileElement element = (MobileElement) driver.findElement(By.xpath(xpathExpression));

				        if (element.isDisplayed()) {
				            isElementVisible = true; // Mark as found
				            System.out.println("Element found!");
				            break; // Exit the loop immediately
				        }
				    } catch (Exception e) {
				        // Get screen size
				        int screenHeight = driver.manage().window().getSize().getHeight();
				        int screenWidth = driver.manage().window().getSize().getWidth();

				        int startX = screenWidth / 2;
				        int startY, endY;

				        if (scrollDown) {
				            // Scroll down
				            startY = (int) (screenHeight * 0.6);
				            endY = (int) (screenHeight * 0.5);
				        } else {
				            // Scroll up
				            startY = (int) (screenHeight * 0.5);
				            endY = (int) (screenHeight * 0.6);
				        }

				        // Perform the swipe gesture
				        TouchAction touchAction = new TouchAction(driver);
				        touchAction.press(PointOption.point(startX, startY))
				                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300))) // Hold for a moment
				                .moveTo(PointOption.point(startX, endY)).release().perform();

				        // Update the scroll direction if needed
				        scrollAttempts++;
				        if (scrollAttempts >= maxScrollAttempts / 2) {
				            scrollDown = !scrollDown; // Switch direction after half the attempts
				        }
				    }
				}

				if (!isElementVisible) {
				    System.out.println("Element not found after scrolling in both directions.");
				}

				
			}
			
			
			//custome Function for FOS
			
			else if(Action.equalsIgnoreCase("SelectDropDownForSanction")) {
//				driver.findElement(By.xpath("//android.view.View[@content-desc='1']")).click();
//				List<WebElement> options = elements;
//				System.out.println("Number of option Size : " + elements.size());
				

				for (WebElement option : elements) {
				    try {
				        String optionText = option.getAttribute("content-desc");
//				        System.out.println("Dropdown Value for "+ section + " = " + optionText);
				        
				        if (optionText.contains(dataSheet2Value)) {
				            option.click();
				            break;
				        }
				    } catch (Exception e) {
//				        System.out.println("Error retrieving content-desc for option: " + e.getMessage());
				    }
				}
			}
			
			else if(Action.equalsIgnoreCase("Dedupe")) {
				try {
				driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Select\"]")).click();
				driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Proceed\"]")).click();
				}catch(Exception e) {
					driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"No Match\"]")).click();
				}
				
			}
			

		} catch (Exception e) {
//			System.out.println(e);
		}

	}
	
	public static void scrollScreen(AppiumDriver driver) {
	    TouchAction action = new TouchAction(driver);

	    // Swipe up (adjust the coordinates for your screen resolution)
	    action.press(PointOption.point(500, 1500)) // Start point (x, y)
	          .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))) // Wait time during swipe
	          .moveTo(PointOption.point(500, 500)) // End point (x, y)
	          .release()
	          .perform();
	}

}
