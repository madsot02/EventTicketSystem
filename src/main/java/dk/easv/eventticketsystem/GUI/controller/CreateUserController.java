package dk.easv.eventticketsystem.GUI.controller;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.Role;
import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.GUI.model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private User editingUser = null;
    private boolean editMode = false;
    @FXML
    private Label lblAddEditUser;

    @FXML
    public void initialize(){
        try{
            //userModel = new UserModel();

            cmbRole.getItems().addAll(Role.values());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setEditingUser(User user) {
        if (user != null) {
            this.editingUser = user;
            this.editMode = true;

            txtFirstName.setText(user.getFirstName());
            txtLastName.setText(user.getLastName());
            txtUsername.setText(user.getUsername());
            cmbRole.setValue(user.getRole());
            // leave password blank — user fills in new one if they want to change it

            btnEditAndCreate.setText("Edit User");
            lblAddEditUser.setText("Edit User");
        }
    }

    @FXML
    private void handleCreateUser(ActionEvent actionEvent) throws IOException {
        try {
            if (editMode && editingUser != null) {
                editingUser.setFirstName(txtFirstName.getText());
                editingUser.setLastName(txtLastName.getText());
                editingUser.setUsername(txtUsername.getText());
                editingUser.setRole(cmbRole.getValue());

                // Only update password if user typed a new one
                if (!pwPassword.getText().isBlank()) {
                    editingUser.setPassword(pwPassword.getText());
                }

                userModel.updateUser(editingUser);
            } else {
                User user = new User(0,
                        txtFirstName.getText(),
                        txtLastName.getText(),
                        txtUsername.getText(),
                        pwPassword.getText(),
                        cmbRole.getValue());
                userModel.createUser(user);
            }

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
