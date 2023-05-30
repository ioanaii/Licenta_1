import pages.*;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.*;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;
import org.openqa.selenium.chrome.ChromeOptions;

public class LogInTests {
    private WebDriver driver = null;
    private HomePage homePage;
    private LogInPage logIn;


    @DataProvider
    public Object[][] propertiesTestData() {
        return DataLoader.loadFromPropertiesFile("src/main/resources/test2.properties", "username", "password", "username2","password2");

    }
    @BeforeMethod
    public void setUp(){

        //WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        driver=new ChromeDriver(options);

        WebDriver driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");

        homePage = new HomePage(driver);
        logIn = new LogInPage(driver);

    }

    @Test(dataProvider = "propertiesTestData")
    public void login_successfulLogin_Test(Object[] data){

        String user1 = (String) data[0];
        String pass1 = (String) data[1];
        String user2 = (String) data[2];
        String pass2 = (String) data[3];


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
        System.out.println("Test 2:" + Thread.currentThread().getId());
    }

    @AfterMethod
    public void tearDown() throws Exception{
            Thread.sleep(3000);
            driver.close();
            driver.quit();
    }
    private void validateIncompleteFields(SoftAssert softAssert, String username, String password) {
        logIn.inputLogIn(username, password);
        softAssert.assertTrue(driver.getPageSource().contains("Invalid User Name or Password."),
                "Validation error not found or is incorrect: Invalid User Name or Password.");
    }
    private void validateInvalidCredentials(SoftAssert softAssert, String username, String password) {
        logIn.inputLogIn(username, password);
        softAssert.assertTrue(driver.getPageSource().contains("Please fill all fields"),
                "Validation error not found or is incorrect: Incomplete fields");
    }

}
