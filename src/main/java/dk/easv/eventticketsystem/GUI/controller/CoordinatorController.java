package dk.easv.eventticketsystem.GUI.controller;

import dk.easv.eventticketsystem.GUI.model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CoordinatorController {

    @FXML
    private TableView tblEvents;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colPlace;
    @FXML
    private TableColumn colDate;
    @FXML
    private TableColumn colTime;
    @FXML
    private TableColumn colTotalTickets;
    @FXML
    private TableColumn colDescription;

    private EventModel eventModel;

    // ✅ FIX: initialize EventModel
    public void initialize() {
        try {
            eventModel = new EventModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setModel(EventModel eventModel){
        this.eventModel = eventModel;
    }

    @FXML
    private void handleCreateEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/CreateEventView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Create Event");
        stage.setScene(scene);

        // ✅ This now works because eventModel is NOT null anymore
        CreateEventController eventController = fxmlLoader.getController();
        eventController.setEventModel(eventModel);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void handleRemoveEvent(ActionEvent actionEvent) {
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) {
    }

    @FXML
    private void handleCreateTiicket(ActionEvent actionEvent) {
    }

    @FXML
    private void handleEditEvent(ActionEvent actionEvent) {
    }
}