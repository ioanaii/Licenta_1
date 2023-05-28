package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import java.util.concurrent.TimeUnit;

public class FlightPurchasePage {
    WebDriver driver;
    By textbox_firstName_FlightPurchase = By.name("passFirst0");
    By textbox_lastName_FlightPurchase = By.name("passLast0");
    By textbox_creditnumber_FlightPurchase = By.name("creditnumber");
    By button_submitButton_FlightPurchase = By.name("buyFlights");

    public FlightPurchasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void inputFlightPurchase(String firstName, String lastName, String creditNumber) {

        driver.findElement(textbox_firstName_FlightPurchase).sendKeys(firstName);
        driver.findElement(textbox_lastName_FlightPurchase).sendKeys(lastName);
        driver.findElement(textbox_creditnumber_FlightPurchase).sendKeys(creditNumber);

        driver.findElement(button_submitButton_FlightPurchase).click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}