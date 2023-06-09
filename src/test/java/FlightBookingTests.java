import utils.*;
import pages.*;


import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class FlightBookingTests extends BaseTest{
    private WebDriver driver = getDriver();

    @DataProvider
    public Object[][] propertiesTestData() {
        return DataLoader.loadFromPropertiesFile("src/main/resources/test2.properties", "username", "password", "username2","password2");

    }

    @Test (dataProvider = "propertiesTestData")
    public void flightBooking_roundTrip_Test(Object[] data) {
        String user1 = (String) data[0];
        String pass1 = (String) data[1];

        WebDriver driver = getDriver();
        HomePage homePage = new HomePage(driver);

        //Set up - adauga user cu care sa ne autentificam
        RegisterPage registerForm = new RegisterPage(driver);
        homePage.accessPage("register");
        //username sa fie unic
        user1+= UUID.randomUUID().toString();
        registerForm.inputRegisterForm(user1, pass1, pass1);

        //login
        LogInPage logIn = new LogInPage(driver);
        FlightFinderPage flightFind = new FlightFinderPage(driver);
        FlightPurchasePage flightPurchase = new FlightPurchasePage(driver);
        FlightSelectionPage flightSelect = new FlightSelectionPage(driver);
        ItineraryPage itinerary = new ItineraryPage(driver);

        homePage.accessPage("signOn");
        logIn.inputLogIn(user1, pass1);

        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Test itinerariy
        flightFind.enterFlightDetails("roundtrip", "2", "Frankfurt", "Feb", "21", "Acapulco", "Feb", "21", "Coach", "No Preference");
        flightSelect.selectFlights(1, 2);
        flightPurchase.inputFlightPurchase("John", "Doe", "1123");

        homePage.accessPage("itinerary");
        Assert.assertTrue(driver.getPageSource().contains("Frankfurt to Acapulco"));
        itinerary.removeSelected();
        Assert.assertFalse(driver.getPageSource().contains("Frankfurt to Acapulco"));
    }

    @Test (dataProvider = "propertiesTestData")
    public void flightBooking_oneWay_Test(Object[] data) {
        String user2 = (String) data[2];
        String pass2 = (String) data[3];

        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);

        //Set up - adauga user cu care sa ne autentificam
        RegisterPage registerForm = new RegisterPage(driver);
        homePage.accessPage("register");
        //username sa fie unic
        user2+= UUID.randomUUID().toString();
        registerForm.inputRegisterForm(user2, pass2, pass2);

        LogInPage logIn = new LogInPage(driver);
        FlightFinderPage flightFind = new FlightFinderPage(driver);
        FlightPurchasePage flightPurchase = new FlightPurchasePage(driver);
        FlightSelectionPage flightSelect = new FlightSelectionPage(driver);
        ItineraryPage itinerary = new ItineraryPage(driver);

        homePage.accessPage("signOn");
        logIn.inputLogIn(user2, pass2);

        flightFind.enterFlightDetails("oneway", "2", "Frankfurt", "Feb", "21", "Acapulco", "Feb", "21", "Business", "Unified Airlines");
        flightSelect.selectFlightsOneWay(0);
        flightPurchase.inputFlightPurchase("John", "Doe", "1123");

        homePage.accessPage("itinerary");

        Assert.assertTrue(driver.getPageSource().contains("Frankfurt to Acapulco"));
        itinerary.removeAll();
        Assert.assertFalse(driver.getPageSource().contains("Frankfurt to Acapulco"));
    }

    @Test (dataProvider = "propertiesTestData")
    public void flightBooking_invalidDates_Test(Object[] data){
        String user1 = (String) data[0];
        String pass1 = (String) data[1];
        String user2 = (String) data[2];
        String pass2 = (String) data[3];

        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        LogInPage logIn = new LogInPage(driver);

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

        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        LogInPage logIn = new LogInPage(driver);
        FlightFinderPage flightFind = new FlightFinderPage(driver);
        FlightSelectionPage flightSelect = new FlightSelectionPage(driver);

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

    private void validateFlightFinderFilters(SoftAssert softAssert, String tripType, String passCount, String fromPort, String fromMonth, String fromDay,
                                                    String toPort, String toMonth, String toDay, String serviceClass, String airline){

        WebDriver driver = getDriver();

        FlightFinderPage flightFind = new FlightFinderPage(driver);

        flightFind.enterFlightDetails(tripType, passCount,  fromPort,  fromMonth,  fromDay, toPort,  toMonth,  toDay,  serviceClass,  airline);
        softAssert.assertTrue(driver.getPageSource().contains("Departure date is greater than the return date."), "Validation error not found");
    }
    private void validateFlightPurchaseIncompleteFields(SoftAssert softAssert, String firstName,  String lastName,  String creditNumber){

        WebDriver driver = getDriver();

        FlightPurchasePage flightPurchase = new FlightPurchasePage(driver);

        flightPurchase.inputFlightPurchase(firstName,  lastName,  creditNumber);
        softAssert.assertTrue(driver.getPageSource().contains(" Please fill all mandatory fields in red, and then resubmit the form."), "Validation error message not found: Please fill all mandatory fields");
    }
    private void validateFlightPurchaseInvalidCard(SoftAssert softAssert, String firstName,  String lastName,  String creditNumber){
        WebDriver driver = getDriver();

        FlightPurchasePage flightPurchase = new FlightPurchasePage(driver);

        flightPurchase.inputFlightPurchase(firstName,  lastName,  creditNumber);
        softAssert.assertTrue(driver.getPageSource().contains(" The credit card number is not valid"), "Validation error message not found: The credit card number is not valid");
    }

}
