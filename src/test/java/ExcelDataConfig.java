import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataConfig {
	
	XSSFWorkbook wb;    //Global Variable
	XSSFSheet sheet1;	//Global Variable
	
	//Open Excel WorkBook
	public ExcelDataConfig(String excelPath) // Constructor
	{
		try 
		{
			File src=new File(excelPath); //  "C:\\Users\\rbhatia\\Google Drive\\Project\\Automation\\TEA\\Sprint2\\TestData.xlsx");  //Specify the File source location			
			FileInputStream fis = new FileInputStream(src); // Load the excel sheet in the form of Bytes			
			wb = new XSSFWorkbook(fis); //Load the excel file by this command.   	// For .xls workbook use HSSFWorkbook... 			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	
	//Get data from Excel	
	public String getData(int sheetnumber, int row, int column)
	{
		DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
				
		sheet1 = wb.getSheetAt(sheetnumber);   // '0' means first sheet in the workbook
		//Cell cell = sheet1.getRow(row).getCell(column).getStringCellValue();
		Cell cell = sheet1.getRow(row).getCell(column);   //get the value from the cell
		String data = formatter.formatCellValue(cell);  //Returns the formatted value of a cell as a String regardless of the cell type.
	
		return data;		
	}
	
	public int getRowCount(int sheetIndex)
	{
		int row = wb.getSheetAt(sheetIndex).getLastRowNum();
		row++;
		return row;
	}
	
	public int getColCount(int sheetIndex)
	{
		int col = wb.getSheetAt(sheetIndex).getRow(0).getLastCellNum();
		//int col1 = wb.getSheetAt(sheetIndex).getRow(0).getPhysicalNumberOfCells();
		return col;
	}
	
	public String changeDateFormat(String datevalue)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		String newdatestring = null;
		try {
			date = sdf.parse(datevalue);
			DateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
	        newdatestring = formatter.format(date);	        
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
		return newdatestring;
		 
		
	/**	
	//	LocalDate localDate = new LocalDate();
	//	DateTimeFormatter dtf = new DateTimeFormatter(, arg1)
	//	LocalDate.parse(datevalue, "dd/MMM/yyyy");
		final String OLD_FORMAT = "MM/dd/yy";
		final String NEW_FORMAT = "dd/MMM/yyyy";
		String olddatestring = datevalue;
		String newdatestring;
		Date d = null;
		//SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		
		try {
			d = sdf.parse(olddatestring);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		sdf.applyPattern(NEW_FORMAT);
		newdatestring = sdf.format(d);
	**/
		
	}
	
/**	public int getColCount(int sheetIndex)
	{
		int col = wb.getSheetAt(sheetIndex).getRow(0).getLastCellNum();   //getLastCellNum(): Gets the index of the last cell contained in this row PLUS ONE
		col++;
		return col;
	}**/
	
}
