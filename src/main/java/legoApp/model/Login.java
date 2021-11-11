package legoApp.model;

import legoApp.utils.ConfigReader;

public class Login {

    public static String getUrl() {

        return ConfigReader.getPropertyObject ( ).getProperty ( "url" );
    }


}
