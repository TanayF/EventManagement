package Pages;

import Utilities.ReusableFunctions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object Model for Ticket Booking Form
 */
public class TicketBookingPage {

    WebDriver driver;
    ReusableFunctions rf;

    // --- Constructor ---
    public TicketBookingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        rf = new ReusableFunctions(driver);
    }

    // --- Booking Details Section ---
    @FindBy(xpath="//input[@id='from']") public WebElement fromField;
    @FindBy(xpath="//input[@id='to']") public WebElement toField;
    @FindBy(xpath="//input[@id='boarding']") public WebElement boardingPointField;
    @FindBy(xpath="//input[@id='dropping']") public WebElement droppingPointField;
    @FindBy(xpath="//input[@id='date']") public WebElement dateField;
    @FindBy(xpath="//select[@id='Time']") public WebElement timeDropdown;
    @FindBy(xpath="//select[@id='bus']") public WebElement busTypeDropdown;

    // --- Personal Details Section ---
    @FindBy(xpath="//input[@id='name']") public WebElement nameField;
    @FindBy(xpath="//input[@id='phone']") public WebElement phoneField;
    @FindBy(xpath="//input[@id='male']") public WebElement genderMale;
    @FindBy(xpath="//input[@id='female']") public WebElement genderFemale;
    @FindBy(xpath="//input[@id='other']") public WebElement genderOther;
    @FindBy(xpath="//select[@id='country']") public WebElement countryDropdown;
    @FindBy(xpath="//textarea[@id='address']") public WebElement addressField;

    // --- Submit Button ---
    @FindBy(xpath = "//button[text()='Submit']") public WebElement submitButton;

    // --- Result Fields after Submission ---
    @FindBy(xpath = "//td[text()='From']/following-sibling::td") public WebElement resultFrom;
    @FindBy(xpath = "//td[text()='TO']/following-sibling::td") public WebElement resultTo;
    @FindBy(xpath = "//td[text()='Boarding']/following-sibling::td") public WebElement resultBoarding;
    @FindBy(xpath = "//td[text()='Dropping']/following-sibling::td") public WebElement resultDropping;
    @FindBy(xpath = "//td[text()='Date']/following-sibling::td") public WebElement resultDate;
    @FindBy(xpath = "//td[text()='Time']/following-sibling::td") public WebElement resultTime;
    @FindBy(xpath = "//td[text()='BusType']/following-sibling::td") public WebElement resultBusType;
    @FindBy(xpath = "//td[text()='Name']/following-sibling::td") public WebElement resultName;
    @FindBy(xpath = "//td[text()='Phone']/following-sibling::td") public WebElement resultPhone;
    @FindBy(xpath = "//td[text()='Gender']/following-sibling::td") public WebElement resultGender;
    @FindBy(xpath = "//td[text()='Country']/following-sibling::td") public WebElement resultCountry;
    @FindBy(xpath = "//td[text()='Address']/following-sibling::td") public WebElement resultAddress;

    // --- Form Filling Actions ---

    public void fillBookingDetails(String from, String to, String board, String drop, String date, String time, String type) {
        rf.enterText(fromField, from, "From");
        rf.enterText(toField, to, "To");
        rf.enterText(boardingPointField, board, "Boarding Point");
        rf.enterText(droppingPointField, drop, "Dropping Point");
        rf.enterText(dateField, date, "Date");
        rf.enterText(timeDropdown, time, "Time");
        rf.enterText(busTypeDropdown, type, "Bus Type");
    }

    public void fillPersonalDetails(String name, String phone, String gender, String country, String address) {
        rf.enterText(nameField, name, "Name");
        rf.enterText(phoneField, phone, "Phone");

        if (gender.equalsIgnoreCase("male")) {
            rf.clickElement(genderMale, "Gender Male");
        } else if (gender.equalsIgnoreCase("female")) {
            rf.clickElement(genderFemale, "Gender Female");
        } else {
            rf.clickElement(genderOther, "Gender Other");
        }

        rf.enterText(countryDropdown, country, "Country");
        rf.enterText(addressField, address, "Address");
    }

    public void submitForm() {
        rf.clickElement(submitButton, "Submit Button");
    }

    // --- Result Getters Using Reusable Functions ---

    public String getSubmittedFrom() {
        return rf.getElementText(resultFrom, "Result From");
    }

    public String getSubmittedTo() {
        return rf.getElementText(resultTo, "Result To");
    }

    public String getSubmittedBoarding() {
        return rf.getElementText(resultBoarding, "Result Boarding");
    }

    public String getSubmittedDropping() {
        return rf.getElementText(resultDropping, "Result Dropping");
    }

    public String getSubmittedDate() {
        return rf.getElementText(resultDate, "Result Date");
    }

    public String getSubmittedTime() {
        return rf.getElementText(resultTime, "Result Time");
    }

    public String getSubmittedBusType() {
        return rf.getElementText(resultBusType, "Result Bus Type");
    }

    public String getSubmittedName() {
        return rf.getElementText(resultName, "Result Name");
    }

    public String getSubmittedPhone() {
        return rf.getElementText(resultPhone, "Result Phone");
    }

    public String getSubmittedGender() {
        return rf.getElementText(resultGender, "Result Gender");
    }

    public String getSubmittedCountry() {
        return rf.getElementText(resultCountry, "Result Country");
    }

    public String getSubmittedAddress() {
        return rf.getElementText(resultAddress, "Result Address");
    }
}
