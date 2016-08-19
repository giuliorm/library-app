package library.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by zotova on 18.08.2016.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface AuthenticationService {

    @WebMethod(operationName="login")
    boolean authenticate(String username, String surname, String token);
}
