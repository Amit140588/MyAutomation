package com.abitnow.Generic;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait extends BaseLib{

	public static void waitFor(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (Exception e) {
			System.out.println("Exception in thread sleep");
		}
	}

	public static void expWaitforVisible(WebDriver driver, int time, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void expWaitforClickable(WebDriver driver, int time, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void impWait(int time) {

		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	public static void expWaitforTextToBePresent(WebDriver driver, int time, WebElement element, String text) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}
	
	public static void waitForTitle(WebDriver driver, int time, String titleText) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.titleContains(titleText));
	}
}
