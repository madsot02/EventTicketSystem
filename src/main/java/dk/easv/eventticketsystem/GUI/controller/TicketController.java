package dk.easv.eventticketsystem.GUI.controller;

import dk.easv.eventticketsystem.BE.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class TicketController {
    @FXML
    private TableView<Ticket> tblTicket;
    @FXML
    private TableColumn<Ticket, String> colTicketName;

    @FXML
    private void handlePrintTicket(ActionEvent actionEvent) {
    }

    @FXML
    private void handleCreateTicket(ActionEvent actionEvent) {
    }

    @FXML
    private void handleEditTicket(ActionEvent actionEvent) {
    }

    @FXML
    private void handleDeleteTicket(ActionEvent actionEvent) {
    }

    @FXML
    private void handleSendTicket(ActionEvent actionEvent) {
    }
}
