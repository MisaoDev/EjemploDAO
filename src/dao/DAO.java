package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class DAO {
  
  protected String dbURL;
  protected String dbDatabase;
  protected String dbUser;
  protected String dbPass;
  
  /**
   * Constructor inicializa los datos de conexión.
   */
  public DAO() {
    dbURL       = "jdbc:mysql://localhost:3306/";
    dbDatabase  = "ejemplodao";
    dbUser      = "root";
    dbPass      = "elsapito20";
  }
  
  /**
   * Crea una nueva conexión a la base de datos.
   * @return el objeto Connection de la nueva conexión
   */
  protected Connection getConnection() {
    try {
      return DriverManager.getConnection(dbURL + dbDatabase, dbUser, dbPass);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * Finaliza la conexión cerrando el objeto <code>connection</code>.
   * @param conn <code>connection</code> a cerrar
   */
  protected void close(Connection conn) {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  /**
   * Finaliza el <code>statement</code>.
   * @param stmt <code>statement</code> a cerrar
   */
  protected void close(Statement stmt) {
    if (stmt != null) {
      try {
        stmt.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
  
}
