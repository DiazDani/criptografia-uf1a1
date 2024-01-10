package activitats;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class a1 {
    public static void main(String[] args) {
        SecretKey sKey =keygenKeyGeneration("AES",128);
        String encodedKey = Base64.getEncoder().encodeToString(sKey.getEncoded());
        System.out.println("clau AES 128 bits generada: " + encodedKey);
    }

    public static SecretKey keygenKeyGeneration(String algorisme, int keySize) {
        SecretKey skey = null;

        try {
            KeyGenerator kgen= KeyGenerator.getInstance(algorisme);
            kgen.init(keySize);
            skey=kgen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return skey;
    }

}