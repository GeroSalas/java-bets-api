package com.gen.desafio.api.domain.exception;

public class DuplicateKeyException extends ApplicationException {

  private static final long serialVersionUID = -672581831205725699L;

  public DuplicateKeyException(String message) {
    super(message);
  }
  
  
}