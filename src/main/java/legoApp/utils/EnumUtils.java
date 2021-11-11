package legoApp.utils;


import legoApp.utils.enums.FilterOptions;

import java.util.ResourceBundle;
public class EnumUtils {

    public static String getLabel(IMenuOptions iMenuOptions) {
        ResourceBundle bundle = ResourceUtils.getBundle(WebAppBundle.HOME_PAGE);
        return bundle.getString(iMenuOptions.name());
    }

    public static String getLabel(FilterOptions filterOptions) {
        ResourceBundle bundle = ResourceUtils.getBundle(WebAppBundle.ITEM_PAGE);
        return bundle.getString(filterOptions.name());
    }

}
