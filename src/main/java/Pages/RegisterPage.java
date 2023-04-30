package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class RegisterPage extends HomePage{

    WebDriver driver = null;
    By textbox_firstName_registerForm = By.name("firstName");
    By textbox_lastName_registerForm = By.name("lastName");
    By textbox_userName_registerForm = By.name("userName");
    By textbox_passwordTxt_registerForm = By.name("password");
    By textbox_confirmPasswordTxt_registerForm = By.name("confirmPassword");
    By button_submitButton_registerForm = By.name("register");

    public RegisterPage(WebDriver driver){
        this.driver = driver;
    }
    public void inputRegisterForm(String firstName, String lastName, String userName, String password, String confirmPassword){
        driver.findElement(textbox_firstName_registerForm).sendKeys(firstName);
        driver.findElement(textbox_lastName_registerForm).sendKeys(lastName);
        driver.findElement(textbox_userName_registerForm).sendKeys(userName);
        driver.findElement(textbox_passwordTxt_registerForm).sendKeys(password);
        driver.findElement(textbox_confirmPasswordTxt_registerForm).sendKeys(confirmPassword);
    }
    public void clickSubmitButton () {
        driver.findElement(button_submitButton_registerForm).click();
    }

    public static void existingUser(WebDriver driver){
        Assert.assertEquals(driver.getPageSource().contains("Note: Error - The user name has been already used, please enter a new name"), true);
    }
    public static void incompleteData(WebDriver driver){
        Assert.assertEquals(driver.getPageSource().contains("Please fill all fields bellow to complete the registration.e"), true);
    }
     }


