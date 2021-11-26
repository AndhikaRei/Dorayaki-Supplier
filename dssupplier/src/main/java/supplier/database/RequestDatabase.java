package supplier.database;

// Import dependency.
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RequestDatabase {
    // get all accepted but not recognized requests and also apply rate-limiter.
    public List<String> getAccNotRecognizedRequest(String ip, String endpoint) throws SQLException, Exception {
        // Get database connection.
        DatabaseHandler handler = new DatabaseHandler();
        Connection conn = handler.getConn();

        // TODO : Change back to 1 menit.
        // Default selama 1 menit.
        Timestamp targetedTimestamp = new Timestamp(System.currentTimeMillis()-60000);
            
        // Prepare and execute the statement.
        PreparedStatement statement = null;
        String sql = "SELECT COUNT(*) as cnt " +
                "FROM request_logs " +
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
        if (total >= 3){
            throw(new Exception("Max request is three request per minute"));
        }

        // Get all accepted request but not recognized.
        List<String> requests = new ArrayList<>();
        String sql2 = "SELECT * " +
                    "FROM requests " +
                    "WHERE status = 'accepted' AND " + 
                    "`recognized` = '0'";
        ResultSet res2 = conn.createStatement().executeQuery(sql2);
        while (res2.next()){
            requests.add(res2.getString("dorayaki"));
            requests.add(res2.getString("count"));
        }
        System.out.println(requests);
        return requests;
    }

    // get all accepted but not recognized requests and also apply rate-limiter.
    public String updateNotRecognizedRequest(String ip, String endpoint) throws SQLException, Exception {
        // Get database connection.
        DatabaseHandler handler = new DatabaseHandler();
        Connection conn = handler.getConn();

        // TODO : Change back to 1 menit.
        // Default selama 1 menit.
        Timestamp targetedTimestamp = new Timestamp(System.currentTimeMillis()-60000);
            
        // Prepare and execute the statement.
        PreparedStatement statement = null;
        String sql = "SELECT COUNT(*) as cnt " +
                "FROM request_logs " +
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
        if (total >= 3){
            throw(new Exception("Max request is one three request per minute"));
        }

        // Get all accepted request but not recognized.
        String sql2 = "UPDATE requests " +
                    "SET recognized = 1 " +
                    "WHERE status = 'accepted' AND " + 
                    "recognized = '0'";
        conn.createStatement().executeUpdate(sql2);
        return "Successfully update not recognized requests";
    }
}
