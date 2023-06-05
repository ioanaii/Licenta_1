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
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    //cross-browser testing setup: WebDriverManager, chromeOptions
    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        WebDriver driver = createDriver(browser);
        driverThreadLocal.set(driver);
    }

    @AfterMethod
    public void tearDown() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
        System.out.println("Test completed successfully");
    }

    private WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions optionsChrome = new ChromeOptions();
                optionsChrome.addArguments("--headless");
                return new ChromeDriver(optionsChrome);
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions optionsEdge = new EdgeOptions();
                optionsEdge.addArguments("--headless");
                return new EdgeDriver(optionsEdge);
            case "firefox":
                WebDriverManager.firefoxdriver().driverVersion("0.33.0").setup();
                FirefoxOptions optionsFirefox = new FirefoxOptions();
                optionsFirefox.addArguments("--headless");
                return new FirefoxDriver(optionsFirefox);
            default:
                throw new IllegalArgumentException("Invalid browser: " + browser);
        }
    }
}
