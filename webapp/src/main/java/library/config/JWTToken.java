package library.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.expression.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

/**
 * Created by zotova on 03.08.2016.
 */
public class JWTToken implements Authentication {

    private static final long serialVersionUID = 1L;

    private JWT jwt;
    private JWTClaimsSet claims;
    private final Collection<GrantedAuthority> authorities;
    private boolean authenticated;
  //  private ReadOnlyJWTClaimsSet claims;

    public JWTToken(JWT jwt) throws Exception {
        this.jwt = jwt;
        String role = null;
        try {
            claims = jwt.getJWTClaimsSet();
            role = (String)claims.getClaim("role");
        } catch (ParseException e) {
        }
        List<GrantedAuthority> tmp = new ArrayList<>();
        if (role != null) {
                tmp.add(new SimpleGrantedAuthority(role));
        }
        this.authorities = Collections.unmodifiableList(tmp);
      //  this.claims = jwt.getJWTClaimsSet();
        authenticated = false;
    }

    public JWT getJwt() {
        return jwt;
    }

   // public ReadOnlyJWTClaimsSet getClaims() {
   //     return claims;
 //   }

    @Override
    public Object getCredentials() {
        HashMap<String, String> cred = new HashMap<>();
        cred.put("username", (String)claims.getClaims().get("username"));
        cred.put("password", (String)claims.getClaims().get("password"));
        return cred;
    }

    @Override
    public Object getPrincipal() {
        return claims.getSubject();
  //      return claims.getSubject();
    }

   @Override
    public String getName() {
       return claims.getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getDetails() {
        return claims.toJSONObject();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

}
