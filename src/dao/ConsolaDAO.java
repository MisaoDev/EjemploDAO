package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Consola;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class ConsolaDAO extends DAO {
  
  //  Queries para los métodos del DAO, los símbolos ? serán reemplazados usando métodos set
  private static final String QUERY_GET_LISTA   = "SELECT * FROM consola ORDER BY id";
  private static final String QUERY_BUSCAR      = "SELECT * FROM consola WHERE id = ?";
  private static final String QUERY_INSERTAR    = "INSERT INTO consola (nombre) VALUES (?)";
  private static final String QUERY_ACTUALIZAR  = "UPDATE consola SET nombre = ? WHERE id = ?";
  private static final String QUERY_ELIMINAR    = "DELETE FROM consola WHERE id = ?";
  
  //  Devuelve la lista completa de registros de la base de datos como objetos del modelo
  public ArrayList<Consola> getLista() {
    Connection conn = null;
    PreparedStatement stmt = null;
    ArrayList<Consola> list = new ArrayList<>();
    
    try {
      //  Crear la conexión, preparar el statement (la query) y ejecutarla
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_GET_LISTA);
      ResultSet rs = stmt.executeQuery();
      
      //  Repetir por cada registro resultante
      while (rs.next()) {
        //  Capturar los datos del registro
        int id        = rs.getInt("id");
        String nombre = rs.getString("nombre");
        
        //  Crear el objeto con los datos capturados y agregarlo a la lista
        Consola c = new Consola(id, nombre);
        list.add(c);
      }
      
      //  Si hay un error de SQL, arrojarlo a la aplicación
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
      
      //  Cerrar la conexión, incluso si ocurre un error
    } finally {
      close(stmt);
      close(conn);
    }
    
    //  Devolver la lista generada
    return list;
  }
  
  //  Busca un registro por id y lo devuelve como objeto del modelo
  public Consola buscar(int id) {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_BUSCAR);
      
      //  Reemplaza el primer(1) símbolo ? en la query por el id a buscar
      stmt.setInt(1, id);
      
      ResultSet rs = stmt.executeQuery();
      
      //  Si hay un resultado, capturar los datos y devolver objeto, sino devolver null
      if (rs.next()) {
        String nombre = rs.getString("nombre");
        
        Consola c = new Consola(id, nombre);
        return c;
        
      } else {
        return null;
      }
      
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
      
    } finally {
      close(stmt);
      close(conn);
    }
  }
  
  //  Inserta un objeto del modelo como registro nuevo en la base de datos
  public void insertar(Consola consola) {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      conn = getConnection();
      
      //  Añadimos un parámetro para indicar que esta query debe devolver la PK de inserción
      stmt = conn.prepareStatement(QUERY_INSERTAR, Statement.RETURN_GENERATED_KEYS);
      
      //  Insertamos en la query los valores (en este caso solo el nombre)
      stmt.setString(1, consola.getNombre());
      
      //  Para query de edición (INSERT, UPDATE, DELETE) usar executeUpdate en vez de executeQuery
      stmt.executeUpdate();
      
      //  Obtiene las PK generados al insertar, luego guarda la primera como id
      ResultSet keys = stmt.getGeneratedKeys();
      keys.next();
      int id = keys.getInt(1);
      
      //  Guarda el id en el objeto que se insertó
      consola.setId(id);
      
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
      
    } finally {
      close(stmt);
      close(conn);
    }
  }
  
  //  Actualiza un registro en la base de datos a partir de un objeto del modelo
  public void actualizar(int id, Consola consola) {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_ACTUALIZAR);
      
      //  Insertamos en la query el nombre y el id (el id siempre va último en un UPDATE)
      stmt.setString  (1, consola.getNombre());
      stmt.setInt     (2, id);
      stmt.executeUpdate();
      
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
      
    } finally {
      close(stmt);
      close(conn);
    }
  }
  
  //  Borra un registro de la base de datos a partir del id
  public void eliminar(int id) {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_ELIMINAR);
      stmt.setInt(1, id);
      stmt.executeUpdate();
      
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
      
    } finally {
      close(stmt);
      close(conn);
    }
  }
  
}
