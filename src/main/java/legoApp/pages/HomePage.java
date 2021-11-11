package legoApp.pages;

import legoApp.utils.EnumUtils;
import legoApp.utils.IMenuOptions;
import org.openqa.selenium.WebDriver;
import legoApp.reporting.Logger;
import legoApp.selenium.LocateBy;
import legoApp.selenium.WebAppElement;
import org.testng.Assert;

public class HomePage {

    public WebDriver driver;

    public WebAppElement webAppElement() {
        return new WebAppElement ( driver );
    }

    protected final Logger logger = Logger.getLogger ( this.getClass ( ) );

    public HomePage(WebDriver driver) {

        this.driver = driver;
    }


    public WebAppElement mainMenuItem(IMenuOptions mainMenu) {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//ul[contains(@class,'MainBar')]//li//button" +
                        "//font[contains(text(),'" + EnumUtils.getLabel ( mainMenu ) + "')]" );
    }

    public WebAppElement toBuyFromMainMenu() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//ul[contains(@class,'MainBar')]//li//button//font[contains(text(),'To buy')]" );
    }

    public WebAppElement menuItem(IMenuOptions menu) {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//span[contains(@class,'SubMenustyles')]//font" +
                        "//font[text()='" + EnumUtils.getLabel ( menu ) + "']" );
    }


    public WebAppElement subMenuItem(IMenuOptions subMenuItem) {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//div[contains(@class,'SubMenu')]//font" +
                        "//font[contains(text(),'" + EnumUtils.getLabel ( subMenuItem ) + "')]" );

    }

    public void selectItemToBuyFromMenu(IMenuOptions category, IMenuOptions item) {
        logger.info ( "selectItemToBuyFromMenu(): " + category + item );
        toBuyFromMainMenu ( ).click ( );
        menuItem ( category ).click ( );
        subMenuItem ( item ).click ( );
    }


    public WebAppElement searchButton() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                " //button[@data-test='search-input-button']" );

    }

    public WebAppElement searchInputBox() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//input[@type='search']" );

    }

    public WebAppElement firstItemInSearchSuggestion() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                " (//ul[contains(@id,'search-suggestions')]//li//div" +
                        "//p[contains(@data-test,'name')]//font//font)[1]" );

    }

    private WebAppElement addToBagFromItemPage() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//button[@data-test='add-to-bag']" );

    }


    public void searchAndSelectForAnItem(String itemName) {
        logger.info ( "searchAndSelectForAnItem(): "+ itemName );

        searchButton ( ).click ( );
        searchInputBox ( ).sendKeys ( itemName );
        firstItemInSearchSuggestion ( ).click ( );
    }

}
