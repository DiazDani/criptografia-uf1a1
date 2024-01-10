package activitats;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;


public class a4 {

    public static void main(String[] args) {
        String nom = "Daniel Diaz Torio";

        try {

            byte[] salt = generarSalt();

            byte[] data = new byte[salt.length + nom.getBytes().length];
            System.arraycopy(salt, 0, data, 0, salt.length);
            System.arraycopy(nom.getBytes(), 0, data, salt.length, nom.getBytes().length);

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(data);

            int longitutClaveAES = 128;
            byte[] clauAES = truncarODerivarHash(hash, longitutClaveAES);


            String clauHexadecimal = byteArrayToHex(clauAES);
            System.out.println("Clau AES en Hexadecimal: " + clauHexadecimal);

        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Error: " + ex);
        }
    }

    public static byte[] generarSalt() {
        byte[] salt = new byte[16];
        System.out.println(salt);
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public static byte[] truncarODerivarHash(byte[] hash, int longitudClaveAES) {
        int bytesNeeded = longitudClaveAES / 8;
        if (hash.length >= bytesNeeded) {
            return Arrays.copyOf(hash, bytesNeeded);
        } else {

            byte[] clauRandom = new byte[bytesNeeded];
            new SecureRandom().nextBytes(clauRandom);
            return clauRandom;
        }
    }

    public static String byteArrayToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
