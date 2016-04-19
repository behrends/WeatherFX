package de.dhbw.weatherfx.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by behrends on 12/04/16.
 */

// Model class to hold weather data; used with Gson library (see JSON structure below)
public class WeatherData {
    private class Main {
        private float temp;
        private short humidity;
    }

    private Main main;

    private class Weather {
        private String description;
        private String icon;
    }

    private List<Weather> weather;

    private class Wind {
        private float speed;
    }

    private Wind wind;

    private class Sys {
        private long sunrise;
        private long sunset;
    }

    private Sys sys;

    private long dt;

    public float getTemperature() {
        return main.temp;
    }

    public String getDescription() {
        return weather.get(0).description;
    }

    public float getWind() {
        return wind.speed;
    }

    public short getHumidity() {
        return main.humidity;
    }

    public String getIconAdress() {
        return "http://openweathermap.org/img/w/" + weather.get(0).icon + ".png";
    }

    public LocalTime getSunrise() {
        Date date = new Date(sys.sunrise * 1000);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public LocalTime getSunset() {
        Date date = new Date(sys.sunset * 1000);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public LocalDateTime getTimeStamp() {
        Date date = new Date(dt * 1000);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @Override
    public String toString() {
        return "WeatherData[Temp: " + getTemperature() + ", Desc: " + getDescription() + "]";
    }
}

/* Example JSON
{"coord":{"lon":7.85,"lat":48},
"weather":[{"id":520,"main":"Rain","description":"light intensity shower rain","icon":"09n"}],
"base":"cmc stations",
"main":{"temp":11.05,"pressure":1009,"humidity":76,"temp_min":10.5,"temp_max":13},
"wind":{"speed":2.6,"deg":330},
"rain":{},
"clouds":{"all":90},
"dt":1460485810,
"sys":{"type":3,"id":5646,"message":0.0048,"country":"DE","sunrise":1460436176,"sunset":1460484968},
"id":2925177,
"name":"Freiburg",
"cod":200}
*/