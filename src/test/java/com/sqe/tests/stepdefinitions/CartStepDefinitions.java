package com.sqe.tests.stepdefinitions;
import com.sqe.framework.base.TestContext;
import static com.sqe.tests.stepdefinitions.BaseStepDefinitions.testContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.testng.Assert;

public class CartStepDefinitions {
    
    private TestContext testContext;
    
    public CartStepDefinitions() {
        this.testContext = BaseStepDefinitions.testContext;
    }
    
    @Then("cart should be empty")
    public void cart_should_be_empty() {
        boolean isEmpty = testContext.getCartPage().isCartEmpty();
        System.out.println("Cart empty check: " + isEmpty);
        Assert.assertTrue(isEmpty, "Cart is not empty");
    }
    
    @Then("item {string} should be in cart")
    public void item_should_be_in_cart(String itemName) {
        // Wait for cart items to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        boolean isInCart = testContext.getCartPage().isItemInCart(itemName);
        System.out.println("Item '" + itemName + "' in cart: " + isInCart);
        
        // List all items for debugging
        int itemCount = testContext.getCartPage().getCartItemsCount();
        System.out.println("Total items in cart: " + itemCount);
        
        for (int i = 0; i < itemCount; i++) {
            String actualName = testContext.getCartPage().getCartItemName(i);
            System.out.println("  Item " + i + ": " + actualName);
        }
        
        Assert.assertTrue(isInCart, "Item '" + itemName + "' is not in cart");
    }
    
    @Given("user should see {int} items in cart")
    public void user_should_see_items_in_cart(int expectedCount) {
        // Wait for cart to update
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        int actualCount = testContext.getCartPage().getCartItemsCount();
        System.out.println("Expected items in cart: " + expectedCount + ", Actual: " + actualCount);
        Assert.assertEquals(actualCount, expectedCount, 
            "Expected " + expectedCount + " items in cart, but found " + actualCount);
    }
    
    @When("user removes item {int} from cart")
    public void user_removes_item_from_cart(int itemIndex) {
        int actualIndex = itemIndex - 1; // Convert to 0-based
        System.out.println("Removing item at index: " + actualIndex);
        testContext.getCartPage().removeItemFromCart(actualIndex);
        
        // Wait for removal
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @When("user clicks continue shopping")
    public void user_clicks_continue_shopping() {
        testContext.getCartPage().clickContinueShopping();
        
        // Wait for navigation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @When("user proceeds to checkout")
    public void user_proceeds_to_checkout() {
        testContext.getCartPage().proceedToCheckout();
        
        // Wait for checkout page
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Then("checkout information page should be displayed")
    public void checkout_information_page_should_be_displayed() {
        boolean isDisplayed = testContext.getCheckoutPage().isCheckoutStepOneDisplayed();
        System.out.println("Checkout step one displayed: " + isDisplayed);
        Assert.assertTrue(isDisplayed, "Checkout information page is not displayed");
    }
    
    @And("user should see {int} item in cart")
    public void user_should_see_item_in_cart(int expectedCount) {
        user_should_see_items_in_cart(expectedCount);
    }
    
    @When("user removes all items from cart")
    public void user_removes_all_items_from_cart() {
        int itemCount = testContext.getCartPage().getCartItemsCount();
        System.out.println("Removing all " + itemCount + " items from cart");
        
        for (int i = 0; i < itemCount; i++) {
            testContext.getCartPage().removeItemFromCart(0); // Always remove first item
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    @And("cart data is stored in Redis for user {string}")
    public void cart_data_is_stored_in_redis_for_user(String username) {
        if (testContext.getRedisManager() != null) {
            // Store cart items as JSON in Redis
            String cartData = "{\"items\": [\"Sauce Labs Backpack\", \"Sauce Labs Bike Light\"]}";
            testContext.getRedisManager().storeCartData(username, cartData);
            System.out.println("✅ Cart data stored in Redis for user: " + username);
        } else {
            System.out.println("⚠️ Redis not available, skipping cart storage");
        }
    }
    
    @Then("cart data should be retrievable from Redis for user {string}")
    public void cart_data_should_be_retrievable_from_redis_for_user(String username) {
        if (testContext.getRedisManager() != null) {
            String cartData = testContext.getRedisManager().getCartData(username);
            Assert.assertNotNull(cartData, "Cart data should be retrievable from Redis");
            Assert.assertTrue(cartData.contains("items"), "Cart data should contain items");
            System.out.println("✅ Cart data retrieved from Redis: " + cartData);
        } else {
            System.out.println("⚠️ Redis not available, skipping cart retrieval");
        }
    }
}
