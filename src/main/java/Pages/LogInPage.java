package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.testng.Assert;

public class LogInPage {

    WebDriver driver = null;

    By textbox_userName_LogInPage = By.name("userName");
    By textbox_password_LogInPage = By.name("password");
    By button_submitButton_LogInPage = By.name("login");

    private LogInPage(WebDriver driver){
        this.driver = driver;
    }

    public void inputLogIn(String username, String password){
        driver.findElement(textbox_userName_LogInPage).sendKeys(username);
        driver.findElement(textbox_password_LogInPage).sendKeys(password);
        driver.findElement(button_submitButton_LogInPage).click();
    }

    public static void validLogIN(WebDriver driver){
        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://localhost:8080/mtours/servlet/com.mercurytours.servlet.ReservationServlet";
        Assert.assertEquals(currentURL, expectedURL);
    }

    public void invalidLogIn(){
        Assert.assertEquals(driver.getPageSource().contains("Invalid User Name or Password."), true);

    }
}
