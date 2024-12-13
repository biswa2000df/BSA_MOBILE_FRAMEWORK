package com.mahindra.mobile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class TestingClass {
	
	public static String Si_No;
	public static String ExecutionType;
	public static String ApplicationName;
	public static String Process;

	// DataSheet
	public static String dataSheetProcess = null;
	public static String TestDataSheet = null;
	public static String ImplicityWait = null;
	public static String ExplicityWait = null;

	// FilePath
	public static String mainControllerFilePath;
	public static String dataSheetFilePath;

	public static void main(String[] args) {

//	public static void main(String[] args) {

		mainControllerFilePath = System.getProperty("user.dir") + File.separator + "MainController2.xlsx";
		File mainControllerFile = new File(mainControllerFilePath);

		try {
			mainControllerFile.exists();
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(mainControllerFilePath);

			String query = "SELECT * FROM MAIN_CONTROLLER";
			String queryForProcessName = "SELECT * FROM MAIN_CONTROLLER Where RunStatus='Y'";
			Recordset recordset = null;

			try {
				recordset = connection.executeQuery(query);
				if (recordset != null) {
//					System.out.println("'MainController' sheet are present...");

					List<String> actualColumnName = recordset.getFieldNames();
					List<String> exceptedColumnName = new ArrayList<String>();
					exceptedColumnName
							.addAll(Arrays.asList("Si_No", "RunStatus", "ExecutionType", "ApplicationName", "Process"));

					List<String> notPresentColumn = new ArrayList<String>();

					Boolean allColumnPresent = true;

					for (String columnName : actualColumnName) {
						if (!exceptedColumnName.contains(columnName)) {
							notPresentColumn.add(columnName);
							allColumnPresent = false;
						}
					}
					if (allColumnPresent) {
//						System.out.println("All the columnName are present");
						recordset.close();
					} else {
						System.out.println("SORRY!!! '" + notPresentColumn + "' these columns are not present");
						System.exit(0);
					}
				}
			} catch (Exception e) {
				System.out.println("SORRY!!!  'MainControllerSheet' sheet are not present...");
				System.exit(0);
			}

			try {
				recordset = connection.executeQuery(queryForProcessName);
				if (recordset != null) {

					List<Object> rowlist = new ArrayList<Object>();

					while (recordset.next()) {
						List<String> columns = recordset.getFieldNames();
						List<Object> rowvalues = new ArrayList<Object>();

						for (String column : columns) {
							rowvalues.add(recordset.getField(column));
						}
						rowlist.add(rowvalues);
					}

					if (recordset != null) {
//						System.out.println("'MainController' sheet are present And RunStatus = 'Y'");
						recordset.close();
					}

//						System.out.println("MainController Row List "+rowlist);    
					int i;
					for ( i = 0; i < rowlist.size(); i++) {
						List<Object> row = (List<Object>) rowlist.get(i);

						Si_No = (String) row.get(0);
						ExecutionType = (String) row.get(2);
						ApplicationName = (String) row.get(3);
						Process = (String) row.get(4);

						
						System.out.println("Si_No==============" + Si_No);
						System.out.println("ExecutionType======" + ExecutionType);
						System.out.println("ApplicationName====" + ApplicationName);
						System.out.println("Process============" + Process);
						
							Boolean value = MainControlerDataSheet(Process);// xlsx file
							
							if(value) {
								i = rowlist.size();
							}
							
//							MobileConfiguration.mobileConfigurationSheet();

						
					}
				}
			} catch (Exception e) {
				System.out.println("SORRY!!! 'MainController' sheet are present BUT problem at RunStatus");
				System.exit(0);
			}

		} catch (Exception e) {
			System.out.println("SORRY!!! 'Main_Controller.xlsx' file are not present...");
			System.exit(0);
		}

	}
	
	
	static boolean isPresent = false;
	
	public static Boolean MainControlerDataSheet(String process) {
		
		if(process.equalsIgnoreCase("Biswajit")) {
			isPresent = true;
		}
		
		return isPresent;
		
	}

}
