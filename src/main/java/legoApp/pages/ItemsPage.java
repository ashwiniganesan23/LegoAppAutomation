package legoApp.pages;

import legoApp.utils.EnumUtils;
import legoApp.utils.enums.FilterOptions;
import org.openqa.selenium.WebDriver;
import legoApp.reporting.Logger;
import legoApp.selenium.LocateBy;
import legoApp.selenium.WebAppElement;
import org.testng.Assert;

import java.util.List;

public class ItemsPage {

    public WebDriver driver;

    public WebAppElement webAppElement() {
        return new WebAppElement ( driver );
    }

    protected final Logger logger = Logger.getLogger ( this.getClass ( ) );


    public ItemsPage(WebDriver driver) {

        this.driver = driver;
    }

    private WebAppElement selectFilter(FilterOptions filterName, String filterOption) {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//font[contains(text(),'" + EnumUtils.getLabel ( filterName ) + "')]/../../../../../div" +
                        "//span//font[contains(text(),'" + filterOption + "')]/../../../div//div" );

    }


    public WebAppElement numberOfItems() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//font[contains(text(),'Kind of product')]/../../../../../div//span//font//font" );

    }

    public WebAppElement showAll() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//a//font[contains(text(),'Show everything')]" );

    }

    public int numberOfItemsPresent() {

        String getProductText = numberOfItems ( ).getText ( );
        String productCount = getProductText.replaceAll ( "^.*?(\\w+)\\W*$", "$1" );
        int totalProducts = Integer.parseInt ( productCount );
        return totalProducts;

    }

    public List<WebAppElement> numberOfPages() {
        return webAppElement ( ).findElements ( LocateBy.XPATH,
                "//a[contains(@data-test,'pagination-page')]" );

    }

    public void numberOfItemsInAPage() {
        System.out.println ( numberOfItemsPresent ( ) );
        if ( numberOfItemsPresent ( ) >= 1 ) {
            if ( numberOfItemsPresent ( ) % 18 == 0 ) {

                Assert.assertEquals ( numberOfItemsPresent ( ) / 18, numberOfPages ( ).size ( ) );
            } else{
                Assert.assertEquals ( (numberOfItemsPresent ( ) / 18) + 1, numberOfPages ( ).size ( ) );
            }
        } else
            Assert.assertEquals ( 1, numberOfPages ( ).size ( ) );
    }

    public void verifyNumberOfItemsPresent() {
        logger.info ( "verifyNumberOfItemsPresent(): " );
        webAppElement ( ).scrollToVisibilityOfElement ( showAll ( ) );
        numberOfItemsInAPage ( );
    }

    public void selectAge(String filterOption) {
        logger.info ( "selectAge(): " + filterOption);
        selectFilter ( FilterOptions.AGE, filterOption ).click ( );
    }

    public void selectPriceRange(String filterOption) {
        logger.info ( "selectPriceRange(): " + filterOption);
        selectFilter ( FilterOptions.PRICE, filterOption ).click ( );
    }

    public void selectFilterAndFilterOption(FilterOptions filterName, String filterOption) {
        selectFilter ( filterName, filterOption ).click ( );
    }

    public String itemInList() {
        return "//li[@data-test='product-item']";

    }

    private WebAppElement addItemToBagWithItemName(String itemName) {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                itemInList ( ) + "//span//font[contains(text(),'" + itemName + "')]/../../../../.." +
                        "/following-sibling::div//font[contains(text(),'Add to Bag')]" );

    }

    private WebAppElement seeMyBag() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//div//a//font[contains(text(),'See my bag')]" );

    }

    private WebAppElement keepBuying() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "//div//button//font[contains(text(),'Keep buying')]" );

    }

    public String selectedItemCost() {
        String text = webAppElement ( ).findElement ( LocateBy.XPATH,
                "(//p[contains(@class,'Text')]//span//font//font)[2]" ).getText ( );
        String cost = text.replaceAll ( "[^\\p{ASCII}]", " " ).trim ( );
        float productCost = Float.parseFloat ( cost );
        return String.valueOf ( productCost );


    }

    public String selectedItemName() {
        return webAppElement ( ).findElement ( LocateBy.XPATH,
                "(//p[contains(@class,'Text')]//span//font//font)[1]" ).getText ( );

    }


    public void addAnyItemToBag(String itemName) {
        logger.info ( "addAnyItemToBag(): " + itemName);
        webAppElement ( ).scrollToVisibilityOfElement ( addItemToBagWithItemName ( itemName ) );
        webAppElement ( ).waitUntilElementVisible ( addItemToBagWithItemName ( itemName ), 5 );
        addItemToBagWithItemName ( itemName ).click ( );
    }

    public void goToMyBag() {
        logger.info ( "goToMyBag(): ");

        seeMyBag ( ).click ( );
    }


    public void selectKeepBuying() {
        logger.info ( "selectKeepBuying(): ");
        keepBuying ( ).click ( );
    }
}
