package Stepdefinitions;

import Pages.TicketBookingPage;
import Utilities.ReusableFunctions;
import io.cucumber.java.en.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import testRunner.TestRunner;
import java.util.Properties;

public class TicketBookingSteps {

    private final WebDriver driver = TestRunner.driver;
    private final Properties prop = TestRunner.prop;

    TicketBookingPage ticket = new TicketBookingPage(driver);
    ReusableFunctions rf = new ReusableFunctions(driver);

    @Given("the user is on the Ticket Booking page")
    public void user_on_ticket_booking_page() {
        String ticketBookingUrl = prop.getProperty("ticketBookingURL");
        driver.get(ticketBookingUrl);
        System.out.println("Navigated to: " + ticketBookingUrl);
        rf.verifyTitle("TicketBooking");
    }

    @When("the user enters {string}, {string}, {string}, {string}, {string}, {string}, {string} in respective fields")
    public void user_enters_invalid_or_blank_data(String from, String to, String board, String drop, String date, String name, String phone) {
        rf.sendText(ticket.fromField, from);
        rf.sendText(ticket.toField, to);
        rf.sendText(ticket.boardingPointField, board);
        rf.sendText(ticket.droppingPointField, drop);
        rf.sendText(ticket.dateField, date);
        rf.sendText(ticket.nameField, name);
        rf.sendText(ticket.phoneField, phone);
    }


    @And("submits the ticket booking form")
    public void submit_ticket_booking_form() {
        rf.clickElement(ticket.submitButton, "Submit Button");
    }


    @When("the user submits the ticket booking form without filling any fields")
    public void submit_ticket_form_without_filling_fields() {
        ticket.fromField.clear();
        ticket.toField.clear();
        ticket.boardingPointField.clear();
        ticket.droppingPointField.clear();
        ticket.dateField.clear();
        rf.clickElement(ticket.submitButton, "Submit Button");
    }

    @Then("validation errors should appear for all required fields")
    public void all_required_validation_errors_should_appear() {
        Assert.assertFalse(ticket.fromField.getAttribute("validationMessage").isEmpty(), "From field validation missing.");
        Assert.assertFalse(ticket.toField.getAttribute("validationMessage").isEmpty(), "To field validation missing.");
        Assert.assertFalse(ticket.boardingPointField.getAttribute("validationMessage").isEmpty(), "Boarding field validation missing.");
        Assert.assertFalse(ticket.droppingPointField.getAttribute("validationMessage").isEmpty(), "Dropping field validation missing.");
        Assert.assertFalse(ticket.dateField.getAttribute("validationMessage").isEmpty(), "Date field validation missing.");
        Assert.assertFalse(ticket.nameField.getAttribute("validationMessage").isEmpty(), "Name field validation missing.");
        Assert.assertFalse(ticket.phoneField.getAttribute("validationMessage").isEmpty(), "Phone field validation missing.");
    }

    @Then("the Time dropdown should have default value as {string}")
    public void time_dropdown_should_have_default_value(String expectedValue) {
        WebElement dropdown = ticket.timeDropdown;
        String selectedText = rf.getSelectedDropdownValue(dropdown);
        Assert.assertEquals(selectedText, expectedValue, "Default dropdown value mismatch.");
    }

    @Then("validation error should appear for the missing required field")
    public void validationErrorShouldAppearForTheMissingRequiredField() {
    }
}
