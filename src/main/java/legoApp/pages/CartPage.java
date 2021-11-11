package legoApp.pages;

import legoApp.reporting.Logger;
import legoApp.selenium.LocateBy;
import legoApp.selenium.WebAppElement;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static legoApp.utils.ThreadWait.delay;


public class CartPage {
    public WebDriver driver;

    public WebAppElement webAppElement() {
        return new WebAppElement ( driver );
    }

    protected final Logger logger = Logger.getLogger ( this.getClass ( ) );

    public CartPage(WebDriver driver) {

        this.driver = driver;
    }

    public void waitForPageLoad(int timeOutInSecs) {
        logger.info ( "waitForPageLoad()" );
        delay ( 10, TimeUnit.SECONDS );
        webAppElement ( ).waitForDocumentObjectModelToGetReady ( 30 );
        webAppElement ( ).waitUntilElementVisible ( cartTitle ( ), timeOutInSecs );
    }


    private WebAppElement cartTitle() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//font//font[contains(text(),'My bag')]" );

    }


    public String totalCostInCart() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "(//div[contains(@class,'Pricing')]//span[contains(@class,'Text')]//following-sibling::span" +
                        "//span//font//font)[1]" ).getText ( );

    }

    public String productNameInCart() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//div[contains(@class,'ItemDetails')]//span[@data-test='product-title']//font//font" ).getText ( );

    }

    public WebAppElement quantityInCart(String itemQuantity) {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//div[contains(@class,'QuantityContainer')]//div//input[@value='" + itemQuantity + "']" );

    }

    public String getQuantityInCart() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//div[contains(@class,'QuantityContainer')]//div//input" ).getAttribute ( "value" );

    }

    private String productPrice() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//div[contains(@class,'ProductInfoContainer')]//span[@data-test='product-price']" ).getText ( );

    }

    public String totalCostOfTheProduct() {

        String text = productPrice ( );
        String cost = text.replaceAll ( "[^\\p{ASCII}]", "" ).trim ( );
        System.out.println ( cost );
        float productPrice = Float.parseFloat ( cost );
        String quantity = getQuantityInCart ( );
        int totalQuantity = Integer.parseInt ( quantity );
        System.out.println ( productPrice * totalQuantity );
        return String.valueOf ( productPrice * totalQuantity );

    }

    public String totalCostOfTheProductInCart() {
        String text = totalCostInCart ( );
        String cost = text.replaceAll ( "[^\\p{ASCII}]", "" ).trim ( );
        System.out.println ( cost );
        float totalCost = Float.parseFloat ( cost );
        return String.valueOf ( totalCost );
    }

    public String productPriceInCart() {
        String text = productPrice ( );
        String cost = text.replaceAll ( "[^\\p{ASCII}]", " " ).trim ( );
        float productPrice = Float.parseFloat ( cost );
        return String.valueOf ( productPrice );
    }
}
