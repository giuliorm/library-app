package library.config;

import com.ibm.websphere.security.UserRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zotova on 02.08.2016.
 */

public class CustomLdapUserDetails implements UserDetails {


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        ArrayList<GrantedAuthority> a = new ArrayList<GrantedAuthority>();
        a.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_ADMIN";
            }
        });

        return a;
    }

    @Override
    public String getPassword() {
            return "12345";
    }

    @Override
    public String getUsername() {
       return "user1";
    }

    @Override
    public boolean isAccountNonExpired() {
            return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
