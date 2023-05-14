package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class FlightSelectionPage {
    WebDriver driver;

    By radio_outFlightFlightSelectionPage = By.xpath("//input[@type='radio' and @name='outFlight");
    By radio_inFlightFlightSelectionPage = By.xpath("//input[@type='radio' and @name='inFlight");

    public FlightSelectionPage(WebDriver driver) {
        this.driver = driver;
    }
    public void test(){
        driver.findElements(radio_outFlightFlightSelectionPage).size();
        System.out.println(driver.findElements(radio_outFlightFlightSelectionPage).size());
    }
}
