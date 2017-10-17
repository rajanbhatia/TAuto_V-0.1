import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;


public class PressKey   // Class to press keyboard keys at different objects
{
	
	public void keyboardXpathKeys(String xpath, String key, WebDriver driver)
	{
		switch (key)
		{
		case "Enter": 	driver.findElement(By.xpath(xpath)).sendKeys(Keys.ENTER); //String Format should be like "Keys.RETURN"	 
		break;
		case "Return": 	driver.findElement(By.xpath(xpath)).sendKeys(Keys.RETURN); //String Format should be like "Keys.RETURN"	 
		break;
		case "Tab": 	driver.findElement(By.xpath(xpath)).sendKeys(Keys.TAB); //String Format should be like "Keys.RETURN"	 
		break;
		case "Escape": 	driver.findElement(By.xpath(xpath)).sendKeys(Keys.ESCAPE); //String Format should be like "Keys.RETURN"	 
		break;
		}
	 
	}

	public void keyboardIdKeys(String id, String key, WebDriver driver)
	{
		switch (key)
		{
		case "Enter":   driver.findElement(By.id(id)).sendKeys(Keys.ENTER); //String Format should be like "Keys.RETURN"
	    //selenium.keyPressNative("10"); // Enter //	 
		break;
		case "Return": 	driver.findElement(By.id(id)).sendKeys(Keys.RETURN); //String Format should be like "Keys.RETURN"	 
		break;
		case "Tab": 	driver.findElement(By.id(id)).sendKeys(Keys.TAB); //String Format should be like "Keys.RETURN"	 
		break;	
		case "Escape": 	driver.findElement(By.id(id)).sendKeys(Keys.ESCAPE); //String Format should be like "Keys.RETURN"	 
		break;
		}	 	 
	}
	
	public void keyboardNameKeys(String name, String key, WebDriver driver)
	{	
		switch (key)
		{
		case "Enter": 	driver.findElement(By.name(name)).sendKeys(Keys.ENTER); //String Format should be like "Keys.RETURN"	 
		break;
		case "Return": 	driver.findElement(By.name(name)).sendKeys(Keys.RETURN); //String Format should be like "Keys.RETURN"	 
		break;
		case "Tab": 	driver.findElement(By.name(name)).sendKeys(Keys.TAB); //String Format should be like "Keys.RETURN"	 
		break;	
		case "Escape": 	driver.findElement(By.name(name)).sendKeys(Keys.ESCAPE); //String Format should be like "Keys.RETURN"	 
		break;
		}	 	 
	}
	
	public void keyboardCssSelectorKeys(String css, String key, WebDriver driver)
	{	
		switch (key)
		{
		case "Enter": 	driver.findElement(By.cssSelector(css)).sendKeys(Keys.ENTER); //String Format should be like "Keys.RETURN"	 
		break;
		case "Return": 	driver.findElement(By.cssSelector(css)).sendKeys(Keys.RETURN); //String Format should be like "Keys.RETURN"	 
		break;
		case "Tab": 	driver.findElement(By.cssSelector(css)).sendKeys(Keys.TAB); //String Format should be like "Keys.RETURN"	 
		break;	
		case "Escape": 	driver.findElement(By.cssSelector(css)).sendKeys(Keys.ESCAPE); //String Format should be like "Keys.RETURN"	 
		break;
		}	 
		 
	}
	
 
	
	
	
	
}
