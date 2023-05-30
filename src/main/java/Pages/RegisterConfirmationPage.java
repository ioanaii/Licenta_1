package pages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.openqa.selenium.By;

public class RegisterConfirmationPage {
    WebDriver driver;
    By hyperlink_signIn_RegisterConfirmationPage = By.name("sign-in");
    public RegisterConfirmationPage(WebDriver driver){
        this.driver = driver;
    }
    public void confirmRegistration(WebDriver driver){
        Assert.assertEquals(driver.getPageSource().contains("Thank you for registering"), true);
    }

    public void signInRedirect(WebDriver driver){
        driver.findElement(hyperlink_signIn_RegisterConfirmationPage).click();

        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://localhost:8080/mtours/servlet/com.mercurytours.servlet.SignonServlet";
        Assert.assertEquals(currentURL, expectedURL);
    }
}
