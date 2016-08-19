package library.services;

import com.ibm.websphere.security.UserRegistry;
import library.config.JWTToken;
import library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zotova on 18.08.2016.
 */
@WebService(endpointInterface = "library.services.AuthenticationService")
@ImportResource("classpath:applicationContext.xml")
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRegistry userRegistry;

    @Autowired
    TokenService tokenService;

    private String userRegistryUid(String username, String password) {
        String uid = null;
        try {
            uid =  userRegistry.checkPassword(username, password);
        }
        catch (Exception e) {

        }
        return uid;
    }

    private JWTToken getToken(String username, String password) {

        JWTToken jwtToken = null;
        try {
            String token = tokenService.getToken(username, password);
            jwtToken = tokenService.parseToken(token);
        }
        catch (Exception e) {

        }
        return jwtToken;
    }

    private JWTToken getTokenObjFirstTime(String username, String password) {
        JWTToken tokenObj = null;
        tokenObj = getToken(username, password);

        return tokenObj;
    }

    private JWTToken getTokenObj(String token) {
        JWTToken tokenObj = tokenService.parseToken(token);;
        Map<String, String> creds = null;
        try {
            creds = (HashMap<String, String>) tokenObj.getCredentials();
            String credsUsername = creds.get("username");
            String credsPassword = creds.get("password");
            if (userRegistryUid(credsUsername, credsPassword) == null)
                throw new Exception("Invalid credentials!");
        }
        catch (Exception ex) {
            tokenObj = null;
        }
        return  tokenObj;
    }


    private boolean checkCredentials(String username, String password) {

        return username != null
                && !username.isEmpty()
                && password != null
                && !password.isEmpty()
                && userRegistryUid(username, password) != null;
    }

    @WebMethod
    public boolean authenticate(String username, String password, String token) {

        JWTToken tokenObj = null;
        try {

            tokenObj = ((token == null || token.equals("null")
                    || token.equals("undefined")) && checkCredentials(username, password)) ?
                    getTokenObjFirstTime(username, password) : getTokenObj(token);

            if (tokenObj == null)
                throw new Exception("Invalid token!");

            Authentication auth = authenticationManager.authenticate(tokenObj);
            SecurityContext ctx = SecurityContextHolder.getContext();
            ctx.setAuthentication(auth);
        }

        catch (Exception ex) {
            return false;
        }

        return true;
    }

}
