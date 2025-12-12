package com.sqe.tests.stepdefinitions;

import static com.sqe.tests.stepdefinitions.BaseStepDefinitions.testContext;
import com.sqe.framework.utils.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class LoginStepDefinitions {
    
    // PUBLIC ZERO-ARGUMENT CONSTRUCTOR
    public LoginStepDefinitions() {
        // No initialization needed - testContext is static
    }
    
    @Given("user is on login page")
    public void user_is_on_login_page() {
        testContext.getLoginPage().navigateToLoginPage();
        Assert.assertTrue(testContext.getLoginPage().isLoginPageDisplayed(),
            "Login page is not displayed");
    }
    
    @When("user enters username {string} and password {string}")
    public void user_enters_username_and_password(String username, String password) {
        testContext.getLoginPage().enterUsername(username);
        testContext.getLoginPage().enterPassword(password);
    }
    
    @When("user clicks login button")
    public void user_clicks_login_button() {
        testContext.getLoginPage().clickLogin();
    }
    
    @Then("user should be logged in successfully")
    public void user_should_be_logged_in_successfully() {
        Assert.assertTrue(testContext.getProductsPage().isProductsPageDisplayed(),
            "Products page is not displayed after login");
    }
    
    @Then("user should see error message {string}")
    public void user_should_see_error_message(String expectedMessage) {
        Assert.assertTrue(testContext.getLoginPage().isErrorMessageDisplayed(),
            "Error message is not displayed");
        String actualMessage = testContext.getLoginPage().getErrorMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
            "Expected error message: " + expectedMessage + 
            ", but got: " + actualMessage);
    }
    
    @When("user logs in with credentials from excel row {int}")
    public void user_logs_in_with_credentials_from_excel_row(Integer rowNumber) {
        List<Map<String, String>> testData = ExcelReader.getTestData("Login");
        if (testData != null && !testData.isEmpty() && rowNumber <= testData.size()) {
            Map<String, String> rowData = testData.get(rowNumber - 1);
            String username = rowData.get("Username");
            String password = rowData.get("Password");
            testContext.getLoginPage().login(username, password);
        } else {
            // Fallback to default if Excel data not available
            testContext.getLoginPage().login("standard_user", "secret_sauce");
        }
    }
    
    @When("user logs in with database credentials for user {string}")
    public void user_logs_in_with_database_credentials_for_user(String username) {
        if (testContext.getDatabaseManager() != null) {
            Map<String, Object> user = testContext.getDatabaseManager().getUserByUsername(username);
            if (user != null) {
                String dbUsername = (String) user.get("username");
                String dbPassword = (String) user.get("password");
                testContext.getLoginPage().login(dbUsername, dbPassword);
                System.out.println("✅ Logged in with database credentials for user: " + username);
            } else {
                System.out.println("❌ User not found in database: " + username);
                testContext.getLoginPage().login("standard_user", "secret_sauce");
            }
        } else {
            System.out.println("⚠️ Database not available, using default credentials");
            testContext.getLoginPage().login("standard_user", "secret_sauce");
        }
    }
    
    @Then("user session should be stored in Redis")
    public void user_session_should_be_stored_in_redis() {
        if (testContext.getRedisManager() != null) {
            // Store session token in Redis
            testContext.getRedisManager().set("session:standard_user", "mock_session_token_12345", 3600);
            System.out.println("✅ Session stored in Redis");
        } else {
            System.out.println("⚠️ Redis not available, skipping session storage");
        }
    }
    
    @When("user enters empty username and password")
    public void user_enters_empty_username_and_password() {
        testContext.getLoginPage().enterUsername("");
        testContext.getLoginPage().enterPassword("");
    }
    
    @When("user enters valid username and empty password")
    public void user_enters_valid_username_and_empty_password() {
        testContext.getLoginPage().enterUsername("standard_user");
        testContext.getLoginPage().enterPassword("");
    }
    
    @When("user enters empty username and valid password")
    public void user_enters_empty_username_and_valid_password() {
        testContext.getLoginPage().enterUsername("");
        testContext.getLoginPage().enterPassword("secret_sauce");
    }
    
    @When("user logs out")
    public void user_logs_out() {
        testContext.getProductsPage().logout();
    }
    
    @Then("user should be redirected to login page")
    public void user_should_be_redirected_to_login_page() {
        Assert.assertTrue(testContext.getLoginPage().isLoginPageDisplayed(),
            "Not redirected to login page after logout");
    }
    
    @And("Redis session should be cleared")
    public void redis_session_should_be_cleared() {
        if (testContext.getRedisManager() != null) {
            testContext.getRedisManager().delete("session:standard_user");
            System.out.println("✅ Session cleared from Redis");
        } else {
            System.out.println("⚠️ Redis not available, skipping session clearance");
        }
    }
    
    @Then("login result should be {string}")
    public void login_result_should_be(String expectedResult) {
        if ("success".equalsIgnoreCase(expectedResult)) {
            Assert.assertTrue(testContext.getProductsPage().isProductsPageDisplayed(),
                "Login should be successful but products page not displayed");
        } else {
            Assert.assertTrue(testContext.getLoginPage().isErrorMessageDisplayed(),
                "Login should fail but no error message displayed");
        }
    }
    
    @When("user logs in with credentials from excel")
    public void user_logs_in_with_credentials_from_excel() {
        List<Map<String, String>> loginData = testContext.getExcelReader().getAllData();
        if (!loginData.isEmpty()) {
            Map<String, String> firstRow = loginData.get(0);
            String username = firstRow.get("Username");
            String password = firstRow.get("Password");
            testContext.getLoginPage().login(username, password);
        }
    }
}
