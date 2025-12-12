package com.sqe.framework.pages;

import com.sqe.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class ProductsPage extends BasePage {
    
    @FindBy(className = "title")
    private WebElement productsTitle;
    
    @FindBy(className = "inventory_item")
    private List<WebElement> productItems;
    
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
    
    public ProductsPage() {
        PageFactory.initElements(driver, this);
    }
    
    public boolean isProductsPageDisplayed() {
        try {
            return productsTitle.isDisplayed() && 
                   productsTitle.getText().equals("Products");
        } catch (Exception e) {
            return false;
        }
    }
    
    public int getProductCount() {
        return productItems.size();
    }
    
    public void addProductToCart(int index) {
        if (index >= 0 && index < addToCartButtons.size()) {
            addToCartButtons.get(index).click();
        }
    }
    
    public void removeProductFromCart(int index) {
        if (index >= 0 && index < removeButtons.size()) {
            removeButtons.get(index).click();
        }
    }
    
    public boolean isProductAddedToCart(int index) {
        if (index >= 0 && index < removeButtons.size()) {
            return removeButtons.get(index).isDisplayed();
        }
        return false;
    }
    
    public void clickCartIcon() {
        cartIcon.click();
    }
    
    public int getCartItemCount() {
        try {
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }
    
    // Add the missing logout method
    public void logout() {
        try {
            menuButton.click();
            Thread.sleep(1000); // Wait for menu to open
            logoutLink.click();
        } catch (Exception e) {
            System.out.println("Logout failed: " + e.getMessage());
        }
    }
}
