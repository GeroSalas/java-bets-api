package com.gen.desafio.api.utils.security;


import com.gen.desafio.api.domain.dto.BaseDTO;


public class JwtClaims extends BaseDTO {
	
	private String iss;          // Issuer (App Owner)
	private String iat;          // Issued at Time
	private String exp;          // Expires at Time
	private String aud;          // Audience
	private String sub;          // Username (unique email)
	private String userId;       // DB Id
	private String clientId;     // DB Id
	private String rolename;     // [SUPER_ADMIN / ADMIN / USER]
	
	
	public JwtClaims() {}

	public JwtClaims(String userId, String username, String rolename) {
		this.userId = userId;
		this.sub = username;
		this.rolename = rolename;
	}


	
	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public String getIat() {
		return iat;
	}

	public void setIat(String iat) {
		this.iat = iat;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getAud() {
		return aud;
	}

	public void setAud(String aud) {
		this.aud = aud;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getRolename() {
		return rolename;
	}
	
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	
}
