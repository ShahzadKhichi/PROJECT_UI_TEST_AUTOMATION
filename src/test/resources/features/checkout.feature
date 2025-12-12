Feature: Checkout Functionality
  As a user with items in cart
  I want to complete the checkout process
  So that I can purchase items

  Background:
    Given user is logged in as "standard_user" with password "secret_sauce"
    And user adds product 1 to cart
    And user clicks on cart icon

  @Checkout @Smoke
  Scenario: Complete checkout with valid information
    When user proceeds to checkout
    And user fills checkout form with "John" "Doe" "12345"
    And user continues to checkout overview
    And user completes the purchase
    Then order confirmation should be displayed
    And user should see thank you message

  @Checkout @project20
  Scenario: Checkout with empty first name
    When user proceeds to checkout
    And user fills checkout form with "" "Doe" "12345"
    And user continues to checkout overview
    Then error message "First Name is required" should be displayed

  @Checkout @project20
  Scenario: Checkout with empty last name
    When user proceeds to checkout
    And user fills checkout form with "John" "" "12345"
    And user continues to checkout overview
    Then error message "Last Name is required" should be displayed

  @Checkout @project20
  Scenario: Checkout with empty postal code
    When user proceeds to checkout
    And user fills checkout form with "John" "Doe" ""
    And user continues to checkout overview
    Then error message "Postal Code is required" should be displayed

  @Checkout @DataDriven
  Scenario Outline: Checkout with different postal code formats
    When user proceeds to checkout
    And user fills checkout form with "John" "Doe" "<postalCode>"
    And user continues to checkout overview
    Then checkout result should be "<result>"

    Examples:
      | postalCode | result  |
      | 12345      | success |
      | ABC12      | success |
      | 1234       | success |
      | 123456     | success |

  @Checkout @project20
  Scenario: Cancel checkout process
    When user proceeds to checkout
    And user cancels checkout
    Then cart page should be displayed

  @Checkout @project20
  Scenario: Verify order summary
    When user proceeds to checkout
    And user fills checkout form with "John" "Doe" "12345"
    And user continues to checkout overview
    Then order summary should display correct totals
  @Checkout @DataDriven
  Scenario Outline: Checkout with various user data
    When user proceeds to checkout
    And user fills checkout form with "<firstName>" "<lastName>" "<postalCode>"
    And user continues to checkout overview
    Then checkout should be "<result>"
    
    Examples:
      | firstName | lastName | postalCode | result  |
      | John      | Doe      | 12345      | success |
      | Jane      | Smith    | A1B2C3     | success |
      | Alice     | Johnson  | 98765-4321 | success |
      | Bob       | Brown    | ""         | fail    |
      | ""        | Wilson   | 54321      | fail    |

