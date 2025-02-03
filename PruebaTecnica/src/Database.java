import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:usuarios.db";

    // Método para obtener una conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Inicializa la base de datos, creando la tabla de usuarios si no existe
    public static void initialize() {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "nombre TEXT NOT NULL, "
                    + "edad INTEGER, "
                    + "direccion TEXT, "
                    + "telefono TEXT, "
                    + "fotografia BLOB"
                    + ");";
            stmt.execute(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
