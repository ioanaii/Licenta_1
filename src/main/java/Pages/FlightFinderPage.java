package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class FlightFinderPage {
    WebDriver driver = null;
    By radioButton_flightType_roundTrip_FlightFinder = By.cssSelector("input[type='radio'][name='tripType'][value='roundtrip']");
    By radioButton_flightType_oneWay_FlightFinder = By.cssSelector("input[type='radio'][name='tripType'][value='oneway']");
    By dropdown_passCount_FlightFinder = By.name("passCount");
    By dropdown_fromPort_FlightFinder = By.name("fromPort");
    By dropdown_departureMonth_FlightFinder = By.name("fromMonth");
    By dropdown_departureDay_FlightFinder = By.name("fromDay");
    By dropdown_toPort_FlightFinder = By.name("toPort");
    By dropdown_arrivalMonth_FlightFinder = By.name("toMonth");
    By dropdown_arrivalDay_FlightFinder = By.name("toDay");
    By radioButtons_serviceClass_coach_FlightFinder = By.cssSelector("input[type='radio'][name='servClass'][value='Coach']");
    By radioButtons_serviceClass_business_FlightFinder = By.cssSelector("input[type='radio'][name='servClass'][value='Business']");
    By radioButtons_serviceClass_first_FlightFinder = By.cssSelector("input[type='radio'][name='servClass'][value='First']");
    By dropdown_airline_FlightFinder = By.name("airline");

    public FlightFinderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterFlightDetails(String tripType, String passCount, String fromPort, String fromMonth, String fromDay,
                                   String toPort, String toMonth, String toDay, String serviceClass, String airline) {
        // Select trip type radio button
        if (tripType.equalsIgnoreCase("roundtrip")) {
            driver.findElement(radioButton_flightType_roundTrip_FlightFinder).click();

        } else if (tripType.equalsIgnoreCase("oneway")) {
            driver.findElement(radioButton_flightType_oneWay_FlightFinder).click();
        }

        // Select pass count
        Select passCountDropdown = new Select(driver.findElement(dropdown_passCount_FlightFinder));
        passCountDropdown.selectByVisibleText(passCount);

        // Select departure from
        Select fromPortDropdown = new Select(driver.findElement(dropdown_fromPort_FlightFinder));
        fromPortDropdown.selectByVisibleText(fromPort);

        // Select departure month
        Select fromMonthDropdown = new Select(driver.findElement(dropdown_departureMonth_FlightFinder));
        fromMonthDropdown.selectByVisibleText(fromMonth);

        // Select departure day
        Select fromDayDropdown = new Select(driver.findElement(dropdown_departureDay_FlightFinder));
        fromDayDropdown.selectByVisibleText(fromDay);

        // Select arrival to
        Select toPortDropdown = new Select(driver.findElement(dropdown_toPort_FlightFinder));
        toPortDropdown.selectByVisibleText(toPort);

        // Select arrival month
        Select toMonthDropdown = new Select(driver.findElement(dropdown_arrivalMonth_FlightFinder));
        toMonthDropdown.selectByVisibleText(toMonth);

        // Select arrival day
        Select toDayDropdown = new Select(driver.findElement(dropdown_arrivalDay_FlightFinder));
        toDayDropdown.selectByVisibleText(toDay);

        // Select service class radio button
        if (serviceClass.equalsIgnoreCase("Coach")) {
            driver.findElement(radioButtons_serviceClass_coach_FlightFinder).click();
        } else if (serviceClass.equalsIgnoreCase("Business")) {
            driver.findElement(radioButtons_serviceClass_business_FlightFinder).click();
        } else if (serviceClass.equalsIgnoreCase("First")) {
            driver.findElement(radioButtons_serviceClass_first_FlightFinder).click();
        }

        // Select airline
        Select airlineDropdown = new Select (driver.findElement(dropdown_airline_FlightFinder));
        airlineDropdown.selectByVisibleText(airline);

        String selectedFromPort = fromPortDropdown.getFirstSelectedOption().getText();
        String selectedToPort = toPortDropdown.getFirstSelectedOption().getText();

        String selectedFromMonth = fromMonthDropdown.getFirstSelectedOption().getText();
        String selectedFromDay = fromDayDropdown.getFirstSelectedOption().getText();
        String selectedToMonth = toMonthDropdown.getFirstSelectedOption().getText();
        String selectedToDay = toDayDropdown.getFirstSelectedOption().getText();

        successfulBooking(selectedFromPort, selectedToPort);
        verifyDepartureDateIsGreaterThanReturnDate(selectedFromMonth, selectedFromDay, selectedToMonth, selectedToDay);
    }


    //To DO: fix this
    public String successfulBooking(String selectedFromPort, String selectedToPort){
        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://localhost:8080/mtours/servlet/com.mercurytours.servlet.ReservationServlet";
        Assert.assertEquals(currentURL, expectedURL);
        String pageSource = driver.getPageSource();

        String expectedMessage1 = selectedFromPort + "to" + selectedToPort;
        if (!pageSource.contains(expectedMessage1)) {
            return "Expected Message 1 not found";
        }

        String expectedMessage2 = selectedToPort + "to" + selectedFromPort;
        if (!pageSource.contains(expectedMessage2)) {
            return "Expected Message 2 not found";
        }

        return "Success";

    }

    public void verifyDepartureDateIsGreaterThanReturnDate (String selectedFromMonth, String selectedFromDay, String selectedToMonth, String selectedToDay) {

        int departureMonth = getMonthIndex(selectedFromMonth);
        int departureDay = Integer.parseInt(selectedFromDay);
        int returnMonth = getMonthIndex(selectedToMonth);
        int returnDay = Integer.parseInt(selectedToDay);

        if (departureMonth > returnMonth || (departureMonth == returnMonth && departureDay > returnDay)) {
            System.out.println("Departure date is greater than the return date. Test failed.");
            // Add your test failure logic here
        } else {
            System.out.println("Departure and return dates are valid. Test passed.");
            // Add your test success logic here
        }
    }

    private int getMonthIndex(String month) {
        switch (month) {
            case "Jan":
                return 1;
            case "Feb":
                return 2;
            case "Mar":
                return 3;
            case "Apr":
                return 4;
            case "May":
                return 5;
            case "Jun":
                return 6;
            case "Jul":
                return 7;
            case "Aug":
                return 8;
            case "Sep":
                return 9;
            case "Oct":
                return 10;
            case "Nov":
                return 11;
            case "Dec":
                return 12;
            default:
                return -1; // Invalid month
        }
    }

}
