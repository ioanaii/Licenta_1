import Pages.HomePage;
import Pages.LogInPage;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.DataLoader;

import java.util.Properties;

public class LogIn{
    private static Properties prop = new Properties();
    private static WebDriver driver = null;


    @DataProvider
    public Object[][] propertiesTestData() {
        return DataLoader.loadFromPropertiesFile("src/main/resources/test2.properties", "username", "password", "username2","password2");

    }
    @BeforeTest
    public static void setUp(){

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");

    }

    @Test(dataProvider = "propertiesTestData")
    public static void loginTest1(Object[] data) {

        String user1 = (String) data[0];
        String pass1 = (String) data[1];
        String user2 = (String) data[2];
        String pass2 = (String) data[3];

        LogInPage logInPage = new LogInPage(driver, user1, pass1, user2, pass2);

        HomePage.accessLogInPage(driver);
        //invalid username or password
        logInPage.inputLogIn(user1, pass2);
        //logInPage.inputLogIn(user2, pass1);

        //incomplete fields
        //logInPage.inputLogIn("", pass1);
        //logInPage.inputLogIn(user1, "pass1");

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
