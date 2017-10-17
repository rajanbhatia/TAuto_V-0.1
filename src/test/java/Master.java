
import static org.testng.Assert.assertTrue;
import io.github.bonigarcia.wdm.FirefoxDriverManager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.IAnnotationTransformer;
import org.testng.IAnnotationTransformer3;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.IListenersAnnotation;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class Master extends BaseClass implements IAnnotationTransformer
{

	Set<String> beforepopup;
	String winhandlebefore;
	WebDriverWait wait;
	WebElement elementId, elementXpath, elementName, elementCssSelector; 
	
	
@Test(dataProvider = "TestSteps")//, threadPoolSize=2)		//, invocationCount=invocationcount) //invocationCount set at run time
public void main(String tcid, String tc_desc, String stepid, String step_desc, String command, String locatortype, String locatorvalue, String testdata) //, String result, String error)
{	
	try
	{			
		//logger = ReportScreenshotUtility.report.startTest("Automation Run: Testcase- "+tcid+", Teststep- "+stepid);  //To log every step on the left panel
		exceptionerror=false;	   //ExceptionError flag to capture errors and log to the logger report   
		stepdescription=step_desc;
		this.stepid=stepid;
		this.command=command;
		
		//Auto parameterize test data every time for the textbox enter commands only.
		if ((multipleExecutionsDifferentTestData) && (command.equals("Textbox: Enter Text (locator value, test data)"))) 
		{
				Calendar cal = Calendar.getInstance();
				testdata=mData(testdata, cal);
		}
		
		//Check explicitly for every Web Element presence
		if (!locatortype.equals("") && !locatortype.equals(null))
		{
			switch (locatortype)
			{
				case "ID":						
						elementId= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
						break;						
				case "Xpath":						
						elementXpath=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
						break;						
				case "Name":						
						elementName=wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
						break;						
				case "CssSelector":						
						elementCssSelector=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorvalue)));
						break;					
				default:				
					errormessage="Invalid or No Locator type specified for the object."; //Send error through the AfterMethod and into the report and not via info as above
					exceptionerror=true;						
			}
			
		}	
		
	    
		
		//System.out.println(tcid + " " + tc_desc + " " + stepid + " " + step_desc + " " + command  + " " + locatortype  + " " + locatorvalue + " " + testdata + " " + "\n");
		switch (command)
		{
			case "Browser: Open (test data)": 
			{
				logger = ReportScreenshotUtility.report.startTest("Automation Run: Testcase- "+tcid+"("+tc_desc+")"); //To log every testcase on the left panel and teststeps on the right.
				//browserSettings(driver, testdata);
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
					case "Internet Explorer":	// 64-bit types slowly in the textfields so use 32-bit					
							/** DEPRECATED DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer(); // To speed up IE. If not use 32 bit driver
							capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
							capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
							capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
							//capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
							capabilities.setCapability("IntroduceInstabilityByIgnoringProtectedModeSettings",true);**/
							
							InternetExplorerOptions options = new InternetExplorerOptions();
							//options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
							options.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
							options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
							options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
							options.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, "accept");
							//options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
							options.setCapability("javascriptEnabled", true);
							options.setCapability("disable-popup-blocking", true);
							
							//options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
							options.setCapability("IgnoringProtectedModeSettings",true);
							System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/drivers/IEDriverServer.exe");
							driver = new InternetExplorerDriver(options);
							
							break;						
					case "Safari": //Deprecated for Windows, only in MAC now						
						/**	System.setProperty("webdriver.safari.driver", "C:\\safaridriver.exe");  //gecko is required for Selenium 3
							driver = new SafariDriver();				
						
							break;**/
					case "Chrome (Non-GUI)":
							//*** Headless ***//
							System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
							ChromeOptions chromeOptions = new ChromeOptions();
					        chromeOptions.addArguments("--headless");
					        chromeOptions.addArguments("--disable-gpu");  //disable GPU accelerator abd it doesn't work properly in headless mode           
					        driver = new ChromeDriver(chromeOptions);
							//HTMLUnitDriver
							//driver = new HtmlUnitDriver();				
							//((HtmlUnitDriver)driver).setJavascriptEnabled(true);
					        break;				        
					case "Firefox (Non-GUI)":
					        /** Headless **/
							FirefoxDriverManager.getInstance().setup();	
							FirefoxBinary firefoxBinary = new FirefoxBinary();
							firefoxBinary.addCommandLineOptions("--headless");
							//System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");  //gecko is required for Selenium 3
							FirefoxOptions firefoxOptions = new FirefoxOptions();
							firefoxOptions.setBinary(firefoxBinary);
							FirefoxDriver fdriver = new FirefoxDriver(firefoxOptions);
							this.driver=fdriver;				        
							break;
					default:						
							//logger.log(LogStatus.INFO,"Invalid Browser specified."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid Browser type specified.";
							exceptionerror=true;						
				}
				if (testdata.equals("") || testdata.equals(null)) //validate that test data is valid
				{
					errormessage="Invalid URL";
					exceptionerror=true;   
				}
				else
				{
					f.setVisible(false);
					f.dispose();
					driver.get("https://"+testdata);	
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					wait = new WebDriverWait(driver, 12);
				}
				break;
			}
					
			case "Textbox: Enter Text (locator value, test data)": 
			{	
				checkLocParamBlankValues(locatorvalue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					
					switch (locatortype)
					{
						case "ID":						
							//input_text.textboxIdEnterText(locatorvalue, testdata, driver);
							elementId.clear();
							elementId.sendKeys(testdata);     // enter text
							break;						
						case "Xpath":						
							//input_text.textboxXpathEnterText(locatorvalue, testdata, driver);
							elementXpath.clear();
							elementXpath.sendKeys(testdata);     // enter text
							break;						
						case "Name":						
							//input_text.textboxNameEnterText(locatorvalue, testdata, driver);
							elementName.clear();
							elementName.sendKeys(testdata);     // enter text
							break;						
						case "CssSelector":						
							//input_text.textboxCssSelectorEnterText(locatorvalue, testdata, driver);
							elementCssSelector.clear();
							elementCssSelector.sendKeys(testdata);     // enter text
							break;					
						default:						
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the textbox to enter text."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the textbox to enter text."; //Send error through the AfterMethod and into the report and not via info as above
							exceptionerror=true;						
					}
				}
				break;
			}
			
			case "Textbox: Validate Text (locator value, test data)": 
			{
				checkLocParamBlankValues(locatorvalue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{	
					switch (locatortype)
					{
						//case "ID": validation.validateTextboxValueById(locatorvalue, testdata, driver);
						case "ID": validation.validateTextboxValueById(elementId, testdata, driver);
						break;
						case "Xpath": validation.validateTextboxValueByXpath(elementXpath, testdata, driver);
						break;
						case "Name": validation.validateTextboxValueByName(elementName, testdata, driver);
						break;
						case "CssSelector": validation.validateTextboxValueByCssSelector(elementCssSelector, testdata, driver);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the textbox to validate text."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the textbox to validate text.";
							exceptionerror=true;						
					}					
				}
				break;
			}
			
			case "Caption/Text: Validate Text (locator value, test data)": 
			{
				checkLocParamBlankValues(locatorvalue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror) //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID": validation.validateCaptionById(elementId, testdata, driver);
						break;
						case "Xpath": validation.validateCaptionByXpath(elementXpath, testdata, driver);
						break;
						case "Name": validation.validateCaptionByName(elementName, testdata, driver);
						break;
						case "CssSelector": validation.validateCaptionByCssSelector(elementCssSelector, testdata, driver);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the caption/text to validate."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the caption/text to validate.";
							exceptionerror=true;   
					}					
				}
				break;
			}
			case "Dropdown: Select Value (locator value, test data)": 
			{
				checkLocParamBlankValues(locatorvalue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror) //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID": new Select(driver.findElement(By.id(locatorvalue))).selectByVisibleText(testdata);//selectdropdown.selectDropdownValueById(locatorvalue, testdata, driver);
						break;
						case "Xpath": new Select(driver.findElement(By.xpath(locatorvalue))).selectByVisibleText(testdata);
						break;
						case "Name":  new Select(driver.findElement(By.name(locatorvalue))).selectByVisibleText(testdata);
						break;
						case "CssSelector":  new Select(driver.findElement(By.cssSelector(locatorvalue))).selectByVisibleText(testdata);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the dropdown to select value."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the dropdown to select value.";
							exceptionerror=true;   
						
					}					
				}
				break;
			}
			
			case "Dropdown: Validate Value (locator value, test data)": 
			{
				checkLocParamBlankValues(locatorvalue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID": validation.validateDropdownValueById(elementId, testdata, driver);
						break;
						case "Xpath": validation.validateDropdownValueByXpath(elementXpath, testdata, driver);
						break;
						case "Name": validation.validateDropdownValueByName(elementName, testdata, driver);
						break;
						case "CssSelector": validation.validateDropdownValueByCssSelector(elementCssSelector, testdata, driver);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified dropdown to validate value."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified dropdown to validate value.";
							exceptionerror=true;   
						
					}					
				}
				break;
			}
			case "Button: Validate Text (locator value, test data)": 
			{
				checkLocParamBlankValues(locatorvalue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID": validation.validateTextboxValueById(elementId, testdata, driver);  //use the textbox attribute code for button also
						break;
						case "Xpath": validation.validateTextboxValueByXpath(elementXpath, testdata, driver); //use the textbox attribute code for button also
						break;
						case "Name": validation.validateTextboxValueByName(elementName, testdata, driver); //use the textbox attribute code for button also
						break;
						case "CssSelector": validation.validateTextboxValueByCssSelector(elementCssSelector, testdata, driver); //use the textbox attribute code for button also
						break;
						default:						
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the button to validate text."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the button to validate text.";
							exceptionerror=true; 						
					}					
				}
				break;
			}
			case "Object: Click (locator value)":
			{
				checkLocBlankValue(locatorvalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror) //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID":	elementId.sendKeys(Keys.ENTER);		// click not working with IE so sendkeys 
							//driver.findElement(By.id(locatorvalue)).click();//click.clickIdObject(locatorvalue, driver);
						//((JavascriptExecutor)driver).executeScript("arguments[0].cli‌​ck()", driver.findElement(By.id(locatorvalue)));
					
						//driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);     // click not working with IE so sendkeys
							
						break;
						case "Xpath": elementXpath.sendKeys(Keys.ENTER);
						break;
						case "Name": elementName.sendKeys(Keys.ENTER);
						break;
						case "CssSelector": elementCssSelector.sendKeys(Keys.ENTER);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the object to click."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the object to click.";   	
							exceptionerror=true; 	
					}					
				}
				break;
			}
			
			
			case "Key: Press (Enter/Return/Tab/Escape) (locator value, test data)":
			{
				checkLocBlankValue(locatorvalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					switch (locatortype)
					{
						case "ID": keypress.keyId(elementId, testdata, driver);
						break;
						case "Xpath": keypress.keyXpath(elementXpath, testdata, driver);
						break;
						case "Name": keypress.keyName(elementName, testdata, driver);
						break;
						case "CssSelector": keypress.keyCssSelector(elementCssSelector, testdata, driver);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the key to press."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the key to press.";
							exceptionerror=true; 						
					}					
				}
				break;
			}	
			
			case "Pause: Delay Execution in Seconds (test data)":
			{
				long sleeptimeinsec= Long.parseLong(testdata+"000");  //convert string to long				
				Thread.sleep(sleeptimeinsec);	
				break;
			}
			case "Browser: Close": 
			{
				driver.close();
				
				//logger.log(LogStatus.INFO,"Test Case - "+tcid+"("+tc_desc+") executed.");
				break;
			}
			
			case "Object: Validate if Present (locator value)":
			{
				checkLocBlankValue(locatorvalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					switch(locatortype)
					{
						case "ID":	assertTrue(isElementPresent(By.id(locatorvalue)));
							//assertTrue(isElementPresent(By.id(locatorvalue)));
						break;
						case "Xpath":	assertTrue(isElementPresent(By.xpath(locatorvalue)));
						break;
						case "Name":	assertTrue(isElementPresent(By.name(locatorvalue)));
						break;
						case "CssSelector":	assertTrue(isElementPresent(By.cssSelector(locatorvalue)));
						break;
						default:
							errormessage = "Invalid or No Locator type specified for the object to verify its presence."; //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							exceptionerror=true;   						
					}
				}
				break;
			}
			
						
			/**case "DateFormat_Change (dd/MMM/yyyy)":
			{
				
				break;
			}	**/
			
			
			case "Checkbox: Validate if Selected (locator value)":
			case "Radiobutton: Validate if Selected (locator value)":
			{
				checkLocBlankValue(locatorvalue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					switch(locatortype)
					{
						case "ID":	assertTrue(elementId.isSelected());
							//	assertTrue(driver.findElement(By.id(locatorvalue)).isSelected());
						break;
						case "Xpath":	assertTrue(elementXpath.isSelected());
						break;
						case "Name":	assertTrue(elementName.isSelected());
						break;
						case "CssSelector":	assertTrue(elementCssSelector.isSelected());
						break;
						default:
							errormessage="Invalid or No Locator type specified for the checkbox/radiobutton to verify the selection."; //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							exceptionerror=true;   
						
					}
				}
				break;
			}	
		/**	case "Execute Above Steps Multiple Times (test data)":
			{
				int maxtimes= Integer.parseInt(testdata);
				int row = 
				for (int i=1;i<=maxtimes;i++)
				{
					for(int j=0;j<rows-2;j++)   //Initializing Array to rows-1. First row is just headings and make sure every column cell has a text
					{					    
						
							main(testcasesdata[j+1][0],testcasesdata[j+1][1],testcasesdata[j+1][2],testcasesdata[j+1][3],testcasesdata[j+1][4],testcasesdata[j+1][5],testcasesdata[j+1][6],testcasesdata[j+1][7])					
											
					}
					main(tcid, tc_desc, stepid, step_desc, command, locatortype, locatorvalue, testdata);					
				}
				break;
			}**/
			
			case "Do Not Execute This Step":
			{
				/// DO Nothing
				break;
			}	
			case "Browser: Save Attributes (before pop-up window)":
			{
				winhandlebefore = driver.getWindowHandle(); 
				// get all the window handles before the popup window appears
				beforepopup = driver.getWindowHandles();
				break;
			}
			case "Browser: Switch to new browser (pop-up window)":
			{
				// get all the window handles after the popup window appears
				Set<String> afterPopup = driver.getWindowHandles();

				// remove all the handles from before the popup window appears
				afterPopup.removeAll(beforepopup);

				// there should be only one window handle left
				if(afterPopup.size() == 1) 
				{
				     driver.switchTo().window((String)afterPopup.toArray()[0]);
				}	    
				break;
			}
			case "Switch back to old browser":
			{
				driver.close();
				driver.switchTo().window(winhandlebefore); 
				break;
			}
			
			default: 
			{
				errormessage="Invalid or No command specified."; //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
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
	
	//sheetnumber = sheetnumber; // As user will input the exact serial number and the index starts from 0.
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
	 if(ITestResult.FAILURE==result.getStatus() || (exceptionerror))  //Check if Test case has failed
	 {
	 	 //String screenshot_path = ReportScreenshotUtility.captureScreenshot(driver,"/test-output/screenshots/",result.getName());   //Take screenshot if Test Case fails
		 String screenshot_path = ReportScreenshotUtility.captureScreenshot(driver,executionreportpath,result.getName());   //Take screenshot if Test Case fails and at the same location where execution report is saved.
	 	 String image=logger.addScreenCapture(screenshot_path);
	 	 logger.log(LogStatus.FAIL, "Failed", image);
	 	 if(ITestResult.FAILURE==result.getStatus())		logger.log(LogStatus.FAIL, "Step ID: "+stepid+", Step Desc: "+stepdescription+": FAILED. Error Message: "+ result.getThrowable());
	 	 if (exceptionerror)  logger.log(LogStatus.FAIL, "Step ID: "+stepid+", Step Desc: "+stepdescription+": FAILED. Error Message: "+ errormessage);
	 }
	 else if (ITestResult.SUCCESS==result.getStatus() && (!exceptionerror))   //Check if Test case has passed
	 {
	 	 if (command.equals("DO NOT EXECUTE THIS STEP")) logger.log(LogStatus.PASS, "Step ID: "+stepid+", Step Desc: "+stepdescription+": SKIPPED");  //to capture the non-executed step in the report.
	 	 else logger.log(LogStatus.PASS, "Step ID: "+stepid+", Step Desc: "+stepdescription+": PASSED");	
	 }
	 else if (ITestResult.SKIP==result.getStatus())  //Check if Test case has passed
	 {
		logger.log(LogStatus.SKIP, "Step ID: "+stepid+", Step Desc: "+stepdescription+": SKIPPED. "+result.getThrowable());	
	 }		
		 ReportScreenshotUtility.report.endTest(logger);
		 ReportScreenshotUtility.report.flush();
		 
		
}


