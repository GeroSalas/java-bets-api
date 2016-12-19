package com.gen.desafio.api.utils.security;


import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.gen.desafio.api.domain.exception.AuthenticationFailureException;
import com.gen.desafio.api.domain.model.AuthorityRole;
import com.gen.desafio.api.domain.model.User;
import com.gen.desafio.api.services.UserService;
import com.gen.desafio.api.utils.APIConstants;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class AuthenticationService {
	
	private final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
	
	@Autowired
	private UserService userService;

    private String secret = APIConstants.API_SECRET_KEY;

    
    // Called on Request by StatelessLoginFilter
    public void addAuthentication(HttpServletResponse response, Authentication authentication) throws Exception {
        User user = (User) authentication.getDetails();
        log.debug("Creating JWT Authentication Header for User Id: " + user.getId());
        
        response.addHeader(APIConstants.X_AUTH_TOKEN, this.createCustomJWT(user));
        response.setContentType(APIConstants.CONTENT_TYPE_JSON);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().println("{ \"Message\": \"" + "Hi " +user.getUsername()+ "!. Please use the <X-AUTH-TOKEN> provided to request data from secured resources." + "\" }");
    }
    
    
    // Called on every Request by APIKeyFilter
    public boolean getAPIKeyAuthentication(HttpServletRequest request) {
    	String apiKey = request.getHeader(APIConstants.X_DESAFIO_API_KEY);
        if(apiKey!=null && apiKey.equals(APIConstants.API_PUBLIC_KEY)) {
        	return true;
        }
        else{
        	return false;
        }
    }

    
    // Called on every Request by StatelessAuthenticationFilter
    public Authentication getAuthentication(HttpServletRequest request) {
    	String header = request.getHeader(APIConstants.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ") || header.length() <= 1 || header.contains("null")) {
        	log.debug("Invalid access token provided");
        }
        else{
        	String authToken = header.substring(7);
            if(authToken!=null){
            	User u = this.parseUserFromCustomJWT(authToken);
            	if (u != null) {
            		log.debug("Returning Authenticated User with Id: " + u.getId());
                    return new UserAuthentication(u);
                }
            }
        }
        
        return null;
    }
    
    
    public User parseUserFromToken(String token) {
    	User uTokenized = null;
        try {
        	// JWT.IO NOT WORKING !
            Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            String username = body.getSubject();
            uTokenized = (User) userService.loadUserByUsername(username);

        } catch (JwtException | ClassCastException e) {
            throw new AuthenticationFailureException(e.getMessage());
        }
        
        return uTokenized;
    }
    
    public String createTokenForUser(User u) throws Exception {
        // JWT.IO NOT WORKING !
    	Claims claims = Jwts.claims();
         claims.setSubject(u.getUsername());
         claims.put("userId", u.getId() + "");
         claims.put("clientId", u.getRelatedClient().getId() + "");
         claims.put("authority", u.getRoles().get(0));
        
         return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secret).compact(); 
    }
    
    
    public String createCustomJWT(User u) throws Exception {
     Base64 base64 = new Base64();
     String jwt = null;
     try{	
    	AuthorityRole role = u.getRoles().get(0); // Main User_Role
    	JwtClaims claims = new JwtClaims(u.getId().toString(), u.getUsername(), role.getRolename());
    	 claims.setIss(APIConstants.APP_ISSUER_NAME);
    	 claims.setIat(null);
    	 claims.setExp(null);
    	 claims.setAud(null);
    	 if(u.getRelatedClient() != null){ claims.setClientId(u.getRelatedClient().getId().toString()); }  // Is Not SuperAdmin
    	 
    	String json = claims.toGson().toString();
    	byte[] userInfo = json.getBytes("UTF8");
    	byte[] user_hashed = this.createHmac(userInfo);
    	
    	String header = new String(base64.encode(userInfo));
		String signature = new String(base64.encode(user_hashed));
		StringBuilder sb = new StringBuilder();
         sb.append(header).append(".").append(signature);
         jwt = sb.toString();
         log.debug("Custom JWT Generated: " + jwt);
       
      } catch(Exception ex){
    	  log.error("ERROR - " + ex.getMessage());
    	  throw ex;
      }
		
	  return jwt;
    }
    
    
    public User parseUserFromCustomJWT(String token){
    	User uTokenized = null;
    	
    	Base64 base64 = new Base64();
    	Gson gson = new Gson();
    	String[] parts = token.split("\\.");
    	byte[] header = base64.decode(parts[0]);
    	byte[] signature = base64.decode(parts[1]);
    	
    	boolean validHash = Arrays.equals(this.createHmac(header), signature);
    	if(validHash){
    		String userClaims = new String(header);
    		JwtClaims u = gson.fromJson(userClaims, JwtClaims.class);
    		log.debug("Username parsed from JWT request auth header: <"+u.getSub()+">");
    		uTokenized = (User) userService.loadUserByUsername(u.getSub());
    	}
    	else{
    		log.debug("Invalid JWT provided.");
    	}      	
        
        return uTokenized;
    }
    
    
    private byte[] createHmac(byte[] userInfo){
    	SecretKeySpec key_spec;
    	Mac hmac;
		try {
			key_spec = new SecretKeySpec(secret.getBytes("UTF8"), APIConstants.HMAC_SHA256);
			hmac = Mac.getInstance(APIConstants.HMAC_SHA256);
			hmac.init(key_spec);
			byte[] userHashed = hmac.doFinal(userInfo); // HS256 Encryption of UserInfo with a secret key signature
			log.debug("Secured Signature: " + new String(new Base64().encode(userHashed)));
			
			return userHashed;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
    }
    
    
}
