package src.test;

import Pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static Pages.HomePage.accessRegisterPage;
import static Pages.RegisterConfirmationPage.confirmRegistration;
import static Pages.RegisterPage.*;
//import static Pages.RegisterPage.submitRegisterForm;

public class RegisterForm {
    private static WebDriver driver = null;

    @BeforeTest
    public void setUpTest(){
        System.setProperty("webdriver.chrome.driver", "/Users/Ioana Ionescu/IdeaProjects/chromedriver_win32/chromedriver.exe");

        driver = new ChromeDriver();

        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
    }

    @Test //successfulRegistration
    public static void submitForm(){

        RegisterPage registerForm = new RegisterPage(driver);

        accessRegisterPage(driver);

        registerForm.inputRegisterForm("best", "sister", "ever", "vivi", "vivi");
        registerForm.clickSubmitButton();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        confirmRegistration(driver);
    }

    @Test //existingUser
    public static void submitForm2(){

        RegisterPage registerForm = new RegisterPage(driver);

        accessRegisterPage(driver);

        registerForm.inputRegisterForm("best", "sister", "ever", "vivi", "vivi");
        registerForm.clickSubmitButton();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        existingUser(driver);
    }

    @Test //incomplete data
    public static void submitForm3(){

        RegisterPage registerForm = new RegisterPage(driver);

        accessRegisterPage(driver);

        registerForm.inputRegisterForm("best", "sister", "ever", "vivi", "vivi");
        registerForm.clickSubmitButton();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        incompleteData(driver);
    }

    @AfterTest
    public void teardDownTest(){
        driver.close();
        driver.quit();
        System.out.println("Test completed successfully");
    }

}
