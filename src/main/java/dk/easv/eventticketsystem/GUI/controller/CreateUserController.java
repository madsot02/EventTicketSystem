package dk.easv.eventticketsystem.GUI.controller;

import dk.easv.eventticketsystem.BE.Role;
import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.GUI.model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateUserController {
    @FXML private TextField txtUsername;
    @FXML private PasswordField pwPassword;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private ComboBox<Role> cmbRole;
    @FXML private Button btnEditAndCreate;

    private UserModel userModel;

    @FXML
    public void initialize(){
        try{
            userModel = new UserModel();

            cmbRole.getItems().addAll(Role.values());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCreateUser(ActionEvent actionEvent) throws IOException {
        try {
            User user = new User(0,
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtUsername.getText(),
                    pwPassword.getText(),
                    cmbRole.getValue());

            userModel.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
