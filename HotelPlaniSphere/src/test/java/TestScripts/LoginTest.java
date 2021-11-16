package TestScripts;

import org.testng.annotations.Test;

import com.abitnow.Generic.BaseLib;
import com.abitnow.Generic.ExcelUtilities;
import com.aventstack.extentreports.Status;

import ja.dev.testplanisphere.hotel.Features.LoginFeature;

public class LoginTest extends BaseLib {
	
	@Test(priority=1)
	public void testBlankEmailandPasswordLogin() {
		test.log(Status.INFO, "Test: Login Test by blank email and password");
		ExcelUtilities eu = new ExcelUtilities("./TestData/testdata.xlsx");
		String email = eu.readData("sheet1",1, 1);
		String password = eu.readData("sheet1", 1, 2);
		LoginFeature lf = new LoginFeature(driver, test);
		lf.verifyLogInPage();
		lf.loginConsumer(email, password);
		lf.verifyBlankEmailMsg();
		lf.verifyBlankPasswordMsg();
	}
	
	@Test(priority=2)
	public void testBlankEmailLogin() {
		test.log(Status.INFO, "Test: Login Test by Invalid Email and Valid Password credentials");
		ExcelUtilities eu = new ExcelUtilities("./TestData/testdata.xlsx");
		String email = eu.readData("sheet1", 3, 1);
		String password = eu.readData("sheet1", 3, 2);
		LoginFeature lf = new LoginFeature(driver, test);
		lf.verifyLogInPage();
		lf.loginConsumer(email, password);
		lf.verifyBlankEmailMsg();
	}
	
	@Test(priority=3)
	public void testBlankPasswordLogin() {
		test.log(Status.INFO, "Test:Login Test by Valid Email and Invalid Password credentials");
		ExcelUtilities eu = new ExcelUtilities("./TestData/testdata.xlsx");
		String email = eu.readData("sheet1", 4, 1);
		String password = eu.readData("sheet1", 4, 2);
		LoginFeature lf = new LoginFeature(driver, test);
		lf.verifyLogInPage();
		lf.loginConsumer(email, password);
		lf.verifyBlankPasswordMsg();
	}
	
	@Test(priority=4)
	public void testInvalidEmailandPasswordLogin() {
		test.log(Status.INFO, "Test: Login Test by Invalid credentials");
		ExcelUtilities eu = new ExcelUtilities("./TestData/testdata.xlsx");
		String email = eu.readData("sheet1", 2, 1);
		String password = eu.readData("sheet1", 2, 2);
		LoginFeature lf = new LoginFeature(driver, test);
		lf.verifyLogInPage();
		lf.loginConsumer(email, password);
		lf.verifyValidEmptyEmailMsg();
	}
	

	@Test(priority=5)
	public void testValidLogin() {
		test.log(Status.INFO, "Test:Login Test by Valid Email and Password");
		ExcelUtilities eu = new ExcelUtilities("./TestData/testdata.xlsx");
		String email = eu.readData("sheet1", 6, 1);
		String password = eu.readData("sheet1", 6, 2);
		LoginFeature lf = new LoginFeature(driver, test);
		lf.verifyLogInPage();
		lf.loginConsumer(email, password);
		lf.verifyMyPage();
		lf.logoutConsumer();
		
	}
	
}
