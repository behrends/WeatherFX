package de.dhbw.weatherfx.model;

import java.io.*;
import java.net.*;
import java.util.Properties;

/**
 * Created by behrends on 11/04/16.
 */
public class WeatherUtil {
    public static String getWeather(String cityName) {
        String result = "20";

        String host = "api.openweathermap.org";
        String path = "/data/2.5/weather";
        String queryString = null;

        try {
            queryString = "q=" + cityName + "&" + "APPID=" + getAPIKeyFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private static String getAPIKeyFromFile() throws IOException {
        Properties properties = new Properties();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream("config.properties"));
        properties.load(stream);
        stream.close();
        String apiKey = properties.getProperty("api.key");
        return apiKey;
    }
}
