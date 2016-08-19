package library.config;

import com.ibm.websphere.security.UserRegistry;
import com.ibm.websphere.security.auth.InvalidTokenException;
import com.ibm.websphere.security.auth.TokenExpiredException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jca.JCAContext;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.*;
import library.repository.UserRepository;
import library.services.TokenServiceImpl;
import library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by zotova on 03.08.2016.
 */
@ImportResource("classpath:applicationContext.xml")
public class CustomAuthenticationProvider implements AuthenticationProvider {

  //  @Autowired
   // UserRegistry registry;


    public CustomAuthenticationProvider() throws JOSEException {

    }
    @Override
    public boolean supports(Class<?> authentication) {
      //  return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
        return JWTToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authentication;
        JWT jwt = jwtToken.getJwt();

        // Check type of the parsed JOSE object
        if (jwt instanceof SignedJWT && handleSignedToken((SignedJWT) jwt)) {
            jwtToken.setAuthenticated(true);
        }// else if (jwt instanceof EncryptedJWT) {
          //  handleEncryptedToken((EncryptedJWT) jwt);
        //}

        //Date referenceTime = new Date();
      /*  ReadOnlyJWTClaimsSet claims = jwtToken.getClaims();

        Date expirationTime = claims.getExpirationTime();

        if (expirationTime == null || expirationTime.before(referenceTime)) {
          return null;
        }

        Date notBeforeTime = claims.getNotBeforeTime();
        if (notBeforeTime == null || notBeforeTime.after(referenceTime)) {
          return null;
        }

        String issuerReference = "library";
        String issuer = claims.getIssuer();
        if (!issuerReference.equals(issuer)) {
         //   throw new InvalidTokenException("Invalid issuer");
            return null;
        }
 */

        return jwtToken;
    }

    private boolean handleSignedToken(SignedJWT jwt) {
        return jwt != null;
    }

}