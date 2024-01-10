package activitats;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class a12 {

    public static void main(String[] args) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey clauSimètrica = keyGen.generateKey();

            byte[] clauBytes = clauSimètrica.getEncoded();
            String clauBase64 = Base64.getEncoder().encodeToString(clauBytes);

            System.out.println("Clau simètrica generada:");
            System.out.println(clauBase64);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }
}

