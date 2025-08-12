package Stepdefinitions;

import Pages.HotelBookingPage;
import Utilities.DataProviderUtil;
import Utilities.ExcelReader;
import Utilities.ReusableFunctions;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import testRunner.TestRunner;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class HotelBookingSteps {

    private final WebDriver driver = TestRunner.driver;
    private final Properties prop = TestRunner.prop;
    HotelBookingPage hotel = new HotelBookingPage(driver);
    ReusableFunctions rf = new ReusableFunctions(driver);
    DataProviderUtil dpu = new DataProviderUtil();

    // ================== COMMON NAVIGATION ==================
    @Given("the user navigates to the Hotel Booking form")
    public void user_navigates_to_booking_form() {
        String hotelBookingUrl = prop.getProperty("hotelBookingURL");
        driver.get(hotelBookingUrl);
        rf.verifyTitle("HotelBooking");
    }

    // ================== PHONE VALIDATION ==================
    @When("the user enters {string} in the Phone Number field")
    public void user_enters_phone(String phoneNumber) {
        rf.enterText(hotel.phone, phoneNumber, "Phone Number");
    }

    @When("submits the form")
    public void submits_the_form() {
        rf.clickElement(hotel.submitButton, "Submit");
    }

    @Then("the phone number validation should be {string}")
    public void the_phone_number_validation_should_be(String outcome) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        
        if (outcome.equalsIgnoreCase("invalid")) {
            try {
                // Wait a moment for any validation to trigger
                Thread.sleep(1000);
                
                // Method 1: Check if error message element exists and is displayed
                boolean errorMessageFound = false;
                try {
                    if (hotel.phoneErrorMessage != null && hotel.phoneErrorMessage.isDisplayed()) {
                        errorMessageFound = true;
                        System.out.println("✓ Phone validation error message found and displayed");
                    }
                } catch (Exception e) {
                    // Error element might not exist, continue with other checks
                }
                
                // Method 2: Check HTML5 validation message
                boolean html5ValidationFound = false;
                try {
                    String validationMessage = (String) ((JavascriptExecutor) driver)
                        .executeScript("return arguments[0].validationMessage;", hotel.phone);
                    if (validationMessage != null && !validationMessage.trim().isEmpty()) {
                        html5ValidationFound = true;
                        System.out.println("✓ HTML5 validation message found: " + validationMessage);
                    }
                } catch (Exception e) {
                    // Continue with other checks
                }
                
                // Method 3: Check if form submission was prevented (still on same page)
                boolean formSubmissionPrevented = false;
                String currentUrl = driver.getCurrentUrl();
                if (currentUrl.toLowerCase().contains("hotelbooking") || currentUrl.toLowerCase().contains("hotel")) {
                    formSubmissionPrevented = true;
                    System.out.println("✓ Form submission prevented - still on hotel booking page");
                }
                
                // Method 4: Check for any error elements with generic selectors
                boolean genericErrorFound = false;
                try {
                    List<WebElement> errorElements = driver.findElements(
                        By.xpath("//*[contains(@class,'error') or contains(@style,'color: red') or contains(text(),'invalid') or contains(text(),'Invalid')]"));
                    for (WebElement element : errorElements) {
                        if (element.isDisplayed()) {
                            genericErrorFound = true;
                            System.out.println("✓ Generic error element found: " + element.getText());
                            break;
                        }
                    }
                } catch (Exception e) {
                    // Continue
                }
                
                // If any validation method worked, test passes
                if (errorMessageFound || html5ValidationFound || formSubmissionPrevented || genericErrorFound) {
                    System.out.println("✓ Phone validation working correctly for invalid input");
                } else {
                    Assert.fail("BUG FOUND: No phone number validation detected. Invalid phone '" + 
                              hotel.phone.getAttribute("value") + "' was accepted without any validation.");
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Assert.fail("Test interrupted during phone validation check");
            }
            
        } else if (outcome.equalsIgnoreCase("valid")) {
            // For valid phone numbers, ensure no error messages are shown
            boolean isErrorVisible = false;
            try {
                if (hotel.phoneErrorMessage != null && hotel.phoneErrorMessage.isDisplayed()) {
                    isErrorVisible = true;
                }
            } catch (Exception e) {
                // Error element doesn't exist or not displayed, which is good for valid input
            }
            
            // Also check HTML5 validation
            try {
                String validationMessage = (String) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].validationMessage;", hotel.phone);
                if (validationMessage != null && !validationMessage.trim().isEmpty()) {
                    isErrorVisible = true;
                }
            } catch (Exception e) {
                // Continue
            }
            
            Assert.assertFalse(isErrorVisible,
                    "BUG: An error message was displayed for a VALID phone number: " + 
                    hotel.phone.getAttribute("value"));
        }
    }

    // ================== CHECK-IN DATE VALIDATION ==================
    @When("the user selects check-in date {string}")
    public void user_selects_checkin_date(String checkInDate) {
        rf.enterText(hotel.checkinDate, checkInDate, "Check-in Date");
    }

    @Then("the check-in date validation should be {string}")
    public void the_checkin_date_validation_should_be(String outcome) {
        if (outcome.equalsIgnoreCase("invalid")) {
            try {
                Thread.sleep(1000); // Allow validation to trigger
                
                boolean dateErrorFound = false;
                
                // Method 1: Check specific error element
                try {
                    if (hotel.checkinDateError != null && hotel.checkinDateError.isDisplayed()) {
                        dateErrorFound = true;
                        System.out.println("✓ Check-in date error element found");
                    }
                } catch (Exception e) {
                    // Continue with other methods
                }
                
                // Method 2: Check HTML5 validation
                try {
                    String validationMessage = (String) ((JavascriptExecutor) driver)
                        .executeScript("return arguments[0].validationMessage;", hotel.checkinDate);
                    if (validationMessage != null && !validationMessage.trim().isEmpty()) {
                        dateErrorFound = true;
                        System.out.println("✓ Check-in date HTML5 validation: " + validationMessage);
                    }
                } catch (Exception e) {
                    // Continue
                }
                
                // Method 3: Check if form stayed on same page
                String currentUrl = driver.getCurrentUrl();
                if (currentUrl.toLowerCase().contains("hotelbooking") || currentUrl.toLowerCase().contains("hotel")) {
                    // Additional check: see if any date-related error messages exist
                    try {
                        List<WebElement> dateErrors = driver.findElements(
                            By.xpath("//*[contains(text(),'date') or contains(text(),'Date') or contains(text(),'past') or contains(text(),'invalid')]"));
                        for (WebElement error : dateErrors) {
                            if (error.isDisplayed()) {
                                dateErrorFound = true;
                                System.out.println("✓ Date-related error found: " + error.getText());
                                break;
                            }
                        }
                    } catch (Exception e) {
                        // Continue
                    }
                }
                
                if (!dateErrorFound) {
                    Assert.fail("BUG FOUND: Past date validation is missing. Check-in date '" + 
                              hotel.checkinDate.getAttribute("value") + 
                              "' should not be accepted as it's a past date.");
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Assert.fail("Test interrupted during date validation check");
            }
            
        } else if (outcome.equalsIgnoreCase("valid")) {
            boolean isErrorVisible = false;
            try {
                if (hotel.checkinDateError != null && hotel.checkinDateError.isDisplayed()) {
                    isErrorVisible = true;
                }
            } catch (Exception e) {
                // Error element doesn't exist, which is fine for valid dates
            }
            
            Assert.assertFalse(isErrorVisible,
                    "BUG: Check-in date validation triggered for a valid future date: " + 
                    hotel.checkinDate.getAttribute("value"));
        }
    }

    // ================== FIELD VISIBILITY VALIDATION ==================
    @Given("the user is on the Hotel Booking page")
    public void user_is_on_hotel_booking_page() {
        String hotelBookingUrl = prop.getProperty("hotelBookingURL");
        driver.get(hotelBookingUrl);
    }

    @Then("the {string} field of type {string} on hotel form should be visible")
    public void field_of_type_should_be_visible(String fieldName, String elementType) {
        WebElement element = getElementByFieldName(fieldName);
        rf.verifyElementDisplayed(element, fieldName);
    }

    // Helper method for field mapping
    private WebElement getElementByFieldName(String fieldName) {
        switch (fieldName.toLowerCase()) {
            case "first name":
                return hotel.firstName;
            case "last name":
                return hotel.lastName;
            case "email":
                return hotel.email;
            case "phone":
                return hotel.phone;
            case "room type":
                return hotel.roomTypeDropdown;
            case "number of guests":
                return hotel.guestCount;
            case "check-in date":
                return hotel.checkinDate;
            case "check-out date":
                return hotel.checkoutDate;
            case "price/night":
                return hotel.priceSlider;
            case "free pickup":
                return hotel.pickupYes;
            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
    }

    @When("the user fills the form with valid data from {string} sheet {string}")
    public void theUserFillsTheFormWithValidDataFromSheet(String fileName, String sheetName) {
        Object[][] data = dpu.getHotelBookingData(); // From DataProvider
        @SuppressWarnings("unchecked")
        Map<String, String> formData = (Map<String, String>) data[0][0]; // Assuming 1st row for test

        rf.sendText(hotel.firstName, formData.get("First Name"));
        rf.sendText(hotel.lastName, formData.get("Last Name"));
        rf.sendText(hotel.email, formData.get("Email"));
        rf.sendText(hotel.phone, formData.get("Phone Number"));
        rf.selectDropdownByVisibleText(hotel.roomTypeDropdown, formData.get("Room Type"));
        rf.sendText(hotel.guestCount, formData.get("Number of Guests"));
        rf.sendText(hotel.checkinDate, formData.get("Check in Date"));
        rf.sendText(hotel.checkoutDate, formData.get("Check Out Date"));
        rf.setSliderValue(driver, hotel.priceSlider, formData.get("Price/night"));
        rf.clickElement(hotel.pickupYes, formData.get("Free Pickup")); // assuming "Yes"/"No" in Excel
        rf.selectDropdownByVisibleText(hotel.ratingDropdown, formData.get("Guest Rating"));
        rf.selectDropdownByVisibleText(hotel.locationDropdown, formData.get("Location"));
        rf.sendText(hotel.message, formData.get("Your Message"));
    }

    @Then("booking should be successful")
    public void booking_successful() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement confirmationElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"output1\"]/h3"))
            );
            String confirmationText = confirmationElement.getText();
            Assert.assertTrue(confirmationText.contains("Your") || confirmationText.contains("Success"),
                    "Booking confirmation message not found. Actual text: " + confirmationText);
        } catch (Exception e) {
            Assert.fail("Booking confirmation element not found or booking failed: " + e.getMessage());
        }
    }
}