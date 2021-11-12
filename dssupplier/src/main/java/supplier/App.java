package supplier;

import jakarta.xml.ws.Endpoint;

import supplier.services.*;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Trying to create javax-ws");

        // URL.
        String address = "http://localhost:6123/ds";
        String hello_address = address + "/hello";

        // Publishing route.
        Endpoint.publish(hello_address, new HelloImpl());

        System.out.println("Hello service created in " + hello_address);
    }
}
