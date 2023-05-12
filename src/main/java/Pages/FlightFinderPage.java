package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
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
            WebElement roundTripRadio = driver.findElement(By.cssSelector("input[type='radio'][name='tripType'][value='roundtrip']"));
            roundTripRadio.click();
        } else if (tripType.equalsIgnoreCase("oneway")) {
            WebElement oneWayRadio = driver.findElement(By.cssSelector("input[type='radio'][name='tripType'][value='oneway']"));
            oneWayRadio.click();
        }

        // Select pass count
        WebElement passCountDropdown = driver.findElement(By.name("passCount"));
        passCountDropdown.sendKeys(passCount);

        // Select departure from
        WebElement fromPortDropdown = driver.findElement(By.name("fromPort"));
        fromPortDropdown.sendKeys(fromPort);

        // Select departure month
        WebElement fromMonthDropdown = driver.findElement(By.name("fromMonth"));
        fromMonthDropdown.sendKeys(fromMonth);

        // Select departure day
        WebElement fromDayDropdown = driver.findElement(By.name("fromDay"));
        fromDayDropdown.sendKeys(fromDay);

        // Select arrival to
        WebElement toPortDropdown = driver.findElement(By.name("toPort"));
        toPortDropdown.sendKeys(toPort);

        // Select arrival month
        WebElement toMonthDropdown = driver.findElement(By.name("toMonth"));
        toMonthDropdown.sendKeys(toMonth);

        // Select arrival day
        WebElement toDayDropdown = driver.findElement(By.name("toDay"));
        toDayDropdown.sendKeys(toDay);

        // Select service class radio button
        if (serviceClass.equalsIgnoreCase("Coach")) {
            WebElement coachRadio = driver.findElement(By.cssSelector("input[type='radio'][name='servClass'][value='Coach']"));
            coachRadio.click();
        } else if (serviceClass.equalsIgnoreCase("Business")) {
            WebElement businessRadio = driver.findElement(By.cssSelector("input[type='radio'][name='servClass'][value='Business']"));
            businessRadio.click();
        } else if (serviceClass.equalsIgnoreCase("First")) {
            WebElement firstRadio = driver.findElement(By.cssSelector("input[type='radio'][name='servClass'][value='First']"));
            firstRadio.click();
        }

        // Select airline
        WebElement airlineDropdown = driver.findElement(By.name("airline"));
        airlineDropdown.sendKeys(airline);
    }

    public static void successfulBooking(WebDriver driver, String fromPort, String toPort){
        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://localhost:8080/mtours/servlet/com.mercurytours.servlet.ReservationServlet?procSub=1&pg=1";
        Assert.assertEquals(currentURL, expectedURL);
        String pageSource = driver.getPageSource();
        String expectedMessage1 = fromPort + "to" + toPort;
        Assert.assertTrue(pageSource.contains(expectedMessage1));
        String expectedMessage2 = toPort + "to" + fromPort;
        Assert.assertTrue(pageSource.contains(expectedMessage2));
    }

    public void verifyDepartureDateIsGreaterThanReturnDate() {
        WebElement fromMonthDropdown = driver.findElement(By.name("fromMonth"));
        String fromMonth = fromMonthDropdown.getAttribute("value");

        WebElement fromDayDropdown = driver.findElement(By.name("fromDay"));
        String fromDay = fromDayDropdown.getAttribute("value");

        WebElement toMonthDropdown = driver.findElement(By.name("toMonth"));
        String toMonth = toMonthDropdown.getAttribute("value");

        WebElement toDayDropdown = driver.findElement(By.name("toDay"));
        String toDay = toDayDropdown.getAttribute("value");

        int departureMonth = getMonthIndex(fromMonth);
        int departureDay = Integer.parseInt(fromDay);
        int returnMonth = getMonthIndex(toMonth);
        int returnDay = Integer.parseInt(toDay);

        if (departureMonth > returnMonth || (departureMonth == returnMonth && departureDay > returnDay)) {
            System.out.println("Departure date is greater than the return date. Test failed.");
            // Add your test failure logic here
        } else {
            System.out.println("Departure date is not greater than the return date. Test passed.");
            // Add your test success logic here
        }
    }

    private int getMonthIndex(String month) {
        switch (month) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return -1; // Invalid month
        }
    }

}
