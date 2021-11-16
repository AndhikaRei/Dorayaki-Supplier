package supplier.services;

// Import dependency.
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.soap.SOAPBinding;

import java.util.List;


@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface DorayakiService {
    @WebMethod
    public List<String> getAllDorayakiName(String endpoint);

}

