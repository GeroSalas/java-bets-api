package com.gen.desafio.api.domain.exception;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class FieldErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String field;
    private final String message;

    
    public FieldErrorDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }


    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
