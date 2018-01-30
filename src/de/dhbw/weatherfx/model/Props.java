package de.dhbw.weatherfx.model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Props {
    public static String weatherApiKey;
    public static String geonamesUser;


    // read API keys from file (happens only once after JVM start)
    static {
        try {
            java.util.Properties properties = new java.util.Properties();
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream("config.properties"));
            properties.load(stream);
            stream.close();
            weatherApiKey = properties.getProperty("api.key");
            geonamesUser = properties.getProperty("geonames");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
