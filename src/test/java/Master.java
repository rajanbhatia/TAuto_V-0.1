
import static org.testng.Assert.assertTrue;
import io.github.bonigarcia.wdm.FirefoxDriverManager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class Master 
{
	public WebDriver driver;
	InputText input_text =  new InputText();
	////Clicks click =  new Clicks();
	//SelectDropdown selectdropdown = new SelectDropdown();
	Validations validation= new Validations();
	KeyPress keypress = new KeyPress();
	public ExtentTest logger;	//Main class to generate the Logs and add to the report
	String errormessage, testcasepath, browsername, executionreportpath;
	int sheetnumber;	
	Boolean exceptionerror;
	String stepdescription, command;
	JFrame f = new JFrame("Launching AutoTool...");
@Test(dataProvider = "TestSteps")
public void main(String tcid, String tc_desc, String stepid, String step_desc, String command, String locatortype, String locatorvalue, String parametervalue) //, String result, String error)
{	
	try
	{	 	
		
		//logger = ReportScreenshotUtility.report.startTest("Automation Run: Testcase- "+tcid+", Teststep- "+stepid);  //To log every step on the left panel
		exceptionerror=false;	   //ExceptionError flag to capture errors and log to the logger report   
		stepdescription=step_desc;
		this.command=command;
		//System.out.println(tcid + " " + tc_desc + " " + stepid + " " + step_desc + " " + command  + " " + locatortype  + " " + locatorvalue + " " + parametervalue + " " + "\n");
		switch (command)
		{
			case "Browser: Open (Parameter Value)": 
			{
				logger = ReportScreenshotUtility.report.startTest("Automation Run: Testcase- "+tcid); //To log every testcase on the left panel and teststeps on the right.
				//browserSettings(driver, parametervalue);
				switch(browsername)
				{
					case "Chrome":						
							System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
							driver = new ChromeDriver();						
							break;						
					case "Firefox":
							FirefoxDriverManager.getInstance().setup();
							//System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");  //gecko is required for Selenium 3
							//System.setProperty("webdriver.chrome.driver", "C:\\firefoxdriver.exe");
							driver = new FirefoxDriver();
							break;						
					case "Internet Explorer":						
							DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer(); // To speed up IE. If not use 32 bit driver
							capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
							capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
							capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			
							//capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
							capabilities.setCapability("IntroduceInstabilityByIgnoringProtectedModeSettings",true);
							System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/drivers/IEDriverServer.exe");
							driver = new InternetExplorerDriver(capabilities);
							break;						
					case "Safari":						
							/** Not for now....System.setProperty("webdriver.safari.driver", "C:\\safaridriver.exe");  //gecko is required for Selenium 3
							driver = new SafariDriver();				
							browserSettings(driver, parametervalue);**/
							break;						
					default:						
							//logger.log(LogStatus.INFO,"Invalid Browser specified."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid Browser specified.";
							exceptionerror=true;						
				}
				if (parametervalue.equals("") || parametervalue.equals(null)) //validate that parameter value is valid
				{
					errormessage="Invalid URL";
					exceptionerror=true;   
				}
				else
				{
					f.setVisible(false);
					f.dispose();
					driver.get("https://"+parametervalue);
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				}
				break;
			}
					
			case "Textbox: Enter Text (Locator Value, Parameter Value)": 
			{
				checkLocParamBlankValues(locatorvalue, parametervalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (exceptionerror.equals(false))  //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID":						
							input_text.textboxIdEnterText(locatorvalue, parametervalue, driver);
							break;						
						case "Xpath":						
							input_text.textboxXpathEnterText(locatorvalue, parametervalue, driver);
							break;						
						case "Name":						
							input_text.textboxNameEnterText(locatorvalue, parametervalue, driver);
							break;						
						case "CssSelector":						
							input_text.textboxCssSelectorEnterText(locatorvalue, parametervalue, driver);
							break;					
						default:						
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the textbox to enter text."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the textbox to enter text."; //Send error through the AfterMethod and into the report and not via info as above
							exceptionerror=true;						
					}
				}
				break;
			}
			
			case "Textbox: Validate Text (Locator Value, Parameter Value)": 
			{
				checkLocParamBlankValues(locatorvalue, parametervalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (exceptionerror.equals(false))  //Execute it only if the values are valid
				{	
					switch (locatortype)
					{
						case "ID": validation.validateTextboxValueById(locatorvalue, parametervalue, driver);
						break;
						case "Xpath": validation.validateTextboxValueByXpath(locatorvalue, parametervalue, driver);
						break;
						case "Name": validation.validateTextboxValueByName(locatorvalue, parametervalue, driver);
						break;
						case "CssSelector": validation.validateTextboxValueByCssSelector(locatorvalue, parametervalue, driver);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the textbox to validate text."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the textbox to validate text.";
							exceptionerror=true;						
					}					
				}
				break;
			}
			
			case "Caption/Text: Validate Text (Locator Value, Parameter Value)": 
			{
				checkLocParamBlankValues(locatorvalue, parametervalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (exceptionerror.equals(false)) //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID": validation.validateCaptionById(locatorvalue, parametervalue, driver);
						break;
						case "Xpath": validation.validateCaptionByXpath(locatorvalue, parametervalue, driver);
						break;
						case "Name": validation.validateCaptionByName(locatorvalue, parametervalue, driver);
						break;
						case "CssSelector": validation.validateCaptionByCssSelector(locatorvalue, parametervalue, driver);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the caption/text to validate."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the caption/text to validate.";
							exceptionerror=true;   
					}					
				}
				break;
			}
			case "Dropdown: Select Value (Locator Value, Parameter Value)": 
			{
				checkLocParamBlankValues(locatorvalue, parametervalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (exceptionerror.equals(false)) //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID": new Select(driver.findElement(By.id(locatorvalue))).selectByVisibleText(parametervalue);//selectdropdown.selectDropdownValueById(locatorvalue, parametervalue, driver);
						break;
						case "Xpath": new Select(driver.findElement(By.xpath(locatorvalue))).selectByVisibleText(parametervalue);
						break;
						case "Name":  new Select(driver.findElement(By.name(locatorvalue))).selectByVisibleText(parametervalue);
						break;
						case "CssSelector":  new Select(driver.findElement(By.cssSelector(locatorvalue))).selectByVisibleText(parametervalue);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the dropdown to select value."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the dropdown to select value.";
							exceptionerror=true;   
						
					}					
				}
				break;
			}
			
			case "Dropdown: Validate Value (Locator Value, Parameter Value)": 
			{
				checkLocParamBlankValues(locatorvalue, parametervalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (exceptionerror.equals(false))  //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID": validation.validateDropdownValueById(locatorvalue, parametervalue, driver);
						break;
						case "Xpath": validation.validateDropdownValueByXpath(locatorvalue, parametervalue, driver);
						break;
						case "Name": validation.validateDropdownValueByName(locatorvalue, parametervalue, driver);
						break;
						case "CssSelector": validation.validateDropdownValueByCssSelector(locatorvalue, parametervalue, driver);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified dropdown to validate value."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified dropdown to validate value.";
							exceptionerror=true;   
						
					}					
				}
				break;
			}
			case "Button: Validate Text (Locator Value, Parameter Value)": 
			{
				checkLocParamBlankValues(locatorvalue, parametervalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (exceptionerror.equals(false))  //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID": validation.validateTextboxValueById(locatorvalue, parametervalue, driver);  //use the textbox attribute code for button also
						break;
						case "Xpath": validation.validateTextboxValueById(locatorvalue, parametervalue, driver); //use the textbox attribute code for button also
						break;
						case "Name": validation.validateTextboxValueById(locatorvalue, parametervalue, driver); //use the textbox attribute code for button also
						break;
						case "CssSelector": validation.validateTextboxValueById(locatorvalue, parametervalue, driver); //use the textbox attribute code for button also
						break;
						default:						
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the button to validate text."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the button to validate text.";
							exceptionerror=true; 						
					}					
				}
				break;
			}
			case "Object: Click (Locator Value)":
			{
				checkLocBlankValue(locatorvalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (exceptionerror.equals(false)) //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID": driver.findElement(By.id(locatorvalue)).click();//click.clickIdObject(locatorvalue, driver);
						break;
						case "Xpath": driver.findElement(By.xpath(locatorvalue)).click();
						break;
						case "Name": driver.findElement(By.name(locatorvalue)).click();
						break;
						case "CssSelector": driver.findElement(By.cssSelector(locatorvalue)).click();
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the object to click."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the object to click.";   							
					}					
				}
				break;
			}
			
			
			case "Key: Press (Enter/Return/Tab/Escape) (Locator Value, Parameter Value)":
			{
				checkLocBlankValue(locatorvalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (exceptionerror.equals(false))  //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID": keypress.keyId(locatorvalue, parametervalue, driver);
						break;
						case "Xpath": keypress.keyXpath(locatorvalue, parametervalue, driver);
						break;
						case "Name": keypress.keyName(locatorvalue, parametervalue, driver);
						break;
						case "CssSelector": keypress.keyCssSelector(locatorvalue, parametervalue, driver);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the key to press."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the key to press.";
							exceptionerror=true; 						
					}					
				}
				break;
			}	
			
			case "Pause: Delay Execution in Seconds":
			{
				long sleeptimeinsec= Long.parseLong(parametervalue+"000");  //convert string to long				
				Thread.sleep(sleeptimeinsec);	
				break;
			}
			case "Browser: Close": 
			{
				driver.close();
				//logger.log(LogStatus.INFO,"Test Case - "+tcid+"("+tc_desc+") executed.");
				break;
			}
			
			case "Object: Validate if Present (Locator Value)":
			{
				checkLocBlankValue(locatorvalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (exceptionerror.equals(false))  //Execute it only if the values are valid
				{
					switch(locatortype)
					{
						case "ID":	assertTrue(isElementPresent(By.id(locatorvalue)));
						break;
						case "Xpath":	assertTrue(isElementPresent(By.xpath(locatorvalue)));
						break;
						case "Name":	assertTrue(isElementPresent(By.name(locatorvalue)));
						break;
						case "CssSelector":	assertTrue(isElementPresent(By.cssSelector(locatorvalue)));
						break;
						default:
							logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the object to verify its presence."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							exceptionerror=true;   						
					}
				}
				break;
			}
			
						
			/**case "DateFormat_Change (dd/MMM/yyyy)":
			{
				
				break;
			}	**/
			
			
			case "Checkbox: Validate if Selected (Locator Value)":
			case "Radiobutton: Validate if Selected (Locator Value)":
			{
				checkLocBlankValue(locatorvalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (exceptionerror.equals(false))  //Execute it only if the values are valid
				{
					switch(locatortype)
					{
						case "ID":	assertTrue(driver.findElement(By.id(locatorvalue)).isSelected());
						break;
						case "Xpath":	assertTrue(driver.findElement(By.xpath(locatorvalue)).isSelected());
						break;
						case "Name":	assertTrue(driver.findElement(By.name(locatorvalue)).isSelected());
						break;
						case "CssSelector":	assertTrue(driver.findElement(By.cssSelector(locatorvalue)).isSelected());
						break;
						default:
							logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the checkbox/radiobutton to verify the selection."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							exceptionerror=true;   
						
					}
				}
				break;
			}	
			
			case "DO NOT EXECUTE THIS STEP":
			{
				/// DO Nothing
				break;
			}							
			default: 
			{
				logger.log(LogStatus.INFO,"Invalid or No command specified."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
				exceptionerror=true;   
				break;
			}
		}
	}
	catch (Exception e)
	{
		System.out.println("Error: "+e.getMessage()); // Comment it later on
		exceptionerror=true;
	    errormessage=e.getMessage();
	}
}

