package de.dhbw.weatherfx.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by behrends on 11/04/16.
 */
public class WeatherUtil {

    private static final String HOST = "api.openweathermap.org";
    private static final String PATH_PREFIX = "/data/2.5/";


    public static WeatherData getCurrentWeatherDataForCity(String city) {
        String path = PATH_PREFIX + "weather";
        String queryString = "q=" + city + "&units=metric&" + "APPID=" + Props.weatherApiKey;

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
