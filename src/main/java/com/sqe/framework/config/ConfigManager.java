package com.sqe.framework.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static ConfigManager instance;
    private Properties properties;

    private ConfigManager() {
        properties = new Properties();
        loadProperties();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties() {
        try {
            // Load from classpath
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream("config/config.properties");

            if (inputStream == null) {
                throw new RuntimeException("Configuration file not found: config/config.properties");
            }

            properties.load(inputStream);
            inputStream.close();

            // Set default values if properties are missing
            setDefaultProperties();

        } catch (Exception e) {
            System.err.println("Failed to load configuration file. Using default values.");
            e.printStackTrace();
            setDefaultProperties();
        }
    }

    private void setDefaultProperties() {
        // Set default values
        properties.putIfAbsent("base.url", "https://www.saucedemo.com");
        properties.putIfAbsent("browser", "chrome");
        properties.putIfAbsent("headless", "false");
        properties.putIfAbsent("timeout", "30");
        properties.putIfAbsent("implicit.wait", "10");
        properties.putIfAbsent("screenshot.on.failure", "true");
        properties.putIfAbsent("screenshot.path", "screenshots/");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getBrowser() {
        return getProperty("browser");
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }

    public int getTimeout() {
        return Integer.parseInt(getProperty("timeout"));
    }

    public String getScreenshotPath() {
        return getProperty("screenshot.path");
    }

    public boolean takeScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure"));
    }
}