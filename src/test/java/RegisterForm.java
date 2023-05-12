import Pages.HomePage;
import Pages.RegisterPage;
import Pages.RegisterConfirmationPage;

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



import java.util.concurrent.TimeUnit;



public class RegisterForm {
    private static String uniqueUsername;
    private static String uniqueUsername2;
    private static WebDriver driver = null;

    @BeforeTest
    //TO DO: Fix Selenium Grid
    /*public static void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.BROWSER_NAME, "chrome");

        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);

        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);


    }*/
    //To be used until figuring out Selenium grid
    public static void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");

    }



    @Test //submitAllFields
    public static void testSubmitAllFields(){
    //WebDriver driver = null;

    // Generate a unique username using UUID
    uniqueUsername = "username_" + UUID.randomUUID().toString().substring(0, 8);
    RegisterPage registerForm = new RegisterPage(driver);

    HomePage.accessRegisterPage(driver);

    registerForm.allFieldsRegistrationForm("FirstName", "LastName", "0729813506","user@email.com",
    "Address 12345, Test 23", "23 Lane, 12", "Bucharest", "Bucharest", "123457",
            "ANTARCTICA",uniqueUsername,"mypassword", "mypassword");
    //registerForm.clickSubmitButton();

    driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

    RegisterPage.successfulRegistration(driver, uniqueUsername);
    RegisterConfirmationPage.confirmRegistration(driver);

    }
    @Test //successfulRegistration
    public static void testSubmitForm(){
        uniqueUsername2 = "username_" + UUID.randomUUID().toString().substring(0, 8);
        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        registerForm.inputRegisterForm(uniqueUsername2, "mypassword", "mypassword");

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        RegisterPage.successfulRegistration(driver, uniqueUsername2);
        RegisterConfirmationPage.confirmRegistration(driver);
    }

    @Test //existingUser
    public static void testSubmitForm2(){
        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        String existingUsername = uniqueUsername;

        registerForm.inputRegisterForm(existingUsername, "mypassword", "mypassword");

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        RegisterPage.existingUser(driver);
    }

    @Test //incomplete data
    public static void testSubmitForm3(){
        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        registerForm.inputRegisterForm("", "mypassword", "mypassword");

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        RegisterPage.incompleteData(driver);
    }

    @Test //invalid pass confirmation
    public static void testSubmitForm4(){
        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        registerForm.inputRegisterForm("myusername", "password", "passwrd");

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        RegisterPage.invalidPassConfirmation(driver);
    }

    @AfterTest
    public void teardDownTest(){
        driver.close();
        driver.quit();
        System.out.println("Test completed successfully");
    }

}
