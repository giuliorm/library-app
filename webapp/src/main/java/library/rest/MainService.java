package library.rest;

import com.sun.jersey.api.view.Viewable;
import library.services.AuthenticationService;
import library.services.AuthenticationServiceImpl;
import library.services.TokenService;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by zotova on 10.08.2016.
 */
@Component
@Path("/")
public class MainService {


    @Autowired
    TokenService tokenService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login() {

      /*  AuthenticationService serv = null;
       try {
             URL url = new URL("http://localhost:9086/ws?wsdl");
            QName qName = new QName("http://services.library/", "AuthenticationServiceImplService");
            Service service = Service.create(url, qName);
            serv = service.getPort(AuthenticationService.class);
        }
        catch (Exception ex) {

        } */

        HashMap<String, String> response = new HashMap<>();
        response.put("token", tokenService.getCurrentToken());
        return Response.ok(response, MediaType.APPLICATION_JSON).type("application/json").build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/main")
    public Viewable main() {

        return new Viewable("/main");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/test")
    public String test() {
        return "test";
    }
}
