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

    //to do: fix this
    public void removeSelected() {

        //TO DO: get checked items by 'name' and verify that removed items are not available after submissions
        List<WebElement> bookedFlights = driver.findElements(radio_bookedFlight_Itinerary);

        int bookedFlightsCount = bookedFlights.size();
        int checkedItemsCount = 0;
        System.out.println("checked items count:"+ bookedFlightsCount);

        for (int i = 0; i < bookedFlightsCount; i += 2) {
            bookedFlights.get(i).click();
            checkedItemsCount++;
        }

        //System.out.println("checked items count:"+ checkedItemsCount);

        driver.findElement(button_removeSelected_Itinerary).click();
    }

}
