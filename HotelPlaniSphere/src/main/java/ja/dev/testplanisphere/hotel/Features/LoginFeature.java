package ja.dev.testplanisphere.hotel.Features;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.abitnow.Generic.Wait;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import ja.dev.testplanisphere.hotel.PageObjects.LoginPage;
import ja.dev.testplanisphere.hotel.PageObjects.MyPage;

public class LoginFeature {
	WebDriver driver;
	LoginPage lp;
	MyPage mp;
	ExtentTest test;

	public LoginFeature(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		lp = new LoginPage(driver);
		mp = new MyPage(driver);
	}

	public void navigateToLoginPage() {
		lp.getLoginLink().click();
		test.log(Status.INFO, "Login Link clicked");	
	}
	
	public void loginConsumer(String email, String password) {
		lp.getEmailTxtBox().sendKeys(email);
		lp.getPasswordTxtBox().sendKeys(password);
		lp.getLoginBtn().click();
		test.log(Status.PASS, "Login button clicked successfully");
		Wait.waitFor(1);
	}
	
	public void logoutConsumer() {
		mp.getLogoutLink().click();
		test.log(Status.INFO, "Logout Link clicked on MyPage");
		Wait.waitFor(1);
	}

	public void verifyLogInPage() {
		Wait.waitForTitle(driver, 20, "ログイン | HOTEL PLANISPHERE - テスト自動化練習サイト");
		String actualTitle = driver.getTitle();
		String expectedTitle = "ログイン | HOTEL PLANISPHERE - テスト自動化練習サイト";
		Assert.assertEquals(actualTitle, expectedTitle);
		test.log(Status.PASS, "Navigated to HOTEL PLANISPHERE Login Page successfully");
	}

	public void verifyMyPage() {
		Wait.waitForTitle(driver, 20, "マイページ | HOTEL PLANISPHERE - テスト自動化練習サイト");
		String actualTitle = driver.getTitle();
		String expectedTitle = "マイページ | HOTEL PLANISPHERE - テスト自動化練習サイト";
		Assert.assertEquals(actualTitle, expectedTitle);
		test.log(Status.PASS, "Navigated to HOTEL PLANISPHERE MyPage successfully");
	}
	
	public void verifyBlankEmailMsg() {
		String actualtext = lp.getInvlidEmailFieldEle().getText();
		String expectedText = "このフィールドを入力してください。";
		Assert.assertEquals(actualtext, expectedText);
		test.log(Status.PASS, "Blank Email Text verified successfully");
	}
	
	public void verifyBlankPasswordMsg() {
		String actualtext = lp.getInvlidPasswordFieldEle().getText();
		String expectedText = "このフィールドを入力してください。";
		Assert.assertEquals(actualtext, expectedText);
		test.log(Status.PASS, "Blank Password Text verified successfully");
	}

	public void verifyInvalidEmailMsg() {
		String actualtext = lp.getInvlidEmailFieldEle().getText();
		String expectedText = "Email or password is invalid.";
		Assert.assertEquals(actualtext, expectedText);
		test.log(Status.PASS, "Inavalid Email Text verified successfully");
	}
		
	public void verifyInvalidPasswordMsg() {
		String actualtext = lp.getInvlidPasswordFieldEle().getText();
		String expectedText = "Email or password is invalid.";
		Assert.assertEquals(actualtext, expectedText);
		test.log(Status.PASS, "Inavalid Password Text verified successfully");
	}
	
	public void verifyValidEmptyEmailMsg() {
		String actualtext = lp.getInvlidEmailFieldEle().getText();
		String expectedText = "メールアドレスを入力してください。";
		Assert.assertEquals(actualtext, expectedText);
		test.log(Status.PASS, "Inavalid Email Text verified successfully");
	}
	
}
