package legoApp.utils;

import java.util.ResourceBundle;


public class ResourceUtils {


    public static ResourceBundle getBundle(WebAppBundle webAppBundle) {
        return ResourceBundle.getBundle(webAppBundle.getPath());
    }

}
