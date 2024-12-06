package com.mahindra.mobile;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.github.javafaker.Faker;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.restassured.response.Response;

public class UtilsActivity extends ConnectToDataSheet {
	
	static Faker faker;

	ExtentHtmlReporter htmlReport;
	ExtentReports extent;
	static ExtentTest test;
	public static String destFileScrnshot;
	static String yearFormat;
	static String time;
	public static String Extent_ReportFile;
	public static String ssDatafield = null;
	public static String ssDataSheet2Value = null;
	public static long executionEndTime;
	public static String TotalExecutionTime;
	
	

	public String takeScreenShot(WebDriver driver) throws IOException {

		String screenShotFileName = ScenarioID + "_" + Module + "_" + TestCaseStepID + "_" + PageName + "_";

		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destPath = getFormat("YYYY", "MMMM", "dd", ConnectToMainController.ApplicationName, "SCREENSHOTs",
				"screenshotMethod");
		destFileScrnshot = destPath + File.separator + screenShotFileName + time + ".png";

		FileUtils.copyFile(srcFile, new File(destFileScrnshot));
		return destFileScrnshot;

	}

	public static String getFormat(String Year, String Month, String Date, String projectName, String Type,
			String methodName) {

		Date date = new Date();
		SimpleDateFormat yer = new SimpleDateFormat(Year);
		SimpleDateFormat mnt = new SimpleDateFormat(Month);
		SimpleDateFormat dat = new SimpleDateFormat(Date);
		SimpleDateFormat tm = new SimpleDateFormat("HH_mm_ss");
		SimpleDateFormat fullyer = new SimpleDateFormat("yyyy-MM-dd");
		yearFormat = fullyer.format(date);

		String year = yer.format(date);
		String Mnth = mnt.format(date);
		String Dt = dat.format(date);
		time = tm.format(date);

//		System.out.println(year);
//		System.out.println(Mnth);
//		System.out.println(Dt);

		String f = null;

		f = System.getProperty("user.dir") + File.separator + "RESULT" + File.separator + year + File.separator + Mnth
				+ File.separator + Dt + File.separator + projectName + File.separator + Type;
		new File(f).mkdirs();
//		System.out.println("==========================================" + f);
		return f;

	}

	public void extentReport() throws IOException {

		String destFile = getFormat("YYYY", "MMMM", "dd", ConnectToMainController.ApplicationName, "REPORTs",
				"ReportMethod");
		Extent_ReportFile = destFile + File.separator + ConnectToMainController.ApplicationName + "_" + "Report_" + time
				+ ".html";

		htmlReport = new ExtentHtmlReporter(Extent_ReportFile);
		extent = new ExtentReports();
		extent.attachReporter(htmlReport);

		htmlReport.config().setDocumentTitle(" MAHINDRA FINANCE ");// Title of the report
		htmlReport.config().setReportName(ConnectToMainController.ApplicationName + " Mobile Automation Report");// Name of the report
		htmlReport.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReport.config().setChartVisibilityOnOpen(false);
		htmlReport.config().setTheme(Theme.DARK);

		extent.setSystemInfo("Comapny Name", "MAHINDRA FINANCE");
		extent.setSystemInfo("FrameWork", "Biswajit Framework");
		extent.setSystemInfo("Project Name", ConnectToMainController.ApplicationName);
		extent.setSystemInfo("Manager", "DHARAM YENGAL");
		extent.setSystemInfo("Test Lead", "Shantesh & Shubham");
		extent.setSystemInfo("Sub-Test Lead", "Vikrant & Namrata");
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("System User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Tester Name", "Biswajit");
		extent.setSystemInfo("Execution", ConnectToMainController.ExecutionType);
		extent.setSystemInfo("Device", MobileConfiguration.DevicePlatform);

	}

	public void testCaseCreate() {

		test = extent
				.createTest(
						"<font color=\"BlueViolet\"><b>" + ScenarioID + "</b></font> - <font color=\"Brown\"><b>"
								+ Module + "</b></font> - <font color=\"Green\"><b>" + TestCaseID + "</b></font> ( "
								+ TestCaseDescription + " )",
						"</br><h4><font color=\"Lime\"><b>" + Module.toUpperCase() + "</b></font></h4>")
				.createNode("<h5><b>" + TestCaseDescription + "</b></h5>").assignCategory("BISWAJIT");

	}

