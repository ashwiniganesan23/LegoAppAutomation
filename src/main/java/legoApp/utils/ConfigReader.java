package legoApp.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    public static Properties getPropertyObject()   {
        Properties prop = new Properties (  );
        FileInputStream fp = null;
        try {
            fp = new FileInputStream ( Paths.GLOBAL_VARIABLES.getPath () );
            prop.load ( fp);
        } catch (IOException e) {
            e.printStackTrace ( );
        }


        return prop;
    }




}
