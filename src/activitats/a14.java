package activitats;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class a14 {

    public static void main(String[] args) {
        try {
            KeyGenerator generadorClau = KeyGenerator.getInstance("AES");
            generadorClau.init(256);
            SecretKey clauSimetrica = generadorClau.generateKey();

            byte[] clauBytes = clauSimetrica.getEncoded();

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

