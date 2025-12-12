package com.sqe.framework.listeners;

import com.sqe.framework.base.DriverManager;
import com.sqe.framework.utils.ScreenshotUtils;
import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getName());
        Allure.description("Test Method: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
        attachScreenshot("Success - " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getName());
        attachScreenshot("Failure - " + result.getName());

        // Log the exception
        if (result.getThrowable() != null) {
            Allure.addAttachment("Stack Trace", "text/plain",
                    result.getThrowable().toString());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite Finished: " + context.getName());
    }

    private void attachScreenshot(String screenshotName) {
        try {
            if (DriverManager.getDriver() != null) {
                // Cast to TakesScreenshot
                org.openqa.selenium.TakesScreenshot takesScreenshot =
                        (org.openqa.selenium.TakesScreenshot) DriverManager.getDriver();
                byte[] screenshot = takesScreenshot.getScreenshotAs(
                        org.openqa.selenium.OutputType.BYTES);
                Allure.addAttachment(screenshotName,
                        new ByteArrayInputStream(screenshot));
            }
        } catch (Exception e) {
            System.out.println("Failed to attach screenshot: " + e.getMessage());
        }
    }
}