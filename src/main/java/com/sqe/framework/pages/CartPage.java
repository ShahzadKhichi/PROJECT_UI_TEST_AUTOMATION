package com.sqe.framework.pages;

import com.sqe.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class CartPage extends BasePage {

    // Correct locators for SauceDemo cart page
    @FindBy(className = "title")
    private WebElement cartTitle;
    
    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;
    
    @FindBy(className = "inventory_item_name")
    private List<WebElement> cartItemNames;
    
    @FindBy(className = "inventory_item_price")
    private List<WebElement> cartItemPrices;
    
    @FindBy(className = "cart_quantity")
    private List<WebElement> cartQuantities;
    
    @FindBy(xpath = "//button[contains(text(), 'Remove')]")
    private List<WebElement> removeButtons;
    
    @FindBy(id = "checkout")
    private WebElement checkoutButton;
    
    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;
    
    // Constructor
    public CartPage() {
        PageFactory.initElements(driver, this);
    }
    
    // Page Actions - FIXED
    public boolean isCartPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("cart"));
            boolean titleVisible = wait.until(ExpectedConditions.visibilityOf(cartTitle)).isDisplayed();
            String titleText = cartTitle.getText();
            System.out.println("Cart page title: " + titleText);
            return titleVisible && (titleText.equalsIgnoreCase("Your Cart") || 
                                  titleText.contains("Cart"));
        } catch (Exception e) {
            System.out.println("Cart page check failed: " + e.getMessage());
            return false;
        }
    }
    
    public int getCartItemsCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(cartItems));
            return cartItems.size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    public String getCartItemName(int index) {
        if (index >= 0 && index < cartItemNames.size()) {
            return cartItemNames.get(index).getText();
        }
        return "";
    }
    
    public String getCartItemPrice(int index) {
        if (index >= 0 && index < cartItemPrices.size()) {
            return cartItemPrices.get(index).getText();
        }
        return "";
    }
    
    public String getCartItemQuantity(int index) {
        if (index >= 0 && index < cartQuantities.size()) {
            return cartQuantities.get(index).getText();
        }
        return "";
    }
    
    public void removeItemFromCart(int index) {
        if (index >= 0 && index < removeButtons.size()) {
            wait.until(ExpectedConditions.elementToBeClickable(removeButtons.get(index)));
            click(removeButtons.get(index));
        }
    }
    
    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        click(checkoutButton);
    }
    
    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
        click(continueShoppingButton);
    }
    
    public boolean isCartEmpty() {
        try {
            // Check if cart items exist
            return cartItems.size() == 0;
        } catch (Exception e) {
            return true;
        }
    }
    
    public boolean isItemInCart(String itemName) {
        try {
            for (WebElement itemNameElement : cartItemNames) {
                if (itemNameElement.getText().equalsIgnoreCase(itemName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void proceedToCheckout() {
        clickCheckout();
    }
}
