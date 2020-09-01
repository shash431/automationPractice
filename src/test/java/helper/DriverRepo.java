package helper;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/*
 * earlier used when thread local has not been implemented
 * 
 * currently not in use
 */
public enum DriverRepo {

	FIREFOX {
		public WebDriver getDriver() {
			String current_os = System.getProperty("os.name");
			log.info("Current OS: " + current_os);
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
		}

	},
	CHROME {
		public WebDriver getDriver() {
			String current_os = System.getProperty("os.name");
			log.info("Current OS: " + current_os);

			if (current_os.equals("Mac OS X")) {
				log.info("MAC: Chrome initialized");
				String path = System.getProperty("user.dir");
				System.setProperty("webdriver.chrome.driver", path + "/src/test/resources/drivers/chromedriver3");
				return new ChromeDriver();
			} else if (current_os.equals("Window")) {
				log.info("WINDOW: Chrome initialized");
				System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
			} else {
				log.info("Unknown OS Exception");
			}
			return new ChromeDriver();
		}
	};

	WebDriver driver;
	Logger log = Logger.getLogger(getClass());

	DriverRepo(WebDriver drv) {
		PropertyConfigurator.configure("log4j.properties");
		this.driver = drv;
	}

	DriverRepo() {
	}

	public WebDriver getDriver() {
		return driver;
	}

}
