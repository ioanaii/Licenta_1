import pages.HomePage;
import pages.RegisterPage;
import pages.RegisterConfirmationPage;
import utils.DataLoader;
import utils.DataLoader.TestData;
import utils.DataGenerator;

import java.util.UUID;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;


public class RegisterForm {
    private static String uniqueUsername;
    private static String uniquePassword;
    private static WebDriver driver = null;
    private static RegisterPage registerForm;
    private static HomePage homePage;
    private static RegisterConfirmationPage registerConfirmationPage;

    @DataProvider
    public Object[][] jsonTestData() {
        TestData[] testData = DataLoader.loadFromJsonFile("src/main/resources/testdata.json");
        return new Object[][]{{testData}};
    }

    @DataProvider
    public Object[][] propertiesTestData() {
        return DataLoader.loadFromPropertiesFile("src/main/resources/test2.properties", "username", "password", "username2","password2");

    }

    @BeforeMethod
    public void generateUniqueUsers() {
        uniqueUsername = "username_" + UUID.randomUUID().toString().substring(0, 8);
        uniquePassword = "pass_" + UUID.randomUUID().toString().substring(0, 8);


    }

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

        registerForm = new RegisterPage(driver);
        homePage = new HomePage(driver);
        registerConfirmationPage = new RegisterConfirmationPage(driver);


    }

    @Test(dataProvider = "jsonTestData")
    public static void registerUser_submitAllFields_Test(TestData[] testDataArray){
        TestData testData = testDataArray[0];
        String firstName = testData.getFirstName();
        String lastName = testData.getLastName();
        String phoneNumber = testData.getPhoneNumber();
        String email = testData.getEmail();
        String address = testData.getAddress();
        String city = testData.getCity();
        String state = testData.getState();
        String postalCode = testData.getPostalCode();

        homePage.accessPage("register");

    registerForm.allFieldsRegistrationForm(firstName, lastName, phoneNumber,email,
            address, address, city, state, postalCode,
            "ANTARCTICA",uniqueUsername,uniquePassword, uniquePassword);

    }
    @Test
    public static void registerUser_submitRequiredFields_Test(){

        homePage.accessPage("register");
        registerForm.inputRegisterForm(uniqueUsername, uniquePassword, uniquePassword);

        }

    @Test(dataProvider = "propertiesTestData")
    public static void registerUser_validationErrors_Test(Object[] data) {
        String user1 = (String) data[0];
        String password = (String) data[1];
        String user2 = (String) data[2];
        String password2 = (String) data[3];

        homePage.accessPage("register");

        SoftAssert softAssert = new SoftAssert();

        //incomplete fields
        validateIncompleteFields(softAssert, "", uniquePassword, uniquePassword);
        validateIncompleteFields(softAssert, uniqueUsername, "", uniquePassword);
        validateIncompleteFields(softAssert, uniqueUsername, uniquePassword, "");

        //existing user
        validateExistingUser(softAssert, user1, uniquePassword, uniquePassword);

        //invalid password
        validateInvalidPassword(softAssert, uniqueUsername, password, password2);
        validateInvalidPassword(softAssert, uniqueUsername, password2, password);

        softAssert.assertAll();
    }

    @AfterTest
    public void tearDownTest(){
        driver.close();
        driver.quit();
        System.out.println("Test completed successfully");
    }
    private static void validateIncompleteFields(SoftAssert softAssert, String username, String password, String confirmPassword) {
        registerForm.inputRegisterForm(username, password, confirmPassword);
        softAssert.assertTrue(driver.getPageSource().contains("Please fill all fields bellow to complete the registration."),
                "Validation error not found or is incorrect: Incomplete fields");
    }

    private static void validateExistingUser(SoftAssert softAssert, String username, String password, String confirmPassword) {
        registerForm.inputRegisterForm(username, password, confirmPassword);
        softAssert.assertTrue(driver.getPageSource().contains("Note: Error - The user name has been already used, please enter a new name"),
                "Validation error not found or is incorrect: User name already used");
    }

    private static void validateInvalidPassword(SoftAssert softAssert, String username, String password, String confirmPassword) {
        registerForm.inputRegisterForm(username, password, confirmPassword);
        softAssert.assertTrue(driver.getPageSource().contains("Note: The confirmed password must be the same as the desired password.."),
                "Validation error not found or is incorrect: Password mismatch");
    }
}
