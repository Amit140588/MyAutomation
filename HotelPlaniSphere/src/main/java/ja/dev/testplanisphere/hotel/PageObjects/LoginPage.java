//Here we will keep all the elements present on the Login Page 
//and initialize all of them with constructor having driver as an argument
package ja.dev.testplanisphere.hotel.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.Getter;

public class LoginPage {

	@FindBy(id="email")
	private @Getter WebElement emailTxtBox;

	@FindBy(id="password")
	private @Getter WebElement passwordTxtBox;
		
	@FindBy(id="login-button")
	private @Getter WebElement loginBtn;
	
	
	@FindBy(xpath="//a[contains(text(),'Login')]")
	private @Getter WebElement loginLink;
	
	@FindBy(id="email-message")
	private @Getter WebElement invlidEmailFieldEle;
	
	@FindBy(id="password-message")
	private @Getter WebElement invlidPasswordFieldEle;
	
	
	public LoginPage(WebDriver driver) {
	PageFactory.initElements(driver, this);
	
	}
	
	
}
