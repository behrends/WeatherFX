package de.dhbw.weatherfx.model;

import com.google.gson.Gson;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by behrends on 11/04/16.
 */
public class WeatherUtil {
    private static String apiKey;

    private static final String HOST = "api.openweathermap.org";
    private static final String PATH_PREFIX = "/data/2.5/";

    // read API key from file (happens only once after JVM start)
    static {
        try {
            Properties properties = new Properties();
            BufferedInputStream stream = null;
            stream = new BufferedInputStream(new FileInputStream("config.properties"));
            properties.load(stream);
            stream.close();
            apiKey = properties.getProperty("api.key");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WeatherData getCurrentWeatherDataForCity(String city) {
        String path = PATH_PREFIX + "weather";
        String queryString = "q=" + city + "&units=metric&" + "APPID=" + apiKey;

        try {
            URL url = new URI("http", HOST, path, queryString, null).toURL();
            Reader reader = new InputStreamReader(url.openStream());
            return new Gson().fromJson(reader, WeatherData.class);
        } catch (URISyntaxException | IOException e) {
            System.err.println("An error occurred while requesting data from weather API.");
        }

        return null;
    }
}
