package dk.easv.eventticketsystem.GUI.model;

//project imports
import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.BLL.UserManager;

//java imports
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
        User created = userManager.createUser(user);
        users.add(created);
        return created;
    }

    public User loginUser(String username, String password) throws Exception{
        return userManager.loginUser(username, password);
    }
    public void updateUser(User user) throws Exception {
        userManager.updateUser(user);
        int index = users.indexOf(user);
        if (index != -1) {
            users.set(index, user);
        }
    }

    public void deleteUser(User selectedUser) throws Exception {
userManager.deleteUser(selectedUser);
users.remove(selectedUser);
    }
}