/**
public void browserSettings(WebDriver driver, String testdata)
{
	driver.get("https://"+testdata);
	//driver.get(testdata);
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

}
**/





public void checkLocParamBlankValues(String locatorvalue, String testdata)
{
	if (testdata.equals("") || testdata.equals(null) || locatorvalue.equals("") || locatorvalue.equals(null)) //validate that parameters value is valid
	{
		errormessage="Invalid Locator or test data.";
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


private boolean isElementPresent(By by) 
	{
    try {
      driver.findElement(by);
      return true;
    	} catch (NoSuchElementException e) {
    		return false;
    	}
	}


@Override
public void transform(ITestAnnotation annotation, Class testClass,
		Constructor testConstructor, Method testMethod) 
	{
		// TODO Auto-generated method stub
		ExcelDataConfig excelreadpreferences = new ExcelDataConfig(System.getProperty("user.dir")+"/Preferences.xlsx");	
		//preferencesdata = new Object[5][1];
		/**for(int i=0;i<5;i++)   //Initializing Array to rows-1. First row is just headings and make sure every column cell has a text
		{
			for(int j=0;j<1;j++)  //Columns value is one more than the index so less than sign
			{
				preferencesdata[4][0]=excelreadpreferences.getData(0, 5, 1);  //Picking data from the 2nd row in excel sheet, so i+1
				
			}					
		}**/
		preferencesdata[4][0]=excelreadpreferences.getData(0, 5, 1); //InvocationCount same data multiple executions  //Picking data from the 2nd row in excel sheet, so i+1
		preferencesdata[5][0]=excelreadpreferences.getData(0, 6, 1); //InvocationCount different data multiple executions
		//Same data multiple execution
		//if (preferencesdata[4][0]!=""  && preferencesdata[4][0]!="0")		
		System.out.println(((String) preferencesdata[4][0]).matches("[0-9]+"));
		System.out.println(((String) preferencesdata[4][0]).length());
		System.out.println(preferencesdata[4][0]);
		if (((String) preferencesdata[4][0]).matches("[0-9]+") && ((String) preferencesdata[4][0]).length() >= 1 && !preferencesdata[4][0].equals("0")) // No need for this condition && !preferencesdata[4][0].equals(""))
		{
				invocationcount = Integer.parseInt((String) preferencesdata[4][0]);
		}
		else
		{
				if (!preferencesdata[5][0].equals("0") && ((String) preferencesdata[5][0]).matches("[0-9]+") && ((String) preferencesdata[5][0]).length() >= 1 )	// check for integer values only
				{	
					invocationcount = Integer.parseInt((String) preferencesdata[5][0]); //Different test data each time for multiple executions. It can't work with multiple runs and same test data
				}
				else
				{									//don't need to do anything if nothing mentioned
					invocationcount = 1;					// This will work if nothing is mentioned for multiple test runs
				}	
		}	
			
		if ("main".equals(testMethod.getName())) 
		{
		      annotation.setInvocationCount(invocationcount);		      
		}
		//Different data multiple execution
		
		
	
	}

public String mData(String string, Calendar cal)
{
	//string=new Timestamp(System.currentTimeMillis()).getTime()+""+cal.get(Calendar.DATE)+string;
	string=System.currentTimeMillis()+""+cal.get(Calendar.DATE)+string;
	return string;
}

}

/**Object result = JOptionPane.showInputDialog(null, "Enter a blog website");
if (result != null) {
        String word2 = (String) result;
}**/

