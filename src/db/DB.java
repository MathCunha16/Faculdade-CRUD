package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
	
	   public static Connection getConnection() {
	        try {
	            Properties props = loadProperties();
	            String url = props.getProperty("dburl") + "?allowPublicKeyRetrieval=true";
	            return DriverManager.getConnection(url, props); // nova conexão a cada chamada
	        } catch (SQLException e) {
	            throw new DbException(e.getMessage());
	        }
	    }

	    public static void closeConnection(Connection conn) { // recebe a conexão como parâmetro
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                throw new DbException(e.getMessage());
	            }
	        }
	    }
	
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}

}
