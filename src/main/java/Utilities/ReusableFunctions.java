package Utilities;

import BaseClass.LibraryClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.*;

public class ReusableFunctions {

    Logger log;

    public ReusableFunctions() {
        this.log = LogManager.getLogger(ReusableFunctions.class);
    }

    public ReusableFunctions(org.openqa.selenium.WebDriver driver) {
        LibraryClass.driver = driver;
        this.log = LogManager.getLogger(ReusableFunctions.class);
    }

    // 🔹 For older step defs
    public void sendText(WebElement element, String value) {
        try {
            element.clear();
            element.sendKeys(value);
            log.info(" sendText(): Entered '" + value + "' in field.");
        } catch (Exception e) {
            log.error(" sendText(): Failed to enter '" + value + "' - " + e.getMessage());
            Assert.fail("Failed to enter text using sendText()");
        }
    }

    // 🔹 Preferred version
    public void enterText(WebElement element, String value, String fieldName) {
        try {
            element.clear();
            element.sendKeys(value);
            log.info("enterText(): '" + value + "' entered in: " + fieldName);
        } catch (Exception e) {
            log.error(" enterText(): Failed in " + fieldName + " - " + e.getMessage());
            Assert.fail("Failed to enter text in " + fieldName);
        }
    }

    // 🔹 Click method with logging and assertion
    public void clickElement(WebElement element, String elementName) {
        try {
            element.click();
            log.info(" Clicked on: " + elementName);
        } catch (Exception e) {
            log.error(" Click failed on: " + elementName + " - " + e.getMessage());
            Assert.fail("Click failed on: " + elementName);
        }
    }

    // 🔹 Get text of an element (often used in POM getters)
    public String getElementText(WebElement element, String fieldName) {
        try {
            String text = element.getText().trim();
            log.info(" Text retrieved from " + fieldName + ": " + text);
            return text;
        } catch (Exception e) {
            log.error(" Could not retrieve text from " + fieldName + ": " + e.getMessage());
            return "";
        }
    }

    // 🔹 Assert visibility
    public void verifyElementDisplayed(WebElement element, String elementName) {
        try {
            Assert.assertTrue(element.isDisplayed(), " Visible: " + elementName);
            log.info(" Element visible: " + elementName);
        } catch (AssertionError e) {
            log.error(" Element visibility check failed: " + elementName);
            throw e;
        }
    }

    // 🔹 Assert page title
    public void verifyTitle(String expectedTitle) {
        try {
            String actualTitle = LibraryClass.getDriver().getTitle();
            Assert.assertEquals(actualTitle, expectedTitle, "❌ Title mismatch");
            log.info("Title matched: " + actualTitle);
        } catch (AssertionError e) {
            log.error("Title mismatch. Expected: " + expectedTitle + ", Actual: " + LibraryClass.getDriver().getTitle());
            throw e;
        }
    }

    // 🔹 HTML5 field validation messages (optional)
    public String getValidationMessage(WebElement element, String fieldName) {
        try {
            String message = element.getAttribute("validationMessage");
            log.info(" Validation message for " + fieldName + ": " + message);
            return message;
        } catch (Exception e) {
            log.error(" Could not fetch validation message for " + fieldName + ": " + e.getMessage());
            return "";
        }
    }

    public void selectDropdownByVisibleText(WebElement dropdownElement, String visibleText) {
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(visibleText);
    }

    public String getSelectedDropdownValue(WebElement dropdownElement) {
        Select select = new Select(dropdownElement);
        return select.getFirstSelectedOption().getText();
    }

    public void setSliderValue(WebDriver driver, WebElement sliderElement, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));",
                sliderElement, value);
    }

}
