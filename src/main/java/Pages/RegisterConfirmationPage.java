package Pages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class RegisterConfirmationPage {

    public static void confirmRegistration(WebDriver driver){
        Assert.assertEquals(driver.getPageSource().contains("Thank you for registering"), true);
    }
}
