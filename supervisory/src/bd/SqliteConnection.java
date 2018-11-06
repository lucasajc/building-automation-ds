package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteConnection {

	public static Connection connect() throws ClassNotFoundException {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/Users/Public/automacao-bd/dados-automacao";
            // create a connection to the database
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        
        return conn;
    }
	
}
