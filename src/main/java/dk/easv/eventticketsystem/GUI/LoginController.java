package dk.easv.eventticketsystem.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML private PasswordField pswPassword;
    @FXML private TextField txtUsername;

    @FXML
    private void onSignInClick(ActionEvent actionEvent) throws IOException {
        String username = "hej";
        String password = "bruh";

        String usernameAdmin = "admin";
        String passwordAdmin = "admin";

        if(txtUsername.getText().equals(username) && pswPassword.getText().equals(password)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/CoordinatorView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            stage.setTitle("Coordinator View");
            stage.setScene(scene);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        else if (txtUsername.getText().equals(usernameAdmin) && pswPassword.getText().equals(passwordAdmin)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/AdminView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            stage.setTitle("Admin View");
            stage.setScene(scene);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }
    }
}

