Feature: Login Functionality
  As a user
  I want to login to the application
  So that I can access the products

  Background:
    Given user is on login page

  @Login @Smoke @project20
  Scenario: Successful login with valid credentials
    When user enters username "standard_user" and password "secret_sauce"
    And user clicks login button
    Then user should be logged in successfully
    And user session should be stored in Redis

  @Login @project20
  Scenario: Login with invalid credentials
    When user enters username "invalid_user" and password "wrong_password"
    And user clicks login button
    Then user should see error message "Username and password do not match"

  @Login @project20
  Scenario: Login with empty fields
    When user enters empty username and password
    And user clicks login button
    Then user should see error message "Username is required"

  @Login @DataDriven
  Scenario Outline: Login with multiple users
    When user enters username "<username>" and password "<password>"
    And user clicks login button
    Then login result should be "<status>"

    Examples:
      | username        | password       | status  |
      | standard_user   | secret_sauce   | success |
      | locked_out_user | secret_sauce   | fail    |
      | problem_user    | secret_sauce   | success |
      | performance_glitch_user | secret_sauce | success |

  @Login @Excel @project20
  Scenario: Login with credentials from Excel
    When user logs in with credentials from excel row 1
    Then user should be logged in successfully

@Login @Database
  Scenario: Login with credentials from Database
    When user logs in with database credentials for user "standard_user"
    Then user should be logged in successfully

  @Login @Logout @project20
  Scenario: Logout functionality
    Given user is logged in as "standard_user" with password "secret_sauce"
    When user logs out
    Then user should be redirected to login page
    And Redis session should be cleared
  @Login @DataDriven
  Scenario Outline: Data-driven login with Excel data
    When user enters username "<username>" and password "<password>"
    And user clicks login button
    Then login result should be "<result>"
    
    Examples:
      | username        | password       | result  |
      | standard_user   | secret_sauce   | success |
      | standard_user   | wrong_password | fail    |
      | invalid_user    | secret_sauce   | fail    |
      | ""              | secret_sauce   | fail    |
      | standard_user   | ""             | fail    |

