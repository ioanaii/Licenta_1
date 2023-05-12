package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class HomePage {
    public static void accessRegisterPage(WebDriver driver) {
        WebElement registerHref = driver.findElement(By.xpath("//a[@href='com.mercurytours.servlet.RegisterServlet']"));
        registerHref.click();
    }

    public static void accessLogInPage(WebDriver driver) {
        WebElement registerHref = driver.findElement(By.xpath("//a[@href='com.mercurytours.servlet.RegisterServlet']"));
        registerHref.click();
    }
}
