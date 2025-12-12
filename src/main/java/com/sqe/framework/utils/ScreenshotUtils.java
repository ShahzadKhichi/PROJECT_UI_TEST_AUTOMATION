package com.sqe.framework.utils;

import com.sqe.framework.config.ConfigManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    public static String takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            return "";
        }

        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotName = testName + "_" + timestamp + ".png";
            String screenshotDir = ConfigManager.getInstance().getScreenshotPath();

            // Create directory if it doesn't exist
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String screenshotPath = screenshotDir + screenshotName;
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File(screenshotPath));

            // For Allure reporting
            attachScreenshotToAllure(screenshotFile, screenshotName);

            return screenshotPath;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void attachScreenshotToAllure(File screenshotFile, String screenshotName) {
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(screenshotFile);
            io.qameta.allure.Allure.addAttachment(screenshotName, "image/png",
                    new java.io.ByteArrayInputStream(fileContent), ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}