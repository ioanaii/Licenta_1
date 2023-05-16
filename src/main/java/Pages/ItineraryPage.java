package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


public class ItineraryPage {


    WebDriver driver;
    By radio_bookedFlight_Itinerary = By.xpath("//input[@type='radio' and @value='out']");
    By button_removeSelected_Itinerary = By.name("removeSelected");
    By button_removeAll_Itinerary = By.name("removeAll");

    public ItineraryPage (WebDriver driver) {
        this.driver = driver;
    }

    public void removeSelected() {
        List<WebElement> bookedFlights = driver.findElements(radio_bookedFlight_Itinerary);
        int bookedFlightsCount = bookedFlights.size();

        // Select a subset of radio buttons, e.g., select the first and third radio buttons
        for (int i = 0; i < bookedFlightsCount; i += 2) {
            bookedFlights.get(i).click();
        }

        // Perform actions on the selected radio buttons, such as clicking a remove button
        driver.findElement(button_removeSelected_Itinerary).click();
    }
}
