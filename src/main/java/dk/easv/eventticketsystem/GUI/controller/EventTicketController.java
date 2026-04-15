package dk.easv.eventticketsystem.GUI.controller;

//project imports
import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.Ticket;
import dk.easv.eventticketsystem.BE.TicketType;
import dk.easv.eventticketsystem.BLL.utils.TicketPDFGenerator;
import dk.easv.eventticketsystem.GUI.model.TicketModel;
import dk.easv.eventticketsystem.GUI.model.TicketTypeModel;
import dk.easv.eventticketsystem.BE.CartItem;

//java imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
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

    //instantiate
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

        //converts items to string
        cbTicketType.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(TicketType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        //displays items
        cbTicketType.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(TicketType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        //sets ticket information
        colCartType.setCellValueFactory(new PropertyValueFactory<>("typeName"));
        colCartAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCartTotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        //sets remove button in the table
        colCartRemove.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Remove");
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
            new Alert(Alert.AlertType.WARNING, "Choose a ticket type").showAndWait();
            return;
        }
        if (amountText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Apply amount").showAndWait();
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountText);
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Amount must be positive").showAndWait();
            return;
        }

        for (CartItem item : cart) {
            if (item.getTicketType().getTypeId() == selectedType.getTypeId()) {
                item.amountProperty().set(item.getAmount() + amount);
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
        lblTotal.setText(String.format("Total: %.2f DKK", total));
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".")
                && email.indexOf("@") < email.lastIndexOf(".");
    }

    @FXML
    private void handleSavePdf(ActionEvent actionEvent) {
        validateAndPrint();
    }

    private void validateAndPrint() {
        String name = txtCustomerFullName.getText().trim();
        String email = txtCustomerMail.getText().trim();

        if (cart.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "The cart is empty - Add at least one ticket type").showAndWait();
            return;
        }

        boolean hasNonVoucher = cart.stream().anyMatch(i -> !i.getTicketType().isVoucher());

        if (hasNonVoucher && (name.isEmpty() || email.isEmpty())) {
            new Alert(Alert.AlertType.WARNING, "Write both customer name and email for normal tickets").showAndWait();
            return;
        }

        if (hasNonVoucher && !isValidEmail(email)) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid email address").showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Tickets As");
        fileChooser.setInitialFileName("tickets_" + currentEvent.getName() + ".pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File outputFile = fileChooser.showSaveDialog(tblCart.getScene().getWindow());
        if (outputFile == null) return;

        try {
            List<Ticket> allTickets = new ArrayList<>();

            for (CartItem cartItem : cart) {
                TicketType type = cartItem.getTicketType();

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

            TicketPDFGenerator.generateTicket(allTickets, currentEvent, outputFile);

            new Alert(Alert.AlertType.INFORMATION,
                    allTickets.size() + " Ticket(s) created and PDF saved!").showAndWait();

            cart.clear();
            txtCustomerFullName.clear();
            txtCustomerMail.clear();
            updateTotalPrice();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).showAndWait();
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