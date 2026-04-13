package dk.easv.eventticketsystem.GUI.controller;

import dk.easv.eventticketsystem.BE.TicketType;
import dk.easv.eventticketsystem.GUI.model.TicketTypeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ManageTicketTypesController {
    @FXML private TextField txtTypeName;
    @FXML private TextField txtTypePrice;
    @FXML private CheckBox chkVoucher;
    @FXML private ListView<TicketType> listTypes;

    private TicketTypeModel ticketTypeModel;
    private int currentEventId;

    public void initialize() {
        listTypes.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(TicketType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });
    }

    public void setup(TicketTypeModel model, int eventId) {
        this.ticketTypeModel = model;
        this.currentEventId = eventId;
        listTypes.setItems(ticketTypeModel.getTicketTypes());
    }

    @FXML
    private void handleAddType(ActionEvent actionEvent) {
        String name = txtTypeName.getText().trim();
        String priceText = txtTypePrice.getText().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill in name and price").showAndWait();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Price must be a number").showAndWait();
            return;
        }

        try {
            TicketType type = new TicketType(0, currentEventId, name, price, chkVoucher.isSelected());
            ticketTypeModel.createTicketType(type);
            txtTypeName.clear();
            txtTypePrice.clear();
            chkVoucher.setSelected(false);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void handleDeleteType(ActionEvent actionEvent) {
        TicketType selected = listTypes.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        try {
            ticketTypeModel.deleteTicketType(selected);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
}
