package supplier;

// Import dependency.
import jakarta.xml.ws.Endpoint;

// Import own package.
import supplier.services.*;

public class App 
{
    public static void main( String[] args )
    {
        try {
            System.out.println("Trying to create javax-ws");
            // URL.
            String address = "http://localhost:6123/ds";
            String helloAddress = address + "/hello";
            String logRequestAddress = address + "/log-request";
            String requestAddress = address + "/request";
            String dorayakiAddress = address + "/dorayaki";

            // Publishing route.
            Endpoint.publish(helloAddress, new HelloImpl());
            Endpoint.publish(logRequestAddress, new LogRequestImpl());
            Endpoint.publish(requestAddress, new RequestImpl());
            Endpoint.publish(dorayakiAddress, new DorayakiImpl());

            System.out.println("Hello service created in " + helloAddress);
            System.out.println("Log request service created in " + logRequestAddress);
            System.out.println("Request service created in " + requestAddress);
            System.out.println("Dorayaki service created in " + dorayakiAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}
