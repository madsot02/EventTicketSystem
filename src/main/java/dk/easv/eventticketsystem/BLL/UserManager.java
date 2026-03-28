package dk.easv.eventticketsystem.BLL;

import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.BLL.utils.Encrypter;
import dk.easv.eventticketsystem.DAL.db.UserDAO_DB;
import dk.easv.eventticketsystem.DAL.interfaces.IUserDataAccess;

import java.util.List;
/*
public class UserManager {
    private final IUserDataAccess userDataAccess;

    public UserManager() throws Exception{
        userDataAccess = new UserDAO_DB();
    }

    public List<User> getAllUsers() throws Exception{
        return userDataAccess.getAllUsers();
    }

    public User createUser(User newUser) throws Exception{
        return userDataAccess.createUser(newUser);
    }

    public void updateUser(User updateUser) throws Exception{
        userDataAccess.updateUser(updateUser);
    }

    public void deleteUser(User deleteUser) throws Exception{
        userDataAccess.deleteUser(deleteUser.getUserId());
    }

    public User loginUser(String username, String password)throws Exception{
        if(username == null || password == null){
            return null;
        }

        User user = userDataAccess.getUserByUsername(username);

        if(user == null) return null;

        Encrypter.verifyPassword(password, user.getPassword());

        return user;
    }
}

 */
