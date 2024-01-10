package activitats;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class a3 {

    public static void main(String[] args) {
        try {
            String textOriginal = "Text a codificar";
            byte[] sha256Hash = generarSHA256(textOriginal);
            int longitudClauAES = 128;
            byte[] clauAES = truncarOderivarHash(sha256Hash, longitudClauAES);
            String clauHexadecimal = byteArrayToHex(clauAES);
            System.out.println("Clau AES en Hexadecimal: " + clauHexadecimal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] generarSHA256(String textOriginal) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(textOriginal.getBytes());
    }

    public static byte[] truncarOderivarHash(byte[] hash, int longitudClauAES) {
        int bytesNecessaris = longitudClauAES / 8;
        if (hash.length >= bytesNecessaris) {
            return Arrays.copyOf(hash, bytesNecessaris);
        } else {
            SecureRandom random = new SecureRandom();
            byte[] clauAleatoria = new byte[bytesNecessaris];
            random.nextBytes(clauAleatoria);
            return clauAleatoria;
        }
    }

    public static String byteArrayToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
