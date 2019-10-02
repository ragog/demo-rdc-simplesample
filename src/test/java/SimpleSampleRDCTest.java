import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.ResultReporter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SimpleSampleRDCTest {

    private RemoteWebDriver driver;
    private String rdcEndpoint = System.getenv("RDC_ENDPOINT");
    private String apiKey = System.getenv("RDC_API_KEY");
    private String platformName = System.getenv("PLATFORM_NAME");
    private String platformVersion = System.getenv("PLATFORM_VERSION");

    @BeforeMethod
    public void setup() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("testobject_api_key", apiKey);
        desiredCapabilities.setCapability("platformName", platformName);
        desiredCapabilities.setCapability("platformVersion", platformVersion);
        this.driver = new RemoteWebDriver(new URL(rdcEndpoint), desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void loginTest() throws InterruptedException {
        driver.get("https://www.saucedemo.com");
        Thread.sleep(15000);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null) {
            String sessionId = driver.getSessionId().toString();
            boolean status = result.isSuccess();

            ResultReporter reporter = new ResultReporter();
            reporter.saveTestStatus(sessionId, status);

            driver.quit();
        }

    }


}