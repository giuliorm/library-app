package library.services;
import com.ibm.websphere.security.UserRegistry;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import library.config.CustomLdapUserDetails;
import library.config.CustomLdapUserDetailsService;
import library.config.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
/**
 * Created by zotova on 03.08.2016.
 */
@Service
@ImportResource("classpath:applicationContext.xml")
public class TokenServiceImpl implements TokenService {

   // @Autowired
  //  private CustomLdapUserDetailsService ldapUserDetailsService;
    private static final String key =
           "superSecretKey12345superSecretKey12345superSecretKey12345superSecretKey12345";
    private String current;


    public String getCurrentToken() {
        return this.current;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public JWTToken parseToken(String token) {

        JWTToken tokenObj  = null;
        try {
            JWT jwt = JWTParser.parse(token);
            tokenObj = new JWTToken(jwt);
        }
        catch (Exception e) {

        }
        return tokenObj;

    }

    @Override
    public String getToken(String username, String password)  {
        if (username == null || password == null)
            return null;

     //   CustomLdapUserDetails user = (CustomLdapUserDetails)
       //         ldapUserDetailsService.loadUserByUsername(username);

        Map<String, Object> tokenData = new HashMap<>();

      //  if (password.equals(user.getPassword())) {
            tokenData.put("clientType", "user");
            tokenData.put("username", username);
            tokenData.put("password", password);
            tokenData.put("token_create_date", new Date().getTime());
            tokenData.put("role", "ADMIN");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 30);
            tokenData.put("token_expiration_date", calendar.getTime());

            JwtBuilder jwtBuilder = Jwts.builder();
            jwtBuilder.setExpiration(calendar.getTime());
            jwtBuilder.setClaims(tokenData);
            String token = jwtBuilder.signWith
                    (io.jsonwebtoken.SignatureAlgorithm.HS512, getKey()).compact();
            this.current = token;
            return token;
     //   }
    }

}

