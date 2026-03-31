package dk.easv.eventticketsystem.GUI.controller;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.BLL.utils.UserSession;
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

public class AdminController {


    @FXML private TableView<User> tblAdmin;
    @FXML
    private TableColumn<User, String> colFullName;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colRole;

    private UserModel userModel;

    FilteredList<User> filteredUsers;
    @FXML
    private Button btnRemoveCoordinator;

    public void initialize(){
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        btnRemoveCoordinator.disableProperty().bind(
                tblAdmin.getSelectionModel().selectedItemProperty().isNull()); // greys out button, until a user is selected



        try{
            userModel = new UserModel();
            tblAdmin.setItems(userModel.getObservableUsers());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddUserAdmin(ActionEvent actionEvent) throws IOException {

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

        stage.setTitle("Create User View");
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void handleRemoveCoordinator(ActionEvent actionEvent) {
        User selectedUser = tblAdmin.getSelectionModel().getSelectedItem();

        if(selectedUser == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Event Selected");
            alert.setHeaderText("Please select an event to delete");
            alert.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Are you sure you want to delete this event");
        confirm.setContentText(selectedUser.getFullName());
        if(confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK){
            return;
        }
        try{
            userModel.deleteUser(selectedUser);
            //filteredEvents.setPredicate(filteredEvents.getPredicate());

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
        }
    }
