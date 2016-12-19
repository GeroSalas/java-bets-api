package com.gen.desafio.api.domain.exception;

public class BadRequestException extends ApplicationException {

  private static final long serialVersionUID = -7687004675876937308L;

  public BadRequestException(String message) {
    super(message);
  }
  
}