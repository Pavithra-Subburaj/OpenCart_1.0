package utilities;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import testBase.BaseClass;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportUtility implements ITestListener {
	public ExtentSparkReporter sparkReporter; //UI of the report--look and feel
	public ExtentReports extent; //populate common info on the report----Environment,who ,browser,os,project, module name
	public ExtentTest test; 
	
	String repName;
	
	public void onStart(ITestContext testContext) //ITestContext-class defines a test context which contains all the information for a given test run.
	{
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		  Date dt=new Date();
		  String currentdatetimestamp=df.format(dt);	
		 */
		String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//timestamp for date time for file name
		repName="Test-Report-"+ timestamp+".html";
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName);//specify the location of the reports storing folder and name
		
		sparkReporter.config().setDocumentTitle("Opencart Automation Report");//title of report
		sparkReporter.config().setReportName("OpenCart Functional Testing");//name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");

//capturing os and broswer , groups name from the xml file which run testcases @test method dynamically  		
		String os=testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser=testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty())
		{
			extent.setSystemInfo("Groups",includedGroups.toString());
		}
	}
	
	 public void onTestSuccess(ITestResult result) //ITestResult result --->result represent the @test method() name
	 {
		 test=extent.createTest(result.getTestClass().getName());
		 test.assignCategory(result.getMethod().getGroups()); //to display groups in tests
		 test.log(Status.PASS,result.getName()+"got successfully executed");
	 }
	 
	 public void onTestFailure(ITestResult result) 
	 {
		 test=extent.createTest(result.getTestClass().getName());
		 test.assignCategory(result.getMethod().getGroups());
		 
		 test.log(Status.FAIL,result.getName()+"got failed");
		 test.log(Status.INFO,result.getThrowable().getMessage());
		 
		 try
		 {
			 String imgPath=new BaseClass().captureScreen(result.getName());
			// test.addScreenCaptureFromPath(imgPath);
			 test.addScreenCaptureFromPath(imgPath);
		 }
		 catch(Exception e1)
		 {
			 e1.printStackTrace();
		 }
	 }
	
	 public void onTestSkipped(ITestResult result)
	 {
		 test=extent.createTest(result.getTestClass().getName());
		 test.assignCategory(result.getMethod().getGroups());
		 test.log(Status.SKIP,result.getName()+" got skipped");
		 test.log(Status.INFO,result.getThrowable().getMessage()); 
	 }
	 
	 public void onFinish(ITestContext context) 
	 {
		 extent.flush(); //without this step report wont be finished/generated (must)
//Extra: to open the report automatically without going to folder to open report manually		
		 String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
		 File extentReport=new File(pathOfExtentReport);
		 try
		 {
			 Desktop.getDesktop().browse(extentReport.toURI());
		 }
		 catch(Exception e1)
		 {
			 e1.printStackTrace();
		 }
//extra 2: to send mail to someone automatically----need dependency from apache - Apache Commons Email » 1.6.0
		 
		 /* try
		  {
           URL url=new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
		   //create the email message
		   ImageHtmlEmail email=new ImageHtmlEmail();
		   email.setDataSourceResolver(new DataSourceUrlResolver(url));
		   email.setHostName("smtp.googlemail.com");//----keeps changing
		   email.setSmtpPort(465);
		   email.setAuthenticator(new
		   DefaultAuthenticator("pavanoltraining@gmail.com","password"));
		   //Sender  
		   email.setSSLOnConnect(true);email.setFrom("pavanoltraining@gmail.com");
		 
		   email.setSubject("Test Results");
		   email.setMsg("Please find Attached Report....");
		   //Receiver ----multiple receiver use (distributor list(dl contain multiple email id)-dl email)
		   email.addTo("pavankumar.busyqa@gmail.com");
		 
		   email.attach(url,"extent report","please check report...");
		   email.send(); //send the email
		   }
		   catch(Exception e)
		   {
		   e.printStackTrace();
		   }
		  */
	 }
	
}
