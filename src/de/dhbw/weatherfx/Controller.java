package de.dhbw.weatherfx;

import de.dhbw.weatherfx.model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller{
    @FXML
    ListView<City> citiesList;

    @FXML
    TextField citiesField;

    private ObservableList<City> cities;

    public void btnClicked(ActionEvent actionEvent) {
        String cityName = citiesField.getText();

        City city = new City();
        city.setName(cityName);

        cities.add(city);
        citiesField.clear();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        cities = FXCollections.observableArrayList();
        citiesList.setItems(cities);
    }
}
