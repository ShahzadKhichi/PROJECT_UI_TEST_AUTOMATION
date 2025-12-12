Feature: Common UI Validations
  As a user
  I want to verify UI elements
  So that I can ensure application quality

  @UI @Smoke @project20
  Scenario: Verify page title and URL
    Given user is on login page
    Then page title should be "Swag Labs"
    And URL should contain "saucedemo.com"

  @UI @project20
  Scenario: Verify broken links
    Given user is logged in as "standard_user" with password "secret_sauce"
    Then all links on page should be valid

  @UI
  Scenario: Verify images are displayed
    Given user is logged in as "standard_user" with password "secret_sauce"
    Then all images on page should be loaded

  @UI @project20
  Scenario: Verify footer content
    Given user is logged in as "standard_user" with password "secret_sauce"
    Then footer should be displayed
    And footer should contain text "© 2023 Sauce Labs. All Rights Reserved"

  @UI
  Scenario: Verify social media links
    Given user is logged in as "standard_user" with password "secret_sauce"
    Then social media links should be present
    And social media links should be clickable

  @UI @Navigation @project20
  Scenario: Verify browser navigation
    Given user is logged in as "standard_user" with password "secret_sauce"
    When user navigates back
    Then user should be on login page
    When user navigates forward
    Then products page should be displayed again

  @UI @project20
  Scenario: Verify menu functionality
    Given user is logged in as "standard_user" with password "secret_sauce"
    When user opens menu
    Then all menu options should be visible
    When user closes menu
    Then menu should not be visible