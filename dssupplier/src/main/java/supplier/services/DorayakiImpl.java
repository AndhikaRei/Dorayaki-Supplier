package supplier.services;

// Import dependency.
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;
import jakarta.annotation.Resource;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import io.github.cdimascio.dotenv.Dotenv;

// Import own package.
import supplier.database.LogRequestDatabase;
import supplier.utility.LogRequestUtility;

@WebService(endpointInterface = "supplier.services.DorayakiService")
public class DorayakiImpl implements DorayakiService {
    // Context of the Soap connection. 
    // Backend pabrik base url.
    private static String SERVER_PORT = Dotenv.load().get("SERVER_PORT", "5000");
    @Resource WebServiceContext context;
    private static final String urlBackendPabrik = "http://localhost:"+SERVER_PORT+"/api/v1/recipes/names";

    private static final LogRequestDatabase logRequestDatabase = new LogRequestDatabase();
    
    @Override
    public List<String> getAllDorayakiName(String endpoint){
        List<String> res_final = new ArrayList<String>();
        try {
            // Get the request IP Address
            String ip = LogRequestUtility.getIpAddress(context);
            System.out.println("Request from IP: " + ip);

            logRequestDatabase.addRequestLog(ip, endpoint);
            
            // Request to backend pabrik.
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlBackendPabrik))
                .build();

            // Get the responses from pabrik and build our own responses.
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray res = new JSONArray(response.body());   
            for (int i = 0 ; i < res.length(); i++) {
                JSONObject obj = res.getJSONObject(i);
                res_final.add(obj.get("name").toString());
            }
            return res_final;

        } catch (Exception e) {
            // Failed to request all dorayaki
            e.printStackTrace();
            res_final.add("Error when adding log request, " + e.getMessage());
            return res_final;
        }
        
    }

}

