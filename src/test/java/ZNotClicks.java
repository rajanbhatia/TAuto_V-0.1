import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ZNotClicks {						//Class to click on the objects (buttons, radio, checkboxes,...)

	
	public void clickXpathObject(String xpath,WebDriver driver)   
	{
		driver.findElement(By.xpath(xpath)).click();     // click xpath
	}
	public void clickIdObject(String id,WebDriver driver)   
	{
		driver.findElement(By.id(id)).click();     // click id
	}
	public void clickCssSelectorObject(String css,WebDriver driver)   
	{
		driver.findElement(By.id(css)).click();     // click css
	}
	public void clickNameObject(String name,WebDriver driver)   
	{
		driver.findElement(By.id(name)).click();     // click name
	}

}
