package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        WebDriver driver;
       
        Wrappers wrap=new Wrappers(driver);
        SoftAssert assrt= new SoftAssert();
        boolean status=false;


        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() throws InterruptedException {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
                
        }

@Test

public void testCase01() throws InterruptedException{
       
        driver.get("https://www.youtube.com/");
        Wrappers wrap=new Wrappers(driver);
        status=wrap.urlcheck("https://www.youtube.com/");
        assrt.assertTrue(status,"Failure while Url check");
        wrap.Footerclick("About");
        assrt.assertAll();
        
}


@Test

public void testCase02()throws NoSuchElementException,InterruptedException{
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe("https://www.youtube.com/"));
        wrap.clktab("Movies");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1/span[text()='Movies']")));
        wrap.scrollrght("Top selling");
        status=wrap.jonrchk("Top selling");
        assrt.assertTrue(status,"Failure in movie jonor checking");
        status=wrap.sensor_chk("Top selling","A");
        assrt.assertTrue(status,"Failure in movie catagory visisblity");
        assrt.assertAll();
}
@Test

public void testCase03()throws  NoSuchElementException,InterruptedException{
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        wrap.clktab("Music");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1/yt-formatted-string[text()='Music']")));
        wrap.scrollrght("Biggest Hits");
        status=wrap.titl_print("Biggest Hits");
        assrt.assertTrue(status, "Failure: Tract containing more than 50 songs");
        assrt.assertAll();
}
@Test

public void testCase04()throws NullPointerException{
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        wrap.clktab("News");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@id='dismissible']//ytd-post-renderer")));
        int res=wrap.likecount("Latest news posts");
        System.out.println("Total Likes Count :"+res);
        assrt.assertAll();
}
@Test(dataProvider = "excelData")
public void testCase05(String searchtext){
      
        wrap.viewscount(searchtext);
}


        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}