package com.ttm.crm.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	/*
	 * These are routine user errors, such as a field being too
	 * short. They will throw a HTTP 400, which the client should
	 * catch and show as a module-specific user error message.
	 * eg: "Field too short, please enter more data".
	 */
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handle(MethodArgumentNotValidException ex) {
		System.out.println("GOT MANVE");
		Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	    	String fieldName = ((FieldError) error).getField();
	    	String errorMessage = error.getDefaultMessage();
	    	errors.put(fieldName, errorMessage);
	    });
		return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ServiceValidationException.class)
	public ResponseEntity<Map<String,String>> handle(ServiceValidationException ex) {
		Map<String, String> errors = new HashMap<>();
	    errors.put(ex.fieldName, ex.message);
		return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
	}

	/*
	 * These are unexpected errors, eg. a problem in mapper.xml,
	 * they will throw an HTTP 500, which should be shown as
	 * a generic error in the client.
	 * eg: "Unable to contact server, better luck next time."
	 */
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String,String>> handle(Exception ex) {
		System.out.println("Exception caught by GlobalException Handler: " + ex + " " + ex.getMessage());
		ex.printStackTrace();
		Map<String, String> errors = new HashMap<>();
		errors.put("serverError", ex.getMessage());
	    return new ResponseEntity(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String,String>> handle(RuntimeException ex) {
		System.out.println("RuntimeException caught by GlobalException Handler: " + ex + " " + ex.getMessage());
		ex.printStackTrace();
		Map<String, String> errors = new HashMap<>();
		errors.put("serverError", ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
