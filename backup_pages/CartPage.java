package com.sqe.framework.pages;

import com.sqe.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends BasePage {

    // Locators
    @FindBy(className = "subheader")
    private WebElement cartTitle;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> cartItemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> cartItemPrices;

    @FindBy(className = "cart_quantity")
    private List<WebElement> cartQuantities;

    @FindBy(css = ".btn_secondary.cart_button")
    private List<WebElement> removeButtons;

    @FindBy(css = ".btn_action.checkout_button")
    private WebElement checkoutButton;

    @FindBy(css = ".btn_secondary")
    private WebElement continueShoppingButton;

    @FindBy(className = "cart_footer")
    private WebElement cartFooter;

    // Constructor
    public CartPage() {
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isCartPageDisplayed() {
        return isDisplayed(cartTitle) && getText(cartTitle).equals("Your Cart");
    }

    public int getCartItemsCount() {
        return cartItems.size();
    }

    public String getCartItemName(int index) {
        return cartItemNames.get(index).getText();
    }

    public String getCartItemPrice(int index) {
        return cartItemPrices.get(index).getText();
    }

    public String getCartItemQuantity(int index) {
        return cartQuantities.get(index).getText();
    }

    public void removeItemFromCart(int index) {
        click(removeButtons.get(index));
    }

    public void clickCheckout() {
        click(checkoutButton);
    }

    public void clickContinueShopping() {
        click(continueShoppingButton);
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }

    public boolean isItemInCart(String itemName) {
        for (WebElement itemNameElement : cartItemNames) {
            if (itemNameElement.getText().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public void proceedToCheckout() {
        clickCheckout();
    }
}