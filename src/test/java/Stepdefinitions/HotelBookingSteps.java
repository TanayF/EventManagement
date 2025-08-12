package Stepdefinitions;

import Pages.HotelBookingPage;
import Utilities.DataProviderUtil;
import Utilities.ExcelReader;
import Utilities.ReusableFunctions;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import testRunner.TestRunner;

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
        if (outcome.equalsIgnoreCase("invalid")) {
            try {
                Thread.sleep(500);
                Assert.assertTrue(hotel.phoneErrorMessage.isDisplayed(),
                        "BUG FOUND: No validation message appeared for an invalid phone number.");
            } catch (Exception e) {
                Assert.fail("BUG FOUND: The phone number error message element could not be found. The form likely submitted incorrectly.");
            }
        } else if (outcome.equalsIgnoreCase("valid")) {
            boolean isErrorVisible;
            try {
                isErrorVisible = hotel.phoneErrorMessage.isDisplayed();
            } catch (org.openqa.selenium.NoSuchElementException e) {
                isErrorVisible = false;
            }
            Assert.assertFalse(isErrorVisible,
                    "BUG: An error message was displayed for a VALID phone number.");
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
                Thread.sleep(500);
                Assert.assertTrue(hotel.checkinDateError.isDisplayed(),
                        "BUG FOUND: The check-in date error message element could not be found. " +
                                "The form likely allows past dates without validation. Functional bug suspected.");
            } catch (Exception e) {
                Assert.fail("BUG FOUND: The check-in date error element was not found. " +
                        "This is a functional bug (no past date validation).");
            }
        } else if (outcome.equalsIgnoreCase("valid")) {
            boolean isErrorVisible;
            try {
                isErrorVisible = hotel.checkinDateError.isDisplayed();
            } catch (org.openqa.selenium.NoSuchElementException e) {
                isErrorVisible = false;
            }
            Assert.assertFalse(isErrorVisible,
                    "BUG: Check-in date validation triggered for a valid date.");
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
        rf.verifyElementDisplayed(element,fieldName);
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
        rf.setSliderValue(driver,hotel.priceSlider, formData.get("Price/night"));
        rf.clickElement(hotel.pickupYes, formData.get("Free Pickup")); // assuming "Yes"/"No" in Excel
        rf.selectDropdownByVisibleText(hotel.ratingDropdown, formData.get("Guest Rating"));
        rf.selectDropdownByVisibleText(hotel.locationDropdown, formData.get("Location"));
        rf.sendText(hotel.message, formData.get("Your Message"));
    }

    @Then("booking should be successful")
    public void booking_successful() {
        String confirmationText = driver.findElement(By.xpath("//*[@id=\"output1\"]/h3")).getText();  // You must implement this method
        Assert.assertTrue(confirmationText.contains("Your") || confirmationText.contains("Success"),
                "Booking confirmation message not found.");
    }
}