package dk.easv.eventticketsystem.BLL.utils;

//project imports
import dk.easv.eventticketsystem.BE.User;

//manages the current logged in user
public class UserSession {
    private static UserSession instance;
    private User currentUser;

    private UserSession() {}

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    //set the current user (when logging in)
    public void setCurrentUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null. Use clear() to log out.");
        }
        this.currentUser = user;
    }

    //get the currently logged in user
    public User getCurrentUser() {
        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }
        return currentUser;
    }

    //clear currentuser when logging out
    public void clear() {
        currentUser = null;
    }
}