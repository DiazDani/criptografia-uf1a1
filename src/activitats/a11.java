package activitats;
import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;

public class a11 {

    public static void main(String[] args) {
        try {
            KeyPair parellClaus = generarParellClausAsimetric();
            String contrasenya = "LaMevaContrasenyaSecreta";
            byte[] hashContrasenya = calcularHash(contrasenya);
            byte[] hashEncriptat = encriptarHash(hashContrasenya, parellClaus.getPrivate());
            byte[] hashDesencriptat = desencriptarHash(hashEncriptat, parellClaus.getPublic());
            String hashDesencriptatBase64 = Base64.getEncoder().encodeToString(hashDesencriptat);
            System.out.println("Hash desencriptat: " + hashDesencriptatBase64);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static KeyPair generarParellClausAsimetric() throws Exception {
        KeyPairGenerator generadorParellClaus = KeyPairGenerator.getInstance("RSA");
        generadorParellClaus.initialize(2048);
        return generadorParellClaus.generateKeyPair();
    }

    public static byte[] encriptarHash(byte[] hash, PrivateKey clauPrivada) throws Exception {
        Cipher xifrador = Cipher.getInstance("RSA");
        xifrador.init(Cipher.ENCRYPT_MODE, clauPrivada);
        return xifrador.doFinal(hash);
    }

    public static byte[] desencriptarHash(byte[] hashEncriptat, PublicKey clauPublica) throws Exception {
        Cipher xifrador = Cipher.getInstance("RSA");
        xifrador.init(Cipher.DECRYPT_MODE, clauPublica);
        return xifrador.doFinal(hashEncriptat);
    }

    public static byte[] calcularHash(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(text.getBytes());
    }
}
