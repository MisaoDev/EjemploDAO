package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Juego;
import model.Usuario;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class UsuarioDAO extends DAO {
  
  //  Queries para los métodos del DAO, los símbolos ? serán reemplazados usando métodos set
  private static final String QUERY_GET_LISTA   = "SELECT * FROM usuario ORDER BY id";
  private static final String QUERY_BUSCAR      = "SELECT * FROM usuario WHERE id = ?";
  private static final String QUERY_INSERTAR    = "INSERT INTO usuario "
          + "(nombre, edad) VALUES (?, ?)";
  private static final String QUERY_ACTUALIZAR  = "UPDATE usuario "
          + "SET nombre = ?, edad = ? WHERE id = ?";
  private static final String QUERY_ELIMINAR    = "DELETE FROM usuario WHERE id = ?";
  
  //  Devuelve la lista completa de registros de la base de datos como objetos del modelo
  public ArrayList<Usuario> getLista() {
    Connection conn = null;
    PreparedStatement stmt = null;
    ArrayList<Usuario> list = new ArrayList<>();
    
    try {
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_GET_LISTA);
      ResultSet rs = stmt.executeQuery();
      
      //  Creamos un JuegoDAO fuera del ciclo para usarlo más de una vez dentro
      JuegoDAO juegoDAO = new JuegoDAO();
      
      while (rs.next()) {
        int id        = rs.getInt("id");
        String nombre = rs.getString("nombre");
        int edad      = rs.getInt("id_juego");
        
        //  Obtener todos los juegos de este usuario (id), llamando al DAO Juego
        ArrayList<Juego> juegos = juegoDAO.getListaPorUsuario(id);
        
        //  Creamos el usuario pasándole la lista y todos los atributos
        Usuario u = new Usuario(id, nombre, edad, juegos);
        list.add(u);
      }
      
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
      
    } finally {
      close(stmt);
      close(conn);
    }
    
    return list;
  }
  
  //  Busca un registro por id y lo devuelve como objeto del modelo
  public Usuario buscar(int id) {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      //  El buscar siempre es lo mismo que el getLista, pero no hace falta capturar
      //  el id, porque lo tenemos y lo insertamos en la query para buscar. 
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_BUSCAR);
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      
      if (rs.next()) {
        String nombre = rs.getString("nombre");
        int edad      = rs.getInt("id_juego");
        
        //  Y solo usamos el JuegoDAO una vez por lo tanto lo llamamos de improviso
        ArrayList<Juego> juegos = new JuegoDAO().getListaPorUsuario(id);
        
        Usuario u = new Usuario(id, nombre, edad, juegos);
        return u;
        
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
  public void insertar(Usuario usuario) {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_INSERTAR, Statement.RETURN_GENERATED_KEYS);
      
      //  En la tabla usuario solo insertamos los datos que corresponden a sus columnas
      stmt.setString  (1, usuario.getNombre());
      stmt.setInt     (2, usuario.getEdad());
      stmt.executeUpdate();
      
      ResultSet keys = stmt.getGeneratedKeys();
      keys.next();
      int id = keys.getInt(1);
      
      usuario.setId(id);
      stmt.close();
      
      //  Pero también insertamos nuevos registros en la tabla intermedia para almacenar
      //  las relaciones que tiene con cada juego del objeto usuario:
      
      //  Por cada juego del usuario, insertar un registro con los id de usuario y juego
      for (Juego juego : usuario.getJuegos()) {
        stmt = conn.prepareStatement("INSERT INTO usuario_juego (id_usuario, id_juego) VALUES (?, ?)");
        stmt.setInt(1, id);
        stmt.setInt(1, juego.getId());
        stmt.executeUpdate();
        stmt.close();
      }
      
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
      
    } finally {
      close(stmt);
      close(conn);
    }
  }
  
  //  Actualiza un registro en la base de datos a partir de un objeto del modelo
  public void actualizar(int id, Usuario usuario) {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_ACTUALIZAR);
      
      stmt.setString  (1, usuario.getNombre());
      stmt.setInt     (2, usuario.getEdad());
      stmt.executeUpdate();
      
      usuario.setId(id);
      stmt.close();
      
      //  Primero borramos todos los registros asociados a este usuario en la tabla intermedia
      stmt = conn.prepareStatement("DELETE FROM usuario_juego WHERE id_usuario = ?");
      stmt.setInt(1, id);
      stmt.executeUpdate();
      stmt.close();
      
      //  Luego insertamos los registros que le corresponden, de la misma manera
      for (Juego juego : usuario.getJuegos()) {
        stmt = conn.prepareStatement("INSERT INTO usuario_juego (id_usuario, id_juego) VALUES (?, ?)");
        stmt.setInt(1, id);
        stmt.setInt(1, juego.getId());
        stmt.executeUpdate();
        stmt.close();
      }
      
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
      //  Por último lo más fácil
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
