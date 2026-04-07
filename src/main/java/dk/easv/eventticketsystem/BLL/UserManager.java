package dk.easv.eventticketsystem.BLL;

import dk.easv.eventticketsystem.BE.Event;
import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.BLL.utils.PasswordHasher;
import dk.easv.eventticketsystem.DAL.db.UserDAO_DB;
import dk.easv.eventticketsystem.DAL.interfaces.IUserDataAccess;

import java.util.List;

public class UserManager {
    private IUserDataAccess userDataAccess;

    public UserManager() throws Exception{
        userDataAccess = new UserDAO_DB();
    }

    public User createUser(User user) throws Exception{
        String hashedPassword = PasswordHasher.hashPassword(user.getPassword());

        User newUser = new User(user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                hashedPassword,
                user.getRole());

        return userDataAccess.createUser(newUser);
    }
    public User loginUser(String username, String password) throws Exception{
        User user = userDataAccess.getUserByUsername(username);

        if (user == null) {
            return null;
        }

        boolean valid = PasswordHasher.verifyPassword(password, user.getPassword());

        if(valid) {
            return user;
        } else {
            return null;
        }
    }

    public List<User> getAllUsers() throws Exception{
        return userDataAccess.getAllUsers();
    }

    public void updateUser(User user) throws Exception {

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(PasswordHasher.hashPassword(user.getPassword()));
        }
        userDataAccess.updateUser(user);
    }

    public void deleteUser(User selectedUser) throws Exception {
        userDataAccess.deleteUser(selectedUser.getUserId());
    }


}
