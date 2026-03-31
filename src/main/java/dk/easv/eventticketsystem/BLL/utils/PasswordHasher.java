package dk.easv.eventticketsystem.BLL.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHasher {
    private static final int WORK_FACTOR = 12;

    public static String hashPassword(String password){
        return BCrypt.withDefaults().hashToString(WORK_FACTOR, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hashedPassword){
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }
}
