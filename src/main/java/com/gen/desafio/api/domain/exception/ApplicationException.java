package com.gen.desafio.api.domain.exception;


public class ApplicationException extends RuntimeException {

  private static final long serialVersionUID = 7467746991536675933L;

  public ApplicationException(String message) {
    super(message);
  }

  public ApplicationException(String message, Throwable cause) {
    super(message, cause);
  }
  
  
}