import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;


public class Validations 		//Class to test all the assertEqual validations, including getAttribute("values")
{   
	// e.g. captions
		public void validateCaptionByXpath(String xpath, String validationtext, WebDriver driver)    // xpath validation
		{
			assertEquals(driver.findElement(By.xpath(xpath)).getText(),validationtext);
		}
		public void validateCaptionById(String id, String validationtext,WebDriver driver)   //id validation
		{
			assertEquals(driver.findElement(By.id(id)).getText(),validationtext);
		}
		public void validateCaptionByName(String name, String validationtext,WebDriver driver)   //name validation
		{
			assertEquals(driver.findElement(By.name(name)).getText(),validationtext);
		}
		public void validateCaptionByCssSelector(String css, String validationtext,WebDriver driver)   //css validation
		{
			assertEquals(driver.findElement(By.cssSelector(css)).getText(),validationtext);
		}
		
		//Attribute Values (e.g. textboxes)
		public void validateTextboxValueByXpath(String xpath, String validationtext,WebDriver driver)    // xpath validation
		{
			assertEquals(driver.findElement(By.xpath(xpath)).getAttribute("value"),validationtext);
		}
		public void validateTextboxValueById(String id, String validationtext,WebDriver driver)   //id validation
		{
			assertEquals(driver.findElement(By.id(id)).getAttribute("value"),validationtext);
		}
		public void validateTextboxValueByName(String name, String validationtext,WebDriver driver)   //name validation
		{
			assertEquals(driver.findElement(By.name(name)).getAttribute("value"),validationtext);
		}
		public void validateTextboxValueByCssSelector(String css, String validationtext,WebDriver driver)   //css validation
		{
			assertEquals(driver.findElement(By.cssSelector(css)).getAttribute("value"),validationtext);		
		}
		
		//Dropdown
		public void validateDropdownValueByXpath(String xpath, String validationtext,WebDriver driver)    // xpath validation
		{
			 assertEquals(new Select(driver.findElement(By.xpath(xpath))).getFirstSelectedOption().getText(), validationtext);	  // To get the selected dropdown value		
		}
		public void validateDropdownValueById(String id, String validationtext,WebDriver driver)   //id validation
		{
			assertEquals(new Select(driver.findElement(By.id(id))).getFirstSelectedOption().getText(), validationtext);	  // To get the selected dropdown value
		}
		public void validateDropdownValueByName(String name, String validationtext,WebDriver driver)   //name validation
		{
			assertEquals(new Select(driver.findElement(By.name(name))).getFirstSelectedOption().getText(), validationtext);	  // To get the selected dropdown value
		}
		public void validateDropdownValueByCssSelector(String css, String validationtext,WebDriver driver)   //css validation
		{
			assertEquals(new Select(driver.findElement(By.cssSelector(css))).getFirstSelectedOption().getText(), validationtext);	  // To get the selected dropdown value
		}
	
	
	
	

}
