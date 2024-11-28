package com.mahindra.mobile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openqa.selenium.WebElement;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.screenrecording.CanRecordScreen;

public class ConnectToDataSheet extends Android_IOS_Driver {

	public static String Si_No;
	public static String Module;
	public static String PageName;
	public static String RunStatus;
	public static String PropertyName;
	public static String PropertyValue;
	public static String DataField;
	public static String Action;
	public static String ActionType;
	public static String ScenarioID;
	public static String ScenarioDescription;
	public static String TestCaseID;
	public static String TestCaseDescription;
	public static String TestCaseStepID;
	public static String TestCaseStepDescription;

	// sheet-2
	public static String dataSheetSi_No;
	public static String ApplicationName;
	public static String Proceed;
	public static String Verify;

	public static WebElement webElement;
	public static List<WebElement> webElements;

	public static String dataSheet2Value;
	public static String sTest_Case = null;

	public static String status; // here status are mention only for write the csv file status 'pass' or 'fail'

	static LocatorManager locatorManager;
	static Function function;
	static UtilsActivity utilsActivity;

	public static int totalTest, pass, fail;
	public static int totalValidations, passValidations, failedValidations;
	
	public static int globallySheetTwoRowCount;

	public static void extractAllData(int sheet2rowCount) throws Exception {
		
		for (int SheetTwoRowCount = 1; SheetTwoRowCount <= sheet2rowCount; SheetTwoRowCount++) {

			globallySheetTwoRowCount = SheetTwoRowCount;
			
			totalTest = 0;
			pass = 0;
			fail = 0;
			totalValidations = 0;
			passValidations = 0;
			failedValidations = 0;

			// create object
			locatorManager = new LocatorManager();
			utilsActivity = new UtilsActivity();

			Fillo fillo = new Fillo();
			Connection conn = fillo.getConnection(ConnectToMainController.dataSheetFilePath);
			Recordset recordset = null;
			String query = "SELECT * FROM Sheet1";
			String queryForModule = "SELECT * FROM Sheet1 WHERE RUNSTATUS='Y' and MODULE='"
					+ ConnectToMainController.Process + "'";
			try {
				recordset = conn.executeQuery(query);
				if (recordset != null) {

					List<String> actualColumnName = recordset.getFieldNames();
					List<String> exceptedColumnName = new ArrayList<String>();
					exceptedColumnName.addAll(Arrays.asList("Si_No", "Module", "PageName", "RunStatus", "PropertyName",
							"PropertyValue", "DataField", "Action", "ActionType", "ScenarioID", "ScenarioDescription",
							"TestCaseID", "TestCaseDescription", "TestCaseStepID", "TestCaseStepDescription"));

					List<String> notPresentColumn = new ArrayList<String>();

					Boolean allColumnPresent = true;

					for (String columnName : actualColumnName) {
						if (!exceptedColumnName.contains(columnName)) {
							notPresentColumn.add(columnName);
							allColumnPresent = false;
						}
					}

					if (allColumnPresent) {
//					System.out.println("All the columnName are present");
						recordset.close();
					} else {
						System.out.println("SORRY!!! '" + notPresentColumn
								+ "' these columns are not present or spelling missmatch or upperLowercase validate");
						System.exit(0);
					}
				}
				try {

					recordset = conn.executeQuery(queryForModule);// here to check the runstatus and module

					if (recordset != null) {

						List<Object> rowsList = new ArrayList<Object>();

						while (recordset.next()) {
							List<String> columns = recordset.getFieldNames();
							List<Object> rowValues = new ArrayList<Object>();
							for (String column : columns) {
								rowValues.add(recordset.getField(column));
							}
							rowsList.add(rowValues);
						}

						try {

							utilsActivity.extentReport(); // call the extent report method
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}

						int i; // for this for-loop
						for (i = 0; i < rowsList.size(); i++) {

							List<Object> row = (List<Object>) rowsList.get(i);

							Si_No = (String) row.get(0);
							Module = (String) row.get(1);
							PageName = (String) row.get(2);
							PropertyName = (String) row.get(4);
							PropertyValue = (String) row.get(5);
							DataField = (String) row.get(6);
							Action = (String) row.get(7);
							ActionType = (String) row.get(8);
							ScenarioID = (String) row.get(9);
							ScenarioDescription = (String) row.get(10);
							TestCaseID = (String) row.get(11);
							TestCaseDescription = (String) row.get(12);
							TestCaseStepID = (String) row.get(13);
							TestCaseStepDescription = (String) row.get(14);

							System.out.println();
							System.out.println("SI_No             ====================> " + Si_No);
							System.out.println("ScenarioID        ====================> " + ScenarioID);
							System.out.println("PropertyName      ====================> " + PropertyName);
							System.out.println("PropertyValue     ====================> " + PropertyValue);
							System.out.println("Datafield         ====================> " + DataField);
							System.out.println("Action            ====================> " + Action);

							try {

								if (!TestCaseID.equals(sTest_Case)) { // here we created the report to the testcase id
									utilsActivity.testCaseCreate();
								}
								sTest_Case = TestCaseID;
								totalTest++;

								if (Action.equalsIgnoreCase("CheckVisibility")) {
									totalValidations++;
								}

								locatorManager.mapToLocator();

								pass = totalTest - fail;
								passValidations = totalValidations - failedValidations;

//							System.out.println("TotalValidation = " + totalValidations + " PassedValidations = " + passValidations + " FailedValidations = " + failedValidations);
//							System.out.println("TotalTest = " + totalTest + " Pass = " + pass + " Fail = " + fail);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
						if (i == rowsList.size()) {
							System.out.println("\n");
//						System.out.println("TotalTest = " + totalTest + " Pass = " + pass + " Fail = " + fail);

							System.out.println(
									"********************  Successfully Completed  ********************" + "\n");
						}
					}
					if (recordset != null)
						recordset.close();

				} catch (Exception e) {
					System.out.println("SORRY!!! 'DataSheet' sheet are present BUT problem on ModulName column value");
					System.exit(0);
				}
			} catch (Exception e) {
				System.out.println("SORRY!!! 'DataSheet' sheet are present or not or SheetName is Missmatch ");
				System.exit(0);
			} finally {
				utilsActivity.ExecutionTime();
				utilsActivity.ExtentFlush();
				UtilsActivity.CreateHtmlTable();
				MailSend.mailSend();
			}
		}

	}


	public static void extractTestData() throws Exception {
		function = new Function();

		if (DataField != null && !DataField.isEmpty()) {
			Fillo fillo = new Fillo();
			Connection conn = fillo.getConnection(System.getProperty("user.dir") + File.separator + "DataSheet"
					+ File.separator + ConnectToMainController.TestDataSheet);
			String query = "SELECT * FROM Sheet2 WHERE RUNSTATUS='Y' and ApplicationName='" + Module + "'";
			
			Recordset recordset = null;
			int currentRowSheetTwo = 0;

			try {
				recordset = conn.executeQuery(query);

				while (recordset.next()) {

					currentRowSheetTwo++;
					
					if (currentRowSheetTwo != globallySheetTwoRowCount) {
						continue;
					} else {

						dataSheetSi_No = recordset.getField("Si_No");
						ApplicationName = recordset.getField("ApplicationName");
						Proceed = recordset.getField("Proceed");
						Verify = recordset.getField("Verify");

						dataSheet2Value = recordset.getField(DataField);
						System.out.println("DataFiels For Sheet2====================================================> "
								+ dataSheet2Value + "\n");
					}
				}

				utilsActivity.testcaseInfoWithDataField(); // print extent report info
				function.ActionRDS();
			} catch (Exception e) {

			}

		} else {
			try {
				utilsActivity.testcaseInfoWithoutDataField(); // print extent report info
				function.ActionRDS();
			} catch (Exception e) {

			}
		}
	}

}
