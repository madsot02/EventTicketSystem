package dk.easv.eventticketsystem.DAL.interfaces;

import dk.easv.eventticketsystem.BE.User;

import java.util.List;

public interface IUserDataAccess {
    User getUserByUsername(String username) throws  Exception;
    User createUser(User newUser) throws Exception;
    List<User> getAllUsers() throws  Exception;
    void deleteUser(int userId);
}
