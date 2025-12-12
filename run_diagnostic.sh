#!/bin/bash

echo "=== Running Diagnostic Test ==="

# First compile to check for errors
echo "1. Compiling project..."
mvn compile

if [ $? -eq 0 ]; then
    echo "├£ Compilation successful!"
    
    echo "\n2. Running a simple diagnostic test..."
    # Create a simple test to verify the pages work
    cat > TestDiagnostic.java << 'TESTEOF'
import com.sqe.framework.base.DriverManager;
import com.sqe.framework.config.ConfigManager;
import com.sqe.framework.pages.LoginPage;

public class TestDiagnostic {
    public static void main(String[] args) {
        try {
            System.out.println("Starting diagnostic...");
            
            // Initialize driver
            DriverManager.initializeDriver();
            
            // Navigate to site
            String url = ConfigManager.getInstance().getBaseUrl();
            DriverManager.getDriver().get(url);
            System.out.println("Navigated to: " + url);
            
            // Test LoginPage
            LoginPage loginPage = new LoginPage();
            System.out.println("Page title: " + loginPage.getPageTitle());
            System.out.println("Current URL: " + loginPage.getCurrentUrl());
            System.out.println("Is login page displayed: " + loginPage.isLoginPageDisplayed());
            
            // Clean up
            DriverManager.quitDriver();
            System.out.println("├£ Diagnostic completed successfully!");
            
        } catch (Exception e) {
            System.out.println("├Ø Diagnostic failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
TESTEOF

    # Compile and run the diagnostic
    javac -cp "target/classes:$(find ~/.m2/repository -name '*.jar' | tr '\n' ':')" TestDiagnostic.java
    if [ $? -eq 0 ]; then
        java -cp ".:target/classes:$(find ~/.m2/repository -name '*.jar' | tr '\n' ':')" TestDiagnostic
    fi
    
    # Clean up
    rm -f TestDiagnostic.java TestDiagnostic.class
else
    echo "├Ø Compilation failed. Please check errors above."
fi

echo "\n=== Diagnostic Complete ==="
