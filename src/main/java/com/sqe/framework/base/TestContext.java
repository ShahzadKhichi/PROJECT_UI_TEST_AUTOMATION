package com.sqe.framework.base;

import com.sqe.framework.pages.*;
import com.sqe.framework.utils.*;
import org.openqa.selenium.WebDriver;

public class TestContext {
    
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private CommonPage commonPage;
    private ExcelReader excelReader;
    private DatabaseManager dbManager;
    private RedisManager redisManager;
    
    public TestContext() {
        // Initialize driver
        this.driver = DriverManager.getDriver();
        
        // Initialize all pages
        this.loginPage = new LoginPage();
        this.productsPage = new ProductsPage();
        this.cartPage = new CartPage();
        this.checkoutPage = new CheckoutPage();
        this.commonPage = new CommonPage();
        
        // Initialize ExcelReader (REQUIRED)
        this.excelReader = new ExcelReader("testdata/testdata.xlsx");
        
        // Initialize DatabaseManager
        try {
            this.dbManager = new DatabaseManager();
            this.dbManager.createTestDatabase();
            System.out.println("✅ DatabaseManager initialized successfully");
        } catch (Exception e) {
            System.out.println("⚠️ DatabaseManager initialization failed: " + e.getMessage());
            this.dbManager = null;
        }
        
        // Initialize RedisManager (OPTIONAL)
        try {
            this.redisManager = new RedisManager();
            System.out.println("✅ RedisManager initialized");
        } catch (Exception e) {
            System.out.println("⚠️ RedisManager not available: " + e.getMessage());
            this.redisManager = null;
        }
    }
    
    // Getters
    public WebDriver getDriver() {
        return driver;
    }
    
    public LoginPage getLoginPage() {
        return loginPage;
    }
    
    public ProductsPage getProductsPage() {
        return productsPage;
    }
    
    public CartPage getCartPage() {
        return cartPage;
    }
    
    public CheckoutPage getCheckoutPage() {
        return checkoutPage;
    }
    
    public CommonPage getCommonPage() {
        return commonPage;
    }
    
    public ExcelReader getExcelReader() {
        return excelReader;
    }
    
    public DatabaseManager getDatabaseManager() {
        return dbManager;
    }
    
    public RedisManager getRedisManager() {
        return redisManager;
    }
    
    public void cleanup() {
        if (excelReader != null) {
            excelReader.close();
        }
        if (dbManager != null) {
            dbManager.close();
        }
        if (redisManager != null) {
            RedisManager.close();
        }
    }
}
