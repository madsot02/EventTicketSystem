package dk.easv.eventticketsystem.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML private TableColumn colName;
    @FXML private TableColumn colEmail;
    @FXML private TableColumn colRole;
    @FXML private TableView tblAdmin;

    @FXML
    private void handleAddUserAdmin(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/CreateUserView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();

        stage.setTitle("Admin View");
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void handleLogout(ActionEvent actionEvent) {
    }

    @FXML
    private void handleAddUser(ActionEvent actionEvent) {
    }

    @FXML
    private void handleRemoveCoordinator(ActionEvent actionEvent) {
    }
}
