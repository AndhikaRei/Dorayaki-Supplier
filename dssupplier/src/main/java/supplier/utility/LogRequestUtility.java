package supplier.utility;

import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;
import java.net.InetSocketAddress;

// Helper class for managing Log Request.
public class LogRequestUtility {
  // Get ip adrress of sender. 
  public static String getIpAddress(WebServiceContext context) {
    // Get the remote address from the context and http exchange.
    MessageContext mc = context.getMessageContext();
    HttpExchange exchange = (HttpExchange)mc.get(JAXWSProperties.HTTP_EXCHANGE);
    InetSocketAddress remoteAddress = exchange.getRemoteAddress();

    // Return the IP address.
    return remoteAddress.getAddress().getHostAddress();
  }
}