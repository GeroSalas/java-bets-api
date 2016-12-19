package com.gen.desafio.api.config.security;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
 
   @Override
   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException{
	   // This is invoked when user tries to access a secured REST resource without supplying valid token credentials
       // We should just send a 401 Unauthorized ERROR Response because we are an API and there is no 'login page' to redirect to
       response.setContentType("application/json");
       response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
       response.getOutputStream().println("{ \"error\": \"" + "Invalid Access Token. " + authException.getMessage()+"." + "\" }"); 
   }

}