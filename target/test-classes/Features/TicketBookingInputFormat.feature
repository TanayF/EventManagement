Feature: Ticket Booking – Input Format Validation

  # ✅ 2. All Fields Empty Validation
  Scenario: Validate booking form submission with all required fields empty
    Given the user is on the Ticket Booking page
    When the user submits the ticket booking form without filling any fields
    Then validation errors should appear for all required fields

  # ✅ 3. One Required Field Empty Validation
  Scenario Outline: Validate required Booking Fields with one field missing
    Given the user is on the Ticket Booking page
    When the user enters "<From>", "<To>", "<Boarding>", "<Dropping>", "<Date>", "<Name>", "<Phone>" in respective fields
    And submits the ticket booking form
    Then validation error should appear for the missing required field

    Examples:
      | From   | To      | Boarding  | Dropping | Date       | Name     | Phone       |
      |        | Bhopal  | BusStop1  | Stop2    | 2024-01-01 | Amit     | 9876543210  |
      | Indore |         | BusStop1  | Stop2    | 2024-01-01 | Amit     | 9876543210  |
      | Ujjain | Dewas   |           | Stop2    | 2024-01-01 | Amit     | 9876543210  |
      | Ujjain | Dewas   | BusStop1  |          | 2024-01-01 | Amit     | 9876543210  |
      | Ujjain | Dewas   | BusStop1  | Stop2    |            | Amit     | 9876543210  |
      | Ujjain | Dewas   | BusStop1  | Stop2    | 2024-01-01 |          | 9876543210  |
      | Ujjain | Dewas   | BusStop1  | Stop2    | 2024-01-01 | Amit     |             |

  # ✅ 4. Default Time Dropdown Value
  Scenario Outline: Validate default value of Time dropdown is displayed
    Given the user is on the Ticket Booking page
    Then the Time dropdown should have default value as "<DefaultTime>"

    Examples:
      | DefaultTime |
      | 06.00AM  |
