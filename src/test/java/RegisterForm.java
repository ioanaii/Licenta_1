import Pages.HomePage;
import Pages.RegisterPage;
import Pages.RegisterConfirmationPage;
import Pages.DataGenerator;
import Pages.LogInPage;
import Pages.DataLoader;
import Pages.DataLoader.TestData;

import java.util.UUID;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.IOException;
import java.util.Properties;
import com.google.gson.Gson;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;
import java.io.FileOutputStream;
import java.io.FileInputStream;



public class RegisterForm {
    private static Properties prop = new Properties();
    private static String uniqueUsername;
    private static String uniqueUsername2;
    private static String uniquePassword;
    private static String uniquePassword2;
    private static WebDriver driver = null;
    //String user1 = prop.getProperty("username");

    @DataProvider
    public Object[][] jsonTestData() {
        TestData[] testData = DataLoader.loadFromJsonFile("src/main/resources/testdata.json");
        return new Object[][]{{testData}};
    }

    @DataProvider
    public Object[][] propertiesTestData() {
        return DataLoader.loadFromPropertiesFile("src/main/resources/test2.properties", "username", "password", "username2","password2");

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
        //DataGenerator dataGenerator = new DataGenerator();
        //dataGenerator.updateJsonFile();

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");
    }

    @Test(dataProvider = "jsonTestData") //submitAllFields
    public static void testSubmitAllFields(TestData[] testDataArray){
        TestData testData = testDataArray[0]; // Use the first element from the array

        // Generate a unique username using UUID
        uniqueUsername = "username_" + UUID.randomUUID().toString().substring(0, 8);
        uniquePassword = "pass_" + UUID.randomUUID().toString().substring(0, 8);
        String firstName = testData.getFirstName();
        String lastName = testData.getLastName();
        String phoneNumber = testData.getPhoneNumber();
        String email = testData.getEmail();
        String address = testData.getAddress();
        String city = testData.getCity();
        String state = testData.getState();
        String postalCode = testData.getPostalCode();

    RegisterPage registerForm = new RegisterPage(driver);

    HomePage.accessRegisterPage(driver);

    registerForm.allFieldsRegistrationForm(firstName, lastName, phoneNumber,email,
            address, address, city, state, postalCode,
            "ANTARCTICA",uniqueUsername,uniquePassword, uniquePassword);

    RegisterConfirmationPage.confirmRegistration(driver);

    }
    @Test //successfulRegistration
    public static void testSubmitForm(){
        uniqueUsername2 = "username_" + UUID.randomUUID().toString().substring(0, 8);
        uniquePassword2 = "pass_" + UUID.randomUUID().toString().substring(0, 8);

        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        registerForm.inputRegisterForm(uniqueUsername2, uniquePassword2, uniquePassword2);

        RegisterConfirmationPage.confirmRegistration(driver);

        }


    @Test(dataProvider = "propertiesTestData") //existingUser
    public static void testSubmitForm2(Object[] data){
        String user1 = (String) data[0];
        String password = (String) data[1];

        RegisterPage registerForm = new RegisterPage(driver, user1);

        HomePage.accessRegisterPage(driver);

        registerForm.inputRegisterForm(user1, password, password);

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

    }

    @AfterTest
    public void teardDownTest(){
        driver.close();
        driver.quit();
        System.out.println("Test completed successfully");
    }

}
