/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendamysql;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author megaybte
 */
public class conexionDAO {
    
    Connection conexion = null;
    List<datosDTO> listaDatos = new ArrayList<>();
   
    private void conecta() {
        String url = "jdbc:mysql://localhost:3306/4to20201?useUnicode=true&useJDBCCompilantTimexoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String password = "Uno234...";
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(conexionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(conexionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

}
    
    public boolean inserta(datosDTO datos){    
        boolean estado = true;
        try{
            conecta();
            PreparedStatement ps = conexion.prepareStatement("insert into datos(nombre, edad, sexo) values (?, ?, ?)");
            ps.setString(1, datos.getNombre());
            ps.setString(2, datos.getEdad());
            ps.setString(3, datos.getSexo());
            ps.execute();
        }catch(SQLException ex){
            estado = false;
        }finally{
        cerrar();
        }
        return estado;
    }
    
    public boolean cargar(){
    boolean estado = true;
    datosDTO datos;
    try{
        conecta();
        PreparedStatement ps = conexion.prepareStatement("select * from datos");
        ResultSet resultado = ps.executeQuery();
        while(resultado.next()){
        datos = new datosDTO();
        datos.setId(resultado.getInt("id"));
        datos.setNombre(resultado.getString("nobre"));
        datos.setEdad(resultado.getString("edad"));
        datos.setSexo(resultado.getString("sexo"));
        listaDatos.add(datos);
        }
    }catch(SQLException ex){
        estado = false;
    }finally{
    cerrar();
    }
    return estado;
    }
    
    public boolean actualiza(datosDTO datos){
        boolean estado = true;
        try{
            conecta();
            PreparedStatement ps = conexion.prepareStatement("update datos set nombre = ?, edad = ?, sexo = ? where id = ?");
            ps.setString(1, datos.getNombre());
            ps.setString(2, datos.getEdad());
            ps.setString(3, datos.getSexo());
            ps.setInt(4, datos.getId());
            ps.execute();
        }catch(SQLException ex){
            estado = false;
        }finally{
            cerrar();
        }
        return estado;  
    }
    
    public boolean eliminar(datosDTO datos){
        boolean estado = true;
    try{
        conecta();
        PreparedStatement ps = conexion.prepareStatement("delete from datos where id = ?");
        ps.setInt(1, datos.getId());
        ps.execute();
    }catch(SQLException ex){
        estado = false;
    }finally{
    cerrar();
    }
    return estado;
    }
    
    public List<datosDTO> getdatos(){
        return listaDatos;
    }
    
    private void cerrar(){
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(conexionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    
}
