package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CrmTest {

	WebDriver driver;
	String browser;
	String url;
	String usename = "demo@codefios.com";
	String password = "abc123";
	
	
	//test or mock data 
	String newDashboardHeaderrTest = "Dashboard";
	String userNameAlertMsg = "Please enter your user name";
	String PasswordAlertMsg = "Please enter your password";
	String newCustomerHeaderrTest = "New Customer";
	String fullName = "Selenium";
	String company = "Techfios";
	String email = "abc@yahoo.com";
	String phoneNo = "2338866401";
	String country = "Afganistan";
	

	By USER_NAME_FIELD = By.xpath("//*[@id=\"user_name\"]");
	By PASSWORD_FIELD = By.xpath("//*[@id=\"password\"]");
	By SIGIN_BUTTON_FIELD = By.xpath("//*[@id=\"login_submit\"]");
	By DASHBOARD_HEADER_FIELD = By.xpath("/html/body/div[1]/section/div/div[2]/div/div/header/div/strong");
	By CUSTOMER_MENU_FIELD = By.xpath("/html/body/div[1]/aside[1]/div/nav/ul[2]/li[2]/a/span");
	By ADD_CUSTOMER_MENU_FIELD = By.xpath("//*[@id=\"customers\"]/li[2]/a/span");
	By ADD_CUSTOMER_HEADER_FIELD = By.xpath("/html/body/div[1]/section/div/div[2]/div/div[1]/div[1]/div/div/header/div/strong");
	By Full_NAME_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[1]/div/input");
	By COMPANY_DROPDOWN_FIELD = By.xpath("//select[@name='company_name']");
	By EMAIL_FIELD = By.xpath("/html/body");
	By PHONENO_FIELD = By.xpath("//*[@id=\"phone\"]");
	By COUNTRY_DROPDOWN_FIELD = By.xpath("//*[@id=\"general_compnay\"]/div[8]/div[1]/select"); 
	
	public void readConfig() {
		
		//InputStream//BufferReader//FileReader/Scanner
		
		try {
			
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println(" Browser used :" + browser);
			url = prop.getProperty("url");
		}
		catch (IOException e) {
			     e.printStackTrace();
			
		}
			
		}
	
	@BeforeMethod
	public void init() {
		
		
		if(browser.equalsIgnoreCase("Chrome")) 
			
		{System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
		driver = new ChromeDriver();
		
		}else if(browser.equalsIgnoreCase("Edge")){

			System.setProperty("webdriver.edge.driver","driver\\msedgedriver.exe");
			driver = new EdgeDriver();
		}else {
			System.out.println("Please enter a valid Browser");
		}
		
		
		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void loginTest() {
          
	
		driver.findElement(USER_NAME_FIELD).sendKeys(usename);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGIN_BUTTON_FIELD).click();
        Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "newDashboardHeaderrTest","Dasboard Page not found!");
	}
	 @Test
     public void validateAlertMsg() {
    	 
    	 driver.findElement(SIGIN_BUTTON_FIELD).click();
    	 String actualuserNameAlertMsg= driver.switchTo().alert().getText();
    	 Assert.assertEquals(actualuserNameAlertMsg, userNameAlertMsg,"Alert doesn't match!");
    	 driver.switchTo().alert().accept();
    	 driver.findElement(ADD_CUSTOMER_MENU_FIELD).sendKeys(usename);
    	 driver.findElement(SIGIN_BUTTON_FIELD).click();
    	 String actualPasswordAlertMsg= driver.switchTo().alert().getText();
    	 Assert.assertEquals(actualPasswordAlertMsg, PasswordAlertMsg,"Alert doesn't match!");
    	 driver.switchTo().alert().accept();
    	 
     }
	 public void addCustomer() {
		 loginTest();
		 driver.findElement(CUSTOMER_MENU_FIELD).click();
		 driver.findElement(ADD_CUSTOMER_MENU_FIELD).click();
		 Assert.assertEquals(driver.findElement(ADD_CUSTOMER_MENU_FIELD).getText(), newCustomerHeaderrTest, "New Customer page not found!");
		 
		
		 
		 
		 driver.findElement(Full_NAME_FIELD).sendKeys(fullName + generateRandomNum(999));
		 
		 selectFromDropdown(COMPANY_DROPDOWN_FIELD,company);
		 
	     driver.findElement(EMAIL_FIELD).sendKeys(generateRandomNum(9999) + email);
		driver.findElement(PHONENO_FIELD).sendKeys(phoneNo + generateRandomNum(999));
		
	     
		 selectFromDropdown(COUNTRY_DROPDOWN_FIELD,country);
	     
	 }
	 
	 private void selectFromDropdown(By field, String VisibleText) {
		 Select sel = new Select(driver.findElement(field));
		 sel.selectByVisibleText(VisibleText);
		
	}

	

	private int generateRandomNum(int boundryNum) {
		 Random rdn = new Random();
		 int generatedNum = rdn.nextInt(999);
		 return  generatedNum;
				 
		 
	 }
	 
	 
	 
	 
	 
	@AfterMethod
	public void tearDown() {

		driver.close();
		driver.quit();

	}

}
