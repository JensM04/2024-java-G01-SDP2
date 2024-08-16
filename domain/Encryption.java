package domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Encryption {
    private static final Random random = new SecureRandom();
    private static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    public static String getSaltvalue(int length)
    {
        StringBuilder finalval = new StringBuilder(length);

        for (int i = 0; i < length; i++)
        {
            finalval.append(characters.charAt(random.nextInt(characters.length())));
        }

        return new String(finalval);
    }
   

    public static String generateSecurePassword(String password, String salt)
    {
        
        MessageDigest digest;
        StringBuilder hexString = new StringBuilder();
		try {
			digest = MessageDigest.getInstance("SHA-256");
		
	        byte[] hash = digest.digest((password+salt).getBytes());
	        
	        for (byte b : hash) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        
        return hexString.toString();
    }

    public static boolean verifyUserPassword(String providedPassword,
            String securedPassword, String salt)
    {
        boolean finalval = false;

        String newSecurePassword = generateSecurePassword(providedPassword, salt);

        finalval = newSecurePassword.equalsIgnoreCase(securedPassword);

        return finalval;
    }
    
    



}