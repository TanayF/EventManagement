package BaseClass;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LibraryClass {

    public static WebDriver driver;
    public static Logger log = LogManager.getLogger(LibraryClass.class);

    public LibraryClass(WebDriver driver) {
        LibraryClass.driver = driver;
    }

    // Screenshot
    public static String captureScreenshot(String testName) {
        String path = "";
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            path = System.getProperty("user.dir") + "/src/test/resources/Screenshots/" + testName + "_" + timestamp + ".png";
            FileUtils.copyFile(src, new File(path));
            log.info("Screenshot saved: " + path);
        } catch (IOException e) {
            log.error("Screenshot failed: " + e.getMessage(), e);
        }
        return path;
    }

    // Waits
    public void waitForElementVisible(WebElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOf(element));
            log.info("Element visible: " + element);
        } catch (Exception e) {
            log.error("Wait failed: Element not visible - " + e.getMessage(), e);
        }
    }

    public void waitForElementClickable(WebElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            log.info("Element clickable: " + element);
        } catch (Exception e) {
            log.error("Wait failed: Element not clickable - " + e.getMessage(), e);
        }
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            boolean visible = element.isDisplayed();
            log.info("Element displayed: " + visible);
            return visible;
        } catch (Exception e) {
            log.error("Element not displayed: " + e.getMessage(), e);
            return false;
        }
    }

    public static void closeBrowser() {
        try {
            if (driver != null) {
                driver.quit();
                log.info("Browser closed.");
            }
        } catch (Exception e) {
            log.error("Failed to close browser: " + e.getMessage(), e);
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
