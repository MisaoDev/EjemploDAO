package model;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class Juego {
  
  private int id;
  private String nombre;
  private Consola consola;

  public Juego() {
  }

  public Juego(int id, String nombre, Consola consola) {
    this.id = id;
    this.nombre = nombre;
    this.consola = consola;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Consola getConsola() {
    return consola;
  }

  public void setConsola(Consola consola) {
    this.consola = consola;
  }
  
}
