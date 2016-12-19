package com.gen.desafio.api.utils.filters;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;
import com.gen.desafio.api.utils.security.AuthenticationService;


public class APIKeyFilter extends GenericFilterBean {

    private final AuthenticationService authenticationService;
    
    
    public APIKeyFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        boolean validAPIKey = authenticationService.getAPIKeyAuthentication(httpRequest);
        if(validAPIKey){
            filterChain.doFilter(request, response);  // OK
        }
        else{
        	if ("OPTIONS".equals(httpRequest.getMethod())) {
        		filterChain.doFilter(request, response);
    		 }
    		 else {
    			 httpResponse.setContentType("application/json");
    	         httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // ERROR 401
    	         httpResponse.getOutputStream().println("{ \"error\": \"" + "Invalid API Key." + "\" }");
    		 }
        }
        
    }

    
}