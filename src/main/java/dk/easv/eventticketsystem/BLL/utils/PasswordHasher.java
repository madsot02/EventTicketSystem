package dk.easv.eventticketsystem.BLL.utils;

//java imports
import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHasher {
    private static final int WORK_FACTOR = 12;

    //hash password
    public static String hashPassword(String password){
        return BCrypt.withDefaults().hashToString(WORK_FACTOR, password.toCharArray());
    }

    //verify password
    public static boolean verifyPassword(String password, String hashedPassword){
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }
}
