package com.mahindra.mobile;

import java.io.File;
import java.io.IOException;
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

public class UtilsActivity extends ConnectToDataSheet {

	ExtentHtmlReporter htmlReport;
	ExtentReports extent;
	static ExtentTest test;
	public static String destFileScrnshot;
	static String yearFormat;
	static String time;
	public static String Extent_ReportFile;
	public static String ssDatafield = null;
	public static String ssDataSheet2Value = null;

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

		htmlReport.config().setDocumentTitle("Biswajit MahindraFinance Report");// Title of the report
		htmlReport.config().setReportName("Mobile Automation Report");// Name of the report
		htmlReport.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReport.config().setChartVisibilityOnOpen(false);
		htmlReport.config().setTheme(Theme.DARK);

		extent.setSystemInfo("Comapny Name", "APMOSYS");
		extent.setSystemInfo("FrameWork", "Biswajit Framework");
		extent.setSystemInfo("Project Name", ConnectToMainController.ApplicationName);
		extent.setSystemInfo("Manager", "Dharam Yengal");
		extent.setSystemInfo("Test Lead", "Shubham Ugale");
		extent.setSystemInfo("Sub-Test Lead", "Namrate Arun Shete");
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
						+ "<font color=\"Lime\"><b>Step - </b></font>" + Si_No + "  "
						+ "<font color=\"Red\"><b>Data Field - </b></font>" + ssDatafield.toUpperCase() + "  "
						+ "<font color=\"Lime\"><b>Test Data - </b></font>" + ssDataSheet2Value);
	}

	
	public static void testcaseInfoWithoutDataField() {
		test.log(Status.INFO,
				"<font color=\"Aqua\"><b>Module - </b></font>" + Module + "  "
						+ "<font color=\"Lime\"><b>Step - </b></font>" + Si_No + "  "
						+ "<font color=\"MediumSlateBlue\"><b>Action - </b></font>" + Action.toUpperCase());
	}
	

	public void passTestCase() throws IOException {

		String TakeScreenshotPath;
		TakeScreenshotPath = takeScreenShot(driver);

		test.log(Status.PASS, "<h6><br><font color=\"Red\"><b> Expected Result is - </b></font></h6>" + ssDataSheet2Value
				+ "	 <h6><br> <font color=\"Red\"><b>Actual Result is - </b></font></h6>" + Function.ActualResult
				+ "<br>",
//					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(driver, Scenario_ID)).build());
				MediaEntityBuilder.createScreenCaptureFromPath(TakeScreenshotPath).build());

	}

	public void failTestCase() throws IOException {

		String TakeScreenshotPath;
		TakeScreenshotPath = takeScreenShot(driver);

		test.log(Status.FAIL, "<h6><br><font color=\"Red\"><b> Expected Result is - </b></font></h6>" +  ssDataSheet2Value
				+ "	 <h6><br> <font color=\"Red\"><b>Actual Result is - </b></font></h6>" + Function.ActualResult
				+ "<br>",
//					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(driver, Scenario_ID)).build());
				MediaEntityBuilder.createScreenCaptureFromPath(TakeScreenshotPath).build());

	}

	public void ExtentFlush() {
		extent.flush();
	}

}
