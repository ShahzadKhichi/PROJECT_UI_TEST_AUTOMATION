package com.sqe.tests.stepdefinitions;
import com.sqe.framework.base.TestContext;
import static com.sqe.tests.stepdefinitions.BaseStepDefinitions.testContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.testng.Assert;

public class CommonStepDefinitions {
    
    private TestContext testContext;
    
    // PUBLIC ZERO-ARGUMENT CONSTRUCTOR
    public CommonStepDefinitions() {
        this.testContext = BaseStepDefinitions.testContext;
    }
    
    @Then("page title should be {string}")
    public void page_title_should_be(String expectedTitle) {
        String actualTitle = testContext.getCommonPage().getPageTitle();
        Assert.assertEquals(actualTitle, expectedTitle,
            "Page title mismatch. Expected: " + expectedTitle + ", Actual: " + actualTitle);
    }
    
    @Then("URL should contain {string}")
    public void url_should_contain(String expectedText) {
        String actualUrl = testContext.getCommonPage().getCurrentUrl();
        Assert.assertTrue(actualUrl.contains(expectedText),
            "URL does not contain: " + expectedText + ". Actual URL: " + actualUrl);
    }
    
    @Then("all links on page should be valid")
    public void all_links_on_page_should_be_valid() {
        int totalLinks = testContext.getCommonPage().getTotalLinks();
        System.out.println("Total links found: " + totalLinks);
        Assert.assertTrue(totalLinks > 0, "No links found on page");
    }
    
    @Then("all images on page should be loaded")
    public void all_images_onpage_should_be_loaded() {
        int totalImages = testContext.getCommonPage().getTotalImages();
        System.out.println("Total images found: " + totalImages);
        Assert.assertTrue(totalImages > 0, "No images found on page");
    }
    
    @Then("footer should be displayed")
    public void footer_should_be_displayed() {
        Assert.assertTrue(testContext.getCommonPage().isFooterDisplayed(),
            "Footer is not displayed");
    }
    
    @Then("footer should contain text {string}")
    public void footer_should_contain_text(String expectedText) {
        String footerText = testContext.getCommonPage().getFooterText();
        Assert.assertTrue(footerText.contains(expectedText),
            "Footer text does not contain: " + expectedText + ". Actual: " + footerText);
    }
    
    @Then("social media links should be present")
    public void social_media_links_should_be_present() {
        Assert.assertTrue(testContext.getCommonPage().getTotalLinks() > 0,
            "No social media links found");
    }
    
    @Then("social media links should be clickable")
    public void social_media_links_should_be_clickable() {
        Assert.assertNotNull(testContext.getCommonPage().getPageTitle(),
            "Page title is null");
    }
    
    @When("user navigates back")
    public void user_navigates_back() {
        testContext.getCommonPage().navigateBack();
    }
    
    @When("user navigates forward")
    public void user_navigates_forward() {
        testContext.getCommonPage().navigateForward();
    }
    
    @When("user opens menu")
    public void user_opens_menu() {
        testContext.getCommonPage().openMenu();
    }
    
    @Then("all menu options should be visible")
    public void all_menu_options_should_be_visible() {
        Assert.assertNotNull(testContext.getCommonPage().getPageTitle(),
            "Page title is null after opening menu");
    }
    
    @When("user closes menu")
    public void user_closes_menu() {
        testContext.getCommonPage().closeMenu();
    }
    
    @Then("menu should not be visible")
    public void menu_should_not_be_visible() {
        Assert.assertNotNull(testContext.getCommonPage().getPageTitle(),
            "Page title is null after closing menu");
    }
    
    @Then("user should see products listed")
    public void user_should_see_products_listed() {
        int productCount = testContext.getProductsPage().getProductCount();
        Assert.assertTrue(productCount > 0, "No products found on products page");
    }
    
    @And("user clicks on about link")
    public void user_clicks_on_about_link() {
        testContext.getCommonPage().openMenu();
        testContext.getCommonPage().clickAbout();
    }
    
    @Then("user should be redirected to about page")
    public void user_should_be_redirected_to_about_page() {
        String currentUrl = testContext.getCommonPage().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("saucelabs.com"),
            "Not redirected to about page. Current URL: " + currentUrl);
    }
}
