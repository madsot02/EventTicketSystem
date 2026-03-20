package dk.easv.eventticketsystem.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class CreateEventController {
    @FXML
    private TextField txtEnterEventName;
    @FXML
    private DatePicker txtDate;
    @FXML
    private ComboBox cBoxTimeSelect;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextArea txtAreaDescription;


    @FXML
    private void handleCreateEvent(ActionEvent actionEvent) {
        String name = txtEnterEventName.getText().trim();
        LocalDate date = txtDate.getValue();
//        int time = cBoxTimeSelect.getVisibleRowCount()
        String location = txtLocation.getText().trim();
        String description = txtAreaDescription.getText().trim();
    }
}
