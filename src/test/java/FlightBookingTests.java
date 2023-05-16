package Pages;

import Pages.FlightFinderPage;
import Pages.FlightPurchasePage;
import Pages.FlightSelectionPage;
import Pages.LogInPage;
import Pages.HomePage;
import Pages.ItineraryPage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class FlightBookingTests {
    private static Properties prop = new Properties();
    private static WebDriver driver = null;

    @BeforeTest
    public static void setUp() {

        // Load the properties file to access the registered usernames
        try (FileInputStream fileIn = new FileInputStream("src/main/resources/test.properties")) {
            prop.load(fileIn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");


        //Log in prior to test execution
        LogInPage logInPage = new LogInPage(driver);

        String user1 = prop.getProperty("username");
        String pass1 = prop.getProperty("password");

        logInPage.inputLogIn(user1, pass1);
    }

    @Test //test will pass
    public static void Test1(){
        FlightFinderPage flightFinder = new FlightFinderPage(driver);
        FlightSelectionPage flightSelect = new FlightSelectionPage(driver);
        FlightPurchasePage flightPurchase= new FlightPurchasePage(driver);
        ItineraryPage itineraryPage = new ItineraryPage(driver);
        HomePage homePage = new HomePage(driver);

        flightFinder.enterFlightDetails("roundtrip", "2", "Frankfurt", "Feb", "22", "Acapulco", "Feb", "22", "Coach", "No Preference");
        flightSelect.selectFlights(1,2);
        flightSelect.successfulFlightSelect();
        flightPurchase.inputFlightPurchase("", "Doe", "1123");
        flightPurchase.inputFlightPurchase("John", "", "1123");
        flightPurchase.inputFlightPurchase("John", "Dose", "1123");

        homePage.accessItinerary(driver);
        itineraryPage.removeSelected();

    }

    @Test //test will fail (departure date>return date)
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
