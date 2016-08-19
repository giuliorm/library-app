package library.config;

import com.ibm.websphere.security.UserRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by zotova on 02.08.2016.
 */
@Service("ldapUserDetailsService")
public class CustomLdapUserDetailsService implements UserDetailsService {

    CustomLdapUserDetails userDetails;

    public CustomLdapUserDetailsService(UserRegistry registry) {
        this.userDetails = new CustomLdapUserDetails();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return this.userDetails;
    }
}
