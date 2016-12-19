package com.gen.desafio.api.domain.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO for transfering error message with a list of field errors.
 */
@JsonInclude(Include.NON_EMPTY)
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;    // SUCCESS, INFO, WARNING, ERROR
    private String message;    
    private String description;  
    private List<FieldErrorDTO> fieldErrors;

    public ErrorDTO(String type, String message) {
        this(type, message, null);
    }

    public ErrorDTO(String type, String message, String description) {
        this.type = type;
        this.message = message;
        this.description = description;
    }

    public ErrorDTO(String type, String message, String description, List<FieldErrorDTO> fieldErrors) {
    	this.type = type;
    	this.message = message;
        this.description = description;
        this.fieldErrors = fieldErrors;
    }

    public void add(String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<FieldErrorDTO>();
        }
        fieldErrors.add(new FieldErrorDTO(field, message));
    }

    
    public String getType() {
        return type;
    }
    
    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }
}
