package dk.easv.eventticketsystem.DAL.interfaces;

import dk.easv.eventticketsystem.BE.User;

import java.util.List;

public interface IUserDataAccess {
    User createUser(User newUser);
    List<User> getAllUsers();
    void updateUser(User updateUser);
    void deleteUser(int userId);
    User getUserByRole(String userType);
    User getUserByUsername(String username);
    boolean userNameExists(String username);
}
