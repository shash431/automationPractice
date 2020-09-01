package helper;

import org.openqa.selenium.WebDriver;

public class ThreadLocalDriver {
    private static ThreadLocal<WebDriver> tl = new ThreadLocal<WebDriver>();
 
    public static WebDriver getDriver() {
        return tl.get();
    }
 
    public static void setWebDriver(WebDriver driver) {
        tl.set(driver);
    }
}