@DataProvider(name="TestSteps")  //Parameterizing @Test code for the Excel records
public Object[][] readTestCases() throws Exception   // Load Data Excel  
{	  		  	
		
	    
	sheetnumber = sheetnumber-1; // As user will input the exact serial number and the index starts from 0.
///	String excelpath=propertyconfig.getExcelSheetPath();
  	//ExcelDataConfig excelconfig = new ExcelDataConfig("C:\\Users\\rbhatia\\Google Drive\\Project\\Automation\\ZAuto\\TestCases.xlsx");	  	  	
  	ExcelDataConfig excelconfig = new ExcelDataConfig(testcasepath);
	int rows=excelconfig.getRowCount(sheetnumber);  //rows in the first sheet
	int cols=excelconfig.getColCount(sheetnumber);  //cols in the first sheet
	Object[][] testcasesdata = new Object[rows-1][cols];	
	for(int i=0;i<rows-1;i++)   //Initializing Array to rows-1. First row is just headings and make sure every column cell has a text
	{
	    
		for (int j=0;j<cols;j++)  //Columns value is one more than the index so less than sign
		{
			testcasesdata[i][j]=excelconfig.getData(sheetnumber, i+1, j);  //Picking data from the 2nd row in excel sheet, so i+1
			
			/**if (j==6)   //As DoB field is in the 7th col (6th index)
			{
				//Calling the function to change the date format from mm/dd/yy to dd/mm/yyyy//
			//	dobforage[i]= (String) data[i][j];
				String datevalue = (String) data[i][j]; 
				String datechange = excelconfig.changeDateFormat(datevalue);  
				data[i][j]=datechange;
				 	
				// -----------------------------------------------------------------     //		
			}**/
		}					
	}
	return testcasesdata;
}

