package legoApp.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import legoApp.driverfactory.DriverFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static legoApp.model.Login.getUrl;

public class BaseTest {

    public ThreadLocal<WebDriver> driver = new ThreadLocal<> ( );
    public DriverFactory df = new DriverFactory ( );
    public Properties pr = ConfigReader.getPropertyObject ( );

    public WebDriver getDriver() {
        return driver.get ( );
    }

    public String setBrowser() {
        return pr.getProperty ( "browser" );
    }

    public void openBrowser() throws IOException {

        driver = df.launchBrowser ( setBrowser ( ) );
        driver.get ( ).manage ( ).timeouts ( ).implicitlyWait ( 10, TimeUnit.SECONDS );
        System.out.println ( "Browser Opened successfully" );
    }

    public void setViewPort(int width, int height) {
        Dimension dimension = new Dimension ( width, height );
        driver.get ( ).manage ( ).window ( ).setSize ( dimension );
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws IOException {
        openBrowser ( );
        setViewPort ( 1440, 1080 );
        navigateToUrl (  );

    }

    public void navigateToUrl() {

        driver.get ( ).get ( getUrl ( ) );
    }

    @AfterMethod(alwaysRun = true)
    public void closeAndQuitDriver() {
        driver.get ().manage ( ).timeouts ( ).implicitlyWait ( 5L, TimeUnit.SECONDS );
        driver.get().close();
    }




}
