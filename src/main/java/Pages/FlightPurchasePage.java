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

        //get user input to check validations
        String firstNameTxt = driver.findElement(textbox_firstName_FlightPurchase).getAttribute("value");
        String lastNameTxt = driver.findElement(textbox_lastName_FlightPurchase).getAttribute("value");
        String creditNumberTxt = driver.findElement(textbox_creditnumber_FlightPurchase).getAttribute("value");


        driver.findElement(button_submitButton_FlightPurchase).click();

        checkValidations(initialURL, firstNameTxt, lastNameTxt, creditNumberTxt);

    }


    public void checkValidations(String initialURL, String firstNameTxt, String lastNameTxt, String creditNumberTxt) {
        SoftAssert softAssert = new SoftAssert();
        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://localhost:8080/mtours/servlet/com.mercurytours.servlet.PurchaseServlet?procSub=1&pg=1";


        //Assert to check the user stays on page/is directed to the correct page; softAssert to check validation errors
        if (firstNameTxt.isEmpty() || lastNameTxt.isEmpty() || creditNumberTxt.isEmpty()) {
            Assert.assertEquals(currentURL, initialURL, "Form is submitted with invalid user input");
            softAssert.assertTrue(driver.getPageSource().contains("Error: Please fill all mandatory fields in red, and then resubmit the form."), "Validation error message not found: Please fill all mandatory fields");
        }
        if (!creditNumberTxt.matches("\\d+")) {
            Assert.assertEquals(currentURL, initialURL, "Form is submitted with invalid user input");
            softAssert.assertTrue(driver.getPageSource().contains("Error: The credit card number is not valid"), "Validation error message not found: The credit card number is not valid");
        } else {
            Assert.assertEquals(currentURL, expectedURL, "User is directed to the wrong page");
        }

        softAssert.assertAll();

    }
}