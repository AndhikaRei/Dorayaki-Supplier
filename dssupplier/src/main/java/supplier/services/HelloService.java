package supplier.services;

// Import dependency.
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;


// Interface of testing class.
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface HelloService {
    @WebMethod
    public String HelloWorld();
}