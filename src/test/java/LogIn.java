import Pages.HomePage;
import Pages.RegisterPage;
import Pages.RegisterConfirmationPage;
import Pages.LogInPage;


import java.net.URL;
import java.util.UUID;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LogIn{
    private static Properties prop = new Properties();
    private static WebDriver driver = null;

    @BeforeTest
    public static void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");

    }

    @Test
    public static void loginTest() {
        // Load the properties file to access the registered usernames
        try (FileInputStream fileIn = new FileInputStream("src/test/resources/test.properties")) {
            prop.load(fileIn);
            String user1 = prop.getProperty("username");
            String user2 = prop.getProperty("username2");
            String pass1 = prop.getProperty("password");
            String pass2 = prop.getProperty("password2");

            // Perform login test using user1 and user2
        HomePage.accessLogInPage(driver);
        //LogInPage.inputLogIn("username", "password");



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
