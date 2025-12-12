package com.sqe.framework.pages;

import com.sqe.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    // Page Actions - FIXED
    public int getTotalLinks() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(allLinks));
            return allLinks.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getTotalImages() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(allImages));
            return allImages.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void openMenu() {
        click(menuButton);
        wait.until(ExpectedConditions.visibilityOf(allItemsLink));
    }

    public void closeMenu() {
        click(closeMenuButton);
        wait.until(ExpectedConditions.invisibilityOf(allItemsLink));
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
        try {
            return getText(footerText);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isFooterDisplayed() {
        try {
            return isDisplayed(footer);
        } catch (Exception e) {
            return false;
        }
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
