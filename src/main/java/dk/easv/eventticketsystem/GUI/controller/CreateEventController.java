package dk.easv.eventticketsystem.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private Button createOrEventBtn;
    @FXML
    private ComboBox cBoxTimeSelectEnd;
    @FXML
    private DatePicker txtEndDate;
    @FXML
    private ComboBox cBoxTimeSelectStart;
    @FXML
    private DatePicker txtStartDate;


    @FXML
    private void handleCreateEvent(ActionEvent actionEvent) {
        String name = txtEnterEventName.getText().trim();
        LocalDate date = txtDate.getValue();
//        int time = cBoxTimeSelect.getVisibleRowCount()
        String location = txtLocation.getText().trim();
        String description = txtAreaDescription.getText().trim();
    }
}
