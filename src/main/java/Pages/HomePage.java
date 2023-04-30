package Pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.UUID;
public class HomePage {
    public static void accessRegisterPage(WebDriver driver) {
        WebElement registerHref = driver.findElement(By.xpath("//a[@href='com.mercurytours.servlet.RegisterServlet']"));
        registerHref.click();
    }
}
