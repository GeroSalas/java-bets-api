package com.gen.desafio.api.domain.exception;

import io.jsonwebtoken.JwtException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionProcessor extends ResponseEntityExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(ExceptionProcessor.class);

  
  @ExceptionHandler(ConcurrencyFailureException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ResponseEntity<Object> handleConcurencyError(ConcurrencyFailureException ex) {
      return createResponseEntity("Concurrency Failure", HttpStatus.CONFLICT, ex);
  }
  
  
  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
      return createResponseEntity("Bad Request", HttpStatus.BAD_REQUEST, fieldErrors, ex);
  }
  
  
  @ExceptionHandler(RecordNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  protected ResponseEntity<Object> handleEnrollmentNotFoundException(RecordNotFoundException ex) {
    return createResponseEntity("Resource Not Found", HttpStatus.NOT_FOUND, ex);
  }

  @ExceptionHandler(DuplicateKeyException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  protected ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException ex) {
    return createResponseEntity("Duplicate Key", HttpStatus.CONFLICT, ex);
  }
  
  @ExceptionHandler(DataAccessException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  protected ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
	  InvalidRequestException customEx = null;
	  String message = ex.getMessage();
	  if(message.contains("could not execute statement") || message.contains("ConstraintViolationException")){
		  customEx = new InvalidRequestException("Por motivos de seguridad, hay resticciones que no permiten realizar ciertas operaciones.");
	  }
	  else{
		  customEx = new InvalidRequestException("Error interno en la Base de Datos. Por favor, intentelo más tarde."); 
	  }
	  
	  return createResponseEntity("Database Access", HttpStatus.INTERNAL_SERVER_ERROR, customEx);
  }

  @ExceptionHandler(InvalidRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  protected ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex, WebRequest request) {
    return createResponseEntity("Bad Request", HttpStatus.BAD_REQUEST, ex.getErrors().getFieldErrors(), ex);
  }
  
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
    return createResponseEntity("Bad Request", HttpStatus.BAD_REQUEST, ex);
  }
  
  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ResponseBody
  protected ResponseEntity<Object> handleUnauthorizedRequestException(UnauthorizedException ex) {
    return createResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED, ex);
  }
  
  
//@ExceptionHandler(ApplicationException.class)
//protected ResponseEntity<Object> handleServiceException(ApplicationException ex) {
//  return createResponseEntity("Service", HttpStatus.CONFLICT, ex);
//}


  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, 
		                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {

    String code = "Parámetro incorrecto";
    String message = "Parámetro incorrecto " + ex.getParameterName();

    ErrorDTO error = new ErrorDTO("ERROR", code, message);

    log.error("handleMissingServletRequestParameter() - " + error);

    return handleExceptionInternal(ex, error, headers, status, request);
  }
  
  @ExceptionHandler(AuthenticationFailureException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ResponseBody
  public ResponseEntity<Object> handleLoginException(Exception e) {
    return createResponseEntity("No Autorizado", HttpStatus.UNAUTHORIZED, e);
  }
  
  @ExceptionHandler(JwtException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ResponseBody
  public ResponseEntity<Object> handleJWTException(Exception e) {
    return createResponseEntity("Invalid Access Token", HttpStatus.UNAUTHORIZED, e);
  }
  
  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  public ResponseEntity<Object> handleAuthException(Exception e) {
    return createResponseEntity("Acceso Denegado.", HttpStatus.FORBIDDEN, e);
  }

  // otherwise everything else
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ResponseEntity<Object> handleException(Exception e) {
    return createResponseEntity("Error interno del servidor. Por favor, intentelo más tarde.", HttpStatus.INTERNAL_SERVER_ERROR, e);
  }
  
//  @ExceptionHandler(Throwable.class)
//  public void handleAnyGenericException(Throwable ex, HttpServletRequest request) {
//    String exceptionName = ClassUtils.getShortName(ex.getClass());
//  }

  /**
   * Helper method to create a ResponseEntity with error code and message,
   * include the contentType header
   * 
   * @param code
   * @param statusCode
   * @param exception
   * @return ResponseEntity with the exception payload
   */
  private ResponseEntity<Object> createResponseEntity(String code, HttpStatus statusCode, Exception exception) {

    // body
    ErrorDTO error = new ErrorDTO("ERROR", code, exception.getMessage());

    // headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    logException(error, headers, statusCode, exception);

    return new ResponseEntity<Object>(error, headers, statusCode);
  }

  /**
   * Helper method to create a ResponseEntity with error code, message and
   * detailed error by field, include the contentType header
   * 
   * @param code
   * @param statusCode
   * @param fieldErrors
   * @param exception
   * @return ResponseEntity with the exception payload
   */
  private ResponseEntity<Object> createResponseEntity(String message, HttpStatus statusCode, List<FieldError> fieldErrors, Exception exception) {

    // body
	ErrorDTO error = new ErrorDTO("ERROR", message, exception.getMessage());

    for (FieldError fieldError : fieldErrors) {
       error.add(fieldError.getField(), fieldError.getDefaultMessage());
    }

    // headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    logException(error, headers, statusCode, exception);

    return new ResponseEntity<Object>(error, headers, statusCode);
  }

  /**
   * Log every exception with every necessary detail, while returning unintrusive and unnecessary info to the user
   * 
   * @param errorResource (return body of the error)
   * @param headers
   * @param statusCode
   * @param cause
   */
  private void logException(ErrorDTO errorResource, HttpHeaders headers, HttpStatus statusCode, Exception exception) {
    StringBuilder builder = new StringBuilder();
    builder.append("errorResource=").append(errorResource).append(", headers=").append(headers).append(", statusCode=").append(statusCode);
    if (exception.getCause() != null) {
      builder.append(", cause=").append(exception.getCause());
    }
    builder.append(", stackTrace=").append(getStringStackTrace(exception));

    log.error(builder.toString());
  }

  
  /**
   * Extract and return as a String the StackTrace
   * 
   * @param exception to extract StackTrace
   * @return String representing the Stack Trace
   * 
   */
  private String getStringStackTrace(Exception exception) {
    StringWriter sw = new StringWriter();
    exception.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }
  
}