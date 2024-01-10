package activitats;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class a8 {

    public static KeyPair generarParellesClausAsimètriques() throws NoSuchAlgorithmException {
        KeyPairGenerator generadorParelles = KeyPairGenerator.getInstance("RSA");
        int midaClaus = 2048;
        generadorParelles.initialize(midaClaus);
        KeyPair parellaClaus = generadorParelles.generateKeyPair();
        return parellaClaus;
    }

    public static void main(String[] args) {
        try {
            KeyPair parellaClaus = generarParellesClausAsimètriques();
            System.out.println("Clau Privada: " + parellaClaus.getPrivate());
            System.out.println("Clau Pública: " + parellaClaus.getPublic());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }
}

