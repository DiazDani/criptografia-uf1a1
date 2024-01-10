package activitats;

import java.security.MessageDigest;

public class a2 {
    public static void hashClass(){
        String nom = "Daniel Diaz Torio";
        try{
            byte[] data = nom.getBytes();
            MessageDigest md =MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(data);
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02X", b));
            }
            System.out.println("el hash de "+nom+" en  SHA-512 és "+ hex);

        }catch(Exception ex) {
            System.err.println("error: "+ ex);
        }
    }
    public static void main(String[] args) {
        hashClass();
    }
}
