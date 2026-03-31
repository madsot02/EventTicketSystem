package dk.easv.eventticketsystem.GUI.model;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserModel {
    private UserManager userManager;
    private ObservableList<User> users;

    public UserModel() throws Exception{
        userManager = new UserManager();

        users = FXCollections.observableArrayList();
        users.addAll(userManager.getAllUsers());
    }

    public ObservableList<User> getObservableUsers() {
        return users;
    }

    public User createUser(User user) throws Exception{
        return userManager.createUser(user);
    }

    public User loginUser(String username, String password) throws Exception{
        return userManager.loginUser(username, password);
    }

    public void deleteUser(User selectedUser) throws Exception {
userManager.deleteUser(selectedUser);
users.remove(selectedUser);
    }
}
