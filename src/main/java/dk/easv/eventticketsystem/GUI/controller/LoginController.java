package dk.easv.eventticketsystem.GUI.controller;

//project imports
import dk.easv.eventticketsystem.BE.Role;
import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.BLL.utils.UserSession;
import dk.easv.eventticketsystem.GUI.model.UserModel;

//java imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {
    @FXML private PasswordField pswPassword;
    @FXML private TextField txtUsername;

    //instantiate
    private UserModel userModel;
    private UserSession session;
    private User user;

    public void initialize(){
        try{
            userModel = new UserModel();
            session = UserSession.getInstance();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onSignInClick(ActionEvent actionEvent) throws Exception {
        String username = txtUsername.getText();
        String password = pswPassword.getText();

        user = userModel.loginUser(username, password);

        if(user == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login failed");
            alert.setHeaderText("Invalid username or password");
            alert.showAndWait();
            return;
        }
        session.setCurrentUser(user);

        if (user.getRole() == Role.COORDINATOR) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/CoordinatorView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            stage.setTitle("Coordinator View");
            stage.setScene(scene);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            loginStage.close();
        } else if (user.getRole() == Role.ADMIN) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/eventticketsystem/AdminView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            stage.setTitle("Admin View");
            stage.setScene(scene);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            loginStage.close();
        }
    }

}

