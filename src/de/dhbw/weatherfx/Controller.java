package de.dhbw.weatherfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label myLabel;

    public void btnClicked(ActionEvent actionEvent) {
        myLabel.setText("wurde geklickt");
    }
}
