package com.gen.desafio.api.domain.exception;

public class UnauthorizedException extends ApplicationException {

  private static final long serialVersionUID = -7687004675876937308L;

  public UnauthorizedException(String message) {
    super(message);
  }
  
}