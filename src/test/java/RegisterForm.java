import Pages.HomePage;
import Pages.RegisterPage;
import Pages.RegisterConfirmationPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

import java.util.Properties;
import java.util.concurrent.TimeUnit;



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

    homePage.accessRegisterPage(driver);

    registerForm.allFieldsRegistrationForm(firstName, lastName, phoneNumber,email,
            address, address, city, state, postalCode,
            "ANTARCTICA",uniqueUsername,uniquePassword, uniquePassword);

    registerConfirmationPage.confirmRegistration(driver);

    }
    @Test
    public static void registerUser_submitRequiredFields_Test(){

        homePage.accessRegisterPage(driver);
        registerForm.inputRegisterForm(uniqueUsername, uniquePassword, uniquePassword);
        registerConfirmationPage.confirmRegistration(driver);

        }

    @Test(dataProvider = "propertiesTestData")
    public static void registerUser_validationErrors_Test(Object[] data){
        String user1 = (String) data[0];
        String password = (String) data[1];
        String user2 = (String) data[2];
        String password2 = (String) data[3];

        registerForm.getExistingUser();
        registerForm.setExistingUser(user1);

        homePage.accessRegisterPage(driver);

        //incomplete fields
        registerForm.inputRegisterForm("", uniquePassword, uniquePassword);
        registerForm.inputRegisterForm(uniqueUsername, "", uniquePassword);
        registerForm.inputRegisterForm(uniqueUsername, uniquePassword, "");

        //existing user
        registerForm.inputRegisterForm(user1, uniquePassword, uniquePassword);

        //invalid password
        registerForm.inputRegisterForm(uniqueUsername, password, password2);
        registerForm.inputRegisterForm(uniqueUsername, password2, password);

    }

    @Test
    public static void updateJson(){
        DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.updateJsonFile();
    }

    @AfterTest
    public void tearDownTest(){
        driver.close();
        driver.quit();
        System.out.println("Test completed successfully");
    }

}
