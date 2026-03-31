package dk.easv.eventticketsystem.DAL.interfaces;

import dk.easv.eventticketsystem.BE.User;

import java.util.List;

public interface IUserDataAccess {
    User createUser(User newUser) throws Exception;
    List<User> getAllUsers();
    void deleteUser(int userId);
}
