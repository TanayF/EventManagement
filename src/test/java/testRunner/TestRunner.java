package testRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

@CucumberOptions(
    features = "src/test/resources/Features",
    glue = {"Stepdefinitions", "Hooks"},
    plugin = {"pretty", "html:target/cucumber-reports.html"},
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // The driver is now owned by the TestRunner
    public static WebDriver driver;
    public static Properties prop;

 // Inside TestRunner.java

    @BeforeSuite
    public void startBrowser() {
        System.out.println("Executing @BeforeSuite...");
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties");
            prop.load(ip);

            String browserName = prop.getProperty("browser");
            if (browserName.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else {
                System.out.println("Browser not supported!");
                return;
            }

            driver.manage().window().maximize();

            //
            // DELETE OR COMMENT OUT THE LINE BELOW. THIS IS THE CAUSE OF YOUR ERROR.
            //
            // driver.get(prop.getProperty("url")); 
            //

            System.out.println("Browser launched successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void closeBrowser() {
        System.out.println("Executing @AfterSuite...");
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed successfully.");
        }
    }
}