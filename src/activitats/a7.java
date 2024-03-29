package activitats;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class a7 {

    private static String contraseñaDB;
    private static String url;
    private static String usuarioDB;

    static {
        try (InputStream input = a7.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Lo siento, no se encuentra el archivo de configuración.");
                System.exit(1);
            }

            prop.load(input);

            url = prop.getProperty("db.url");
            usuarioDB = prop.getProperty("db.user");
            contraseñaDB = prop.getProperty("db.password");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean verificarContrasenya(String usuario, String contrasenya) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conexion = DriverManager.getConnection(url, usuarioDB, contraseñaDB);

            String consulta = "SELECT contrasenya_hash, salt FROM usuaris WHERE nom = ?";
            statement = conexion.prepareStatement(consulta);
            statement.setString(1, usuario);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String hashAlmacenado = resultSet.getString("contrasenya_hash");
                String salt = resultSet.getString("salt");

                String hashEntrante = calcularHashContraseña(contrasenya, salt);

                return hashAlmacenado.equals(hashEntrante);
            }
        } catch (SQLException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public static String calcularHashContraseña(String contraseña, String salt) throws NoSuchAlgorithmException {
        String contraseñaConSalt = contraseña + salt;
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hash = md.digest(contraseñaConSalt.getBytes());
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02X", b));
        }
        return hex.toString();
    }

    public static void main(String[] args) {
        String usuario = "NomUsuari";
        String contrasenya = "ContrasenyaSegura";

        boolean esContrasenyaCorrecta = verificarContrasenya(usuario, contrasenya);

        if (esContrasenyaCorrecta) {
            System.out.println("La contraseña es correcta.");
        } else {
            System.out.println("La contraseña no es correcta.");
        }
    }
}
