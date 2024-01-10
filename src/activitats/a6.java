package activitats;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class a6 {

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
            String url = "jdbc:mysql://localhost:3306/uf1cripto";
            String usuari = "root";
            String contrasenya = "Admin123";
            Connection connexio = DriverManager.getConnection(url, usuari, contrasenya);
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
