package com.farfetch.toggle.controller;



import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.farfetch.toggle.domain.toggle.ToggleNotFoundException;
import com.farfetch.toggle.domain.user.UserNotFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
	
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleConflict(DataIntegrityViolationException ex, final HttpServletRequest request) {
    		logger.error(request.getRequestURL().toString(), ex);    
	    	return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleIntenalServerError(final Exception ex, final HttpServletRequest request) {
    		logger.error(request.getRequestURL().toString(), ex);    
	    	return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final HttpServletRequest request) {
    		logger.error(request.getRequestURL().toString(), ex);     
	    	return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    	}

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException ex, final HttpServletRequest request) {
    		logger.error(request.getRequestURL().toString(), ex);     
	    	return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ UserNotFoundException.class, ToggleNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final HttpServletRequest request) {
    		logger.error(request.getRequestURL().toString(), ex);  
    		return new ResponseEntity<Object>(ex.getMessage() + " not found!", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class })
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final HttpServletRequest request) {
    		logger.error(request.getRequestURL().toString(), ex);  
    		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
    }
    
}
