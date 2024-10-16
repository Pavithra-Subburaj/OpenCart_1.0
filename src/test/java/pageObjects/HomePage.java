package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
	
	
//constructor	
	public HomePage(WebDriver driver)
	{
		super(driver);
	}
//locator
	@FindBy(xpath="//*[text()='My Account']")
	WebElement lnkMyaccount;
	@FindBy(xpath="//a[text()='Register']")
	WebElement lnkRegister;
	@FindBy(xpath="//a[text()='Login']")//step5
	WebElement lnkLogin;
//test case-test methods
	public void clickMyAccount()
	{
		lnkMyaccount.click();
	}
	public void clickRegister()
	{
		lnkRegister.click();
	}
	public void clickLogin()
	{
		lnkLogin.click();
	}
	
}
