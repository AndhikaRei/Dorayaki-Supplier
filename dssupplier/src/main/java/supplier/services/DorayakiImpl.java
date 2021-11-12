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

// Import own package.
import supplier.database.LogRequestDatabase;
import supplier.utility.LogRequestUtility;

@WebService(endpointInterface = "supplier.services.DorayakiService")
public class DorayakiImpl implements DorayakiService {
    // Context of the Soap connection. 
    // TODO: Change to real url
    // Backend pabrik base url.
    @Resource WebServiceContext context;
    private static final String urlBackendPabrik = "https://jsonplaceholder.typicode.com/posts";

    private static final LogRequestDatabase logRequestDatabase = new LogRequestDatabase();
    
    @Override
    public List<String> getAllDorayakiName(String endpoint){
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
            List<String> res_final = new ArrayList<String>();
            for (int i = 0 ; i < res.length(); i++) {
                JSONObject obj = res.getJSONObject(i);
                res_final.add(obj.get("title").toString());
            }
            return res_final;

        } catch (Exception e) {
            // Failed to request all dorayaki
            e.printStackTrace();
            System.out.println( "Error when adding log request, " + e.getMessage());
            return new ArrayList<>();
        }
        
    }
}

