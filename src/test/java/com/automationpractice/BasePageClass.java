package com.automationpractice;


import helper.DriverFactory;
import helper.PropertyFileReader;
import helper.ThreadLocalDriver;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
 * Base page is parent class of all page classes
 * contains all static general methods
 * reading property file + driver repo 
 */
public class BasePageClass {

	WebDriver driver = null;
	PropertyFileReader propertiesReader = null;
	Map<String, String> propertyMap;
	String driverToRun = null;
	Logger log = Logger.getLogger(BasePageClass.class);
	WebDriverWait wait;

	// singleton implemented
	private static BasePageClass instance = null;

	/**
	 * log4j need to be initialized once
	 */
	public static BasePageClass getInstance() throws MalformedURLException {
		PropertyConfigurator.configure("log4j.properties");

		// if (instance == null) {
		instance = new BasePageClass();
		// }
		return instance;
	}

	/**
	 * This method will read properties from xyz.properties file and return a
	 * HashMap object with key, values pair
	 */
	public Map<String, String> getProperties() {
		if (propertiesReader == null) {
			propertiesReader = new PropertyFileReader();
		}
		propertyMap = propertiesReader.getPropertyMap();
		log.info("fetched all properties");
		return propertyMap;
	}

	/**
	 * driver can be picked from Thread local or from Enum(w/o thread local
	 * implementation)
	 */
	public WebDriver getDriver() {

		String driverToRun = getProperties().get("driver");
		String url = getProperties().get("server");

		if (driverToRun.equals("firefox")) {
			ThreadLocalDriver.setWebDriver(DriverFactory.createInstance("firefox"));
			driver = ThreadLocalDriver.getDriver();

		} else if (driverToRun.equals("chrome")) {
			ThreadLocalDriver.setWebDriver(DriverFactory.createInstance("chrome"));
			driver = ThreadLocalDriver.getDriver();

		} else {
			ThreadLocalDriver.setWebDriver(DriverFactory.createInstance("chrome"));
			driver = ThreadLocalDriver.getDriver();
		}

		//launch the application
		driver.get(url);
		log.debug("driver has been initialized as:  " + driver);
		return driver;
	}

	public void quit() {
		log.info("quit application");
		driver.quit();
	}

	public void close() {
		log.info("close application");
		driver.close();
	}

	public void get(String url) {
		log.info("open url: " + url);
		driver.get(url);
	}

	/**
	 * Need to pass driver, as null pointer coming in case we don't pass driver
	 * from TestListeners class
	 */
	public String getScreenshot(WebDriver driver, String testclass, String testname) throws IOException {

		log.info("taking screenshot for failed test case: " + testclass + "_" + testname);
		String filePath = "./src/test/resources/screenshots/";
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		String path = filePath + testclass + "_" + testname + "_" + timestamp;

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(path + ".png"));
			log.info("screenshot captured at: " + path + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path + ".png";
	}

	@Attachment(value = "{0}", type = "image/png")
	public static byte[] attachScreenShotInAllureReport(String attachmentName) {
		// log.info("taking screenshot for failed test case: ");
		byte[] res = null;

		try {
			BufferedImage image = ImageIO.read(new File(attachmentName));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			res = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private static byte[] toByteArray(File file) throws IOException {
		return Files.readAllBytes(Paths.get(file.getPath()));
	}

	

	/**
	 * Common explicit wait methods; 
	 * Generic can be used be most of the cases
	 * 
	 * wait time need to be picked from properties file
	 */
	public void waitForVisibilityOfElement(WebElement element) {
		wait = new WebDriverWait(ThreadLocalDriver.getDriver(), 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Common explicit wait methods
	 */
	public void waitForClickablityOfElement(WebElement element) {
		wait = new WebDriverWait(ThreadLocalDriver.getDriver(), 10);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * todo: How to use By locator with PF - Issue in PF sol: use only expected
	 * cond which req web element
	 * 
	 * Instead of declaring separate wait method and calling them in test cases
	 * We have added Expected condition in every action methods e.g. click,
	 * sendkeys etc
	 */
	public void waitForPresenceOfElement(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}
	
	
	/**
	 * todo: not using as of now
	 */
	public Wait<WebDriver> setupFluentWait(WebDriver driver, Integer timeout) {
		// get driver using threadlocal
		return new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
	}

	public void click(WebElement element) {
		waitForClickablityOfElement(element);
		element.click();
	}

	public void sendKeys(WebElement element, String keyword) {
		waitForVisibilityOfElement(element);
		element.clear();
		element.sendKeys(keyword);
	}

	public boolean isPageLoaded(WebElement element) {
		waitForVisibilityOfElement(element);
		return element.isDisplayed();
	}

	public boolean isElementPresent(WebElement element) {
		waitForVisibilityOfElement(element);
		return element.isDisplayed();
	}

	public String getText(WebElement element) {
		waitForVisibilityOfElement(element);
		return element.getText();
	}

	public String getAttributeValue(WebElement element, String attribute) {
		waitForVisibilityOfElement(element);
		return element.getAttribute(attribute);
	}
	


}
