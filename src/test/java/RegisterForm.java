import org.testng.Assert;
import pages.*;
import utils.*;
import utils.DataLoader.TestData;

import java.util.UUID;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


public class RegisterForm extends BaseTest{
    private String uniqueUsername;
    private String uniquePassword;

    @DataProvider
    public Object[][] jsonTestData() {
        TestData[] testData = DataLoader.loadFromJsonFile("src/main/resources/testdata.json");
        return new Object[][]{{testData}};
    }

    @DataProvider
    public Object[][] propertiesTestData() {
        return DataLoader.loadFromPropertiesFile("src/main/resources/test2.properties", "username", "password", "username2","password2");

    }

    @Test(dataProvider = "jsonTestData")
    public void registerUser_submitAllFields_Test(TestData[] testDataArray){
        TestData testData = testDataArray[0];
        String firstName = testData.getFirstName();
        String lastName = testData.getLastName();
        String phoneNumber = testData.getPhoneNumber();
        String email = testData.getEmail();
        String address = testData.getAddress();
        String city = testData.getCity();
        String state = testData.getState();
        String postalCode = testData.getPostalCode();

        WebDriver driver = getDriver();
        RegisterPage registerForm = new RegisterPage(driver);
        HomePage homePage = new HomePage(driver);

        uniqueUsername = "username_" + UUID.randomUUID().toString().substring(0, 8);
        uniquePassword = "pass_" + UUID.randomUUID().toString().substring(0, 8);

        homePage.accessPage("register");

        registerForm.allFieldsRegistrationForm(firstName, lastName, phoneNumber,email,
            address, address, city, state, postalCode,
            "ANTARCTICA",uniqueUsername,uniquePassword, uniquePassword);

    }
    @Test
    public void registerUser_submitRequiredFields_Test(){

        WebDriver driver = getDriver();

        RegisterPage registerForm = new RegisterPage(driver);
        HomePage homePage = new HomePage(driver);
        RegisterConfirmationPage registerConfirmationPage = new RegisterConfirmationPage(driver);

        homePage.accessPage("register");

        uniqueUsername = "username_" + UUID.randomUUID().toString().substring(0, 8);
        uniquePassword = "pass_" + UUID.randomUUID().toString().substring(0, 8);

        registerForm.inputRegisterForm(uniqueUsername, uniquePassword, uniquePassword);

        }

    @Test(dataProvider = "propertiesTestData")
    public void registerUser_validationErrors_Test(Object[] data) {
        String user1 = (String) data[0];
        String password = (String) data[1];
        String user2 = (String) data[2];
        String password2 = (String) data[3];

        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);

        homePage.accessPage("register");
        String expectedURL = "com.mercurytours.servlet.RegisterServlet";

        SoftAssert softAssert = new SoftAssert();

        //incomplete fields
        validateIncompleteFields(softAssert, "", uniquePassword, uniquePassword);
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedURL));
        validateIncompleteFields(softAssert, uniqueUsername, "", uniquePassword);
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedURL));
        validateIncompleteFields(softAssert, uniqueUsername, uniquePassword, "");
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedURL));

        //existing user
        validateExistingUser(softAssert, user1, uniquePassword, uniquePassword);
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedURL));

        //invalid password
        validateInvalidPassword(softAssert, uniqueUsername, password, password2);
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedURL));
        validateInvalidPassword(softAssert, uniqueUsername, password2, password);
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedURL));

        softAssert.assertAll();
    }

    private void validateIncompleteFields(SoftAssert softAssert, String username, String password, String confirmPassword) {
        WebDriver driver = getDriver();

        RegisterPage registerForm = new RegisterPage(driver);
        registerForm.inputRegisterForm(username, password, confirmPassword);
        softAssert.assertTrue(driver.getPageSource().contains("Please fill all fields bellow to complete the registration."),
                "Validation error not found or is incorrect: Incomplete fields");
    }

    private void validateExistingUser(SoftAssert softAssert, String username, String password, String confirmPassword) {
        WebDriver driver = getDriver();

        RegisterPage registerForm = new RegisterPage(driver);

        registerForm.inputRegisterForm(username, password, confirmPassword);
        softAssert.assertTrue(driver.getPageSource().contains("Note: Error - The user name has been already used, please enter a new name"),
                "Validation error not found or is incorrect: User name already used");
    }

    private void validateInvalidPassword(SoftAssert softAssert, String username, String password, String confirmPassword) {
        WebDriver driver = getDriver();

        RegisterPage registerForm = new RegisterPage(driver);

        registerForm.inputRegisterForm(username, password, confirmPassword);
        softAssert.assertTrue(driver.getPageSource().contains("Note: The confirmed password must be the same as the desired password.."),
                "Validation error not found or is incorrect: Password mismatch");
    }
}
