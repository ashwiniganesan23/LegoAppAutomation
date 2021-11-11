package legoApp.components;

import legoApp.reporting.Logger;
import org.openqa.selenium.WebDriver;
import legoApp.selenium.LocateBy;
import legoApp.selenium.WebAppElement;

public class legoComponents {

    WebDriver driver;

    public legoComponents(WebDriver driver) {

        this.driver = driver;
    }
    protected final Logger logger = Logger.getLogger ( this.getClass ( ) );


    public WebAppElement webAppElement() {
        return new WebAppElement ( driver );
    }

    public WebAppElement continueToShopToBuy() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//button//font[contains(text(),'Continue')]" );
    }

    public WebAppElement justNecessaryCookies() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//button//font[contains(text(),'Just what is necessary')]" );
    }

    public void continueToShop() {
        logger.info ( "continueToShop(): " );
        continueToShopToBuy ( ).click ( );
    }

    public void acceptJustNecessaryCookies() {
        logger.info ( "acceptJustNecessaryCookies(): " );
        justNecessaryCookies ( ).click ( );
    }


}
