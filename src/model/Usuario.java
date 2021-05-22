package model;

import java.util.ArrayList;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class Usuario {
  
  private int id;
  private String nombre;
  private int edad;
  private ArrayList<Juego> juegos;

  public Usuario() {
  }

  public Usuario(int id, String nombre, int edad, ArrayList<Juego> juegos) {
    this.id = id;
    this.nombre = nombre;
    this.edad = edad;
    this.juegos = juegos;
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

  public int getEdad() {
    return edad;
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  public ArrayList<Juego> getJuegos() {
    return juegos;
  }

  public void setJuegos(ArrayList<Juego> juegos) {
    this.juegos = juegos;
  }
  
  
  
}
