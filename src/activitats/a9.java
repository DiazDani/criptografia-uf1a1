package activitats;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import java.util.Base64;
public class a9 {
    public static void main(String[] args) {
        try {
            KeyPair parClaus = generarParClausAsimètriques();
            String contrasenya = "LaMevaContrasenyaSecreta";
            byte[] contrasenyaEncriptada = encriptarContrasenya(contrasenya, parClaus.getPrivate());
            String contrasenyaEncriptadaBase64 = Base64.getEncoder().encodeToString(contrasenyaEncriptada);
            System.out.println("Contrasenya encriptada: " + contrasenyaEncriptadaBase64);
        } catch (Exception ex) {
            ex.printStackTrace();
        }}
    public static KeyPair generarParClausAsimètriques() throws Exception {
        KeyPairGenerator generadorParClau = KeyPairGenerator.getInstance("RSA");
        generadorParClau.initialize(2048);
        return generadorParClau.generateKeyPair();
    }
    public static byte[] encriptarContrasenya(String contrasenya, PrivateKey clauPrivada) throws Exception {
        Cipher xifrador = Cipher.getInstance("RSA");
        xifrador.init(Cipher.ENCRYPT_MODE, clauPrivada);
        return xifrador.doFinal(contrasenya.getBytes());
    }}

