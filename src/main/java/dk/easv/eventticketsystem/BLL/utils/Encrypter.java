package dk.easv.eventticketsystem.BLL.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Encrypter {
    public static String hashPasswordEncrypter(String password) {
        int workFactor = 12;
        // Hash password using the specified cost
        String bcryptHashString = BCrypt.withDefaults().hashToString(workFactor, password.toCharArray());
        // Example hash: $2a$12$US00g/uMhoSBm.HiuieBjeMtoN69SN.GE25fCpldebzkryUyopws6

        // Verify hash with original password
        //System.out.println(result);
        return bcryptHashString;
    }

    public static boolean verifyPassword(String password, String hash) throws  Exception{

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
        return result.verified;
    }
}
