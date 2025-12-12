package com.sqe.framework.utils;

import com.sqe.framework.base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class DebugUtils {
    
    public static void printPageInfo() {
        System.out.println("\n=== PAGE DEBUG INFO ===");
        System.out.println("Title: " + DriverManager.getDriver().getTitle());
        System.out.println("URL: " + DriverManager.getDriver().getCurrentUrl());
        System.out.println("Page Source Length: " + DriverManager.getDriver().getPageSource().length());
        
        // Count elements
        List<WebElement> allElements = DriverManager.getDriver().findElements(By.cssSelector("*"));
        System.out.println("Total elements on page: " + allElements.size());
        
        // Count specific elements
        System.out.println("Input fields: " + DriverManager.getDriver().findElements(By.tagName("input")).size());
        System.out.println("Buttons: " + DriverManager.getDriver().findElements(By.tagName("button")).size());
        System.out.println("Links: " + DriverManager.getDriver().findElements(By.tagName("a")).size());
        System.out.println("Images: " + DriverManager.getDriver().findElements(By.tagName("img")).size());
        
        System.out.println("=== END DEBUG INFO ===\n");
    }
    
    public static void findElementByText(String text) {
        System.out.println("\n=== SEARCHING FOR TEXT: '" + text + "' ===");
        
        try {
            // Try different methods
            List<WebElement> elements = DriverManager.getDriver().findElements(
                By.xpath("//*[contains(text(), '" + text + "')]"));
            
            System.out.println("Found " + elements.size() + " elements containing text");
            
            for (int i = 0; i < elements.size(); i++) {
                WebElement element = elements.get(i);
                System.out.println("Element " + i + ":");
                System.out.println("  Tag: " + element.getTagName());
                System.out.println("  Text: " + element.getText());
                System.out.println("  Visible: " + element.isDisplayed());
            }
        } catch (Exception e) {
            System.out.println("Error searching for text: " + e.getMessage());
        }
        
        System.out.println("=== END SEARCH ===\n");
    }
}
