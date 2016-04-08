package de.dhbw.weatherfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    Label myLabel;

    public void myButtonClicked(ActionEvent actionEvent) {
        myLabel.setText("wurde geklickt");
    }
}
