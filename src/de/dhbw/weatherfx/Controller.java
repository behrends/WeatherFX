package de.dhbw.weatherfx;

import de.dhbw.weatherfx.model.City;
import de.dhbw.weatherfx.model.CurrentWeatherTask;
import de.dhbw.weatherfx.model.Storage;
import de.dhbw.weatherfx.model.WeatherData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller{
    @FXML
    private ListView<City> cityListView;

    @FXML
    private TextField citiesField;

    @FXML
    private Label cityName, description, temperature, wind, humidity, sunrise, sunset, timestamp;

    private ObservableList<City> cities;

    private Service<WeatherData> currentWeatherDataService = new Service<WeatherData>() {
        @Override
        protected Task<WeatherData> createTask() {
            return new CurrentWeatherTask(cityName.getText());
        }
    };

    public void btnClicked(ActionEvent actionEvent) {
        String cityName = citiesField.getText();

        cities.add(new City(cityName));
        citiesField.clear();

        // save cities to disk:
        // 1. create an empty array of City objects to hold all cities
        City[] cityArray = new City[cities.size()];
        // 2. convert cities list to cityArray and pass to method saving cities to file
        Storage.saveCitiesToFile(cities.toArray(cityArray));
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // read cities from file and populate city list
        List<City> cityList = Storage.readCitiesFromFile();
        cities = FXCollections.observableArrayList();
        cities.setAll(cityList);

        cityListView.setItems(cities);

        cityListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayWeatherForecast(newValue)
        );

        // whenever new data has been fetched, we update the UI
        // (valueProperty is the result of the background service)
        currentWeatherDataService.valueProperty().addListener(
                (observable, oldValue, newValue) -> updateCurrentWeatherPane(newValue)
        );
    }

    private void displayWeatherForecast(City city) {
        cityName.setText(city.getName());
        // start new weather data request in background
        currentWeatherDataService.restart();
    }

    private void updateCurrentWeatherPane(WeatherData currentData) {
        if(currentData != null) {
            description.setText(currentData.getDescription());
            temperature.setGraphic(new ImageView(currentData.getIconAdress()));
            temperature.setText(currentData.getTemperature() + " \u00B0C");
            wind.setText("Wind: " + currentData.getWind() + " m/s");
            humidity.setText("Humidity: " + currentData.getHumidity() + "%");

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            sunrise.setText("Sunrise: " + dateTimeFormatter.format(currentData.getSunrise()));
            sunset.setText("Sunset: " + dateTimeFormatter.format(currentData.getSunset()));
            dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
            timestamp.setText("measured at: " + dateTimeFormatter.format(currentData.getTimeStamp()));
        }
        else {
            description.setText(null);
            temperature.setText(null);
        }
    }
}
