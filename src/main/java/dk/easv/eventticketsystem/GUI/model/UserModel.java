package dk.easv.eventticketsystem.GUI.model;

import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.BLL.UserManager;

public class UserModel {
    private UserManager userManager;

    public UserModel() throws Exception{
        userManager = new UserManager();
    }

    public User createUser(User user) throws Exception{
        return userManager.createUser(user);
    }
}
