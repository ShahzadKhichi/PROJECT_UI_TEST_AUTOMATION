package com.sqe.framework.config;

public class Constants {

    // Browser Types
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String EDGE = "edge";
    public static final String SAFARI = "safari";

    // Configuration File
    public static final String CONFIG_FILE = "config/config.properties";

    // Timeouts
    public static final int DEFAULT_TIMEOUT = 30;
    public static final int IMPLICIT_WAIT = 10;
    public static final int PAGE_LOAD_TIMEOUT = 30;

    // Test Data Sheets
    public static final String LOGIN_SHEET = "Login";
    public static final String PRODUCTS_SHEET = "Products";
    public static final String USERS_SHEET = "Users";

    // Paths
    public static final String SCREENSHOT_PATH = "screenshots/";
    public static final String TEST_DATA_PATH = "src/main/resources/testdata/";

    // Database
    public static final String DB_URL = "jdbc:mysql://localhost:3306/test_automation";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "password";

    // URLs
    public static final String BASE_URL = "https://www.saucedemo.com";
    public static final String LOGIN_URL = BASE_URL + "/";
    public static final String PRODUCTS_URL = BASE_URL + "/inventory.html";
    public static final String CART_URL = BASE_URL + "/cart.html";
    public static final String CHECKOUT_URL = BASE_URL + "/checkout-step-one.html";

    // File Paths
    public static final String TEST_DATA_EXCEL = "testdata/testdata.xlsx";

}
