package dk.easv.eventticketsystem.BLL;

import dk.easv.eventticketsystem.BE.User;
import dk.easv.eventticketsystem.BLL.utils.PasswordHasher;
import dk.easv.eventticketsystem.DAL.db.UserDAO_DB;
import dk.easv.eventticketsystem.DAL.interfaces.IUserDataAccess;

import java.util.List;

public class UserManager {
    private IUserDataAccess userDataAccess;

    public UserManager() throws Exception {
        userDataAccess = new UserDAO_DB();
    }

    public User createUser(User user) throws Exception {
        String hashedPassword = PasswordHasher.hashPassword(user.getPassword());

        User newUser = new User(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                hashedPassword,
                user.getRole());

        return userDataAccess.createUser(newUser);
    }

    public User loginUser(String username, String password) throws Exception {
        User user = userDataAccess.getUserByUsername(username);

        if (user == null) return null;

        boolean valid = PasswordHasher.verifyPassword(password, user.getPassword());
        return valid ? user : null;
    }

    public List<User> getAllUsers() throws Exception {
        return userDataAccess.getAllUsers();
    }

    public void updateUser(User user) throws Exception {
        // Bug  fix — only hash if it's a new plain text password, not an existing bcrypt hash
        if (user.getPassword() != null && !user.getPassword().isBlank()
                && !user.getPassword().startsWith("$2")) {
            user.setPassword(PasswordHasher.hashPassword(user.getPassword()));
        }
        userDataAccess.updateUser(user);
    }

    public void deleteUser(User selectedUser) throws Exception {
        userDataAccess.deleteUser(selectedUser.getUserId());
    }
}