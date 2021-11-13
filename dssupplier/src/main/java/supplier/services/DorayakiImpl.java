package supplier.services;

// Import dependency.
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;
import jakarta.annotation.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;


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
    private static final String urlTambahStockToko = "http://localhost:3000/api/dorayaki/addStockDorayaki.php";
    

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
                res_final.add(obj.get("title").toString());
            }
            return res_final;

        } catch (Exception e) {
            // Failed to request all dorayaki
            e.printStackTrace();
            res_final.add("Error when adding log request, " + e.getMessage());
            return res_final;
        }
        
    }
    @Override
    public String addDorayakiStock(String dorayakiName, int amount){
        try {
            // // Make request to toko.
            // // Create body of the request and change to JSON using jackson library.
            // HashMap<String, String> values = new HashMap<String, String>() {{
            //     put("name", dorayakiName);
            //     put ("amount", Integer.toString(amount));
            // }};
            // ObjectMapper objectMapper = new ObjectMapper();
            // String requestBody = objectMapper.writeValueAsString(values);

            // // Create http client for sending request
            // HttpClient client = HttpClient.newHttpClient();
            // HttpRequest request = HttpRequest.newBuilder()
            //     .uri(URI.create(urlTambahStockToko))
            //     .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            //     .build();
            
            //     // Log the response to cmd.
            // HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // System.out.println(response.body());

            URL url = new URL(urlTambahStockToko); // URL to your application
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("name", dorayakiName); // All parameters, also easy
            params.put("amount", amount);

            StringBuilder postData = new StringBuilder();
            // POST as urlencoded is basically key-value pairs, as with GET
            // This creates key=value&key=value&... pairs
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            // Convert string to byte array, as it should be sent
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            // Connect, easy
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            // Tell server that this is POST and in which format is the data
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
    
            // This gets the output from your server
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            for (int c; (c = in.read()) >= 0;)
                System.out.print((char)c);
                
            return "Success add stock in toko";

        } catch (Exception e) {
            // Failed to request all dorayaki
            e.printStackTrace();
            System.out.println("Error when adding log request, " + e.getMessage());
            return "Error when adding log request, " + e.getMessage();
        }
        
    }
}

