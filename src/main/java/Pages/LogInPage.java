package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LogInPage {
    private static Properties prop = new Properties();
    private static String user1;
    private static String pass1;
    private static String user2;
    private static String pass2;

    WebDriver driver;
    By textbox_userName_LogInPage = By.name("userName");
    By textbox_password_LogInPage = By.name("password");
    By button_submitButton_LogInPage = By.name("login");

    public LogInPage(WebDriver driver){
        this.driver = driver;
    }
    public LogInPage(WebDriver driver, String user1, String pass1, String user2, String pass2) {
        this.driver = driver;
        this.user1 = user1;
        this.pass1 = pass1;
        this.user2 = user2;
        this.pass2 = pass2;
    }
    public void inputLogIn(String username, String password){

        String initialURL=driver.getCurrentUrl();

        driver.findElement(textbox_userName_LogInPage).sendKeys(username);
        driver.findElement(textbox_password_LogInPage).sendKeys(password);

        String user = driver.findElement(textbox_userName_LogInPage).getAttribute("value");
        String pass = driver.findElement(textbox_password_LogInPage).getAttribute("value");

        driver.findElement(button_submitButton_LogInPage).click();

        validationErrorsLogIn(initialURL,user,pass);
    }

    public void validationErrorsLogIn(String initialURL, String user, String pass) {
        SoftAssert softAssert = new SoftAssert();
        String currentURL = driver.getCurrentUrl();
        String expectedURL="http://localhost:8080/mtours/servlet/com.mercurytours.servlet.ReservationServlet";

        // Check for validation errors
        if (user.equals(user2) && pass.equals(pass1)) {
            Assert.assertEquals(currentURL, initialURL, "Invalid login successful");
            softAssert.assertTrue(driver.getPageSource().contains("Invalid User Name or Password."), "Validation error message for incorrect password not found");
        } else if (user.equals(user1) && pass.equals(pass2)) {
            Assert.assertEquals(currentURL, initialURL, "Invalid login successful");
            softAssert.assertTrue(driver.getPageSource().contains("Invalid User Name or Password."), "Validation error message for incorrect password not found");
        } else if (user.isEmpty() || pass.isEmpty()) {
            Assert.assertEquals(currentURL, initialURL, "Invalid login successful");
            softAssert.assertTrue(driver.getPageSource().contains("Please fill all fields"), "Validation error message for incomplete fields not found");
        } else {
            Assert.assertEquals(currentURL, expectedURL, "User is directed to the wrong page");
        }
        softAssert.assertAll();
    }


}
