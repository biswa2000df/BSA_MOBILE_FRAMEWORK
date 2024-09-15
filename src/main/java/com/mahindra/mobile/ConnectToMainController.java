package com.mahindra.mobile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;


public class ConnectToMainController {

	public static String Si_No = null;
	public static String ExecutionType = null;
	public static String ApplicationId = null;
	public static String Process = null;
	
	

	// FilePath
	public static String mainControllerFilePath;

//	public static void mainControllerSheet() {
	
	public static void main(String[] args) {
		
	

		mainControllerFilePath = System.getProperty("user.dir") + File.separator + "MainController.xlsx";
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
							.addAll(Arrays.asList("Si_No", "RunStatus", "ExecutionType", "ApplicationId", "Process"));

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

					for (int i = 0; i < rowlist.size(); i++) {
						List<Object> row = (List<Object>) rowlist.get(i);

						Si_No = (String) row.get(0);
						ExecutionType = (String) row.get(2);
						ApplicationId = (String) row.get(3);
						Process = (String) row.get(4);

						if (Si_No != null && !Si_No.isEmpty() && ExecutionType != null && !ExecutionType.isEmpty()
								&& ApplicationId != null && !ApplicationId.isEmpty() && Process != null
								&& !Process.isEmpty())

						{
							mainControllerDataSheet(Process);//xlsx file 
							//System.out.println(Si_No + " " + ExecutionType + " " + ApplicationId + " " + Process);
							MobileConfiguration.mobileConfigurationSheet();

						} else {
							System.out.println(
									"Please Filled all the column data Properly inside the MainController Sheet");
							System.exit(0);
						}
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
	
	public static void mainControllerDataSheet(String DataSheetFile) {
		try{
			String dataSheetFolderPath = System.getProperty("user.dir") + File.separator + "DataSheet";
			File DataSheetFolder = new File(dataSheetFolderPath);
			DataSheetFolder.isDirectory();
			
			String dataSheetFilePath = System.getProperty("user.dir") + File.separator + "DataSheet" + File.separator + DataSheetFile;
			File dataSheetFile = new File(dataSheetFilePath);
			
			try {
				dataSheetFile.exists();
				
				//call to data sheet xlsx file
				
			}catch(Exception e) {
				System.out.println("SORRY!!! '" + DataSheetFile + "'  File are not present inside the 'DataSheet' Folder");
				System.exit(0);
			}
			
		}catch(Exception e) {
			System.out.println("SORRY!!! 'DataSheet' folder is not present");
			System.exit(0);
		}
	}
	
	

}
