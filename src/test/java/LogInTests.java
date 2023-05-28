import pages.HomePage;
import pages.LogInPage;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.DataLoader;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;

public class LogInTests {
    private static WebDriver driver = null;
    private static HomePage homePage;
    private static LogInPage logIn;


    @DataProvider
    public Object[][] propertiesTestData() {
        return DataLoader.loadFromPropertiesFile("src/main/resources/test2.properties", "username", "password", "username2","password2");

    }
    @BeforeTest
    public static void setUp(){

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");

        homePage = new HomePage(driver);
        logIn = new LogInPage(driver);

    }

    @Test(dataProvider = "propertiesTestData")
    public void login_successfulLogin_Test(Object[] data) {

        String user1 = (String) data[0];
        String pass1 = (String) data[1];
        String user2 = (String) data[2];
        String pass2 = (String) data[3];


        homePage.accessPage("signOn");

        //invalid username or password
        logIn.inputLogIn(user1, pass2);

        //incomplete fields
        logIn.inputLogIn("", pass1);

        //logInPage.inputLogIn(user1, "");
        //valid login
        //logInPage.inputLogIn(user1, pass1);
        //SoftAssert softAssert = validationErrorsLogIn(initialURL, user, pass);
        //softAssert.assertAll();
    }

    @Test(dataProvider = "propertiesTestData")
    public void login_verifyValidations_Test(Object[] data) {

        String user1 = (String) data[0];
        String pass1 = (String) data[1];
        String user2 = (String) data[2];
        String pass2 = (String) data[3];

        SoftAssert softAssert = new SoftAssert();

        String expectedURL = driver.getCurrentUrl();

        homePage.accessPage("signOn");

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
    }

    @AfterTest
    public void teardDownTest(){
        driver.close();
        driver.quit();
        System.out.println("Test completed successfully");
    }
    private static void validateIncompleteFields(SoftAssert softAssert, String username, String password) {
        logIn.inputLogIn(username, password);
        softAssert.assertTrue(driver.getPageSource().contains("Invalid User Name or Password."),
                "Validation error not found or is incorrect: Invalid User Name or Password.");
    }
    private static void validateInvalidCredentials(SoftAssert softAssert, String username, String password) {
        logIn.inputLogIn(username, password);
        softAssert.assertTrue(driver.getPageSource().contains("Please fill all fields"),
                "Validation error not found or is incorrect: Incomplete fields");
    }

}
