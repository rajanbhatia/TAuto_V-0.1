import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;


public class SelectDropdown  //To select values from the dropdowns 
{
	
	public void selectXpathValue(String xpath, String value, WebDriver driver)
	{
		new Select(driver.findElement(By.xpath(xpath))).selectByVisibleText(value);
	}
	
	public void selectIdValue(String id, String value, WebDriver driver)
	{
		new Select(driver.findElement(By.id(id))).selectByVisibleText(value);
	}
	
	public void selectNameValue(String name, String value, WebDriver driver)
	{
		new Select(driver.findElement(By.name(name))).selectByVisibleText(value);
	}
	
	public void selectCssSelectorValue(String css, String value, WebDriver driver)
	{
		new Select(driver.findElement(By.cssSelector(css))).selectByVisibleText(value);
	}

}
