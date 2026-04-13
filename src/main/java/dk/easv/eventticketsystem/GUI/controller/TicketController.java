package dk.easv.eventticketsystem.GUI.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class TicketController {
    @FXML
    private AnchorPane handlePrintTicket;
    @FXML
    private TableView tblTicket;
    @FXML
    private TableColumn colTicketName;
    @FXML
    private Button handleCreateTicket;
    @FXML
    private Button handleEditTicket;
    @FXML
    private Button handleDeleteTicket;
    @FXML
    private Button handleSendTicket;
}
