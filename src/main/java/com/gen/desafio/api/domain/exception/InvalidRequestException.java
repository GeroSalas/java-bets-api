package com.gen.desafio.api.domain.exception;

import org.springframework.validation.Errors;

public class InvalidRequestException extends ApplicationException {

  private static final long serialVersionUID = 7105481322156370997L;
  private Errors errors;
  
  public InvalidRequestException(String message) {
	    super(message);
  }

  public InvalidRequestException(String message, Errors errors) {
    super(message);
    this.errors = errors;
  }

  public Errors getErrors() {
    return errors;
  }
}