import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;


public class ZNotSelectDropdown
{
	
	public void selectDropdownValueByXpath(String xpath, String value, WebDriver driver)
	{
		new Select(driver.findElement(By.xpath(xpath))).selectByVisibleText(value);
	}
	
	public void selectDropdownValueById(String id, String value, WebDriver driver)
	{
		new Select(driver.findElement(By.id(id))).selectByVisibleText(value);
	}
	
	public void selectDropdownValueByName(String name, String value, WebDriver driver)
	{
		new Select(driver.findElement(By.name(name))).selectByVisibleText(value);
	}
	
	public void selectDropdownValueByCssSelector(String css, String value, WebDriver driver)
	{
		new Select(driver.findElement(By.cssSelector(css))).selectByVisibleText(value);
	}

}
