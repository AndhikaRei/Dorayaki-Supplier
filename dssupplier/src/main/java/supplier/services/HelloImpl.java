package supplier.services;

import jakarta.jws.WebService;

@WebService(endpointInterface = "supplier.services.HelloService")
public class HelloImpl implements HelloService {
    @Override
    public String HelloWorld(){
        return "Hello, welcome to dorayaki supllier interface!!!";
    }
}
