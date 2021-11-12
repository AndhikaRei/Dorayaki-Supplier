package supplier.database;

import java.sql.*;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * DBHandler class is responsible for handling the database connection.
 */
public class DatabaseHandler {

  // Database connection.
  private Connection conn;

  // Database parameter for connection.
  // private static String DB_URL = "jdbc:mysql://loalhost:3306/factory";
  private static String DB_HOST = Dotenv.load().get("MYSQL_HOST", "localhost");
  private static String DB_PORT = Dotenv.load().get("MYSQL_PORT", "3306");
  private static String DB_NAME = Dotenv.load().get("MYSQL_DATABASE", "factory");
  private static String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

  // Database credentials for login.
  private static String DB_USER = Dotenv.load().get("MYSQL_USER", "root");
  private static String DB_PASS = Dotenv.load().get("MYSQL_PASSWORD", "");
  

  // Constructor for database handler.
  public DatabaseHandler() {
    try {
      // Trying to connnect database.
      System.out.println("Connecting to MYSQL database...");

      Class.forName("com.mysql.cj.jdbc.Driver");
      this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

    } catch (Exception e) {
      // Failed to connect
      e.printStackTrace();
      System.out.println("Connection failed...");
    }
  }

  // Get database connection.
  public Connection getConn() {
    return this.conn;
  }
}
