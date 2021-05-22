INSERT INTO usuario (nombre, edad) VALUES
  ("Don Sapo",  22),
  ("Lagarto",   25),
  ("Tortuga",   19),
  ("Pitón",     21),
  ("Axolote",   17);

INSERT INTO consola (nombre) VALUES
  ("Nintendo Switch"), ("Play Station 5"), ("Play Station 4"), ("Steam");

INSERT INTO juego (nombre, id_consola) VALUES
  ("Project Diva MegaMix", 1),
  ("Super Smash Bros. Ultimate", 1),
  ("Final Fantasy VII Remake", 2),
  ("Dragon Ball FighterZ", 3),
  ("Omori", 4);

INSERT INTO usuario_juego (id_usuario, id_juego) VALUES
  (1, 1), (1, 2), (1, 4), (1, 5),
  (2, 3), (2, 4),
  (3, 1),
  (4, 2), (4, 3),
  (5, 1), (5, 2), (5, 3), (5, 4), (5, 5);