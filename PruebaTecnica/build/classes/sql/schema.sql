CREATE TABLE usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre_completo TEXT NOT NULL,
    edad INTEGER NOT NULL,
    direccion TEXT NOT NULL,
    telefono TEXT NOT NULL,
    fotografia BLOB
);