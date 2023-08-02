package Controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import DTO.dto;

/**
 *
 * @author juan
 */
public class RegisterController {
    
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
    
    
     private boolean isSecurePassword(String password){
        if(password.length() < 8){
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for(char c : password.toCharArray()){
            if(Character.isUpperCase(c)){
                hasUppercase = true;
            }else if(Character.isLowerCase(c)){
                hasLowercase = true;
            }else if(Character.isDigit(c)){
                hasNumber = true;
            }else{
                hasSpecialChar = true;
            }
        }

        return hasUppercase && hasLowercase && hasNumber && hasSpecialChar;
    }
    
    public void Registro(String Name, String Pass, String Repass){
        
        String hashPass = "";
        
        dto datos = new dto();
        
       
        if(Name.isEmpty() || Pass.isEmpty() || Repass.isEmpty()){
            throw new RuntimeException("Por favor llena todos los campos");
        }
        
        if(!Pass.equals(Repass)){
            throw new RuntimeException("Las contraseñas no coinciden");
        }
        
        if(!isSecurePassword(Pass)){
            throw new RuntimeException("Contraseña no segura");
        }
        
        hashPass = sha256(Pass);
        
        try{
            datos.insertUser(Name, hashPass);
        }catch ( RuntimeException e){
            throw new RuntimeException("Error al insertar los datos");
        }
    }
}
