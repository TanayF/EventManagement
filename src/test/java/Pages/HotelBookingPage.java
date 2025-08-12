package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Utilities.ReusableFunctions;

public class HotelBookingPage {

    WebDriver driver;
    ReusableFunctions rf;

    // Constructor
    public HotelBookingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        rf = new ReusableFunctions(driver);
    }

    // --- Form Fields ---
    
    @FindBy(id = "phone-error")
    public WebElement phoneErrorMessage;

    @FindBy(xpath = "//input[@id='firstName']") public WebElement firstName;
    @FindBy(xpath = "//input[@id='lastName']") public WebElement lastName;
    @FindBy(xpath = "//input[@id='email']") public WebElement email;
    @FindBy(xpath = "//input[@id='phone']") public WebElement phone;

    @FindBy(xpath = "//select[@name='Room Type']") public WebElement roomTypeDropdown;
    @FindBy(xpath = "//input[@id='guest']") public WebElement guestCount;
    @FindBy(xpath = "//input[@id='checkIn']") public WebElement checkinDate;
    @FindBy(xpath = "//input[@id='checkOut']") public WebElement checkoutDate;

    @FindBy(xpath = "//input[@id='myRange']") public WebElement priceSlider;
    @FindBy(xpath = "//input[@id='yes']") public WebElement pickupYes;
    @FindBy(xpath = "//li[10]//select[1]") public WebElement ratingDropdown;
    @FindBy(xpath = "//li[11]//select[1]") public WebElement locationDropdown;
    @FindBy(xpath = "//textarea[@id='message']") public WebElement message;
    @FindBy(xpath = "//input[@id='submit']") public WebElement submitButton;

    // --- Result Table after submission ---
    @FindBy(xpath = "//td[text()='First Name']/following-sibling::td") public WebElement resultFirstName;
    @FindBy(xpath = "//td[text()='Last Name']/following-sibling::td") public WebElement resultLastName;
    @FindBy(xpath = "//td[text()='Email']/following-sibling::td") public WebElement resultEmail;
    @FindBy(xpath = "//td[text()='Phone']/following-sibling::td") public WebElement resultPhone;
    @FindBy(xpath = "//td[text()='Room Type']/following-sibling::td") public WebElement resultRoomType;
    @FindBy(xpath = "//td[text()='Number of Guests']/following-sibling::td") public WebElement resultGuests;
    @FindBy(xpath = "//td[text()='CheckIn']/following-sibling::td") public WebElement resultCheckIn;
    @FindBy(xpath = "//td[text()='CheckOut']/following-sibling::td") public WebElement resultCheckOut;
    @FindBy(xpath = "//td[text()='Price']/following-sibling::td") public WebElement resultPrice;
    @FindBy(xpath = "//td[text()='Pickup']/following-sibling::td") public WebElement resultPickup;
    @FindBy(xpath = "//td[text()='Rating']/following-sibling::td") public WebElement resultRating;
    @FindBy(xpath = "//td[text()='location']/following-sibling::td") public WebElement resultLocation;
    @FindBy(xpath = "//td[text()='Message']/following-sibling::td") public WebElement resultMessage;
    @FindBy(id = "checkInError") // replace with correct locator if different
    public WebElement checkinDateError;
    // --- Actions ---

    public void fillBookingForm(String fullName, String mail, String mobile, String room, String guest, String inDate, String outDate, String rate, String loc, String msg) {
        rf.enterText(lastName, fullName, "Full Name");
        rf.enterText(email, mail, "Email");
        rf.enterText(phone, mobile, "Phone Number");
        rf.enterText(roomTypeDropdown, room, "Room Type");
        rf.enterText(guestCount, guest, "Guest Count");
        rf.enterText(checkinDate, inDate, "Check-in Date");
        rf.enterText(checkoutDate, outDate, "Check-out Date");
        rf.enterText(ratingDropdown, rate, "Rating");
        rf.enterText(locationDropdown, loc, "Location");
        rf.enterText(message, msg, "Message");
    }

    public void submitForm() {
        rf.clickElement(submitButton, "Submit");
    }

    // --- Result Getters using ReusableFunctions ---

    public String getSubmittedName() {
        return rf.getElementText(resultFirstName, "Result First Name") + " " +
               rf.getElementText(resultLastName, "Result Last Name");
    }

    public String getSubmittedEmail() {
        return rf.getElementText(resultEmail, "Result Email");
    }

    public String getSubmittedPhone() {
        return rf.getElementText(resultPhone, "Result Phone");
    }

    public String getSubmittedRoomType() {
        return rf.getElementText(resultRoomType, "Result Room Type");
    }

    public String getSubmittedGuestCount() {
        return rf.getElementText(resultGuests, "Result Guest Count");
    }

    public String getSubmittedCheckInDate() {
        return rf.getElementText(resultCheckIn, "Result Check-In");
    }

    public String getSubmittedCheckOutDate() {
        return rf.getElementText(resultCheckOut, "Result Check-Out");
    }

    public String getSubmittedPrice() {
        return rf.getElementText(resultPrice, "Result Price");
    }

    public String getSubmittedPickup() {
        return rf.getElementText(resultPickup, "Result Pickup");
    }

    public String getSubmittedRating() {
        return rf.getElementText(resultRating, "Result Rating");
    }

    public String getSubmittedLocation() {
        return rf.getElementText(resultLocation, "Result Location");
    }

    public String getSubmittedMessage() {
        return rf.getElementText(resultMessage, "Result Message");
    }
}
