import Pages.HomePage;
import Pages.RegisterPage;
import Pages.RegisterConfirmationPage;
import Pages.DataGenerator;
import Pages.LogInPage;

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
    private static String user1;
    private static String pass1;
    private static WebDriver driver = null;


    @DataProvider
    public Object[][] testData() {
        //generate new data in Json file
        DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.updateJsonFile();

        try (FileReader reader = new FileReader("src/main/resources/testdata.json")) {
            Gson gson = new Gson();
            TestData[] testData = gson.fromJson(reader, TestData[].class);
            return new Object[][]{{testData}};
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
            return new Object[0][0];
        }
    }

    @DataProvider
        public Object[][] testData2(){
            // Load the properties file to access the registered usernames
            try (FileInputStream fileIn = new FileInputStream("src/main/resources/test2.properties")) {
                prop.load(fileIn);
            } catch (IOException e) {
                e.printStackTrace();
            }

            user1 = prop.getProperty("username");
            pass1 = prop.getProperty("password");

            return new Object[][] {
                    { user1, pass1}
            };
        }
        // Data model class representing the structure of the JSON data
        private static class TestData {
            private String firstName;
            private String lastName;
            private String phoneNumber;
            private String email;
            private String address;
            private String city;
            private String state;
            private String postalCode;

            public String getFirstName() {
                return firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public String getEmail() {
                return email;
            }

            public String getAddress() {
                return address;
            }

            public String getCity() {
                return city;
            }

            public String getState() {
                return state;
            }

            public String getPostalCode() {
                return postalCode;
            }
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
        DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.updateJsonFile();

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");

    }



    @Test(dataProvider = "testData") //submitAllFields
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

    RegisterPage.successfulRegistration(driver, uniqueUsername);
    RegisterConfirmationPage.confirmRegistration(driver);

    }
    @Test //successfulRegistration
    public static void testSubmitForm(){
        uniqueUsername2 = "username_" + UUID.randomUUID().toString().substring(0, 8);
        uniquePassword2 = "pass_" + UUID.randomUUID().toString().substring(0, 8);

        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        registerForm.inputRegisterForm(uniqueUsername2, uniquePassword2, uniquePassword2);

        //previously used to add data to test.properties
        /*prop.setProperty("username2", uniqueUsername2);
        //prop.setProperty("password2", uniquePassword2);

        try (FileOutputStream fileOut = new FileOutputStream("src/main/resources/test.properties")) {
            prop.store(fileOut, "Registered Usernames and Passwords");
        } catch (IOException e) {
            e.printStackTrace();*/

        RegisterPage.successfulRegistration(driver, uniqueUsername2);
        RegisterConfirmationPage.confirmRegistration(driver);


        }


    @Test //existingUser
    public static void testSubmitForm2(){
        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        String existingUsername = uniqueUsername;

        registerForm.inputRegisterForm(existingUsername, uniquePassword, uniquePassword);

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        RegisterPage.existingUser(driver);
    }

    @Test //incomplete data
    public static void testSubmitForm3(){
        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        registerForm.inputRegisterForm("", uniquePassword, uniquePassword);

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        RegisterPage.incompleteData(driver);
    }

    @Test //invalid pass confirmation
    public static void testSubmitForm4(){
        RegisterPage registerForm = new RegisterPage(driver);

        HomePage.accessRegisterPage(driver);

        registerForm.inputRegisterForm(uniqueUsername, uniquePassword2, uniquePassword2);

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
