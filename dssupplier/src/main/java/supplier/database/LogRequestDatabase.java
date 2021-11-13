package supplier.database;

// Import dependency.
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

// Import own package
public class LogRequestDatabase {
    
    // Create log request table.
    public void createLogRequestTable() throws SQLException{
        // Get database connection.
        DatabaseHandler handler = new DatabaseHandler();
        Connection conn = handler.getConn();
            
        // Prepare and execute the statement.
        Statement statement = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS log_request " +
                "(id INTEGER NOT NULL AUTO_INCREMENT , " +
                " ip VARCHAR(255), " + 
                " endpoint VARCHAR(255), " + 
                " timestamp TIMESTAMP, " + 
                " PRIMARY KEY ( id ))";
        statement.executeUpdate(sql);
    }

    // Add request to table and also apply rate-limiter.
    public String addRequestLog(String ip, String endpoint) throws SQLException, Exception {
        // Get database connection.
        DatabaseHandler handler = new DatabaseHandler();
        Connection conn = handler.getConn();

        // Get timestamp
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        // TODO : Change back to 1 menit.
        // Default selama 1 menit.
        Timestamp targetedTimestamp = new Timestamp(System.currentTimeMillis()-10000);
            
        // Prepare and execute the statement.
        PreparedStatement statement = null;
        String sql = "SELECT COUNT(*) as cnt " +
                "FROM log_request " +
                "WHERE ip = ? AND " + 
                "endpoint = ? AND " + 
                "timestamp > ? ";
        statement = conn.prepareStatement(sql);
        statement.setString(1, ip);
        statement.setString(2, endpoint);
        statement.setTimestamp(3, targetedTimestamp);

        // Get the result and do limiter.
        ResultSet res = statement.executeQuery();
        res.next();
        int total = res.getInt(1);
        if (total >= 1){
            throw(new Exception("Max request is one request per minute"));
        }

        // Add the log_request to database.
        // Prepare and execute the statement.
        PreparedStatement statement2 = null;
        String sql2 = "INSERT INTO log_request(ip, endpoint, timestamp) VALUES (?, ?, ?)";
        statement2 = conn.prepareStatement(sql2);
        statement2.setString(1, ip);
        statement2.setString(2, endpoint);
        statement2.setTimestamp(3, currentTimestamp);
        statement2.executeUpdate();
        
        return "Success adding log request " + ip +" "+ endpoint + " " + currentTimestamp;
    }
    
}
