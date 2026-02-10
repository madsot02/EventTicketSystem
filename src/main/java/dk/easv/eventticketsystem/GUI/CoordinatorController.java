package dk.easv.eventticketsystem.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    @FXML
    private void handleCreateEvent(ActionEvent actionEvent) {
    }

    @FXML
    private void handleRemoveEvent(ActionEvent actionEvent) {
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) {

    }
}
