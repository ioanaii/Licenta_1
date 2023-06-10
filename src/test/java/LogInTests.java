import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;
import utils.*;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class LogInTests extends BaseTest{
    private WebDriver driver = getDriver();


    @DataProvider
    public Object[][] propertiesTestData() {
        return DataLoader.loadFromPropertiesFile("src/main/resources/test2.properties", "username", "password", "username2","password2");

    }

    @Test(dataProvider = "propertiesTestData")
    public void login_successfulLogin_Test(Object[] data){

        String user1 = (String) data[0];
        String pass1 = (String) data[1];

        user1+=UUID.randomUUID().toString();

        WebDriver driver = getDriver();

        //Set up - adauga user cu care sa ne autentificam
        RegisterPage registerForm = new RegisterPage(driver);
        HomePage homePage = new HomePage(driver);
        homePage.accessPage("register");
        registerForm.inputRegisterForm(user1, pass1, pass1);

        //Test
        LogInPage logIn = new LogInPage(driver);
        homePage.accessPage("signOn");
        logIn.inputLogIn(user1, pass1);

        System.out.println("Test 1:" + Thread.currentThread().getId());

        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertTrue(driver.getCurrentUrl().contains("com.mercurytours.servlet.ReservationServlet")); // a fost redirectionat catre pagina de rezervari
        Assert.assertTrue(driver.getPageSource().contains("t_signoff")); //numele butonului Sign Off, care se incarca doar daca utilizatorul e auth
    }

 @Test(dataProvider = "propertiesTestData")
    public void login_verifyValidations_Test(Object[] data) {

        String user1 = (String) data[0];
        String pass1 = (String) data[1];
        String user2 = (String) data[2];
        String pass2 = (String) data[3];

        SoftAssert softAssert = new SoftAssert();

        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);

        homePage.accessPage("signOn");

        String expectedURL = driver.getCurrentUrl();

        //invalid username or password
        validateInvalidCredentials(softAssert, user1, pass2);
        Assert.assertTrue(driver.getCurrentUrl().equals(expectedURL));
        validateInvalidCredentials(softAssert, user2, pass1);
        Assert.assertTrue(driver.getCurrentUrl().equals(expectedURL));

        //incomplete fields
        validateIncompleteFields(softAssert, "", pass1);
        Assert.assertTrue(driver.getCurrentUrl().equals(expectedURL));
        validateIncompleteFields(softAssert, user1, "");
        Assert.assertTrue(driver.getCurrentUrl().equals(expectedURL));

        softAssert.assertAll();
        System.out.println("Test 2:" + Thread.currentThread().getId());
    }

    private void validateIncompleteFields(SoftAssert softAssert, String username, String password) {

        WebDriver driver = getDriver();

        LogInPage logIn = new LogInPage(driver);
        logIn.inputLogIn(username, password);
        softAssert.assertTrue(driver.getPageSource().contains("Invalid User Name or Password."),
                "Validation error not found or is incorrect: Invalid User Name or Password.");
    }
    private void validateInvalidCredentials(SoftAssert softAssert, String username, String password) {

        WebDriver driver = getDriver();

        LogInPage logIn = new LogInPage(driver);
        logIn.inputLogIn(username, password);
        softAssert.assertTrue(driver.getPageSource().contains("Please fill all fields"),
                "Validation error not found or is incorrect: Incomplete fields");
    }

}

