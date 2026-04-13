package dk.easv.eventticketsystem.GUI.controller;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.BLL.utils.TicketPDFGenerator;
import dk.easv.eventticketsystem.GUI.model.EventModel;
import dk.easv.eventticketsystem.GUI.model.TicketModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class EventTicketController  {
    @FXML
    private TextField txtCustomerFullName;
    @FXML
    private TextField txtCustomerMail;
    @FXML
    private ComboBox<String> cbTicketType;
    @FXML
    private TextField txtNumberOfTickets;
    @FXML
    private Label lblPrice;

    private TicketModel ticketModel;

    private EventModel eventModel;

    private Event currentEvent;

    List<Ticket> createdTickets = new ArrayList<>();

    public void initialize(){
        try {
            ticketModel = new TicketModel(FXCollections.observableArrayList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cbTicketType.getItems().addAll("VIP", "Normal");
        cbTicketType.setOnAction(e -> {
            String selected = cbTicketType.getSelectionModel().getSelectedItem();
            cbTicketType.setPromptText(selected);
        });

        txtNumberOfTickets.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTotalPrice();
        });
    }

    public void setEvent(Event event){
        this.currentEvent = event;
        updateTotalPrice();

    }
    public void setEventModel(EventModel eventModel) {
        this.eventModel = eventModel;
    }
    @FXML
    private void handleSavePdf(ActionEvent actionEvent) {
        try {

            if (txtCustomerFullName.getText().isEmpty()
                    || txtCustomerMail.getText().isEmpty()
                    || txtNumberOfTickets.getText().isEmpty()
                    || cbTicketType.getValue() == null) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please fill in all fields!");
                alert.show();
                return;
            }

            createdTickets.clear();

            String name = txtCustomerFullName.getText();
            String email = txtCustomerMail.getText();
            String type = cbTicketType.getValue();
            int amount = Integer.parseInt(txtNumberOfTickets.getText());
            double price = currentEvent.getPrice();

            for (int i = 0; i < amount; i++) {
                Ticket ticket = new Ticket(
                        0,
                        null,
                        currentEvent.getId(),
                        type,
                        false,
                        name,
                        email,
                        price
                );

                ticket.setCustomerName(name);
                ticket.setCustomerEmail(email);
                ticket.setPrice(price);

                Ticket createdTicket = ticketModel.createTicket(ticket);
                createdTickets.add(createdTicket);
            }
            TicketPDFGenerator.generateTicket(createdTickets, currentEvent);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Tickets created + PDF generated!");
            alert.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSendMail(ActionEvent actionEvent) {
    }

    private void updateTotalPrice() {
        try {
            if (currentEvent == null) return;

            int amount = Integer.parseInt(txtNumberOfTickets.getText());
            double pricePerTicket = currentEvent.getPrice();
            double totalPrice = amount * pricePerTicket;

            lblPrice.setText(String.format("%d x %.2f = %.2f DKK", amount, pricePerTicket, totalPrice));

        } catch (NumberFormatException e) {
            lblPrice.setText("0 DKK");
        }
    }

}
