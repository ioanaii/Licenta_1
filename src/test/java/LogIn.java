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
import org.testng.annotations.DataProvider;
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
    private static String user1;
    private static String pass1;
    private static String user2;
    private static String pass2;

    @DataProvider
    public Object[][] testData(){
        // Load the properties file to access the registered usernames
        try (FileInputStream fileIn = new FileInputStream("src/main/resources/test2.properties")) {
            prop.load(fileIn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        user1 = prop.getProperty("username");
        pass1 = prop.getProperty("password");
        user2 = prop.getProperty("username2");
        pass2 = prop.getProperty("password2");

        return new Object[][] {
                { user1, pass1, user2, pass2 }
        };

    }
    @BeforeTest
    public static void setUp(){

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");

    }

    @Test(dataProvider = "testData")
    public static void loginTest1(String user1, String pass1, String user2, String pass2) {
        LogInPage logInPage = new LogInPage(driver, user1, pass1, user2, pass2);

        HomePage.accessLogInPage(driver);
        //invalid username or password
        logInPage.inputLogIn(user1, pass2);
        logInPage.inputLogIn(user2, pass1);

        //incomplete fields
        logInPage.inputLogIn("", pass1);
        logInPage.inputLogIn(user1, "pass1");

        //valid login
        logInPage.inputLogIn(user1, pass1);

    }

    @AfterTest
    public void teardDownTest(){
        driver.close();
        driver.quit();
        System.out.println("Test completed successfully");
    }
}
