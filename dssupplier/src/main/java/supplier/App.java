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

            // Publishing route.
            Endpoint.publish(helloAddress, new HelloImpl());
            Endpoint.publish(logRequestAddress, new LogRequestImpl());

            System.out.println("Hello service created in " + helloAddress);
            System.out.println("Log request service created in " + logRequestAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}
