//Here we will keep all the elements present on the Login Page 
//and initialize all of them with constructor having driver as an argument
package ja.dev.testplanisphere.hotel.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.Getter;

public class MyPage {

	@FindBy(id="email")
	private @Getter WebElement emailTxt;

	@FindBy(id="username")
	private @Getter WebElement nameTxt;
	
	@FindBy(xpath="//*[contains(text(),'ログアウト')]")
	private @Getter WebElement logoutLink;
	
	
	public MyPage(WebDriver driver) {
	PageFactory.initElements(driver, this);
	
	}
	
	
	
}
