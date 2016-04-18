package de.dhbw.weatherfx.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

/**
 * Created by behrends on 18/04/16.
 */
public class CurrentWeatherTask extends Task<WeatherData> {
    private String city;

    public static StringProperty currentTemp = new SimpleStringProperty();

    public CurrentWeatherTask(String city) {
        this.city = city;
    }

    @Override
    protected WeatherData call() throws Exception {
        WeatherData data = WeatherUtil.getCurrentWeatherDataForCity(city);
        Platform.runLater(() -> currentTemp.setValue(data.getTemperature() + " \u00B0C"));
        return data;
    }
}
