package Controller;

import DTO.dto;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author juan
 */
public class LogginController {
     private String sha256(String mensaje){
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte [] digest = sha.digest(mensaje.getBytes());

            StringBuilder hexString = new StringBuilder();

            for(byte b : digest){
                hexString.append(String.format("%02x",b));
            }

            return hexString.toString();


        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    } 
    
    public void Loggin(String name, String pass) throws Exception{
        if(name.isEmpty() || pass.isEmpty()){
            throw new Exception("Por favor llena todos los campos");
        }
        
        String passdb = "", hashpass;
        dto datos = new dto();
        
        try{
            passdb = datos.consultaUsuario(name);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        hashpass = sha256(pass);
        
        if(! hashpass.equals(passdb)){
            throw new Exception("Datos Incorrectos");
        }
        
    } 
}
