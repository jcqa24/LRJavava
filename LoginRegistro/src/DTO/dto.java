package DTO;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juan
 */
public class dto {
    
    private Connection connection;
    
    public void ConectarBD(){
        try {
            String host = "jdbc:mysql://localhost/";
            String user = "root";
            String pass = "";
            String dbname ="loginUser";
            
            
            connection = DriverManager.getConnection(host+dbname,user,pass);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void Desconeccion(){
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void insertUser(String name, String pass) throws RuntimeException{
        ConectarBD();
        String sql = "Insert Into Users(Name,pass) Values('"+name+"','"+pass+"')";
        Statement stmt;
        int result;
        
        try{
            stmt = connection.createStatement();
            result = stmt.executeUpdate(sql);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al insertar los datos");
        }
    }
    
    public String consultaUsuario(String name) throws Exception{
        try {
            ConectarBD();
            String sql ="SELECT * FROM Users WHERE Name = '"+name+"'";
            Statement stmt;
            ResultSet rs;
            
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            
            if(!rs.next()){
                throw new Exception("Datos Incorrectos");
            }
            
            return rs.getString("pass");
        } catch (SQLException ex) {
            Logger.getLogger(dto.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            Desconeccion();
        }
        return "";
    }
}
