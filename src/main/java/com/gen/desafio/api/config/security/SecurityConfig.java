package com.gen.desafio.api.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import com.gen.desafio.api.services.UserService;
import com.gen.desafio.api.utils.filters.APIKeyFilter;
import com.gen.desafio.api.utils.security.AuthenticationService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
		
	@Autowired
	UserService userService;
	
	
	public SecurityConfig() {
		super(true); // disabled default config
	}
	
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());  // DAOAuthenticationProvider
         auth.authenticationProvider(authenticationProvider());                              // TokenAuthenticationProvider (Custom)
    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.csrf().disable();
	  http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	  http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint())
	      .and().anonymous().and().servletApi().and().headers().cacheControl();         
      http.authorizeRequests()
          .antMatchers(HttpMethod.GET, "/index.jsp").permitAll()
          .antMatchers(HttpMethod.OPTIONS, "/api/v1a/**", "/api/v1c/**", "/api/v1m/**").permitAll()  // prevent preflighted CORS request problems
          .antMatchers(HttpMethod.POST, "/api/login/", "api/forgotpassword/").permitAll()            // generic login service for all clients
          .antMatchers("/api/**").authenticated()
          .antMatchers("/api/v1a/**").hasRole("SUPER_ADMIN")  // services for Version Super Admin     (Gabriel Dashboard) 
          .antMatchers("/api/v1c/**").hasRole("ADMIN")        // services for Version Customer Admin  (Client Dashboard)
          .antMatchers("/api/v1m/**").hasRole("USER")         // services for Version Mobile          (Users Phonegap - DesafioApp)
      .and()
        .addFilterBefore(new APIKeyFilter(tokenService()), AbstractPreAuthenticatedProcessingFilter.class)
        .addFilterBefore(new StatelessAuthenticationFilter(tokenService()), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Override
    public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/index.jsp")
		              .antMatchers("/api/login/")
		              .antMatchers("/api/forgotpassword/"); 
    }

	
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    
    @Override
   	protected UserDetailsService userDetailsService() {
   		return userService;
   	}
    
    
    @Bean
    public TokenAuthenticationProvider authenticationProvider() {
    	// Add Password Encoder if need here
        return new TokenAuthenticationProvider();
    }
    
    
    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }
    
    
    @Bean
    public JwtAuthenticationSuccessHandler authSuccessHandler(){
        return new JwtAuthenticationSuccessHandler();
    }
    
    
    @Bean
    public SimpleUrlAuthenticationFailureHandler authFailureHandler(){
        return new SimpleUrlAuthenticationFailureHandler();
    }
    
    
    @Bean
    public AuthenticationService tokenService() {
        return new AuthenticationService();
    }
    
	
}