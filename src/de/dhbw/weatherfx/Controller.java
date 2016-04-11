package de.dhbw.weatherfx;

import de.dhbw.weatherfx.model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller{
    @FXML
    ListView<City> cityListView;

    @FXML
    TextField citiesField;

    @FXML
    Label cityName;

    private ObservableList<City> cities;

    public void btnClicked(ActionEvent actionEvent) {
        String cityName = citiesField.getText();

        cities.add(new City(cityName));
        citiesField.clear();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        cities = FXCollections.observableArrayList();

        cities.add(new City("Barcelona"));
        cities.add(new City("Basel"));
        cities.add(new City("Freiburg"));
        cities.add(new City("Hamburg"));

        cityListView.setItems(cities);

        cityListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayWeatherForecast(newValue)
        );
    }

    private void displayWeatherForecast(City city) {
        cityName.setText(city.getName());
    }
}
