package com.abitnow.Generic;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;

	public static ExtentReports createInstance() {
		String fileName = getReportName();
		String directory = System.getProperty("user.dir") + "/Reports/";
		new File(directory).mkdirs();
		String path = directory + fileName;
		ExtentHtmlReporter reporter = new ExtentHtmlReporter(path);
		reporter.config().setReportName("Automation Test Results");
		reporter.config().setDocumentTitle("Automation Report");
		reporter.config().setEncoding("utf-8");
		reporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Organisation", "ABitNow");
		extent.setSystemInfo("Tester", "Amit");
		extent.setSystemInfo("Environment", "QA");
		return extent;

	}
	
	public static String getReportName() {
		Date date = new Date();
		String d = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(date);
		String fileName = "AutomationReport_" + d.toString().replace(":","_").replace(" ", "_").replace(",", "") +".html";
		return fileName;
	}
}
