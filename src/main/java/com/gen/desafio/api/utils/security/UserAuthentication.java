package com.gen.desafio.api.utils.security;


import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import com.gen.desafio.api.domain.model.User;


public class UserAuthentication implements Authentication {

    private final User user;
    private boolean authenticated = true;

    
    public UserAuthentication(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getFirstname()+" "+user.getLastname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public String getDetails() {
        return user.getUsername();
    }

    @Override
    public User getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    
}
