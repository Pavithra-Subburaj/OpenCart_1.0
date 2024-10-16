package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}
		@FindBy(xpath="//h2[text()='My Account']") //My Account page heading
		WebElement msgHding;
		
		
		@FindBy(xpath="//*[@class='list-group-item' and text()='Logout']") //added step6
		WebElement lnkLogout;
	
		public boolean isMyAccountPageExists()
		{
			System.out.println(msgHding);
			
			try {
					return (msgHding.isDisplayed());
				}
			catch(Exception e)
				{
					return false;
				}
		}
		public void clickLogout()
		{
			lnkLogout.click();
		}
		
}

