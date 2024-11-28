package com.mahindra.mobile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class demoTesting {

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

	public static void main(String[] args) throws Exception {

		int sheet2rowSize = dataSheetTwoRowCount();

		Fillo fillo = new Fillo();
		Connection conn = fillo.getConnection(
				"C:\\Users\\biswa\\eclipse-workspace\\BSA_MOBILE_FRAMEWORK\\DataSheet\\ForMultipledataTest.xlsx");
		Recordset recordset = null;
		String query = "SELECT * FROM Sheet1";
		String queryForModule = "SELECT * FROM Sheet1 WHERE RUNSTATUS='Y' and MODULE= 'FOS'";

		recordset = conn.executeQuery(queryForModule);// here to check the runstatus and module

		List<Object> rowsList = new ArrayList<Object>();

		while (recordset.next()) {
			List<String> columns = recordset.getFieldNames();
			List<Object> rowValues = new ArrayList<Object>();
			for (String column : columns) {
				rowValues.add(recordset.getField(column));
			}
			rowsList.add(rowValues);
		}

		for (int sheet2row = 1; sheet2row <= sheet2rowSize; sheet2row++) {

			for (int i = 0; i < rowsList.size(); i++) {

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
//				System.out.println("SI_No             ====================> " + Si_No);
//				System.out.println("ScenarioID        ====================> " + ScenarioID);
//				System.out.println("PropertyName      ====================> " + PropertyName);
//				System.out.println("PropertyValue     ====================> " + PropertyValue);
//				System.out.println("Datafield         ====================> " + DataField);
//				System.out.println("Action            ====================> " + Action);

				if (!DataField.isBlank() || !DataField.isEmpty()) {
					getSheet2DataUsingRowWise(sheet2row);
				}

			}
		}

	}

	public static void getSheet2DataUsingRowWise(int rowNumber) throws Exception {

		Fillo fillo = new Fillo();
		Connection conn = fillo.getConnection(
				"C:\\Users\\biswa\\eclipse-workspace\\BSA_MOBILE_FRAMEWORK\\DataSheet\\ForMultipledataTest.xlsx");
		String query = "SELECT * FROM Sheet2 WHERE RUNSTATUS='Y' and ApplicationName='FOS'";
		Recordset recordset = null;

		int currentRow = 0;

		try {
			recordset = conn.executeQuery(query);

			while (recordset.next()) {
				currentRow++;
				if (currentRow != rowNumber) {
					continue;
				} else {
					String dataSheet2Value = recordset.getField(DataField);
					System.out.println("DataFiels For Sheet2 for row number===> " + rowNumber + " and Value ===> "
							+ dataSheet2Value + "\n");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int dataSheetTwoRowCount() throws Exception {
		Fillo fillo = new Fillo();
		Connection conn = fillo.getConnection(
				"C:\\Users\\biswa\\eclipse-workspace\\BSA_MOBILE_FRAMEWORK\\DataSheet\\ForMultipledataTest.xlsx");
		Recordset recordset = null;
		String query = "SELECT * FROM Sheet2";
		String queryForModule = "SELECT * FROM Sheet2 WHERE RUNSTATUS='Y' and ApplicationName= 'FOS'";
		recordset = conn.executeQuery(queryForModule);// here to check the runstatus and module

		int i = 0;
		while (recordset.next()) {
			i++;
		}

		System.out.println("Total sheet_2 row count = " + i);
		return i;
	}

}



/*

(//*[@class='android.widget.CheckBox'])[2]
(//android.view.View[@content-desc="PAN Verification"])[2]

	
(//android.view.View[@content-desc="Proof Of Identity"])[2]
//android.view.View[@content-desc="Aadhaar Card(Preferred)"]/android.view.View


*/