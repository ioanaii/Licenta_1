package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
public class RegisterPage {

    WebDriver driver = null;
    By textbox_firstName_registerForm = By.name("firstName");
    By textbox_lastName_registerForm = By.name("lastName");
    By textbox_phone_registerForm = By.name("phone");
    By textbox_email_registerForm = By.name("email");
    By textbox_address1_registerForm = By.name("address1");
    By textbox_address2_registerForm = By.name("address2");
    By textbox_city_registerForm = By.name("city");
    By textbox_state_registerForm = By.name("state");
    By textbox_postalCode_registerForm = By.name("postalCode");
    By dropdown_country_registerForm = By.name("country");
    By textbox_userName_registerForm = By.name("userName");
    By textbox_passwordTxt_registerForm = By.name("password");
    By textbox_confirmPasswordTxt_registerForm = By.name("confirmPassword");
    By button_submitButton_registerForm = By.name("register");

    public RegisterPage(WebDriver driver){
        this.driver = driver;
    }



    public void allFieldsRegistrationForm(String firstName, String lastName, String phone, String email,
                                          String address1, String address2, String city, String state, String postalCode,
                                          String country, String username, String password, String confirmPassword)
    {

        // Fill in the form data
        driver.findElement(textbox_firstName_registerForm).sendKeys(firstName);
        driver.findElement(textbox_lastName_registerForm).sendKeys(lastName);
        driver.findElement(textbox_phone_registerForm).sendKeys(phone);
        driver.findElement(textbox_email_registerForm).sendKeys(email);
        driver.findElement(textbox_address1_registerForm).sendKeys(address1);
        driver.findElement(textbox_address2_registerForm).sendKeys(address2);
        driver.findElement(textbox_city_registerForm).sendKeys(city);
        driver.findElement(textbox_state_registerForm).sendKeys(state);
        driver.findElement(textbox_postalCode_registerForm).sendKeys(postalCode);
        driver.findElement(dropdown_country_registerForm).click();

        WebElement countryDropdown = driver.findElement(dropdown_country_registerForm);
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText(country);

        driver.findElement(textbox_userName_registerForm).sendKeys(username);
        driver.findElement(textbox_passwordTxt_registerForm).sendKeys(password);
        driver.findElement(textbox_confirmPasswordTxt_registerForm).sendKeys(confirmPassword);

        // Submit the form
        driver.findElement(button_submitButton_registerForm).click();
    }

    public void inputRegisterForm(String username, String password, String confirmPassword){
        driver.findElement(textbox_userName_registerForm).sendKeys(username);
        driver.findElement(textbox_passwordTxt_registerForm).sendKeys(password);
        driver.findElement(textbox_confirmPasswordTxt_registerForm).sendKeys(confirmPassword);
        driver.findElement(button_submitButton_registerForm).click();
    }
    public static void successfulRegistration(WebDriver driver, String username){
        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://localhost:8080/mtours/servlet/com.mercurytours.servlet.RegisterServlet?procSub=1";
        Assert.assertEquals(currentURL, expectedURL);
        String pageSource = driver.getPageSource();
        String expectedMessage = "Note: Your user name is " + username;
        Assert.assertTrue(pageSource.contains(expectedMessage));
    }

    public static void existingUser(WebDriver driver){
        Assert.assertEquals(driver.getPageSource().contains("Note: Error - The user name has been already used, please enter a new name"), true);
    }
    public static void incompleteData(WebDriver driver){
        Assert.assertEquals(driver.getPageSource().contains("Please fill all fields bellow to complete the registration."), true);
    }

    public static void invalidPassConfirmation(WebDriver driver){
        Assert.assertEquals(driver.getPageSource().contains("Note: The confirmed password must be the same as the desired password.."), true);
    }

     }


