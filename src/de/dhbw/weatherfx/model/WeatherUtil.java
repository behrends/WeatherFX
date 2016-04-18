package de.dhbw.weatherfx.model;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by behrends on 11/04/16.
 */
public class WeatherUtil extends Thread {
    private String cityName;

    public static StringProperty currentTemp = new SimpleStringProperty();

    public WeatherUtil(String cityName) {
        this.cityName = cityName;
    }

    private static String apiKey;

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

    @Override
    public void run() {
        String host = "api.openweathermap.org";
        String path = "/data/2.5/weather";

        try {
            String queryString = "q=" + cityName + "&units=metric&" + "APPID=" + apiKey;

            URL url = new URI("http", host, path, queryString, null).toURL();
            Reader reader = new InputStreamReader(url.openStream());
            WeatherData weatherData = new Gson().fromJson(reader, WeatherData.class);

            // update bound property for temperature on JavaFX Application Thread
            Platform.runLater(() -> currentTemp.setValue(weatherData.getTemperature() + " \u00B0C"));

        } catch (URISyntaxException | IOException e) {
            System.err.println("An error occurred while requesting data from weather API.");
        }
    }
}
