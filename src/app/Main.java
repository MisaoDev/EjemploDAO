package app;

import gui.FormMain;

/**
 *
 * @author MisaoDev <{@link https://github.com/MisaoDev}>
 */
public class Main {
  
  public static void main(String[] args) {
    
    //  Crear un FormMain, ubicarlo en el centro de la pantalla, hacerlo visible
    FormMain ventana = new FormMain();
    ventana.setLocationRelativeTo(null);
    ventana.setVisible(true);
    
  }
  
}