@AfterMethod   //executed after every method. Creating to capture the results of Failure.
public void tearD(ITestResult result) throws Exception
{
 if(ITestResult.FAILURE==result.getStatus() || (exceptionerror.equals(true)))  //Check if Test case has failed
 {
 	 //String screenshot_path = ReportScreenshotUtility.captureScreenshot(driver,"/test-output/screenshots/",result.getName());   //Take screenshot if Test Case fails
	 String screenshot_path = ReportScreenshotUtility.captureScreenshot(driver,executionreportpath,result.getName());   //Take screenshot if Test Case fails and at the same location where execution report is saved.
 	 String image=logger.addScreenCapture(screenshot_path);
 	 logger.log(LogStatus.FAIL, "Failed", image);
 	 if(ITestResult.FAILURE==result.getStatus())		logger.log(LogStatus.FAIL, stepdescription+": FAILED. Error Message: "+ result.getThrowable());
 	 if (exceptionerror==true)  logger.log(LogStatus.FAIL, stepdescription+": FAILED. Error Message: "+ errormessage);
 }
 else if (ITestResult.SUCCESS==result.getStatus() && (exceptionerror.equals(false)))   //Check if Test case has passed
 {
 	 if (command.equals("DO NOT EXECUTE THIS STEP")) logger.log(LogStatus.PASS, stepdescription+": SKIPPED");  //to capture the non-executed step in the report.
 	 else logger.log(LogStatus.PASS, stepdescription+": PASSED");	
 }
 else if (ITestResult.SKIP==result.getStatus())  //Check if Test case has passed
 {
	logger.log(LogStatus.SKIP, stepdescription+": SKIPPED. "+result.getThrowable());	
 }		
	 ReportScreenshotUtility.report.endTest(logger);
	 ReportScreenshotUtility.report.flush();
}