	public static void testcaseInfoWithDataField() {
		ssDatafield = DataField;
		ssDataSheet2Value = dataSheet2Value;

		test.log(Status.INFO,
				"<font color=\"Aqua\"><b>Module - </b></font>" + Module + "  "
						+ " <font color=\"Lime\"><b>Step - </b></font>" + Si_No + "  "
						+ " <font color=\"Red\"><b>Data Field - </b></font>" + ssDatafield.toUpperCase() + "  "
						+ " <font color=\"MediumSlateBlue\"><b>Test Data - </b></font>" + ssDataSheet2Value
						+ " <font color=\"Yellow\"><b>StepDescription - </b></font>" + TestCaseStepDescription);
	}

	public static void testcaseInfoWithoutDataField() {
		test.log(Status.INFO,
			    "<font color=\"Aqua\"><b>Module - </b></font>" + Module + "  "
			    + " <font color=\"Lime\"><b>Step - </b></font>" + Si_No + "  "
			    + " <font color=\"MediumSlateBlue\"><b>Action - </b></font>" + Action.toUpperCase()
			    + " <font color=\"Gold\"><b>StepDescription - </b></font>" + TestCaseStepDescription);

	}

	public void passTestCase() throws IOException {

		String TakeScreenshotPath;
		TakeScreenshotPath = takeScreenShot(driver);

		test.log(Status.PASS,
			    "<h6><br><font color=\"Red\"><b>Expected Result is - </b></font></h6>" +
			    "<h6><font color=\"Lime\"><b>" + ssDataSheet2Value.toUpperCase() + "</b></font></h6>" + 
			    "<h6><br><font color=\"Red\"><b>Actual Result is - </b></font></h6>" +
			    "<h6><font color=\"Lime\"><b>" + String.valueOf(Function.ActualResult).toUpperCase() + "</b></font></h6><br>",
			    MediaEntityBuilder.createScreenCaptureFromPath(TakeScreenshotPath).build());

	}

	public void failTestCase() throws IOException {

		String TakeScreenshotPath;
		TakeScreenshotPath = takeScreenShot(driver);

		test.log(Status.FAIL,
			    "<h6><br><font color=\"Red\"><b>Expected Result is - </b></font></h6>" +
			    "<h6><font color=\"Lime\"><b>" + ssDataSheet2Value.toUpperCase() + "</b></font></h6>" + 
			    "<h6><br><font color=\"Red\"><b>Actual Result is - </b></font></h6>" +
			    "<h6><font color=\"Red\"><b>" + String.valueOf(Function.ActualResult).toUpperCase() + "</b></font></h6><br>",
			    MediaEntityBuilder.createScreenCaptureFromPath(TakeScreenshotPath).build());

	}

	public void ExtentFlush() {
		extent.flush();
	}

	// create html Table here

