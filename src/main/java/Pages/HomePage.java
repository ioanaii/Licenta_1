package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public static void accessRegisterPage(WebDriver driver) {
        WebElement registerHref = driver.findElement(By.xpath("//a[@href='com.mercurytours.servlet.RegisterServlet']"));
        registerHref.click();
    }

    public static void accessLogInPage(WebDriver driver) {
        WebElement registerHref = driver.findElement(By.xpath("//a[@href='com.mercurytours.servlet.SignonServlet']"));
        registerHref.click();
    }

    public static void accessItinerary(WebDriver driver) {
        WebElement registerHref = driver.findElement(By.xpath("//a[@href='com.mercurytours.servlet.IteneraryServlet']"));
        registerHref.click();
    }
}