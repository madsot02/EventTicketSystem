package dk.easv.eventticketsystem.DAL.interfaces;

//project imports
import dk.easv.eventticketsystem.BE.User;

//java imports
import java.util.List;

public interface IUserDataAccess {
    User getUserByUsername(String username) throws  Exception;
    User createUser(User newUser) throws Exception;
    List<User> getAllUsers() throws  Exception;
    void deleteUser(int userId) throws Exception;
    void updateUser(User user) throws Exception;
}
