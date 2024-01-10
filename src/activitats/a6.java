package activitats;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class a6 {

    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    static {
        try (InputStream input = a6.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Lo siento, no se encuentra el archivo de configuración.");
                System.exit(1);
            }

            prop.load(input);

            dbUrl = prop.getProperty("db.url");
            dbUser = prop.getProperty("db.user");
            dbPassword = prop.getProperty("db.password");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String generarSaltHexadecimal() {
        SecureRandom random = new SecureRandom();
        int salt = random.nextInt();
        return Integer.toHexString(salt);
    }

    public static String calcularHashContraseña(String contrasenya, String salt) throws NoSuchAlgorithmException {
        String contrasenyaAmbSalt = contrasenya + salt;
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hash = md.digest(contrasenyaAmbSalt.getBytes());
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02X", b));
        }
        return hex.toString();
    }

    public static void guardarUsuariEnBaseDeDades(String nom, String cognoms, String contrasenya) {
        Connection connexioDB = obtenirConnexioBaseDeDades();
        if (connexioDB != null) {
            try {
                String salt = generarSaltHexadecimal();
                String hashContrasenya = calcularHashContraseña(contrasenya, salt);

                String consulta = "INSERT INTO usuaris (nom, cognoms, contrasenya_hash, salt) VALUES (?, ?, ?, ?)";
                PreparedStatement sentencia = connexioDB.prepareStatement(consulta);
                sentencia.setString(1, nom);
                sentencia.setString(2, cognoms);
                sentencia.setString(3, hashContrasenya);
                sentencia.setString(4, salt);

                sentencia.executeUpdate();

                sentencia.close();
                connexioDB.close();

                System.out.println("Usuari guardat a la base de dades.");

            } catch (NoSuchAlgorithmException | SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static Connection obtenirConnexioBaseDeDades() {
        try {
            Connection connexio = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            return connexio;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        guardarUsuariEnBaseDeDades("NomUsuari", "CognomsUsuari", "ContrasenyaSegura");
    }
}
