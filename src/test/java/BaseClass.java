import java.awt.BorderLayout;
import java.awt.Container;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import org.openqa.selenium.WebDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.relevantcodes.extentreports.ExtentTest;


public class BaseClass {
	protected WebDriver driver;
	protected InputText input_text =  new InputText();
	////Clicks click =  new Clicks();
	//SelectDropdown selectdropdown = new SelectDropdown();
	protected Validations validation= new Validations();
	protected KeyPress keypress = new KeyPress();
	protected ExtentTest logger;	//Main class to generate the Logs and add to the report
	protected String executionreportpath, errormessage, testcasepath, browsername;;
	protected int sheetnumber, invocationcount;	//invcationcount to run multiple times for same set of test data
	
	protected Object preferencesdata[][]=new Object[6][1];
	protected Boolean exceptionerror;
	protected String stepdescription, command, stepid;
	protected JFrame f = new JFrame("Starting Test Execution...");
	protected Boolean multipleExecutionsDifferentTestData=false;
@BeforeClass(alwaysRun=true)
public void setUp() throws Exception 
{		
	progressBar(); // Call the Progress Bar code
	f.setVisible(true); //Display the Progress Bar
	LogManager.getLogManager().reset();
	Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
	globalLogger.setLevel(java.util.logging.Level.OFF);	
	ExcelDataConfig excelreadpreferences = new ExcelDataConfig(System.getProperty("user.dir")+"/Preferences.xlsx");	
	//preferencesdata = new Object[5][1];
	for(int i=0;i<6;i++)   //Initializing Array to rows-1. First row is just headings and make sure every column cell has a text
	{
		for(int j=0;j<1;j++)  //Columns value is one more than the index so less than sign
		{
			preferencesdata[i][j]=excelreadpreferences.getData(0, i+1, j+1);  //Picking data from the 2nd row in excel sheet, so i+1
		}					
	}
	testcasepath = (String) preferencesdata[0][0];
	sheetnumber =  Integer.parseInt((String) preferencesdata[1][0]);
	//check null chrome browser parameter
	if (preferencesdata[2][0]!="" && preferencesdata[2][0]!="0")  	browsername = (String) preferencesdata[2][0];  
	else								browsername = "Chrome";						//Default browser is Chrome, if none specified
	//check null report path parameter
	if (preferencesdata[3][0]!="" && preferencesdata[3][0]!="0")		executionreportpath = (String) preferencesdata[3][0];
	else								executionreportpath = "";					//Report path local directory
	
	if (preferencesdata[5][0]!="" && preferencesdata[5][0]!="0" && preferencesdata[5][0]!=null )	
	{	
		multipleExecutionsDifferentTestData = true;
	}
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
		f.setSize(600, 100);
		f.setAlwaysOnTop(true);
		f.setLocationRelativeTo(null);
	}
	
	
	
	
	
}
