package activitats;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class a13 {

    public static void main(String[] args) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey clauSimètrica = keyGen.generateKey();
            byte[] clauBytes = clauSimètrica.getEncoded();

            System.out.println("Clau simètrica generada:");

            for (byte b : clauBytes) {
                System.out.printf("%02X", b);
            }
            System.out.println();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }
}

