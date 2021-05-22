DROP DATABASE IF EXISTS ejemplodao;
CREATE DATABASE ejemplodao;
USE ejemplodao;

CREATE TABLE usuario (
  id        INT           PRIMARY KEY AUTO_INCREMENT,
  nombre    VARCHAR(20)   NOT NULL,
  edad      INT
);

CREATE TABLE usuario_juego (
  id_usuario  INT NOT NULL,
  id_juego    INT NOT NULL,
  PRIMARY KEY (id_usuario, id_juego)
);

CREATE TABLE juego (
  id          INT           PRIMARY KEY AUTO_INCREMENT,
  nombre      VARCHAR(30)   NOT NULL UNIQUE,
  id_consola  INT           NOT NULL
);

CREATE TABLE consola (
  id          INT           PRIMARY KEY AUTO_INCREMENT,
  nombre      VARCHAR(16)   NOT NULL UNIQUE
);

ALTER TABLE usuario_juego
  ADD CONSTRAINT fk_usuariojuego_usuario
  FOREIGN KEY (id_usuario) REFERENCES usuario(id)
  ON DELETE CASCADE ON UPDATE CASCADE,
  
  ADD CONSTRAINT fk_usuariojuego_juego
  FOREIGN KEY (id_juego) REFERENCES juego(id)
  ON DELETE CASCADE ON UPDATE CASCADE;
  
ALTER TABLE juego
  ADD CONSTRAINT fk_juego_consola
  FOREIGN KEY (id_consola) REFERENCES consola(id)
  ON DELETE RESTRICT ON UPDATE CASCADE;