@BeforeClass(alwaysRun=true)
public void setUp() throws Exception 
{		
	progressBar(); // Call the Progress Bar code
	f.setVisible(true); //Display the Progress Bar
	LogManager.getLogManager().reset();
	Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
	globalLogger.setLevel(java.util.logging.Level.OFF);	
	ExcelDataConfig excelreadpreferences = new ExcelDataConfig(System.getProperty("user.dir")+"/Preferences.xlsx");	
	Object[][] preferencesdata = new Object[4][1];
	for(int i=0;i<4;i++)   //Initializing Array to rows-1. First row is just headings and make sure every column cell has a text
	{
		for(int j=0;j<1;j++)  //Columns value is one more than the index so less than sign
		{
			preferencesdata[i][j]=excelreadpreferences.getData(0, i+1, j+1);  //Picking data from the 2nd row in excel sheet, so i+1	
		}					
	}
	testcasepath = (String) preferencesdata[0][0];
	sheetnumber =  Integer.parseInt((String) preferencesdata[1][0]);
	
	//check null chrome browser parameter
	if (preferencesdata[2][0]!="")  	browsername = (String) preferencesdata[2][0];  
	else								browsername = "Chrome";						//Default browser is Chrome, if none specified
	
	//check null report path parameter
	if (preferencesdata[3][0]!="")		executionreportpath = (String) preferencesdata[3][0];
	else								executionreportpath = "";					//Report path local directory
	
	//Setup Logging off - First one seems to be working
	
	
	/**Logger globalLogger = Logger.getLogger("global");
	/**Handler[] handlers = globalLogger.getHandlers();
	for(Handler handler : handlers) {
	 globalLogger.removeHandler(handler);}
	**/
	//Logger.getLogger("");
	
///	propertyconfig = new ConfigReader(); //Read the Config Property value
	//System.setProperty("webdriver.gecko.driver", propertyconfig.getGeckoPath());  //gecko is required for Selenium 3
///	System.setProperty("webdriver.chrome.driver", propertyconfig.getChromePath());
	///String app_url = JOptionPane.showInputDialog(null,"Enter Application URL"); //To create window
///	System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
///	driver = new ChromeDriver();
	///driver.get("https://"+app_url); // To open url in browse
	//report = new ExtentReports(System.getProperty("user.dir")+ propertyconfig.getReportPath()); //Set the HTML Execution Report Path. Putting another parameter TRUE will overwrite the file everytime.
	ReportScreenshotUtility.GetExtent(executionreportpath);
	//ReportScreenshotUtility.report.loadConfig(new File(System.getProperty("user.dir")+"/src/main/resources/extent-config.xml")); //Load the config settings frot he report from xml.
	
	//driver = new InternetExplorerDriver();
    //baseUrl = "http://www.waikato.ac.nz/";
	//driver = new FirefoxDriver();
	
}
/**
public void browserSettings(WebDriver driver, String parametervalue)
{
	driver.get("https://"+parametervalue);
	//driver.get(parametervalue);
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

}
**/

