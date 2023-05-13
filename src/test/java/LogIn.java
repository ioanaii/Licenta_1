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

            // Load the properties file to access the registered usernames
            try (FileInputStream fileIn = new FileInputStream("src/main/resources/test.properties")) {
                prop.load(fileIn);
            } catch (IOException e) {
                e.printStackTrace();
            }

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");

    }

    @Test //successful login
    public static void loginTest1() {
        LogInPage logInPage = new LogInPage(driver);

        // Load the properties file to access the registered usernames
        String user1 = prop.getProperty("username");
        String pass1 = prop.getProperty("password");

        HomePage.accessLogInPage(driver);
        logInPage.inputLogIn(user1, pass1);
        logInPage.validLogIN(driver);

    }

    @Test //incorrect password
    public static void loginTest2() {
        LogInPage logInPage = new LogInPage(driver);

        String user1 = prop.getProperty("username1");
        String pass1 = prop.getProperty("password");


            HomePage.accessLogInPage(driver);

            logInPage.inputLogIn(user1, pass1);
            logInPage.invalidUsernameOrPassword();

    }

    @Test //
    public static void loginTest3() {
        LogInPage logInPage = new LogInPage(driver);

        String user1 = prop.getProperty("username");
        String pass1 = prop.getProperty("password");

            HomePage.accessLogInPage(driver);

            logInPage.inputLogIn(user1, "");

            logInPage.incompleteFields();

    }

    @AfterTest
    public void teardDownTest(){
        driver.close();
        driver.quit();
        System.out.println("Test completed successfully");
    }
}
