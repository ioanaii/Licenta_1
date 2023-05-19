package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import java.util.List;


public class FlightSelectionPage {
    WebDriver driver;

    By radio_outFlightFlightSelectionPage = By.xpath("//input[@type='radio' and @name='outFlight']");
    By radio_inFlightFlightSelectionPage = By.xpath("//input[@type='radio' and @name='inFlight']");
    By button_submitButton_FlightSelectionPage = By.name("reserveFlights");

    public FlightSelectionPage(WebDriver driver) {
        this.driver = driver;
    }
    public void selectFlights(int outFlightIndex, int inFlightIndex) {
        List<WebElement> outFlightRadioButtons = driver.findElements(radio_outFlightFlightSelectionPage);
        int outFlightCount = outFlightRadioButtons.size();

        List<WebElement> inFlightRadioButtons = driver.findElements(radio_inFlightFlightSelectionPage);
        int inFlightCount = inFlightRadioButtons.size();


        if (outFlightIndex >= 0 && outFlightIndex < outFlightCount) {
            WebElement outFlightRadioButton = outFlightRadioButtons.get(outFlightIndex);
            outFlightRadioButton.click();
        } else {
            throw new IllegalArgumentException("Invalid outFlightIndex provided.");

        }

        if (inFlightIndex >= 0 && inFlightIndex < inFlightCount) {
            WebElement inFlightRadioButton = inFlightRadioButtons.get(inFlightIndex);
            inFlightRadioButton.click();
        } else {
            throw new IllegalArgumentException("Invalid outFlightIndex provided.");
        }

        driver.findElement(button_submitButton_FlightSelectionPage).click();

        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://localhost:8080/mtours/servlet/com.mercurytours.servlet.PurchaseServlet";
        Assert.assertEquals(currentURL, expectedURL, "User is directed to wrong page");
    }

}
