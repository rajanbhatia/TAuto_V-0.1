
 
import io.selendroid.client.SelendroidDriver;



import java.net.MalformedURLException;
import java.net.URL;
 



import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
 
public class AppiumDriverBase {
 
    protected SelendroidDriver driver;
    protected WebDriverWait wait;
 
    //before Test Annotation makes a java function to run every time before a TestNG test case
    @Test
    protected void createAppiumDriver() throws MalformedURLException, InterruptedException {
 
    //relative path to apk file
   /// final File classpathRoot = new File(System.getProperty("user.dir"));
   /// final File appDir = new File(classpathRoot, "src/test/resources/apps/");
    ///final File app = new File(appDir, "ApiDemos-debug.apk");
 
    //setting up desired capability
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("browserName", "");
    caps.setCapability("platform", "ANDROID");
    caps.setCapability("platformVersion", "5.0");
    caps.setCapability("deviceName", "ANDROID");
   /// caps.setCapability("app", app.getAbsolutePath());
 
    //initializing driver object
    try {
		//driver = new SelendroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    	driver = new SelendroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.getMessage();
	}
 
    //initializing explicit wait object
    wait = new WebDriverWait(driver, 10);
    }
 
    //After Test Annotation makes a java function to run every time after a TestNG test case
    @AfterTest
    public void afterTest(){
 
    //quit the driver
    driver.quit();
    }
 
}
