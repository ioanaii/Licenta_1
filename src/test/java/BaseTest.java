import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;

public class BaseTest {
    protected WebDriver driver;

//cross-browser testing setup: WebDriverManager, chromeOptions
    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) throws InterruptedException {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions optionsChrome = new ChromeOptions();
                optionsChrome.addArguments("--headless");
                driver = new ChromeDriver(optionsChrome);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions optionsEdge = new EdgeOptions();
                optionsEdge.addArguments("--headless");
                driver = new EdgeDriver(optionsEdge);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().driverVersion("0.33.0").setup();
                FirefoxOptions optionsFirefox = new FirefoxOptions();
                optionsFirefox.addArguments("--headless");
                driver = new FirefoxDriver(optionsFirefox);
                break;
            default:
                throw new IllegalArgumentException("Invalid browser: " + browser);
        }

        driver.get("http://localhost:8080/mtours/servlet/com.mercurytours.servlet.WelcomeServlet");
        Thread.sleep(500);
    }


    @AfterMethod
    public void tearDown() throws InterruptedException{
        Thread.sleep(500);
        if (driver != null) {
        driver.quit();
    }
    }


}
