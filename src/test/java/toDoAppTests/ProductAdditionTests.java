package toDoAppTests;

import legoApp.utils.enums.FilterOptions;
import legoApp.utils.enums.MainMenuItem;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import legoApp.utils.LegoApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductAdditionTests extends LegoApp {

    @DataProvider(name = "shoppingItems")
    public Object[][] getListOfToDoItems() {
        List<String> items = new ArrayList<> ( );
        Collections.addAll ( items, "6+", "€ 0 - € 20", "Land Speeder Amulet", "BB-8 ™ LEGO® Star Wars ™ Keychain");
        Object[][] objData = new Object[1][1];
        objData[0][0] = items;
        return objData;
    }



    @Test(description = "Verify all the product details by adding products to the cart", dataProvider = "shoppingItems")
    public void testE2ETestProductAddition(List<String> items) {
        legoComponents ().continueToShop ();
        legoComponents ().acceptJustNecessaryCookies ();
        //Add multiple filters
        homePage ().selectItemToBuyFromMenu( MainMenuItem.LEGO_ITEMS,MainMenuItem.KEYCHAINS);
        itemsPage ().selectAge ( items.get ( 0 ) );
        itemsPage ().selectPriceRange ( items.get ( 1 ) );
        //Verifying number of items present  by validating the number of pages present
        // (Number of items is retrieved from "Kind of product" filter)
        itemsPage ().verifyNumberOfItemsPresent ();
        // Add items twice so that quantity is 2
        itemsPage ().addAnyItemToBag ( items.get ( 2 ) );
        itemsPage ().selectKeepBuying ();
        itemsPage ().addAnyItemToBag ( items.get ( 2 ) );
        // Get item cost and item name while adding the product to the cart
        String selectedItemCost = itemsPage ( ).selectedItemCost ( );
        String selectedItemName = itemsPage ().selectedItemName ();
        // Go to my cart
        itemsPage ().goToMyBag ();
        cartPage ().waitForPageLoad(10);
        // Assert product price while adding item and price displayed in cart
        Assert.assertEquals ( selectedItemCost,cartPage ().productPriceInCart ());
        // Assert product name while adding item and name displayed in cart
        Assert.assertEquals ( selectedItemName,cartPage ().productNameInCart () );
        // Assert product quantity while adding item and quantity displayed in cart
        Assert.assertTrue ( cartPage ().quantityInCart ( "2" ).isPresent () );
        // Assert product cost while adding item and cost displayed in cart
        Assert.assertEquals ( cartPage ().totalCostOfTheProduct (  ), cartPage ().totalCostOfTheProductInCart() );

    }


    @Test(description = "Verify if on searching a product, suggestions are shown with item details on clicking", dataProvider = "shoppingItems")
    public void testSearchProductAddition(List<String> items) {
        legoComponents ().continueToShop ();
        legoComponents ().acceptJustNecessaryCookies ();
        //Search for the item and select teh item
        homePage ().searchAndSelectForAnItem(items.get ( 3 ));
        //Verify if user is directed to product page
        productPage ().waitForPageLoad ( 10 );
        //Verify if the product tile matches
        productPage ().verifyProductTitle(items.get ( 3 ));
        //Add the item to the bag
        productPage ().addToBag ();
        String selectedItemName = productPage ( ).selectedItemName ( );
        //Go to Cart
        itemsPage ().goToMyBag ();
        //Verify if valid item is added to the cart
        Assert.assertEquals ( selectedItemName,cartPage ().productNameInCart () );



    }


}
