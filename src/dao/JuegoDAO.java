package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Consola;
import model.Juego;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class JuegoDAO extends DAO {
  
  //  Queries para los métodos del DAO, los símbolos ? serán reemplazados usando métodos set
  private static final String QUERY_GET_LISTA   = "SELECT * FROM juego ORDER BY id";
  private static final String QUERY_BUSCAR      = "SELECT * FROM juego WHERE id = ?";
  private static final String QUERY_INSERTAR    = "INSERT INTO juego "
          + "(nombre, id_consola) VALUES (?, ?)";
  private static final String QUERY_ACTUALIZAR  = "UPDATE juego "
          + "SET nombre = ?, id_consola = ? WHERE id = ?";
  private static final String QUERY_ELIMINAR    = "DELETE FROM juego WHERE id = ?";
  
  //  Usaremos esta para buscar todos los juegos de un usuario específico
  private static final String QUERY_GET_LISTA_POR_USUARIO   = 
            "SELECT juego.* FROM juego "
          + "JOIN usuario_juego AS uj ON uj.id_juego = juego.id "
          + "WHERE uj.id_usuario = ? ORDER BY juego.id";
  
  //  Devuelve la lista completa de registros de la base de datos como objetos del modelo
  public ArrayList<Juego> getLista() {
    Connection conn = null;
    PreparedStatement stmt = null;
    ArrayList<Juego> list = new ArrayList<>();
    
    try {
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_GET_LISTA);
      ResultSet rs = stmt.executeQuery();
      
      //  Creamos un ConsolaDAO para usarlo en el ciclo
      ConsolaDAO consolaDAO = new ConsolaDAO();
      
      while (rs.next()) {
        //  Capturamos los datos del registro, nótese el idConsola (columna id_consola)
        int id        = rs.getInt("id");
        String nombre = rs.getString("nombre");
        int idConsola = rs.getInt("id_consola");
        
        //  Buscamos ese id con el DAO consola para crear un objeto Consola
        Consola consola = consolaDAO.buscar(idConsola);
        
        //  Creamos el objeto Juego con los atributos capturados y el objeto consola
        Juego j = new Juego(id, nombre, consola);
        list.add(j);
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
  public Juego buscar(int id) {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_BUSCAR);
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      
      if (rs.next()) {
        //  Capturamos los datos
        String nombre = rs.getString("nombre");
        int idConsola = rs.getInt("id_consola");
        
        //  Aquí no hay un ciclo así que podemos crear el DAO en el momento
        Consola consola = new ConsolaDAO().buscar(idConsola);
        
        //  Creamos el objeto y lo devolvemos
        Juego j = new Juego(id, nombre, consola);
        return j;
        
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
  public void insertar(Juego juego) {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_INSERTAR, Statement.RETURN_GENERATED_KEYS);
      
      //  En esta query debemos insertar el nombre y el id_consola, este último simplemente
      //  lo traemos del objeto Consola almacenado en el objeto Juego
      stmt.setString  (1, juego.getNombre());
      stmt.setInt     (2, juego.getConsola().getId());
      stmt.executeUpdate();
      
      ResultSet keys = stmt.getGeneratedKeys();
      keys.next();
      int id = keys.getInt(1);
      
      juego.setId(id);
      
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
      
    } finally {
      close(stmt);
      close(conn);
    }
  }
  
  //  Actualiza un registro en la base de datos a partir de un objeto del modelo
  public void actualizar(int id, Juego juego) {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_ACTUALIZAR);
      
      //  Lo mismo que el INSERT, pero con el id al final (para la cláusula WHERE)
      stmt.setString  (1, juego.getNombre());
      stmt.setInt     (2, juego.getConsola().getId());
      stmt.setInt     (3, id);
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
      //  El DELETE siempre es lo mismo y no hay nada especial que añadir ni cambiar
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
  
  //  Devuelve todos los juegos asociados a un usuario especificado por id
  //  Usaremos esto para pasarle la lista al DAO Usuario
  public ArrayList<Juego> getListaPorUsuario(int idUsuario) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ArrayList<Juego> list = new ArrayList<>();
    
    try {
      conn = getConnection();
      stmt = conn.prepareStatement(QUERY_GET_LISTA_POR_USUARIO);
      
      //  Insertamos el id de usuario en la query, el resto es igual al getLista normal
      stmt.setInt(1, idUsuario);
      
      ResultSet rs = stmt.executeQuery();
      
      ConsolaDAO consolaDAO = new ConsolaDAO();
      
      while (rs.next()) {
        int id        = rs.getInt("id");
        String nombre = rs.getString("nombre");
        int idConsola = rs.getInt("id_consola");
        
        Consola consola = consolaDAO.buscar(idConsola);
        
        Juego j = new Juego(id, nombre, consola);
        list.add(j);
      }
      
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
      
    } finally {
      close(stmt);
      close(conn);
    }
    
    return list;
  }
  
}
