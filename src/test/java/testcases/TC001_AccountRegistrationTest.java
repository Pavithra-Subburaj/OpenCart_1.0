package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{

	
	
	@Test(groups= {"Regression","Master"})
	public void verify_account_registration()
	{
		
		try
		{
		logger.info("Validating TC001_AccountRegistrationTest");
		HomePage bp=new HomePage(driver);
		logger.info("clicking MyAccount tab");
		bp.clickMyAccount();
		
		logger.info("Clicking Registration tab");
		bp.clickRegister();
		
		AccountRegistrationPage regpage =new AccountRegistrationPage(driver);
		
		BaseClass rs=new BaseClass();
		logger.info("Providing registration details");
		regpage.setFirstName(rs.randomString().toUpperCase());
		regpage.setLastName(rs.randomString().toUpperCase());
//email has to be unique		
		regpage.setEmail(rs.randomString()+"@gmail.com");
		regpage.setTelephone(rs.randomNumber());
		String password=rs.randomAlphaNumeric();
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		String confmsg=regpage.getConfirmationMsg();
		logger.info("Validating testcase confirmation message");
		if(confmsg.equals("Your Account Has Been Created!"))
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
		logger.info("End of TC001_AccountRegistrationTest");
	}
	
}
