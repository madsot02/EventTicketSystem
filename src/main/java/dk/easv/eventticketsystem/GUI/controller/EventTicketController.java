package dk.easv.eventticketsystem.GUI.controller;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.BE.TicketType;
import dk.easv.eventticketsystem.BLL.utils.TicketPDFGenerator;
import dk.easv.eventticketsystem.GUI.model.TicketModel;
import dk.easv.eventticketsystem.GUI.model.TicketTypeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EventTicketController {

    @FXML private TextField txtCustomerFullName;
    @FXML private TextField txtCustomerMail;
    @FXML private ComboBox<TicketType> cbTicketType;
    @FXML private TextField txtAmount;
    @FXML private Label lblEventTitle;
    @FXML private Label lblTotal;

    @FXML private TableView<CartItem> tblCart;
    @FXML private TableColumn<CartItem, String> colCartType;
    @FXML private TableColumn<CartItem, Integer> colCartAmount;
    @FXML private TableColumn<CartItem, Double> colCartPrice;
    @FXML private TableColumn<CartItem, Double> colCartTotal;
    @FXML private TableColumn<CartItem, Void> colCartRemove;

    private TicketModel ticketModel;
    private TicketTypeModel ticketTypeModel;
    private Event currentEvent;

    private final ObservableList<CartItem> cart = FXCollections.observableArrayList();

    public void initialize() {
        try {
            ticketModel = new TicketModel(FXCollections.observableArrayList());
            ticketTypeModel = new TicketTypeModel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cbTicketType.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(TicketType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });
        cbTicketType.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(TicketType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        colCartType.setCellValueFactory(new PropertyValueFactory<>("typeName"));
        colCartAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCartTotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        // Fjern-knap kolonne
        colCartRemove.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Fjern");
            {
                btn.setOnAction(e -> {
                    CartItem item = getTableView().getItems().get(getIndex());
                    cart.remove(item);
                    updateTotalPrice();
                });
            }
            @Override
            protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                setGraphic(empty ? null : btn);
            }
        });

        tblCart.setItems(cart);
    }

    public void setEvent(Event event) {
        this.currentEvent = event;
        if (lblEventTitle != null) {
            lblEventTitle.setText("Create Ticket — " + event.getName());
        }
        try {
            ticketTypeModel.loadForEvent(event.getId());
            cbTicketType.setItems(ticketTypeModel.getTicketTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddToCart(ActionEvent actionEvent) {
        TicketType selectedType = cbTicketType.getValue();
        String amountText = txtAmount.getText().trim();

        if (selectedType == null) {
            new Alert(Alert.AlertType.WARNING, "Vælg en ticket type").showAndWait();
            return;
        }
        if (amountText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Angiv antal").showAndWait();
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountText);
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Antal skal være et positivt tal").showAndWait();
            return;
        }

        // Hvis typen allerede er i kurven, læg antal oven i
        for (CartItem item : cart) {
            if (item.getTicketType().getTypeId() == selectedType.getTypeId()) {
                item.amountProperty().set(item.getAmount() + amount);
                tblCart.refresh();
                updateTotalPrice();
                txtAmount.clear();
                cbTicketType.getSelectionModel().clearSelection();
                return;
            }
        }

        cart.add(new CartItem(selectedType, amount));
        updateTotalPrice();
        txtAmount.clear();
        cbTicketType.getSelectionModel().clearSelection();
    }

    private void updateTotalPrice() {
        double total = cart.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
        lblTotal.setText(String.format("Total: %.2f kr", total));
    }

    @FXML
    private void handleSavePdf(ActionEvent actionEvent) {
        validateAndPrint();
    }

    @FXML
    private void handleSendMail(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.INFORMATION, "Email funktionalitet kommer snart").showAndWait();
    }

    private void validateAndPrint() {
        String name = txtCustomerFullName.getText().trim();
        String email = txtCustomerMail.getText().trim();

        if (cart.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Kurven er tom — tilføj mindst én ticket type").showAndWait();
            return;
        }

        // Kun kræv kundeinfo hvis der er mindst én ikke-voucher i kurven
        boolean hasNonVoucher = cart.stream().anyMatch(i -> !i.getTicketType().isVoucher());
        if (hasNonVoucher && (name.isEmpty() || email.isEmpty())) {
            new Alert(Alert.AlertType.WARNING, "Udfyld kundenavn og email for normale billetter").showAndWait();
            return;
        }

        try {
            List<Ticket> allTickets = new ArrayList<>();

            for (CartItem cartItem : cart) {
                TicketType type = cartItem.getTicketType();

                // Vouchers får ikke kundenavn/email
                String ticketName  = type.isVoucher() ? "" : name;
                String ticketEmail = type.isVoucher() ? "" : email;

                for (int i = 0; i < cartItem.getAmount(); i++) {
                    Ticket ticket = new Ticket(
                            0, null, currentEvent.getId(),
                            type.getTypeName(), false,
                            ticketName, ticketEmail, type.getPrice()
                    );
                    allTickets.add(ticketModel.createTicket(ticket));
                }
            }

            TicketPDFGenerator.generateTicket(allTickets, currentEvent);

            new Alert(Alert.AlertType.INFORMATION,
                    allTickets.size() + " billet(ter) oprettet og PDF genereret!").showAndWait();

            // Ryd klar til næste kunde
            cart.clear();
            txtCustomerFullName.clear();
            txtCustomerMail.clear();
            updateTotalPrice();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fejl: " + e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleManageTypes(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/dk/easv/eventticketsystem/ManageTicketTypesView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Manage Ticket Types");
            stage.setScene(scene);

            ManageTicketTypesController controller = loader.getController();
            controller.setup(ticketTypeModel, currentEvent.getId());

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}