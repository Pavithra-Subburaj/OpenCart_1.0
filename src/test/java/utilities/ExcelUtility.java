package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	public  FileInputStream fi;
	public  FileOutputStream fo;
	public  XSSFWorkbook workbook;
	public  XSSFSheet sheet;
	public  XSSFRow row;
	public  XSSFCell cell;
	public  XSSFCellStyle style;
	String path;
	public ExcelUtility(String path)
	{
		this.path=path;
	}
	public int getRowCount(String sheetName) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		int rowcount=sheet.getLastRowNum();
		workbook.close();
		fi.close();
		return rowcount;	
	}
	public  int getCellCount(String sheetName,int rownum) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		int cellcount=row.getLastCellNum();
		workbook.close();
		fi.close();
		return cellcount;
		
	}
	public  String getCellData(String sheetName,int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		DataFormatter formatter=new DataFormatter();
		String data;
		try
		{
			//Another way----data=cell.toString();
			data=formatter.formatCellValue(cell);//return formatted data of the cell in string regardless of data
		}
		catch(Exception e)
		{
			data="";//if cell has no data it will throw exception do assign null
		}
		
		workbook.close();
		fi.close();
		return data;
		
	}
public  void setCellData(String sheetName,int rownum,int colnum,String data)throws IOException
	
	{
		File xlfile=new File(path);
		if(!xlfile.exists())
		{
		workbook=new XSSFWorkbook();
		fo=new FileOutputStream(path);
		workbook.write(fo);
		}
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		
		if(workbook.getSheetIndex(sheetName)==-1) //If sheet not exists then create new Sheet
			workbook.createSheet(sheetName);
		sheet=workbook.getSheet(sheetName);
		
		if(sheet.getRow(rownum)==null) //If row not exists then create new row
			sheet.createRow(rownum);
		row=sheet.getRow(rownum);
		
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fo=new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
		
	}
	
public  void fillGreencolor(String sheetName,int rownum,int colnum)throws IOException
{
	fi=new FileInputStream(path);
	workbook=new XSSFWorkbook(fi);
	sheet=workbook.getSheet(sheetName);
	row=sheet.getRow(rownum);
	cell=row.getCell(colnum);
	
	style=workbook.createCellStyle();
	
	style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	
	cell.setCellStyle(style);
	//fo=new FileOutputStream(sheetName);
	workbook.write(fo);
	workbook.close();
	fi.close();
	fo.close();
	
}


public  void fillRedcolor(String sheetName,int rownum,int colnum)throws IOException
{
	fi=new FileInputStream(path);
	workbook=new XSSFWorkbook(fi);
	sheet=workbook.getSheet(sheetName);
	row=sheet.getRow(rownum);
	cell=row.getCell(colnum);
	
	style=workbook.createCellStyle();
	
	style.setFillForegroundColor(IndexedColors.RED.getIndex());
	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	
	cell.setCellStyle(style);
	//fo=new FileOutputStream(sheetName);
	workbook.write(fo);
	workbook.close();
	fi.close();
}
}
