package legoApp.pages;

import legoApp.reporting.Logger;
import legoApp.selenium.LocateBy;
import legoApp.selenium.WebAppElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static legoApp.utils.ThreadWait.delay;

public class ProductPage {
    public WebDriver driver;
    public WebAppElement webAppElement(){
        return new WebAppElement (driver);
    }
    protected final Logger logger = Logger.getLogger(this.getClass());

    public ProductPage(WebDriver driver) {

        this.driver = driver;
    }

    public void waitForPageLoad(int timeOutInSecs) {
        logger.info("waitForPageLoad()");
        delay ( 20, TimeUnit.SECONDS );
        webAppElement ().waitForDocumentObjectModelToGetReady(30);
        webAppElement().waitUntilElementVisible(addToBagFromItemPage(), timeOutInSecs);
    }

    public String selectedItemName() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "(//div[@data-test='added-item']//p[contains(@class,'Text')]//span//font//font)[1]" ).getText ();

    }

    public List<WebAppElement> getProductTitle() {
        return webAppElement ( ).findElements ( LocateBy.XPATH,
                "//h1[@data-test='product-overview-name']//font//font" );
    }

    public void verifyProductTitle(String itemTitle){
        logger.info ( "verifyProductTitle(): " + itemTitle);

            for(WebAppElement s : getProductTitle ()){
                Assert.assertTrue ( itemTitle.contains ( s.getText () ) );
            }
    }

    private WebAppElement addToBagFromItemPage() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//button[@data-test='add-to-bag']" );

    }

    public void addToBag(){
        logger.info ( "addToBag(): " );

        addToBagFromItemPage ().click ();
    }

}
