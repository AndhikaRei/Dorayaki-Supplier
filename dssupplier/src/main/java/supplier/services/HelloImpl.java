package supplier.services;

import jakarta.jws.WebService;

// Class that used for testing.
@WebService(endpointInterface = "supplier.services.HelloService")
public class HelloImpl implements HelloService {
    @Override
    public String HelloWorld(){
        return "Hello, welcome to dorayaki supllier interface!!!";
    }
}
