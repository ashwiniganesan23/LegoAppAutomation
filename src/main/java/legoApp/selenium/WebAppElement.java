package legoApp.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import legoApp.exceptions.WebElementFailedException;
import legoApp.exceptions.WebElementInstantiationException;
import legoApp.reporting.Logger;
import legoApp.utils.ThreadWait;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class WebAppElement {

    protected final Logger logger = Logger.getLogger ( this.getClass ( ) );

    private final WebDriver driver;
    private WebElement webElement;
    LocateBy locate;
    private String value;

    public WebAppElement(WebDriver driver, LocateBy locate, String value) {
        this.driver = driver;
        this.locate = locate;
        this.value = value;
    }


    public WebAppElement(WebDriver driver) {
        this.driver = driver;
    }

    public WebAppElement(WebDriver driver, WebElement element) {
        this.webElement = element;
        this.driver = driver;
    }

    public By getBy() {

        if ( value == null || value.isEmpty ( ) ) {
            throw new WebElementInstantiationException ( "The identifier used to locate the element is null." );
        }

        switch (locate) {
            case XPATH:
                return By.xpath ( value );
            case CSS:
                return By.cssSelector ( value );
            case CLASS:
                return By.className ( value );
            case ID:
                return By.id ( value );
            case TAG:
                return By.tagName ( value );
            case NAME:
                return By.name ( value );
            case TEXT:
                return By.xpath ( "//*[normalize-space(text())='" + value + "']" );
            default: {
                System.out.println ( ("None of the findBys are set, hence cant create element") );//to add a logger
                throw new WebElementInstantiationException ( "None of the findBys are set, hence cant create element" );
            }
        }
    }

    public WebAppElement findElement(LocateBy locator, String value) {
        return new WebAppElement ( driver, locator, value );
    }

    public static synchronized List<WebAppElement> toWebAppElementList(WebDriver driver, List<WebElement> webElements) {
        List<WebAppElement> webAppElements = new ArrayList<> ( );
        webElements.stream ( ).forEach ( element -> webAppElements.add ( new WebAppElement ( driver, element ) ) );
        return webAppElements;
    }

    public List<WebAppElement> findElements(LocateBy locator, String value) {
        List<WebElement> elements = new WebAppElement ( driver, locator, value ).getWebElements ( );
        return WebAppElement.toWebAppElementList ( driver, elements );
    }

    public WebElement getWebElement() {
        if ( webElement == null ) {
            WebElement element = driver.findElement ( getBy ( ) );
            if ( element == null ) {
                 logger.error("Unable to find WebElement with " + toString());
                throw new WebElementFailedException ( "Unable to find WebElement with " + toString ( ) );
            }
              logger.info("WebElement Found, " + toString());
            return element;
        }
        return webElement;

    }

    public List<WebElement> getWebElements() {
        List<WebElement> element = driver.findElements ( getBy ( ) );
        if ( element == null || element.size ( ) <= 0 ) {
              logger.error("Unable to find WebElements with " + toString());
            throw new WebElementFailedException ( "Unable to find WebElements with " + toString ( ) );
        }
          logger.info("Found elements " + toString());
        return element;
    }

    public void click() {
        new ThreadWait ( ).delay ( 100, TimeUnit.MILLISECONDS );
        getWebElement ( ).click ();
        new ThreadWait ( ).delay ( 100, TimeUnit.MILLISECONDS );
    }


    public void implicitlyWait(int timeOut, TimeUnit timeUnit) {
        driver.manage ( ).timeouts ( ).implicitlyWait ( timeOut, timeUnit );
    }


    private JavascriptExecutor getJavascriptExecutor() {
        return ((JavascriptExecutor) driver);
    }


    public void sendKeys(String keysToSend) {
        ThreadWait.delay ( 200, TimeUnit.MILLISECONDS );
        //focusAndClick();
        getWebElement ( ).sendKeys ( keysToSend );
        ThreadWait.delay ( 200, TimeUnit.MILLISECONDS );
    }

    public String getText() {
        String text = getWebElement ( ).getText ( );
        return text;
    }


    public void waitUntilElementVisible(WebAppElement webAppElement, int timeoutInSeconds) {
        implicitlyWait ( 5, TimeUnit.SECONDS );
        WebDriverWait wait = new WebDriverWait ( driver, timeoutInSeconds );
        wait.until ( ExpectedConditions.visibilityOfElementLocated ( (By) webAppElement.getBy ( ) ) );
    }

    public void waitForDocumentObjectModelToGetReady(int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
        logger.info("Wait For Document Object Model To Get Ready passed ");
    }

    public void scrollToVisibilityOfElement(WebAppElement webAppElement) {
        WebElement element = webAppElement.getWebElement();
        getJavascriptExecutor().executeScript("arguments[0].scrollIntoView();", element);
    }

    public String getAttribute(String attributeName) {
        return getWebElement().getAttribute(attributeName);
    }

    public boolean isPresent() {
        try {
            driver.manage ( ).timeouts ( ).implicitlyWait ( 2, TimeUnit.SECONDS );
            getWebElement ( );
            return true;
        } catch (NoSuchElementException e) {
            System.out.println ( "No such Element present" + e );
            return false;
        }

    }

    public void scrollUntilAllItemsAreLoaded(){
        try {
            long lastHeight = (long)getJavascriptExecutor().executeScript("return document.body.scrollHeight");

            while (true) {
                getJavascriptExecutor().executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(5000);

                long newHeight = (long) getJavascriptExecutor().executeScript("return document.body.scrollHeight");
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
