package com.parking.commons.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionControllerAdvice {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> checkEmail(ConstraintViolationException ex) {
		List l = new ArrayList<String>();
		ex.getConstraintViolations().forEach((error) -> {
			String errorMessage = error.getMessageTemplate();
			l.add(errorMessage);
		});
		return new ResponseEntity(l, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomExceptionResponse> getResourceException(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomExceptionResponse(e.getMessage(), LocalDateTime.now(), e.getLocalizedMessage()));
	}

	/*
	 * @ExceptionHandler(FeignClientException.class) public
	 * ResponseEntity<CustomExceptionResponse> getFiegnException(Exception e) {
	 * return ResponseEntity.status(HttpStatus.NOT_FOUND) .body(new
	 * CustomExceptionResponse(e.getMessage(), LocalDateTime.now(),
	 * e.getLocalizedMessage())); }
	 */

	//@ExceptionHandler(value = UsernameNotFoundException.c)
	public ResponseEntity<CustomExceptionResponse> getZuulException(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new CustomExceptionResponse(e.getMessage(), LocalDateTime.now(), e.getLocalizedMessage()));
	}
}
