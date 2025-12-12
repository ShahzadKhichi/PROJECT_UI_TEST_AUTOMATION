package com.sqe.framework.pages;

import com.sqe.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends BasePage {

    // Checkout Step One Locators
    @FindBy(className = "subheader")
    private WebElement checkoutTitle;

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(css = ".btn_primary.cart_button")
    private WebElement continueButton;

    @FindBy(css = ".btn_secondary.cart_button")
    private WebElement cancelButton;

    @FindBy(css = ".error-message-container h3")
    private WebElement errorMessage;

    // Checkout Step Two Locators
    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(css = ".btn_action.cart_button")
    private WebElement finishButton;

    @FindBy(css = ".cart_cancel_link.btn_secondary")
    private WebElement cancelCheckoutButton;

    // Checkout Complete Locators
    @FindBy(className = "complete-header")
    private WebElement thankYouMessage;

    @FindBy(className = "complete-text")
    private WebElement orderDispatchMessage;

    @FindBy(css = ".btn_primary")
    private WebElement backHomeButton;

    // Constructor
    public CheckoutPage() {
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isCheckoutStepOneDisplayed() {
        return isDisplayed(checkoutTitle) && getText(checkoutTitle).equals("Checkout: Your Information");
    }

    public void enterFirstName(String firstName) {
        sendKeys(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        sendKeys(lastNameField, lastName);
    }

    public void enterPostalCode(String postalCode) {
        sendKeys(postalCodeField, postalCode);
    }

    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void clickContinue() {
        click(continueButton);
    }

    public void clickCancel() {
        click(cancelButton);
    }

    public boolean isCheckoutStepTwoDisplayed() {
        return isDisplayed(itemTotalLabel);
    }

    public String getItemTotal() {
        return getText(itemTotalLabel);
    }

    public String getTax() {
        return getText(taxLabel);
    }

    public String getTotal() {
        return getText(totalLabel);
    }

    public void clickFinish() {
        click(finishButton);
    }

    public boolean isOrderCompleteDisplayed() {
        return isDisplayed(thankYouMessage);
    }

    public String getThankYouMessage() {
        return getText(thankYouMessage);
    }

    public String getOrderDispatchMessage() {
        return getText(orderDispatchMessage);
    }

    public void clickBackHome() {
        click(backHomeButton);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public void completeCheckout(String firstName, String lastName, String postalCode) {
        fillCheckoutForm(firstName, lastName, postalCode);
        clickContinue();
        clickFinish();
    }
}