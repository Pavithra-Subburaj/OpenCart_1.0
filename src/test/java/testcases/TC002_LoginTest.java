package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest  extends BaseClass {
	
	@Test(groups={"Sanity","Master"})
	public void verify_login()
	{
	try {
//home page	
		logger.info("Validating TC002_LoginTest");
		HomePage hp=new HomePage(driver);
		logger.info("clicking MyAccount tab");
		hp.clickMyAccount();
		logger.info("Clicking Login tab");
		hp.clickLogin();
//login page		
		logger.info("Entering Login credentials");
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
//MyAccount page	
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetpage=macc.isMyAccountPageExists();
		if(targetpage==true)
		{
			Assert.assertTrue(true);
			logger.info("Test passed");
		}
		else
		{
			logger.error("Test failed..");
			logger.debug("Debug logs...");
			Assert.assertTrue(false);
		}
	}
	catch(Exception e)
	{
		Assert.fail();
	}
	logger.info("End of TC002_LoginTest");
 }
}
