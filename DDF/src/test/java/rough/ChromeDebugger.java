package rough;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDebugger {

	//chrome.exe --remote-debugging-port=9222 --user-data-dir="c:\autoprofile"
	public static void main(String[] args) throws InterruptedException {
		ChromeOptions cp = new ChromeOptions();
		cp.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
		 
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
			ChromeDriver driver = new ChromeDriver(cp);
		
			driver.findElement(By.name("q")).sendKeys("Hello");
			
		        }
}
