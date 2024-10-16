package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseClass {
	
public static WebDriver driver;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
public Logger logger;	//log4j
public Properties p;	
	@BeforeClass(groups= {"Sanity","Master","DataDriven","Regression"})
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException
	{
//Loading config.properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
//log 4j2		
		logger=LogManager.getLogger(this.getClass());
		
//for remote execution of selenium grid
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			
			DesiredCapabilities capabilities=new DesiredCapabilities();
			//os
			switch(os.toLowerCase())
			{
			case "windows":capabilities.setPlatform(Platform.WIN11);break;
			case "linux":capabilities.setPlatform(Platform.LINUX);break;
			case "mac":capabilities.setPlatform(Platform.MAC);break;
			default :System.out.println("No matching OS");return;
			}
			//browser
			switch(br.toLowerCase())
			{
			case "chrome":capabilities.setBrowserName("chrome");break;
			case "edge":capabilities.setBrowserName("MicrosoftEdge");break;
			case "firefox":capabilities.setBrowserName("firefox");break;
			default :System.out.println("Invalid browser");
			return;
			}
		
			driver=new RemoteWebDriver(new URL("http://192.168.1.75:4444/wd/hub"),capabilities);
		}
				
//the below code is for local execution of browser	
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome":driver=new ChromeDriver(); break;
			case "edge":driver=new EdgeDriver();break;
			case "firefox":driver=new FirefoxDriver();break;
			default : System.out.println("Invalid browser");return;
			}
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appurl"));
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups= {"Sanity","Master","DataDriven","Regression"})
	public void tearDown()
	{
		driver.quit();
	}
	
	
	public String randomString()
	{
		@SuppressWarnings("deprecation")
		String str=RandomStringUtils.randomAlphabetic(8);
		return str;
		
	}
	public  String randomNumber() {
		@SuppressWarnings("deprecation")
		String num=RandomStringUtils.randomNumeric(10);
		return num;
	}
	public  String randomAlphaNumeric() {
		
		/*String alphastring=RandomStringUtils.randomAlphabetic(3);
		 String numberstring=RandomStringUtils.randomNumeric(4);
		 return (alphastring+"@"+numberstring);
		 */
		@SuppressWarnings("deprecation")
		String alphanum=RandomStringUtils.randomAlphanumeric(8);
		return alphanum+"$";
	}
	public String captureScreen(String tname) throws IOException{
		
		String timestamp=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		
		TakesScreenshot takescreenshot=(TakesScreenshot)driver;
		File sourceFile=takescreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timestamp+".png";
		File targetFile=new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
		
	}
}

/*
public static void main(String[] args) {
    final String uuid = UUID.randomUUID().toString().replace("-", "");
    System.out.println("uuid = " + uuid);
}
*/


/*
 * protected String getrandomAlpha_Numeric_String() {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder random_str = new StringBuilder();
        Random rnd = new Random();
        while (random_str.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * str.length());
            random_str.append(str.charAt(index));
        }
        String final_str = random_str.toString();
        return final_str;

    }
 */
