package com.gen.desafio.api.config.security;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import com.gen.desafio.api.utils.security.AuthenticationService;


public class StatelessAuthenticationFilter extends GenericFilterBean {
	
	private static Logger log = Logger.getLogger(StatelessAuthenticationFilter.class);

    private final AuthenticationService authenticationService;
    
    
    public StatelessAuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Authentication authentication = authenticationService.getAuthentication(httpRequest);
        if(authentication != null){
        	log.debug("User authenticated with JWT is requesting server...");
        	SecurityContextHolder.getContext().setAuthentication(authentication);  // OK
            filterChain.doFilter(request, response);
        }
        else{
        	if ("OPTIONS".equals(httpRequest.getMethod())) {
        		log.debug("Receiving browser pre-flight OPTIONS request for CORS support...");
        		filterChain.doFilter(request, response);
    		 }
    		 else {
    			 log.debug("Anonymous user requesting server...");
    			 httpResponse.setContentType("application/json");
    	         httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // ERROR 401
    	         httpResponse.getOutputStream().println("{ \"error\": \"" + "Invalid access token." + "\" }");
    		 }
        }
        
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    
}