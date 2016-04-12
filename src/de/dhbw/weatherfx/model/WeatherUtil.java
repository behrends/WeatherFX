package de.dhbw.weatherfx.model;

import java.io.*;
import java.net.*;
import java.util.Properties;

/**
 * Created by behrends on 11/04/16.
 */
public class WeatherUtil {
    public static String getWeather(String cityName) {
        String result = "???";

        String host = "api.openweathermap.org";
        String path = "/data/2.5/weather";

        try {
            String queryString = "q=" + cityName + "&units=metric&" + "APPID=" + getAPIKeyFromFile();

            URL url = new URI("http", host, path, queryString, null).toURL();

            try { // simulate slow network
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            System.out.println(buffer);

        } catch (URISyntaxException | IOException e) {
            System.err.println("An error occurred while requesting data from weather API.");
        }

        return result;
    }

    private static String getAPIKeyFromFile() throws IOException {
        Properties properties = new Properties();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream("config.properties"));
        properties.load(stream);
        stream.close();
        return properties.getProperty("api.key");
    }
}
