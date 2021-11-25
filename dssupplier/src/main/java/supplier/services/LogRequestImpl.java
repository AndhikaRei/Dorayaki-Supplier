package supplier.services;

// Import dependency.
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;
import jakarta.annotation.Resource;
import jakarta.jws.WebMethod;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

// Import own package
import supplier.database.*;
import supplier.utility.*;

@WebService(endpointInterface = "supplier.services.LogRequestService")
public class LogRequestImpl implements LogRequestService{
    
    // Context of the Soap connection. 
    @Resource WebServiceContext context;
    // Database object.
    private static final LogRequestDatabase logRequestDatabase = new LogRequestDatabase();
    // Backend pabrik base url.
    private static String SERVER_PORT = Dotenv.load().get("SERVER_PORT", "5000");
    private static final String urlBackendPabrikRequest = "http://localhost:"+SERVER_PORT+"/api/v1/requests";
    
    @Override
    public String createLogRequestTable(){
        try {
            // Create table.
            logRequestDatabase.createLogRequestTable();
            return "Succes to create table";

        } catch (Exception e) {
            // Failed to create the table.
            e.printStackTrace();
            return "Error when defining the table, " + e.getMessage();
        }
        
    }

    @WebMethod
    public String addLogRequest(String endpoint, String dorayakiName, int amount){
        try {
            // Get the request IP Address
            String ip = LogRequestUtility.getIpAddress(context);
            System.out.println("Request from IP: " + ip);

            String res = logRequestDatabase.addRequestLog(ip, endpoint);
            
            // Forward request to backend.
            // Create body of the request and change to JSON using jackson library.
            HashMap<String, String> values = new HashMap<String, String>() {{
                put("name", dorayakiName);
                put ("amount", Integer.toString(amount));
                put ("ip", ip);
            }};
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(values);

            // Create http client for sending request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlBackendPabrikRequest))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();
            
            // Log the response to cmd.
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200){
                throw new Exception("Failed to request, maybe dorayaki not exist in factory");
            }
            return res;

        } catch (Exception e) {
            // Failed to add request_logs.
            e.printStackTrace();
            return "Error when adding log request, " + e.getMessage();
        }
    }

}
