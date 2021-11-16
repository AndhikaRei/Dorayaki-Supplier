package supplier.services;

// Import dependency.
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

// Import own package
import supplier.database.*;
import supplier.utility.*;

@WebService(endpointInterface = "supplier.services.RequestService")
public class RequestImpl implements RequestService {

    // Context of the Soap connection. 
    @Resource WebServiceContext context;
    // Database object.
    private static final RequestDatabase requestDatabase = new RequestDatabase();

    @Override
    public List<String> syncRequest(String endpoint){
        List<String> res = new ArrayList<>();
        try {
            // Get sender ip address.
            String ip = LogRequestUtility.getIpAddress(context);
            System.out.println("Request from IP: " + ip);

            // Get npt recognized request and update it to be recognized.
            res = requestDatabase.getAccNotRecognizedRequest(ip, endpoint);
            requestDatabase.updateNotRecognizedRequest(ip, endpoint + "/recognize");
            
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res.add("Error when adding log request, " + e.getMessage());
            return res;
        }
    }
}