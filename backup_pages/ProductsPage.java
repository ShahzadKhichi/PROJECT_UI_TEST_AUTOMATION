
package com.sqe.framework.pages;

import com.sqe.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductsPage extends BasePage {
    
    // Updated locators for SauceDemo
    @FindBy(className = "title")
    private WebElement productsTitle;
    
    @FindBy(className = "inventory_item")
    private List<WebElement> productItems;
    
    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;
    
    @FindBy(css = ".btn.btn_primary.btn_small.btn_inventory")
    private List<WebElement> addToCartButtons;
    
    @FindBy(css = ".btn.btn_secondary.btn_small.btn_inventory")
    private List<WebElement> removeButtons;
    
    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;
    
    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;
    
    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;
    
    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;
    
    // Constructor
    public ProductsPage() {
        PageFactory.initElements(driver, this);
    }
    
    // Page Actions
    public boolean isProductsPageDisplayed() {
        try {
            return isDisplayed(productsTitle) && 
                   getText(productsTitle).equals("Products");
        } catch (Exception e) {
            System.out.println("Products page not displayed: " + e.getMessage());
            return false;
        }
    }
    
    public int getProductCount() {
        return productItems.size();
    }
    
    public void addProductToCart(int index) {
        if (index < addToCartButtons.size()) {
            click(addToCartButtons.get(index));
        }
    }
    
    public void removeProductFromCart(int index) {
        if (index < removeButtons.size()) {
            click(removeButtons.get(index));
        }
    }
    
    public boolean isProductAddedToCart(int index) {
        if (index < removeButtons.size()) {
            return removeButtons.get(index).isDisplayed();
        }
        return false;
    }
    
    public void clickCartIcon() {
        click(cartIcon);
    }
    
    public int getCartItemCount() {
        try {
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }
    
    public void logout() {
        click(menuButton);
        waitForVisibility(logoutLink);
        click(logoutLink);
    }
}
