package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class LogInPage {

    WebDriver driver;
    By textbox_userName_LogInPage = By.name("userName");
    By textbox_password_LogInPage = By.name("password");
    By button_submitButton_LogInPage = By.name("login");

    public LogInPage(WebDriver driver){
        this.driver = driver;
    }

    public void inputLogIn(String username, String password){
        driver.findElement(textbox_userName_LogInPage).sendKeys(username);
        driver.findElement(textbox_password_LogInPage).sendKeys(password);
        driver.findElement(button_submitButton_LogInPage).click();

    }

}
