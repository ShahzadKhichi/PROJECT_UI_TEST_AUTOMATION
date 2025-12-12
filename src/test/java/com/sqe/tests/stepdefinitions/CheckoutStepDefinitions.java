package com.sqe.tests.stepdefinitions;
import com.sqe.framework.base.TestContext;
import static com.sqe.tests.stepdefinitions.BaseStepDefinitions.testContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.testng.Assert;

public class CheckoutStepDefinitions {
    
    private TestContext testContext;
    
    // PUBLIC ZERO-ARGUMENT CONSTRUCTOR
    public CheckoutStepDefinitions() {
        this.testContext = BaseStepDefinitions.testContext;
    }
    
    @When("user fills checkout form with {string} {string} {string}")
    public void user_fills_checkout_form_with(String firstName, String lastName, String postalCode) {
        testContext.getCheckoutPage().fillCheckoutForm(firstName, lastName, postalCode);
    }
    
    @When("user continues to checkout overview")
    public void user_continues_to_checkout_overview() {
        testContext.getCheckoutPage().clickContinue();
    }
    
    @When("user completes the purchase")
    public void user_completes_the_purchase() {
        testContext.getCheckoutPage().clickFinish();
    }
    
    @Then("order confirmation should be displayed")
    public void order_confirmation_should_be_displayed() {
        Assert.assertTrue(testContext.getCheckoutPage().isOrderCompleteDisplayed(),
            "Order confirmation is not displayed");
    }
    
    @Then("user should see thank you message")
    public void user_should_see_thank_you_message() {
        String thankYouMessage = testContext.getCheckoutPage().getThankYouMessage();
        Assert.assertEquals(thankYouMessage, "THANK YOU FOR YOUR ORDER",
            "Thank you message is incorrect. Got: " + thankYouMessage);
    }
    
    @Then("error message {string} should be displayed")
    public void error_message_should_be_displayed(String expectedError) {
        Assert.assertTrue(testContext.getCheckoutPage().isErrorMessageDisplayed(),
            "Error message is not displayed");
        String actualError = testContext.getCheckoutPage().getErrorMessage();
        Assert.assertTrue(actualError.contains(expectedError),
            "Expected error: " + expectedError + ", but got: " + actualError);
    }
    
    @Then("checkout result should be {string}")
    public void checkout_result_should_be(String result) {
        if ("success".equalsIgnoreCase(result)) {
            Assert.assertTrue(testContext.getCheckoutPage().isCheckoutStepTwoDisplayed(),
                "Checkout step two not displayed for successful checkout");
        } else {
            Assert.assertTrue(testContext.getCheckoutPage().isErrorMessageDisplayed(),
                "Error message not displayed for failed checkout");
        }
    }
    
    @When("user cancels checkout")
    public void user_cancels_checkout() {
        testContext.getCheckoutPage().clickCancel();
    }
    
    @Then("order summary should display correct totals")
    public void order_summary_should_display_correct_totals() {
        String itemTotal = testContext.getCheckoutPage().getItemTotal();
        String tax = testContext.getCheckoutPage().getTax();
        String total = testContext.getCheckoutPage().getTotal();
        
        Assert.assertNotNull(itemTotal, "Item total is not displayed");
        Assert.assertNotNull(tax, "Tax is not displayed");
        Assert.assertNotNull(total, "Total is not displayed");
    }
    
    @And("user should see order dispatch message")
    public void user_should_see_order_dispatch_message() {
        String dispatchMessage = testContext.getCheckoutPage().getOrderDispatchMessage();
        Assert.assertNotNull(dispatchMessage, "Order dispatch message is not displayed");
        Assert.assertFalse(dispatchMessage.isEmpty(), "Order dispatch message is empty");
    }
    
    @When("user goes back home from order confirmation")
    public void user_goes_back_home_from_order_confirmation() {
        testContext.getCheckoutPage().clickBackHome();
    }
    
    @Then("user should be redirected to products page")
    public void user_should_be_redirected_to_products_page() {
        Assert.assertTrue(testContext.getProductsPage().isProductsPageDisplayed(),
            "Not redirected to products page after order completion");
    }
}
