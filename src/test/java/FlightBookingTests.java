import pages.*;

import utils.DataLoader;

import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;


public class FlightBookingTests {
    private static WebDriver driver = null;
    private static HomePage homePage;
    private static LogInPage logIn;
    private static FlightFinderPage flightFind;
    private static FlightPurchasePage flightPurchase;
    private static FlightSelectionPage flightSelect;
    private static ItineraryPage itinerary;

    @DataProvider
    public Object[][] propertiesTestData() {
        return DataLoader.loadFromPropertiesFile("src/main/resources/test2.properties", "username", "password", "username2","password2");

    }

    @BeforeClass
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");

        homePage = new HomePage(driver);
        logIn = new LogInPage(driver);
        flightFind = new FlightFinderPage(driver);
        flightPurchase = new FlightPurchasePage(driver);
        flightSelect = new FlightSelectionPage(driver);
        itinerary = new ItineraryPage(driver);
    }

    @Test (dataProvider = "propertiesTestData")
    public void flightBooking_roundTrip_Test(Object[] data) {
        String user1 = (String) data[0];
        String pass1 = (String) data[1];
        String user2 = (String) data[2];
        String pass2 = (String) data[3];

        homePage.accessPage("signOn");
        logIn.inputLogIn(user1, pass1);

        flightFind.enterFlightDetails("roundtrip", "2", "Frankfurt", "Feb", "21", "Acapulco", "Feb", "21", "Coach", "No Preference");
        flightSelect.selectFlights(1, 2);
        flightPurchase.inputFlightPurchase("John", "Doe", "1123");

        homePage.accessPage("itinerary");
        itinerary.removeSelected();

    }

    @Test (dataProvider = "propertiesTestData")
    public void flightBooking_oneWay_Test(Object[] data) {
        String user1 = (String) data[0];
        String pass1 = (String) data[1];
        String user2 = (String) data[2];
        String pass2 = (String) data[3];

        homePage.accessPage("signOn");
        logIn.inputLogIn(user2, pass2);

        flightFind.enterFlightDetails("oneway", "2", "Frankfurt", "Feb", "21", "Acapulco", "Feb", "21", "Business", "Unified Airlines");
        flightSelect.selectFlightsOneWay(0);
        flightPurchase.inputFlightPurchase("John", "Doe", "1123");

        homePage.accessPage("itinerary");
        itinerary.removeAll();

    }

    @Test (dataProvider = "propertiesTestData")
    public void flightBooking_invalidDates_Test(Object[] data){
        String user1 = (String) data[0];
        String pass1 = (String) data[1];
        String user2 = (String) data[2];
        String pass2 = (String) data[3];

        String expectedURL = "com.mercurytours.servlet.ReservationServlet";
        SoftAssert softAssert = new SoftAssert();

        homePage.accessPage("signOn");
        logIn.inputLogIn(user2, pass2);

        validateFlightFinderFilters(softAssert, "roundtrip", "2", "Frankfurt", "Feb", "22", "Acapulco", "Feb", "21", "Coach", "Unified Airlines");
        Assert.assertTrue(driver.getCurrentUrl().endsWith(expectedURL), "Incorrect URL");
        softAssert.assertAll();
    }

    @Test (dataProvider = "propertiesTestData")
    public void flightBooking_checkFlightPurchaseValidationErrors_Test(Object[] data){
        String user1 = (String) data[0];
        String pass1 = (String) data[1];
        String user2 = (String) data[2];
        String pass2 = (String) data[3];

        String expectedURL = "com.mercurytours.servlet.PurchaseServlet";

        SoftAssert softAssert = new SoftAssert();

        homePage.accessPage("signOn");
        logIn.inputLogIn(user2, pass2);

        flightFind.enterFlightDetails("roundtrip", "2", "Frankfurt", "Feb", "22", "Acapulco", "Feb", "24", "Coach", "Unified Airlines");
        flightSelect.selectFlights(0, 0);
        validateFlightPurchaseIncompleteFields(softAssert, "", "Doe", "49501791335");
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedURL));
        validateFlightPurchaseIncompleteFields(softAssert, "John", "", "49501791335");
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedURL));
        validateFlightPurchaseIncompleteFields(softAssert, "John", "Doe", "");
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedURL));
        validateFlightPurchaseInvalidCard(softAssert, "John", "Doe", "test49501791335");
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedURL));

        softAssert.assertAll();
    }


    @AfterClass
    public void teardDownTest(){
        driver.close();
        driver.quit();
        System.out.println("Test completed successfully");
    }
    private static void validateFlightFinderFilters(SoftAssert softAssert, String tripType, String passCount, String fromPort, String fromMonth, String fromDay,
                                                    String toPort, String toMonth, String toDay, String serviceClass, String airline){
        flightFind.enterFlightDetails(tripType, passCount,  fromPort,  fromMonth,  fromDay, toPort,  toMonth,  toDay,  serviceClass,  airline);
        softAssert.assertTrue(driver.getPageSource().contains("Departure date is greater than the return date."), "Validation error not found");
    }
    private static void validateFlightPurchaseIncompleteFields(SoftAssert softAssert, String firstName,  String lastName,  String creditNumber){
        flightPurchase.inputFlightPurchase(firstName,  lastName,  creditNumber);
        softAssert.assertTrue(driver.getPageSource().contains(" Please fill all mandatory fields in red, and then resubmit the form."), "Validation error message not found: Please fill all mandatory fields");
    }
    private static void validateFlightPurchaseInvalidCard(SoftAssert softAssert, String firstName,  String lastName,  String creditNumber){
        flightPurchase.inputFlightPurchase(firstName,  lastName,  creditNumber);
        softAssert.assertTrue(driver.getPageSource().contains(" The credit card number is not valid"), "Validation error message not found: The credit card number is not valid");
    }

}
