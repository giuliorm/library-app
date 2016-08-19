package library.config;

import com.ibm.websphere.security.PasswordCheckFailedException;
import com.ibm.websphere.security.UserRegistry;
import com.ibm.websphere.security.auth.InvalidTokenException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.Jwts;
import library.services.AuthenticationService;
import library.services.TokenService;
import library.services.UserService;
import org.apache.commons.collections.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ImportResource;
import org.springframework.expression.ParseException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.URL;
import java.util.*;

//@Component
@ImportResource("classpath:applicationContext.xml")
public class AuthenticationTokenProcessingFilter extends GenericFilterBean
{

    private static class AuthenticationServiceHolder {

        private static AuthenticationService serv;
        private static boolean iTriedToGetService = false;
        private  AuthenticationServiceHolder() {
            serv = null;

        }

        public static AuthenticationService getInstance() {
            if (serv == null && !iTriedToGetService) {
                try {
                    URL url = new URL("http://localhost:9086/ws?wsdl");
                    QName qName = new QName("http://services.library/", "AuthenticationServiceImplService");
                    Service service = Service.create(url, qName);
                    serv = service.getPort(AuthenticationService.class);
                }
                catch (Exception ex) {

                }
                iTriedToGetService = true;
            }
            return serv;
        }

    }

  //  @Autowired
    private AuthenticationManager authenticationManager;
    private AuthenticationEntryPoint entryPoint;

    @Autowired
    @Qualifier("authService")
    AuthenticationService authenticationService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRegistry userRegistry;

    public void setAuthenticationEntryPoint(AuthenticationEntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return this.entryPoint;
    }

    public void setAuthenticationManager(AuthenticationManager manager) {
        this.authenticationManager = manager;
    }

    public AuthenticationManager authenticationManager() {
        return this.authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        SecurityContext ctx = SecurityContextHolder.getContext();

       // if (a == null)
        //    chain.doFilter(request, response);
        //else
     //   if (a != null)
        try {

                Cookie[] cookies = req.getCookies();

                int tokenInd =  Arrays.binarySearch(cookies, new Cookie("token", ""),
                        new Comparator<Cookie>() {
                            @Override
                            public int compare(Cookie o1, Cookie o2) {
                                 return o1.getName().compareTo(o2.getName());
                            }
                        });
                String stringToken = tokenInd >= 0 ? req.getCookies()[tokenInd].getValue() : null;
                JWTToken tokenObj = null;
                String username = req.getParameter("username");
                String password = req.getParameter("password");

            //   if (!AuthenticationServiceHolder.getInstance()
               //         .authenticate(username, password, stringToken))
               //    throw new Exception("Cannot authenticate!");

               if (!authenticationService.authenticate(username, password, stringToken))
                    throw new Exception("Cannot authenticate!");

                chain.doFilter(request,response);

            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                if (entryPoint != null) {
                    entryPoint.commence(req, res, e);
                }
            }
            catch (Exception e) {

            }

    }

   /* private KeyValue handleCookie(final String cookie) {

        return new KeyValue() {
            @Override
            public Object getKey() {
                return cookie.substring(0, cookie.indexOf('='));
            }

            @Override
            public Object getValue() {
                return cookie.substring(cookie.indexOf('='), cookie.length() - 1);
            }
        };
    }
    private Map<String, String> getCookies(String cookies) {

      HashMap<String, String> cookiesMap = new HashMap<>();
        String key = null;
          for(String val : cookies.split("=,")) {

              if (key != null) {
                  cookiesMap.put(key, val);
                  key = null;
              }
              else key = val;
          }
        return cookiesMap;
    } */

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

   /* @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) { //???
              return null;
        }

        String authToken = header.substring(7);

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);

      //  return getAuthenticationManager().authenticate(authRequest);
        return null;
    }


    private HttpServletRequest getAsHttpRequest(ServletRequest request)
    {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }

        return (HttpServletRequest) request;
    }

  /*  @Override
    public void init(FilterConfig conf) {

    }
 */
  /*@Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
          throws IOException, ServletException {
      super.successfulAuthentication(request, response, chain, authResult);

      // As this authentication is in HTTP header, after success we need to continue the request normally
      // and return the response as if the resource was not secured at all
      chain.doFilter(request, response);
  } */

 //  private String extractAuthTokenFromRequest(HttpServletRequest httpRequest)
 //   {
		/* Get token from header */
    //    String authToken = httpRequest.getHeader("X-Auth-Token");

	//	/* If token not found get it from request parameter */
     //   if (authToken == null) {
     //       authToken = httpRequest.getParameter("token");
    //    }

   //     return authToken;
  //  }

  /*  @Override
    public void destroy() {

    } */

}