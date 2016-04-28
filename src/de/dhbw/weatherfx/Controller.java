package de.dhbw.weatherfx;

import de.dhbw.weatherfx.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.controlsfx.control.textfield.TextFields;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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

    private class CityCell extends ListCell<City> {
        private Button deleteBtn;
        private Label name;
        private GridPane pane;

        public CityCell() {
            super();

            deleteBtn = new Button("X");
            deleteBtn.setPadding(Insets.EMPTY); // button with no padding
            deleteBtn.setStyle("-fx-background-color: #C9302C;");
            deleteBtn.setOnAction((event) -> {
                City city = getItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Remove " + city + " from list");
                alert.setHeaderText(null);
                alert.setContentText("Do you really want to remove " + city + " from the list?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    cities.remove(city);
                    saveCitiesToDisk();
                }
            });

            // add nodes to cell (label and button)
            name = new Label();
            pane = new GridPane();
            pane.add(name, 0, 0);
            pane.add(deleteBtn, 1, 0);

            // make sure the button is aligned to the far right
            ColumnConstraints rightCol = new ColumnConstraints();
            rightCol.setHalignment(HPos.RIGHT);
            rightCol.setHgrow(Priority.ALWAYS);
            pane.getColumnConstraints().addAll(new ColumnConstraints(), rightCol);

            setText(null);
        }
        @Override
        protected void updateItem(City item, boolean empty) {
            super.updateItem(item, empty);
            setEditable(false);
            if (item != null && !empty) {
                name.setText(item.toString());
                setGraphic(pane);
            } else {
                setGraphic(null);
            }
        }
    }

    public void btnClicked(ActionEvent actionEvent) {
        String cityName = citiesField.getText();

        cities.add(new City(cityName));
        citiesField.clear();

        saveCitiesToDisk();
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

        cityListView.setCellFactory((param) -> new CityCell());
        cityListView.setItems(cities);
        cityListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displayWeatherForecast(newValue)
        );

        // whenever new data has been fetched, we update the UI
        // (valueProperty is the result of the background service)
        currentWeatherDataService.valueProperty().addListener(
                (observable, oldValue, newValue) -> updateCurrentWeatherPane(newValue)
        );

        TextFields.bindAutoCompletion(citiesField, (suggestionRequest) -> GeonamesUtil.getMovies(suggestionRequest.getUserText()));
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


    private void saveCitiesToDisk() {
        // save cities to disk:
        // 1. create an empty array of City objects to hold all cities
        City[] cityArray = new City[cities.size()];
        // 2. convert cities list to cityArray and pass to method saving cities to file
        Storage.saveCitiesToFile(cities.toArray(cityArray));
    }
}
