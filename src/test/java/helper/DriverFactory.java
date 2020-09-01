package helper;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * Used along with thread local implementation
 * refer ThreadLocalDriver.java
 * refer BasePageClass > getDriver()
 */
public class DriverFactory {

	static Logger log = Logger.getLogger(DriverFactory.class);
	
	public static WebDriver createInstance(String browserName) {
		WebDriver driver = null;
		if (browserName.toLowerCase().contains("firefox")) {
			String current_os = System.getProperty("os.name");
			if (current_os.equals("Mac OS X")) {
				log.info("MAC: FF initialized");
				String path = System.getProperty("user.dir");
				System.setProperty("webdriver.gecko.driver", path + "/src/test/resources/drivers/geckodriver");
			} else if (current_os.equals("Windows")) {
				log.info("WINDOW: FF initialized");
				System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
			} else {
				log.info("Unknown OS Exception");
			}
			return new FirefoxDriver();
		}else if(browserName.toLowerCase().contains("chrome")){
			log.info("Chrome setup");
			String path = System.getProperty("user.dir");
			System.setProperty("webdriver.chrome.driver", path + "/drivers/chromedriver");
			return new ChromeDriver();
		}else{
			log.info("Default setup pending");
		}

		return driver;

	}
}
