package com.gen.desafio.api.utils.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.UUID;


/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

	
    private SecurityUtils() {}

    
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)) {
                    return false;
                }
            }
        }
        return true;
    }


    
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if(authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                return springSecurityUser.getAuthorities().contains(new SimpleGrantedAuthority(authority));
            }
        }
        return false;
    }
    
    public static com.gen.desafio.api.domain.model.User getCurrentUserLogged() {	
    	com.gen.desafio.api.domain.model.User u = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication !=null && authentication instanceof UserAuthentication) {
			if (authentication.getDetails() instanceof com.gen.desafio.api.domain.model.User) {
                u = (com.gen.desafio.api.domain.model.User) authentication.getPrincipal();
                if(u != null){
    				return u;
    			}
            }
		}
		return u;
	}
    
  
    public static String generateRandomPassword(){
    	UUID random = UUID.randomUUID();
    	String pass = "D" + String.valueOf(random).split("-")[0].toUpperCase(); 
    	return pass;
    }
    
    
}
