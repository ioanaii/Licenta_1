package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class LogInPage {

    WebDriver driver = null;

    By textbox_userName_LogInPage = By.name("userName");
    By textbox_password_LogInPage = By.name("password");
    By button_submitButton_LogInPage = By.name("login");

    public LogInPage(WebDriver driver){
        this.driver = driver;
    }

    public void inputLogIn(String username, String password){
        driver.findElement(textbox_userName_LogInPage).sendKeys(username);
        driver.findElement(textbox_password_LogInPage).sendKeys(password);

        String initialURL = driver.getCurrentUrl();

        driver.findElement(button_submitButton_LogInPage).click();

        String currentURL = driver.getCurrentUrl();


        String userNameLogIn = driver.findElement(textbox_userName_LogInPage).getText();
        String passwordLogIn = driver.findElement(textbox_password_LogInPage).getText();

    }

    public static void validLogIN(WebDriver driver){
        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://localhost:8080/mtours/servlet/com.mercurytours.servlet.ReservationServlet";
        Assert.assertEquals(currentURL, expectedURL);
    }

    public void invalidUsernameOrPassword(){
        Assert.assertEquals(driver.getPageSource().contains("Invalid User Name or Password."), true);
    }

    public void incompleteFields(){
        Assert.assertEquals(driver.getPageSource().contains("Please fill all fields."), true);
    }

    public void validationErrorsLogIn(){

       

    }

}
