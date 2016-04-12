package de.dhbw.weatherfx.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;
import java.net.HttpURLConnection;
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

    public void run() {
        String host = "api.openweathermap.org";
        String path = "/data/2.5/weather";

        try {
            // TODO: get API-key only once
            String queryString = "q=" + cityName + "&units=metric&" + "APPID=" + getAPIKeyFromFile();

            URL url = new URI("http", host, path, queryString, null).toURL();

            try { // simulate slow network
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // update bound property for temperature on JavaFX Application Thread
            Platform.runLater(() -> currentTemp.setValue("temperature in " + cityName));

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            System.out.println(buffer);


        } catch (URISyntaxException | IOException e) {
            System.err.println("An error occurred while requesting data from weather API.");
        }
    }

    private String getAPIKeyFromFile() throws IOException {
        Properties properties = new Properties();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream("config.properties"));
        properties.load(stream);
        stream.close();
        return properties.getProperty("api.key");
    }
}
