package de.dhbw.weatherfx.model;

import javafx.concurrent.Task;

/**
 * Created by behrends on 18/04/16.
 */
public class CurrentWeatherTask extends Task<WeatherData> {
    private String city;

    public CurrentWeatherTask(String city) {
        this.city = city;
    }

    @Override
    protected WeatherData call() throws Exception {
        return WeatherUtil.getCurrentWeatherDataForCity(city);
    }
}
