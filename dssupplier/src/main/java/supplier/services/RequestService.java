package supplier.services;

// Import dependency.
import jakarta.jws.WebService;

import java.util.List;
import jakarta.jws.WebMethod;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface RequestService {

    @WebMethod
    public List<String> syncRequest(String endpoint);
}