package com.gen.desafio.api.domain.exception;

public class RecordNotFoundException extends ApplicationException {

  private static final long serialVersionUID = -7687004675876937308L;

  public RecordNotFoundException(String message) {
    super(message);
  }
  
}