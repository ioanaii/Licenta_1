package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import java.util.ArrayList;

import java.util.List;


public class ItineraryPage {

    WebDriver driver;
    By radio_bookedFlight_Itinerary = By.xpath("//input[@type='checkbox' and @value='on']");
    By button_removeSelected_Itinerary = By.name("removeSelected");
    By button_removeAll_Itinerary = By.name("removeAll");

    public ItineraryPage (WebDriver driver) {
        this.driver = driver;
    }

    public void removeSelected() {

        List<WebElement> bookedFlights = driver.findElements(radio_bookedFlight_Itinerary);
        int bookedFlightsCount = bookedFlights.size();
        int checkedItemsCount = 0;

        System.out.println("bookedFlightsCount: " + bookedFlightsCount);

        List<String> selectedFlights = new ArrayList<>();

        for (int i = 0; i < bookedFlightsCount; i++) {
            if (i % 2 == 0) {  // Select every second radio button
                bookedFlights.get(i).click();
                checkedItemsCount++;
            }
        }

        System.out.println("Checked items count after selection: " + checkedItemsCount);

        driver.findElement(button_removeSelected_Itinerary).click();

        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://localhost:8080/mtours/servlet/com.mercurytours.servlet.IteneraryServlet?procSub=1";
        Assert.assertEquals(currentURL, expectedURL, "User is directed to the wrong page");
    }

    public void removeAll(){
        SoftAssert softAssert = new SoftAssert();
        driver.findElement(button_removeAll_Itinerary).click();

        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://localhost:8080/mtours/servlet/com.mercurytours.servlet.IteneraryServlet?procSub=1";
        Assert.assertEquals(currentURL, expectedURL, "User is directed to the wrong page");
        softAssert.assertTrue(driver.getPageSource().contains("Note: There are no orders for this account"), "Text not found");
    }

}


