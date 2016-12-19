package com.gen.desafio.api.domain.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationFailureException extends AuthenticationException {

  private static final long serialVersionUID = -672581831205725699L;

  public AuthenticationFailureException(String message) {
    super(message);
  }
  
  
}