	public static void CreateHtmlTable() throws IOException {

		try {

			String htmlTable = getFormat("YYYY", "MMMM", "dd", ConnectToMainController.ApplicationName, "HtmlTables",
					"htmlTableMethod");

			String filename = htmlTable + File.separator + ConnectToMainController.ApplicationName + "_"
					+ "HtmlTable_Report_" + time + ".html";

			String backGroundImage = new File(System.getProperty("user.dir") + File.separator + "DataSheet"
					+ File.separator + "BackGroundImage" + File.separator + "Automation1.png").toURI().toString();

			FileWriter writer = new FileWriter(filename);

			writer.write("<!DOCTYPE html>\n<html>\n<head>\n");

			writer.write("<style>");
			writer.write("body { background-image: url('" + backGroundImage
					+ "'); background-size: cover; background-repeat: no-repeat; }");
			writer.write("table { border-collapse: collapse; width: 50%; margin: auto; margin-top: 20px; }");
			writer.write(
					"th, td { border: 1px solid black; padding: 8px; text-align: center; background-color: #E4E5E5; }");
			writer.write("th { background-color: #E4E5E5; }");
			writer.write("h1 { text-align: center; color: white; margin-top: 20px; font-weight: bold; }"); 
			writer.write("h2 { text-align: center; color: white; margin-top: 20px; font-weight: bold; }"); 
			writer.write("</style>");
			writer.write("</head>\n<body>\n");
			
			writer.write("<h1><b>Mahindra & Mahindra Financial Services Limited - " + ConnectToMainController.ApplicationName + " Mobile Automation Test Report</b></h1>\n");
			writer.write("<h2>Welcome Biswajit Sahoo Framework Report</h2>\n");
		//	writer.write("<h1><b>Welcome Biswajit, Automation Report</b></h1>\n");

			writer.write("<table border=\"1\">\n");
			writer.write("<tr>"
					+ "<th style=\"text-align:center; border: 1px solid black; background-color:#4CAF50; color: white;\">Project</th>"
					+ "<th style=\"text-align:center; border: 1px solid black; background-color:#1E90FF; color: white;\">Total TCs</th>"
					+ "<th style=\"text-align:center; border: 1px solid black; background-color:#4CAF50; color: white;\">Passed TCs</th>"
					+ "<th style=\"text-align:center; border: 1px solid black; background-color:#FF6347; color: white;\">Failed TCs</th>"
					+ "<th style=\"text-align:center; border: 1px solid black; background-color:#4682B4; color: white;\">Total Validations in all the TCs</th>"
					+ "<th style=\"text-align:center; border: 1px solid black; background-color:#32CD32; color: white;\">Passed Validations</th>"
					+ "<th style=\"text-align:center; border: 1px solid black; background-color:#FF6347; color: white;\">Failed Validations</th>"
					+ "<th>Report</th>"
					+ "<th style=\"text-align:center; border: 1px solid black; background-color:#4CAF50; color: white;\">ExecutionTime</th>"
					+ "</tr>");

			writer.write("<tr>" + "<td style=\"text-align:center; border: 1px solid black;\">"
					+ ConnectToMainController.ApplicationName + "</td>"
					+ "<td style=\"text-align:center; border: 1px solid black;\">" + ConnectToDataSheet.totalTest
					+ "</td>" + "<td style=\"text-align:center; border: 1px solid black;\">" + ConnectToDataSheet.pass
					+ "</td>" + "<td style=\"text-align:center; border: 1px solid black;\">" + ConnectToDataSheet.fail
					+ "</td>" + "<td style=\"text-align:center; border: 1px solid black;\">"
					+ ConnectToDataSheet.totalValidations + "</td>"
					+ "<td style=\"text-align:center; border: 1px solid black;\">" + ConnectToDataSheet.passValidations
					+ "</td>" + "<td style=\"text-align:center; border: 1px solid black;\">"
					+ ConnectToDataSheet.failedValidations + "</td>" + "<td><a href=" + Extent_ReportFile
					+ " target=_blank>View Report</a></td>"
					+ "<td style=\"text-align:center; border: 1px solid black;\">" + TotalExecutionTime + "</td></tr>");

			writer.write("</table>\n");
			writer.write("</body>\n</html>");
			writer.close();

//		System.out.println("HTML table has been generated in " + filename);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void ExecutionTime() {
		long hours = 0;
		long minutes = 0;
		long seconds = 0;
		long remainingMilliseconds = 0;
		long milliseconds = 0;

		executionEndTime = System.nanoTime();
		long executionTimeInMilliseconds = (executionEndTime - Function.executionStartTime) / 1_000_000;

		hours = executionTimeInMilliseconds / (60 * 60 * 1000);
		remainingMilliseconds = executionTimeInMilliseconds % (60 * 60 * 1000);
		minutes = remainingMilliseconds / (60 * 1000);
		remainingMilliseconds %= (60 * 1000);
		seconds = remainingMilliseconds / 1000;
		milliseconds = remainingMilliseconds % 1000;

		if (hours != 0) {
			TotalExecutionTime = hours + " hour " + minutes + " min " + seconds + " seconds " + milliseconds + " ms";
		} else if (minutes != 0) {
			TotalExecutionTime = minutes + " min " + seconds + " seconds " + milliseconds + " ms";
		} else if (seconds != 0) {
			TotalExecutionTime = seconds + " seconds " + milliseconds + " ms";
		} else
			TotalExecutionTime = milliseconds + " ms";

	}
	
	
	

}
