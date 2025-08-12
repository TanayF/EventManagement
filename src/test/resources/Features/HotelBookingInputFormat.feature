Feature: Hotel Booking – Input Format Validation


  Scenario Outline: Check for valid and invalid phone number format
    Given the user navigates to the Hotel Booking form
    When the user enters "<Phone>" in the Phone Number field
    And submits the form
    Then the phone number validation should be "<Outcome>"

    Examples:
      | Phone      | Outcome |
      | 9876543210 | valid   |
      | abce123    | invalid |

  Scenario Outline: Validate past check-in date
    Given the user navigates to the Hotel Booking form
    When the user selects check-in date "<CheckInDate>"
    And submits the form
    Then the check-in date validation should be "<Outcome>"

    Examples:
      | CheckInDate | Outcome  |
      | 2024-01-01  | invalid  |
      | 2025-08-01  | valid    |


  Scenario Outline: Validate visibility of required fields on Hotel Booking page
    Given the user is on the Hotel Booking page
    Then the "<FieldName>" field of type "<ElementType>" on hotel form should be visible

    Examples:
      | FieldName          | ElementType |
      | First Name         | input       |
      | Last Name          | input       |
      | Email              | input       |
      | Phone              | input       |
      | Room Type          | dropdown    |
      | Number of Guests   | input       |
      | Check-in Date      | input       |
      | Check-out Date     | input       |
      | Price/Night        | slider      |
      | Free Pickup        | radio       |

  Scenario: Submit the Hotel Booking form with all valid data from Excel
    Given the user is on the Hotel Booking page
    When the user fills the form with valid data from "src/test/resources/testdata/HotelBookingData.xlsx" sheet "Sheet1"
    And submits the form
    Then booking should be successful