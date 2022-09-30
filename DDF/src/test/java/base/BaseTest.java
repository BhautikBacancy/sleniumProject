package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.DbManager;
import utilities.ExcelReader;
import utilities.ExtentManager;
import utilities.MonitoringMail;

public class BaseTest {
	
	
	/*
	 * Excel - done 
	 * Logs - done
	 * Properties - done 
	 * TestNG - done
	 * JavaMail - done
	 * ReportNG - 
	 * Database - 
	 * WebDriver - done 
	 * Explicit and ImplicitWait - done
	 * Keywords - 
	 * Screenshots - 
	 * Maven - Build tool
	 * Jenkins
	 * 
	 */
	
	public static WebDriver driver;
	public static Properties OR = new Properties();
	public static Properties Config = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(BaseTest.class);
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testdata.xlsx");
	public static MonitoringMail mail = new MonitoringMail();
	public static WebDriverWait wait;
	public static WebElement dropdown;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static ChromeOptions options;
	public static FirefoxOptions foptions;
	public static String browser;
	public static String browserType;
	
	@BeforeSuite
	public void setUp() {
		
		
		if(driver==null) {
			
			PropertyConfigurator.configure(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\log4j.properties");
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Config.load(fis);
				log.info("Config file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.info("OR file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(System.getenv("browserType")!=null && !System.getenv("browserType").isEmpty()) {
				browserType = System.getenv("browseType");
			}else {
				browserType = Config.getProperty("browserType");
			}
			
			Config.setProperty("browserType", browserType);
			
			
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()) {
				browser = System.getenv("browser");
			}else {
				browser = Config.getProperty("browser");
			}
			
			Config.setProperty("browser", browser);
		
		    
			
			
			
			
			
			if(Config.getProperty("browserType").equals("headless")) {
			
			if(Config.getProperty("browser").equals("firefox")) {
				
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\geckodriver.exe");
				 foptions = new FirefoxOptions();
				 foptions.addArguments("headless");
				driver = new FirefoxDriver(foptions);
				log.info("Firefox Launched");
				
			}else if(Config.getProperty("browser").equals("chrome")) {
				
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
				options = new ChromeOptions();
				options.addArguments("headless");
				driver = new ChromeDriver(options);
				log.info("Chrome Launched");
				}
			
			
		}else if(Config.getProperty("browserType").equals("withhead")) {
			if(Config.getProperty("browser").equals("firefox")) {
				
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\geckodriver.exe");
				
				driver = new FirefoxDriver();
				log.info("Firefox Launched");
				
			}else if(Config.getProperty("browser").equals("chrome")) {
				
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
				log.info("Chrome Launched");
				
			}
			}
			
			driver.get(Config.getProperty("testSiteURL"));
			log.info("Navigated to : "+Config.getProperty("testSiteURL"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")), TimeUnit.SECONDS);
			
			try {
				DbManager.setMysqlDbConnection();
				log.info("DB Connection established !!!");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			wait = new WebDriverWait(driver,Integer.parseInt(Config.getProperty("explicit.wait")));
		}
		
		
		
	}
	
	
	
	
	public static void click(String locatorKey) {

		
		if (locatorKey.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(locatorKey))).click();

		}

		else if (locatorKey.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(OR.getProperty(locatorKey))).click();

		} else if (locatorKey.endsWith(OR.getProperty("_ID"))) {

			driver.findElement(By.id(locatorKey)).click();

		}

		log.info("Clicking on an Element : " + locatorKey);
	    test.log(LogStatus.INFO, "Clicking on: "+locatorKey);	
	
	
	}

	public static void type(String locatorKey, String value) throws IOException, AddressException, MessagingException {
	
		if (locatorKey.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(locatorKey))).sendKeys(value);

		}

		else if (locatorKey.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(OR.getProperty(locatorKey))).sendKeys(value);

		} else if (locatorKey.endsWith(OR.getProperty("_ID"))) {

			driver.findElement(By.id(locatorKey)).sendKeys(value);

		}

		log.info("Typing in an Element : " + locatorKey+" entered the value as : "+value);
		test.log(LogStatus.INFO, "Typeing in: "+locatorKey+"entered value as "+value);
	}
	
	
	
	
	public static void select(String locatorKey, String value) throws IOException, AddressException, MessagingException {
		
		
		
		
		if (locatorKey.endsWith("_XPATH")) {

			dropdown = driver.findElement(By.xpath(OR.getProperty(locatorKey)));

		}

		else if (locatorKey.endsWith("_CSS")) {

			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locatorKey)));

		} else if (locatorKey.endsWith(OR.getProperty("_ID"))) {

			dropdown = driver.findElement(By.id(locatorKey));

		}
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		log.info("Typing in an Element : " + locatorKey+" entered the value as : "+value);
		test.log(LogStatus.INFO, "Selected element: "+locatorKey+"value is "+value);
	}
	
	
	
	
	
	

	public static boolean isElementPresent(String locatorKey) {

		try {

			if (locatorKey.endsWith("_XPATH")) {

				driver.findElement(By.xpath(OR.getProperty(locatorKey)));

			}

			else if (locatorKey.endsWith("_CSS")) {

				driver.findElement(By.cssSelector(OR.getProperty(locatorKey)));

			} else if (locatorKey.endsWith(OR.getProperty("_ID"))) {

				driver.findElement(By.id(locatorKey));

			}
			
			log.info("Finding the Element : "+locatorKey);
			test.log(LogStatus.INFO, "Finding the Element: "+locatorKey);
			return true;
		} catch (Throwable t) {

			log.info("Error while finding an element : "+locatorKey+" exception message is : "+t.getMessage());
			test.log(LogStatus.INFO, "Error while finding an element: "+locatorKey+" exception message is : "+t.getMessage());
			return false;
			
		}

	}
	
	
	
	@AfterSuite
	public void tearDown() {
		
		//driver.quit();
		//log.info("Test Execution Completed !!!");
	}
	

}




