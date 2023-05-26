import Pages.FlightFinderPage;
import Pages.FlightPurchasePage;
import Pages.FlightSelectionPage;
import Pages.LogInPage;
import Pages.HomePage;
import Pages.ItineraryPage;
import org.testng.annotations.*;
import utils.DataLoader;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class FlightBookingTests {
    private static WebDriver driver = null;

    @DataProvider
    public Object[][] propertiesTestData() {
        return DataLoader.loadFromPropertiesFile("src/main/resources/test2.properties", "username", "password", "username2","password2");

    }

    @DataProvider
    public Object[][] provideUserData() {
        // Provide user login data here
        return new Object[][]{
                {"username", "password"},
                {"username2", "password2"},
        };
    }

    @BeforeTest
    @Parameters({ "username", "password" })
    public static void setUp(String username, String password) {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");

        //Log in prior to test execution
        LogInPage logInPage = new LogInPage(driver);
        logInPage.inputLogIn(username, password);
    }

    @Test //test will pass
    public static void Test1() {
        FlightFinderPage flightFinder = new FlightFinderPage(driver);
        FlightSelectionPage flightSelect = new FlightSelectionPage(driver);
        FlightPurchasePage flightPurchase = new FlightPurchasePage(driver);
        ItineraryPage itineraryPage = new ItineraryPage(driver);
        HomePage homePage = new HomePage(driver);

        flightFinder.enterFlightDetails("roundtrip", "2", "Frankfurt", "Feb", "21", "Acapulco", "Feb", "21", "Coach", "No Preference");
        flightSelect.selectFlights(1, 2);
        //flightPurchase.inputFlightPurchase("", "Doe", "1123");
        //flightPurchase.inputFlightPurchase("John", "", "1123");
        flightPurchase.inputFlightPurchase("John", "Dose", "1123");

        homePage.accessItinerary(driver);
        itineraryPage.removeSelected();

    }

    @Test //departure date>return date
    public static void Test2(){
        FlightFinderPage flightFinder = new FlightFinderPage(driver);

        flightFinder.enterFlightDetails("roundtrip", "2", "Frankfurt", "Feb", "22", "Acapulco", "Feb", "21", "Coach", "Unified Airlines");
    }


    @AfterTest
    public void teardDownTest(){
        driver.close();
        driver.quit();
        System.out.println("Test completed successfully");
    }
}
