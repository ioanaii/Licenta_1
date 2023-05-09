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


import java.util.concurrent.TimeUnit;



public class RegisterForm {

    private static WebDriver driver = null;

    @BeforeTest
    public static void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.BROWSER_NAME, "chrome");

        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);

        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);


    }


    @Test //submitAllFields
    public static void testSubmitAllFields(){
    //WebDriver driver = null;
    RegisterPage registerForm = new RegisterPage(driver);

    HomePage.accessRegisterPage(driver);

    registerForm.allFieldsRegistrationForm("FirstName", "LastName", "0729813506","user@email.com",
    "no", "nobody", "one", "cares", "yes",
            "ANTARCTICA","diditwork","itworks", "itworks");
    //registerForm.clickSubmitButton();

    driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

    RegisterPage.successfulRegistration(driver, "username");
    RegisterConfirmationPage.confirmRegistration(driver);

    }
    @Test //successfulRegistration
    public static void testSubmitForm(){
        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        registerForm.inputRegisterForm("myusername", "mypassword", "mypassword");

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        RegisterPage.successfulRegistration(driver, "myusername");
        //RegisterConfirmationPage.confirmRegistration(driver);
    }

    @Test //existingUser
    public static void testSubmitForm2(){
        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        registerForm.inputRegisterForm("User", "pass1!", "pass1!");

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        RegisterPage.existingUser(driver);
    }

    @Test //incomplete data
    public static void testSubmitForm3(){
        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        registerForm.inputRegisterForm("User1", "passwrd", "");

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        //-> to verify; may not work RegisterPage.incompleteData(driver);
    }

    @Test //invalid pass confirmation
    public static void testSubmitForm4(){
        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        registerForm.inputRegisterForm("User1", "password", "passwrd");

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
