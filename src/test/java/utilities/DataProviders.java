package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviders {

	//DataProvider 1
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws InterruptedException, IOException
	{
		String path=".\\testData\\Opencart_LoginData.xlsx";//taking Excel file from testData folder ----.\\ represents System.getProperty("user.dir")
		ExcelUtility xlutil=new ExcelUtility(path);//creating an object for XLUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcol=xlutil.getCellCount("Sheet1", 1);
		String logindata[][]=new String[totalrows][totalcol];//creating 2D array of string
		for(int i=1;i<=totalrows;i++) //row data starts at 1
		{
			for(int j=0;j<totalcol;j++) //column starts at 0
			{
				logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j); // storing array starts at logindata(0,0)
				System.out.println(logindata[i-1][j]);
			}
		}
		return logindata;//return 2D array of login data's
	}
}