public void checkLocParamBlankValues(String locatorvalue, String parametervalue)
{
	if (parametervalue.equals("") || parametervalue.equals(null) || locatorvalue.equals("") || locatorvalue.equals(null)) //validate that parameters value is valid
	{
		errormessage="Invalid Locator or Parameter value.";
		exceptionerror=true;   
	}
	
}

public void checkLocBlankValue(String locatorvalue)
{
	if (locatorvalue.equals("") || locatorvalue.equals(null)) //validate that parameters value is valid
	{
		errormessage="Invalid Locator value.";
		exceptionerror=true;   
	}
	
}


@AfterClass(alwaysRun=true)
public void tearDown() throws Exception 
 {
	StringBuffer verificationErrors = new StringBuffer();  
    if ((driver != null))		driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) 
    {
      AssertJUnit.fail(verificationErrorString);
    }
    //ReportScreenshotUtility.report.flush();   
  }




private boolean isElementPresent(By by) 
	{
    try {
      driver.findElement(by);
      return true;
    	} catch (NoSuchElementException e) {
    		return false;
    	}
	}

public void progressBar()
{	
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Container content = f.getContentPane();
	JProgressBar progressBar = new JProgressBar();
	progressBar.setIndeterminate(true);
	//progressBar.setValue(25);
	//progressBar.setStringPainted(true);
	//Border border = BorderFactory.createTitledBorder("Launching...");
	///progressBar.setBorder(border);
	content.add(progressBar, BorderLayout.NORTH);
	f.setSize(400, 100);
	f.setAlwaysOnTop(true);
	f.setLocationRelativeTo(null);
}

}

/**Object result = JOptionPane.showInputDialog(null, "Enter a blog website");
if (result != null) {
        String word2 = (String) result;
}**/

