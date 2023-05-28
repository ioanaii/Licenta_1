package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
public class HomePage {
    WebDriver driver;

    By button_SignOn_SignOff = By.xpath("//a[@href='com.mercurytours.servlet.SignonServlet']");
    By button_Register = By.xpath("//a[@href='com.mercurytours.servlet.RegisterServlet']");
    By button_Home = By.xpath("//a[@href='com.mercurytours.servlet.WelcomeServlet']");
    By button_Flights = By.xpath("//a[@href='com.mercurytours.servlet.ReservationServlet']");
    By button_Itinerary = By.xpath("//a[@href='com.mercurytours.servlet.IteneraryServlet']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public static void registerPageButton(WebDriver driver) {
        WebElement registerHref = driver.findElement(By.xpath("//a[@href='com.mercurytours.servlet.RegisterServlet']"));
        registerHref.click();
    }

    public void accessPage(String page){

        switch (page){
            case ("home"):
                driver.findElement(button_Home).click();
                break;
            case("signOn"), ("signOff"):
                driver.findElement(button_SignOn_SignOff).click();
                break;
            case("register"):
                driver.findElement(button_Register).click();
                break;
            case("flight"):
                driver.findElement(button_Flights).click();
                break;
            case("itinerary"):
                driver.findElement(button_Itinerary).click();
                break;
            default:
                System.out.println("incorrect page name");
        }
    }
}