package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

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


        String initialURL = driver.getCurrentUrl();

        driver.findElement(textbox_firstName_FlightPurchase).sendKeys(firstName);
        driver.findElement(textbox_lastName_FlightPurchase).sendKeys(lastName);
        driver.findElement(textbox_creditnumber_FlightPurchase).sendKeys(creditNumber);
        driver.findElement(button_submitButton_FlightPurchase).click();

        String currentURL = driver.getCurrentUrl();

        if (initialURL == currentURL) {
            String firstNameFlightPurchase = driver.findElement(textbox_firstName_FlightPurchase).getText();
            String lastNameFlightPurchase = driver.findElement(textbox_lastName_FlightPurchase).getText();
            String creditNumberFlightPurchase = driver.findElement(textbox_creditnumber_FlightPurchase).getText();

            checkValidationErrors(firstNameFlightPurchase, lastNameFlightPurchase, creditNumberFlightPurchase);
        } else {
            String expectedURL = "http://localhost:8080/mtours/servlet/com.mercurytours.servlet.PurchaseServlet?procSub=1&pg=1";
            Assert.assertEquals(currentURL, expectedURL, "User is directed to the wrong page");
        }
    }


    public void checkValidationErrors(String firstNameFlightPurchase, String lastNameFlightPurchase, String creditNumberFlightPurchase) {
        SoftAssert softAssert = new SoftAssert();

        if (firstNameFlightPurchase.isEmpty() || lastNameFlightPurchase.isEmpty() || creditNumberFlightPurchase.isEmpty()) {
            softAssert.assertTrue(driver.getPageSource().contains("Error: Please fill all mandatory fields in red, and then resubmit the form."), "Validation error message not found: Please fill all mandatory fields");
        }
        if (!creditNumberFlightPurchase.matches("\\d+")) {
            softAssert.assertTrue(driver.getPageSource().contains("Error: The credit card number is not valid"), "Validation error message not found: The credit card number is not valid");
        } else {
            Assert.fail("Valid user input, form submission failed");
        }

        softAssert.assertAll();

    }
}