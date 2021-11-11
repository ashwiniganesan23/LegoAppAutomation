package legoApp.utils;

import legoApp.components.legoComponents;
import legoApp.pages.CartPage;
import legoApp.pages.HomePage;
import legoApp.pages.ItemsPage;
import legoApp.pages.ProductPage;

public class LegoApp extends BaseTest {


    public ItemsPage itemsPage() {
        return new ItemsPage ( driver.get ( ) );
    }

    public legoComponents legoComponents() {
        return new legoComponents ( driver.get ( ) );
    }

    public HomePage homePage() {
        return new HomePage ( driver.get ( ) );
    }

    public CartPage cartPage() {
        return new CartPage ( driver.get ( ) );
    }

    public ProductPage productPage() {
        return new ProductPage ( driver.get ( ) );
    }
}
