package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/* all scenario
 Data is valid----1.login success-test pass-logout
 				  2.login fail-test fail
 Data is invalid--1.login success-test fail-logout
 				  2.login fail-test pass				  	
 */

public class TC003_LoginDDT extends BaseClass{
	
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="DataDriven")//getting data provider from different class different package
	public void verify_loginDDT(String email,String pwd, String exp)
	{
	try {
//home page	
		logger.info("Validating TC003_LoginTestDDT");
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
//login page		
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
//MyAccount page	
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetpage=macc.isMyAccountPageExists();
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetpage==true)
			{
			logger.info("Login success....Test passed");
			macc.clickLogout();
			Assert.assertTrue(true);
			}
			else
			{
			logger.error("Login failed...Test failed");
			Assert.assertTrue(false);
			}
		}
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(targetpage==true)
			{
			logger.info("Login success....Test failed");
			macc.clickLogout();
			Assert.assertTrue(false);
			}
			else
			{
				logger.info("Login failed....Test passed");
				Assert.assertTrue(true);	
			}
		}
	}
	catch(Exception e)
	{
		Assert.fail();
	}
	logger.info("End of TC003_LoginTest");
 }

}
