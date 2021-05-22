# EjemploDAO
Ejemplo básico de implementación de DAOs y su aplicación con swing.

## Instrucciones
1. Clonar/Descargar el proyecto
2. Crear la base de datos con los scripts de la carpeta **sql**
3. Editar la clase *_dao.DAO_* y en el constructor poner tus datos de conexión (cambiar la password)
4. Ejecutar el proyecto

En la aplicación solo funciona el módulo de **_Consola_** por ahora. Pero están todos los DAO.  
  &nbsp;

## Cómo estudiar el código
Para comprender mejor el ejemplo, se recomienda estudiar las partes del programa en un orden específico. Los comentarios del código están pensados para este orden.

1. Analizar el _modelo_ de datos y los objetos que lo representan.
2. Analizar los DAO en orden:
   * _**DAO**_ - Clase padre de los DAO con la lógica de conexión
   * _**ConsolaDAO**_
   * _**JuegoDAO**_
   * _**UsuarioDAO**_
   
3. _**Main**_ - Punto de inicio del programa. Inicializa la primera ventana.
4. _**FormMain**_ - Primera ventana. Contiene un poco de lógica para abrir las otras ventanas.
5. _**FormConsola**_ - CRUD básico. Muestra el uso del DAO y el manejo del jTable.

  &nbsp;

## Modelo de datos

![modelo](https://github.com/MisaoDev/EjemploDAO/blob/main/sql/modelo.png)
