package com.sqe.framework.pages;

import com.sqe.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CommonPage extends BasePage {

    // Locators
    @FindBy(tagName = "a")
    private List<WebElement> allLinks;

    @FindBy(tagName = "img")
    private List<WebElement> allImages;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItemsLink;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetLink;

    @FindBy(className = "bm-cross-button")
    private WebElement closeMenuButton;

    @FindBy(className = "footer")
    private WebElement footer;

    @FindBy(className = "footer_copy")
    private WebElement footerText;

    @FindBy(className = "social_twitter")
    private WebElement twitterLink;

    @FindBy(className = "social_facebook")
    private WebElement facebookLink;

    @FindBy(className = "social_linkedin")
    private WebElement linkedinLink;

    // Constructor
    public CommonPage() {
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public int getTotalLinks() {
        return allLinks.size();
    }

    public int getTotalImages() {
        return allImages.size();
    }

    public void openMenu() {
        click(menuButton);
        waitForVisibility(allItemsLink);
    }

    public void closeMenu() {
        click(closeMenuButton);
    }

    public void clickAllItems() {
        click(allItemsLink);
    }

    public void clickAbout() {
        click(aboutLink);
    }

    public void clickLogout() {
        click(logoutLink);
    }

    public void clickReset() {
        click(resetLink);
    }

    public String getFooterText() {
        return getText(footerText);
    }

    public boolean isFooterDisplayed() {
        return isDisplayed(footer);
    }

    public void clickTwitterLink() {
        click(twitterLink);
    }

    public void clickFacebookLink() {
        click(facebookLink);
    }

    public void clickLinkedinLink() {
        click(linkedinLink);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public void navigateForward() {
        driver.navigate().forward();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }
}