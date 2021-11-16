package com.abitnow.Generic;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.common.io.Files;

public class MyTestNGListener implements ITestListener {

	private static ExtentReports extent = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	public static int executionCount, passCount, failCount, skipCount=0;

	public void onTestStart(ITestResult result) {
		executionCount++;
		ExtentTest test = extent.createTest(result.getTestClass().getName() + 
								" :: " + result.getMethod().getMethodName());
		extentTest.set(test);
		String logText = "<b>Test Case " + result.getMethod().getMethodName() + " Started </b> ";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.BLACK);
		extentTest.get().log(Status.INFO, m);
	}

	public void onTestSuccess(ITestResult result) {
		passCount++;
		String logText = "<b>Test Case " + result.getMethod().getMethodName() + " Passed Successfully </b> ";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		extentTest.get().log(Status.PASS, m);
	}

	public void onTestFailure(ITestResult result) {
		failCount++;
		String methodName = result.getMethod().getMethodName();
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		extentTest.get().fail("<b><font color=red>"+result.getThrowable()+"</b></font>");
		extentTest.get().fail("<details><summary><b><font color=red>" + "Exception Occured, click to see details: "
				+ "</font></b></summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details \n>");

		String path = takeScreenShot(methodName);
		try {
			extentTest.get().fail("<b><font color=red>" + "Screenshot of Failure: " + "</font></b>",
					MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		} catch (IOException e) {
			extentTest.get().fail("Test Failed, cannot attach screeshot");
		}

		String logText = "<b>Test Case " + result.getMethod().getMethodName() + " Failed </b> ";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
		extentTest.get().log(Status.FAIL, m);

	}

	public void onTestSkipped(ITestResult result) {
		skipCount++;
		String logText = "<b>Test Case " + result.getMethod().getMethodName() + " Skipped </b> ";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.AMBER);
		extentTest.get().log(Status.SKIP, m);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		Reporter.log("Suite execution Finished", true);	
		Reporter.log("Total script executed "+executionCount, true);
		Reporter.log("Total script Passed "+ passCount, true);
		Reporter.log("Total script Failed "+failCount, true);
		Reporter.log("Total script skipped "+skipCount, true);
		
		if (extent != null) {
			extent.flush();
		}

	}

	public String takeScreenShot(String methodName) {
		String fileName = methodName + ".png";
		TakesScreenshot ts = (TakesScreenshot) BaseLib.driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		String directory = System.getProperty("user.dir") + "/screenshots/";
		new File(directory).mkdirs();
		String path = directory+ fileName;
		File destFile = new File(path);

		try {
			Files.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
