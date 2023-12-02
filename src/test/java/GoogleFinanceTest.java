import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GoogleFinanceTest {

    private WebDriver driver;
    private String baseUrl;
    private final List<String> expectedStockSymbols = Arrays.asList("NFLX", "MSFT", "TSLA", "AAPL", "META", "AMZN");

    @BeforeClass
    public void setUp() {

        WebDriverManager.chromedriver().driverVersion("119.0.6045.200").setup();
        driver = new ChromeDriver();
        baseUrl = "https://www.google.com/finance";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testGoogleFinance() {
        driver.get(baseUrl);

        //Assert page title
        Assert.assertEquals(driver.getTitle(),
                "Google Finance - Stock Market Prices, Real-time Quotes & Business News");

        // Retrieve stock symbols
        List<WebElement> stockElements = driver.findElements(By.xpath
                ("//*[@aria-labelledby='smart-watchlist-title']//div[contains(@class, 'COaKTb')]"));
        Set<String> retrievedStockSymbols = new HashSet<>();
        for (WebElement element : stockElements) {
            retrievedStockSymbols.add(element.getText());
        }

        //Adding expected symbols to HashSet to compare with retrieved Symbols
        Set<String> expectedSymbolsSet = new HashSet<>(expectedStockSymbols);

        // Print symbols in webpage but not in expected list
        System.out.println("Symbols in webpage but not in expected list:");
        for (String symbol : retrievedStockSymbols) {
            if (!expectedSymbolsSet.contains(symbol)) {
                System.out.println(symbol);
            }
        }

        // Print symbols in expected list but not in webpage
        System.out.println("Symbols in expected list but not in webpage:");
        for (String symbol : expectedStockSymbols) {
            if (!retrievedStockSymbols.contains(symbol)) {
                System.out.println(symbol);
            }
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}