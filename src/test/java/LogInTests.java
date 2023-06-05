import pages.*;
import utils.*;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;

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
        String user2 = (String) data[2];
        String pass2 = (String) data[3];

        WebDriver driver = getDriver();
        HomePage homePage = new HomePage(driver);
        LogInPage logIn = new LogInPage(driver);

        homePage.accessPage("signOn");
        logIn.inputLogIn(user1, pass1);

        System.out.println("Test 1:" + Thread.currentThread().getId());

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

