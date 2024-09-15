package com.mahindra.mobile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class MobileConfiguration {

	public static String Si_No = null;
	public static String Process = null;
	public static String App_PackageName = null;
	public static String App_PackageActivityName = null;
	public static String DeviceName = null;
	public static String DevicePlatform = null;
	public static String DevicePlatformVersion = null;
	public static String AppReset = null;
	public static String Pre_InstalledApp = null;
	public static String AppPath = null;
	public static String AppiumPort = null;
	public static String TestingPlatform = null;
	public static String UserName = null;
	public static String AccessKey = null;

	// FilePath
	public static String mainControllerFilePath;

	public static void mobileConfigurationSheet() {

		mainControllerFilePath = System.getProperty("user.dir") + File.separator + "MainController.xlsx";
		File mainControllerFile = new File(mainControllerFilePath);
		

		try {
			mainControllerFile.exists();
			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(mainControllerFilePath);

//			String query = "SELECT * FROM MOBILE_CONFIGURATION";
			String queryForProcessName = "SELECT * FROM MOBILE_CONFIGURATION Where RunStatus='Y' and Process = '" + ConnectToMainController.Process + "'";
			Recordset recordset = null;

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
//							System.out.println("'MainController' sheet are present And RunStatus = 'Y'");
						recordset.close();
					}

//							System.out.println("MainController Row List "+rowlist);    

					for (int i = 0; i < rowlist.size(); i++) {
						List<Object> row = (List<Object>) rowlist.get(i);

						Si_No = (String) row.get(0);
						Process = (String) row.get(2);
						App_PackageName = (String) row.get(3);
						App_PackageActivityName = (String) row.get(4);
						DeviceName = (String) row.get(5);
						DevicePlatform = (String) row.get(6);
						DevicePlatformVersion = (String) row.get(7);
						AppReset = (String) row.get(8);
						Pre_InstalledApp = (String) row.get(9);
						AppPath = (String) row.get(10);
						AppiumPort = (String) row.get(11);
						TestingPlatform = (String) row.get(12);
						UserName = (String) row.get(13);
						AccessKey = (String) row.get(14);

						if (ConnectToMainController.ExecutionType.equalsIgnoreCase("Remote")) {
							
							if (Si_No != null && !Si_No.isEmpty() && TestingPlatform != null
									&& !TestingPlatform.isEmpty() && UserName != null && !UserName.isEmpty()
									&& AccessKey != null && !AccessKey.isEmpty() && Process != null
									&& !Process.isEmpty() && App_PackageName != null && !App_PackageName.isEmpty()
									&& App_PackageActivityName != null && !App_PackageActivityName.isEmpty()
									&& DeviceName != null && !DeviceName.isEmpty() && DevicePlatform != null
									&& !DevicePlatform.isEmpty() && DevicePlatformVersion != null
									&& !DevicePlatformVersion.isEmpty() && AppReset != null && !AppReset.isEmpty()
									&& Pre_InstalledApp != null && !Pre_InstalledApp.isEmpty()) {
								
								System.out.println(ConnectToMainController.ExecutionType);
								Android_IOS_Driver.InitialisationDriverRemote();
								
							}
						} else if (ConnectToMainController.ExecutionType.equalsIgnoreCase("local")) {
							
							if (Si_No != null && !Si_No.isEmpty() && AppiumPort != null
									&& !AppiumPort.isEmpty()  && Process != null
									&& !Process.isEmpty() && App_PackageName != null && !App_PackageName.isEmpty()
									&& App_PackageActivityName != null && !App_PackageActivityName.isEmpty()
									&& DeviceName != null && !DeviceName.isEmpty() && DevicePlatform != null
									&& !DevicePlatform.isEmpty() && DevicePlatformVersion != null
									&& !DevicePlatformVersion.isEmpty() && AppReset != null && !AppReset.isEmpty()
									&& Pre_InstalledApp != null && !Pre_InstalledApp.isEmpty()) {
								
								
								try {
									Android_IOS_Driver.InitialisationDriverLocal();
								}catch(Exception e) {
									System.out.println(e);
								}
								
								
							}
						}

					}
				}
			} catch (Exception e) {
				System.out.println("SORRY!!! 'MOBILE_CONFIGURATION' sheet are present BUT Process Name are MissMatch");
				System.exit(0);
			}

		} catch (Exception e) {
			System.out.println("SORRY!!! 'Main_Controller.xlsx' file are not present...");
			System.exit(0);
		}

	}

}
