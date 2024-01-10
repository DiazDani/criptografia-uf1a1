package activitats;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import java.util.Base64;
import java.security.MessageDigest;
public class a10 {
    public static void main(String[] args) {
        try {
            KeyPair parClau = generarParClauAsimetrica();
            String contrasenya = "LaMevaContrasenyaSecreta";
            byte[] hashContrasenya = calcularHash(contrasenya);
            byte[] hashEncriptat = encriptarHash(hashContrasenya, parClau.getPrivate());
            String hashEncriptatBase64 = Base64.getEncoder().encodeToString(hashEncriptat);
            System.out.println("Hash encriptat: " + hashEncriptatBase64);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static KeyPair generarParClauAsimetrica() throws Exception {
        KeyPairGenerator generadorParClau = KeyPairGenerator.getInstance("RSA");
        generadorParClau.initialize(2048);
        return generadorParClau.generateKeyPair();
    }
    public static byte[] encriptarHash(byte[] hash, PrivateKey clauPrivada) throws Exception {
        Cipher xifrador = Cipher.getInstance("RSA");
        xifrador.init(Cipher.ENCRYPT_MODE, clauPrivada);
        return xifrador.doFinal(hash);
    }
    public static byte[] calcularHash(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(text.getBytes());
    }
}

