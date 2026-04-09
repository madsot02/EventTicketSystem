package dk.easv.eventticketsystem.GUI.controller;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.BLL.utils.UserSession;
import dk.easv.eventticketsystem.GUI.model.EventModel;
import dk.easv.eventticketsystem.GUI.model.UserModel;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AdminController {

    // User table
    @FXML private TableView<User> tblAdmin;
    @FXML private TableColumn<User, String> colFullName;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colRole;
    @FXML private Button btnRemoveCoordinatorOrEvent;

    // Event table
    @FXML private TableView<Event> tblEventManagement;
    @FXML private TableColumn<Event, String> colEventName;
    @FXML private TableColumn<Event, String> colEventLocation;
    @FXML private TableColumn<Event, LocalDate> colStartDateAdmin;
    @FXML private TableColumn<Event, LocalDate> colEndDateAdmin;
    @FXML private TableColumn colAssignedCoordinators;

    private UserModel userModel;
    private EventModel eventModel;

    public void initialize() {
        // User table setup
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        // Event table setup
        colEventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEventLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colStartDateAdmin.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDateAdmin.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colAssignedCoordinators.setCellValueFactory(new PropertyValueFactory<>("assignedCoordinators"));

        try {
            userModel = new UserModel();
            tblAdmin.setItems(userModel.getObservableUsers());
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            eventModel = new EventModel();
            FilteredList<Event> activeEvents = new FilteredList<>(eventModel.getObservableEvents(), Event::isActive);
            tblEventManagement.setItems(activeEvents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/LoginView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        UserSession session = UserSession.getInstance();
        session.clear();

        Stage adminStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        adminStage.close();
    }

    @FXML
    private void handleAddUser(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/CreateUserView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();

        stage.setTitle("Create User");
        stage.setScene(scene);

        CreateUserController userController = loader.getController();
        userController.setUserModel(userModel);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void handleRemoveCoordinator(ActionEvent actionEvent) {
        User selectedUser = tblAdmin.getSelectionModel().getSelectedItem();
        Event selectedEvent = tblEventManagement.getSelectionModel().getSelectedItem();


        if (selectedEvent != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText("Are you sure you want to delete this event?");
            confirm.setContentText(selectedEvent.getName());
            if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) return;

            try {
                eventModel.deleteEvent(selectedEvent);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            }
            return;
        }


        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing Selected");
            alert.setHeaderText("Please select a user or event to delete");
            alert.showAndWait();
            return;
        }

        UserSession session = UserSession.getInstance();
        if (selectedUser.getUserId() == session.getCurrentUser().getUserId()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Action Not Allowed");
            alert.setHeaderText("You cannot remove your own account");
            alert.showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Are you sure you want to delete this user?");
        confirm.setContentText(selectedUser.getFullName());
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) return;

        try {
            userModel.deleteUser(selectedUser);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditUser(ActionEvent actionEvent) throws IOException {
        User selectedUser = tblAdmin.getSelectionModel().getSelectedItem();
        if (selectedUser == null) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/CreateUserView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();

        stage.setTitle("Edit User");
        stage.setScene(scene);

        CreateUserController userController = loader.getController();
        userController.setUserModel(userModel);
        userController.setEditingUser(selectedUser);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void handleAssignCoordinators(ActionEvent actionEvent) throws IOException {
        Event selectedEvent = tblEventManagement.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/CreateEventView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();

        stage.setTitle("Edit Event");
        stage.setScene(scene);

        CreateEventController controller = loader.getController();
        controller.setEventModel(eventModel);
        controller.setEditingEvent(selectedEvent);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void handleDeleteEvent(ActionEvent actionEvent) {
    }
}