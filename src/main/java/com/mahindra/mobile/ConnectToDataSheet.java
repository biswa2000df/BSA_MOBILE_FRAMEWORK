package com.mahindra.mobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ConnectToDataSheet {

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

	static LocatorManager locatorManager;

	public static void extractAllData() throws Exception {

		// create object
		locatorManager = new LocatorManager();

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

					int i;
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
						System.out.println("ActionType        ====================> " + Action);

						

						MobileConfiguration.mobileConfigurationSheet();
						
						locatorManager.mapToLocator();
						
					}
					if (i == rowsList.size()) {

						System.out.println("\n");
						System.out.println("********************  Successfully Completed  ********************" + "\n");
					}
				}
//					System.out.println("'DataSheet' sheet are present And RunStatus are 'Y' and modul name also same");
				recordset.close();

			} catch (Exception e) {
				System.out.println("SORRY!!! 'DataSheet' sheet are present BUT problem on ModulName column value");
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println("SORRY!!! 'DataSheet' sheet are present or not or SheetName is Missmatch ");
			System.exit(0);
		}

	}

	public static void extractTestData() throws Exception {
		
		Fillo fillo = new Fillo();
		Connection conn = fillo.getConnection(ConnectToMainController.dataSheetFilePath);
		Recordset recordset = null;
		String query = "SELECT * FROM Sheet2";
		String queryForModule = "SELECT * FROM Sheet2 WHERE RUNSTATUS='Y' and ApplicationName='" + Module + "'";
		try {
			recordset = conn.executeQuery(query);
			if (recordset != null) {

				List<String> actualColumnName = recordset.getFieldNames();
				List<String> exceptedColumnName = new ArrayList<String>();
				exceptedColumnName.addAll(Arrays.asList("Si_No", "RunStatus", "ApplicationName", "Proceed", "Verify"));

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
							+ "' these columns are not present or spelling missmatch or upperLowercase validate ");
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
					
					if(recordset != null) {
						recordset.close();
					}
					
					for (int i = 0; i < rowsList.size(); i++) {

						List<Object> row = (List<Object>) rowsList.get(i);

						dataSheetSi_No = (String) row.get(0);
						ApplicationName = (String) row.get(2);
						Proceed = (String) row.get(3);
						Verify = (String) row.get(4);
						
						if (dataSheetSi_No != null && !dataSheetSi_No.isEmpty() && ApplicationName != null
								&& !ApplicationName.isEmpty() ) {
							
							System.out.println("SI_No             ====================> " + dataSheetSi_No);
							System.out.println("ApplicationName        ====================> " + ApplicationName);
							System.out.println("Proceed      ====================> " + Proceed);
							System.out.println("Verify     ====================> " + Verify);
							
							///here i write the query to get the sheet to value and call the function class
							
							
						} else {
							System.out.println("Please filled the data MainController DataSheet !!!");
							System.exit(0);
						}
						
						

					}
					
				}
//					System.out.println("'DataSheet' sheet are present And RunStatus are 'Y' and modul name also same");
				recordset.close();

			} catch (Exception e) {
				System.out.println("SORRY!!! 'DataSheet' sheet are present BUT problem on ApplicationName column value");
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println("SORRY!!! 'DataSheet' sheet are present or not or SheetName is Missmatch ");
			System.exit(0);
		}

	}

}
