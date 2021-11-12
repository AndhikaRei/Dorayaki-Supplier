package supplier.services;

// Import dependency.
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface LogRequestService {
    @WebMethod
    public String createLogRequestTable();

    @WebMethod
    public String addLogRequest(String endpoint, String dorayakiName, int amount);
}
