package de.dhbw.weatherfx.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by behrends on 11/04/16.
 */
public class WeatherUtil {
    public static String getWeather(String cityName) {
        String result = "20";

        String host = "api.openweathermap.org";
        String path = "/data/2.5/weather";
        String queryString = "q=" + cityName + "&" + "APPID=";

        try {
            URL url = new URI("http", host, path, queryString, null).toURL();

            System.out.println(url);

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            System.out.println(buffer);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
