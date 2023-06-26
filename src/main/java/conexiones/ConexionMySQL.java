package conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
	private static ConexionMySQL instancia;
	private Connection connect;

	private ConexionMySQL() {
/*
 * Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/feedback?"
                            + "user=sqluser&password=sqluserpw");
 * */
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/aplicacion", "tpogodio", "P@ssw0rd!"); //P@ssw0rd! 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static ConexionMySQL getInstancia() {
		if (instancia == null)
			instancia = new ConexionMySQL();
		return instancia;
	}
	public Connection getConnection() {
		return connect;
	}
}
