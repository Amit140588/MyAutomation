package com.abitnow.Generic;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.common.io.Files;

public class BaseLib {
	public static WebDriver driver;
	public static ExtentReports report;
	public static ExtentTest test = null;
	public static String adminURL;

	@BeforeClass
	public void beforeClass() {
		report = ExtentManager.createInstance();
	}

	@AfterClass
	public void afterClass() {
		report.flush();
	}

	@BeforeMethod
	@Parameters({ "browser", "baseurl"})
	public void preCondition(String browserName, String url) {
		try {
			driver=BrowserFactory.selectBrowser(browserName);
			test.log(Status.INFO, ""+browserName+" browser launched");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(url);
			Reporter.log("Navigated to URL"+ url, true);
			test.log(Status.INFO, "Navigated to URL  "+url);
			
		} catch (Exception e) {
			Reporter.log("Browser Driver Version not Updated. Please Update Your Chrome Driver ", true);
			test.log(Status.INFO, "Browser Driver Version not Updated. Please Update Your Chrome Driver "+url);
		}
		
	}

	@AfterMethod
	public void postCondition() {
		driver.quit();
		Reporter.log("Browser Closed", true);
		test.log(Status.INFO, "Browser Closed");
	}

	public static int randomNumberGenerator(int size) {
		int count = 0;

		Random rand = new Random();
		count = rand.nextInt(size - 1) + 1;

		return count;
	}
	
	public static String randomStringGenerator(String aToZ) {
		Random rand = new Random();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			int randIndex = rand.nextInt(aToZ.length());
			res.append(aToZ.charAt(randIndex));
		}
		return res.toString();
	}
	
	public static String stringGenerator(int size) {
		return RandomStringUtils.randomAlphabetic(size).toUpperCase();
	}

	public static void manageSessions() {
		String mainWindow = driver.getWindowHandle(); // do not forget to return to main window after calling method
		Set<String> sessionID = driver.getWindowHandles();
		Iterator<String> itr = sessionID.iterator();
		while (itr.hasNext()) {
			String childWindow = (String) itr.next();
			if (!(mainWindow.equals(childWindow))) {
				driver.switchTo().window(childWindow);
				driver.close();
			}
		}
		driver.switchTo().window(mainWindow);
	}

	public static void switchToNextTab() {
		String mainWindow = driver.getWindowHandle();
		Set<String> sessionID = driver.getWindowHandles();
		Iterator<String> itr = sessionID.iterator();
		while (itr.hasNext()) {
			String childWindow = (String) itr.next();
			if (!(mainWindow.equals(childWindow))) {
				driver.switchTo().window(childWindow);
			}
		}
	}

	public static void openLinkInNewTab(WebElement element) {
		String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN); 
		element.sendKeys(selectLinkOpeninNewTab);
		switchToNextTab();
	}
	
	public static void dropDownSelect(WebElement element) {
		Select s = new Select(element);
		List<WebElement> options = s.getOptions();
		int size = options.size();
		s.selectByIndex(BaseLib.randomNumberGenerator(size));
	}
	
	public static void scrollUp() {
		Dimension dim = driver.findElement(By.tagName("body")).getSize();
		int pageHeight = dim.getHeight();
		System.out.println("Page Height is :"+ pageHeight);
		String jscode = "window.scrollBy(0,0)";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(jscode);
	}
	
	public static void scrollToElement(WebElement element) {
		String jscode = "arguments[0].scrollIntoView(true)";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeAsyncScript(jscode, element);
	}
	
	public static boolean elementIsDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

	@BeforeMethod
	public void brforeMethod(ITestResult result) {
		test = report.createTest(result.getMethod().getMethodName());
		String logText = "<b> Test Case " + result.getMethod().getMethodName() + " Started </b> ";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.ORANGE);
		test.log(Status.INFO, m);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		String methodName = result.getMethod().getMethodName();

		if (result.getStatus() == ITestResult.FAILURE) {
			String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
			test.fail("<details><summary><b><font color=red>" + "Exception Occured, click to see details:"
					+ "</font></b></summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details> \n");

			String path = takeScreenShot(result.getMethod().getMethodName());
			try {
				test.fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>",
						MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			} catch (IOException e) {
				test.fail("Test Failed, cannot attach screenshot");
			}

			String logText = "<b>Test Case " + methodName + " Failed</b>";
			Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
			test.log(Status.FAIL, m);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			String logText = "<b>Test Case " + methodName + " Passed Successfully</b>";
			Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
			test.log(Status.PASS, m);
		} else if (result.getStatus() == ITestResult.SKIP) {
			String logText = "<b>Test Case " + methodName + " Skipped</b>";
			Markup m = MarkupHelper.createLabel(logText, ExtentColor.AMBER);
			test.log(Status.SKIP, m);
		}
	}

	public String takeScreenShot(String methodName) {
		String fileName = methodName + ".png";
		TakesScreenshot ts = (TakesScreenshot) BaseLib.driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		String directory = System.getProperty("user.dir") + "/screenshots/";
		new File(directory).mkdirs();
		String path = directory + fileName;
		File destFile = new File(path);

		try {
			Files.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
