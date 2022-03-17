
package persistencia;

import entidades.Socio;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SociosDAO implements ISociosDAO {

    private IConexionBD conexionBD;

    public SociosDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }       
    
    @Override
    public boolean agregar(Socio socio) {
        try{
            // ESTABLECEMOS UNA CONEXION CON MYSQL.. SI NO SE PUEDE LANZA SQLEXCEPTION
            Connection conexion = this.conexionBD.crearConexion();
            
            // DEFINIMOS UN OBJETO STATEMENT PARA ENVIAR COMANDOS SQL
            Statement comandoSql = conexion.createStatement();
            
            String codigoSQL = String.format("INSERT INTO socios (nombre, curp) "
                    + "VALUES ('%s', '%s');",
                    socio.getNombre(),
                    socio.getCurp());
            
            // ESTE MÉTODO SE UTILIZA PARA HACER OPERACIONES QUE ALTEREN LOS DATOS (INSERT, DELETE, UPDATE)
            int numeroRegistrosAfectados = comandoSql.executeUpdate(codigoSQL);
            
            // SOLICITAR CERRAR EXPLICITAMENTE LA CONEXIÓN HACIA LA BD
            conexion.close();
            
            //return numeroRegistrosAfectados == 1;
            
            if(numeroRegistrosAfectados == 1){
                return true;
            }else{
                return false;
            }                        
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
            return false;
        }  
    }

    @Override
    public boolean actualizar(Socio socio) {
        try{
            // ESTABLECEMOS UNA CONEXION CON MYSQL.. SI NO SE PUEDE LANZA SQLEXCEPTION
            Connection conexion = this.conexionBD.crearConexion();
            
            // DEFINIMOS UN OBJETO STATEMENT PARA ENVIAR COMANDOS SQL
            Statement comandoSql = conexion.createStatement();
            
            String codigoSQL = String.format("UPDATE socios SET nombre='%s', curp='%s' "
                    + "WHERE id_socio = %d;",
                    socio.getNombre(),
                    socio.getCurp(),
                    socio.getIdSocio());
            
            // ESTE MÉTODO SE UTILIZA PARA HACER OPERACIONES QUE ALTEREN LOS DATOS (INSERT, DELETE, UPDATE)
            int numeroRegistrosAfectados = comandoSql.executeUpdate(codigoSQL);
            
            // SOLICITAR CERRAR EXPLICITAMENTE LA CONEXIÓN HACIA LA BD
            conexion.close();
            
            //return numeroRegistrosAfectados == 1;
            
            if(numeroRegistrosAfectados == 1){
                return true;
            }else{
                return false;
            }                        
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(Long idSocio) {
        try{
            // ESTABLECEMOS UNA CONEXION CON MYSQL.. SI NO SE PUEDE LANZA SQLEXCEPTION
            Connection conexion = this.conexionBD.crearConexion();
            
            // DEFINIMOS UN OBJETO STATEMENT PARA ENVIAR COMANDOS SQL
            Statement comandoSql = conexion.createStatement();
            
            String codigoSQL = String.format("DELETE FROM socios WHERE id_socio = %d;",
                    idSocio);
            
            // ESTE MÉTODO SE UTILIZA PARA HACER OPERACIONES QUE ALTEREN LOS DATOS (INSERT, DELETE, UPDATE)
            int numeroRegistrosAfectados = comandoSql.executeUpdate(codigoSQL);
            
            // SOLICITAR CERRAR EXPLICITAMENTE LA CONEXIÓN HACIA LA BD
            conexion.close();
            
            //return numeroRegistrosAfectados == 1;
            
            if(numeroRegistrosAfectados == 1){
                return true;
            }else{
                return false;
            }                        
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public Socio consultar(Long idSocio) {
        Socio socio = null;
        try{
            Connection conexion = this.conexionBD.crearConexion();
            
            Statement comandoSQL = conexion.createStatement();
            
            String codigoSQL = String.format("SELECT id_socio, nombre, curp "
                    + "FROM socios WHERE id_socio = %d;",
                    idSocio);
            
            ResultSet resultado = comandoSQL.executeQuery(codigoSQL);
            
            if(resultado.next()){
                Long id = resultado.getLong("id_socio");
                String nombre = resultado.getString("nombre");
                String curp = resultado.getString("curp");
                socio = new Socio(id, nombre, curp);
            }
            
            conexion.close();
            return socio;
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
            return socio;
        }
    }

    @Override
    public List<Socio> consultarTodos() {
        List<Socio> listaSocios = new ArrayList<>();
        try{
            Connection conexion = this.conexionBD.crearConexion();
            
            Statement comandoSQL = conexion.createStatement();
            
            String codigoSQL = String.format("SELECT id_socio, nombre, curp FROM socios;");
            
            ResultSet resultado = comandoSQL.executeQuery(codigoSQL);
            
            while(resultado.next()){
                Long idSocio = resultado.getLong("id_socio");
                String nombre = resultado.getString("nombre");
                String curp = resultado.getString("curp");
                Socio socio = new Socio(idSocio, nombre, curp);
                listaSocios.add(socio);
            }
            
            conexion.close();
            return listaSocios;
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
            return listaSocios;
        }
    }
    
}
