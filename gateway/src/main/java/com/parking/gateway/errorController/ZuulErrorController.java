package com.parking.gateway.errorController;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeAttribute;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.commons.exception.CustomExceptionResponse;

@RestController
public class ZuulErrorController extends AbstractErrorController {

	@Value("${error.path:/error}")
	private String errorPath;
	@Autowired(required = false)
	private BasicErrorController bs;

	public ZuulErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@Override
	public String getErrorPath() {
		return errorPath;
	}

	@RequestMapping(value = "${error.path:/error}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<CustomExceptionResponse> error(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		final String errorMessage = getErrorMessage(request);
		return ResponseEntity.status(status)
				.body(new CustomExceptionResponse(errorMessage, LocalDateTime.now(), errorMessage));
	}

	private String getErrorMessage(HttpServletRequest request) {
		final Throwable exc = (Throwable) request.getAttribute("javax.servlet.error.exception");
		return exc != null ? exc.getMessage() : bs.error(request).toString();
	}
	private String getFromBasicErrorController(HttpServletRequest req) {
		ErrorProperties ep=new ErrorProperties();
		ep.setIncludeMessage(IncludeAttribute.ALWAYS);
		BasicErrorController bs=new BasicErrorController(new DefaultErrorAttributes(),ep);
		return bs.error(req).getBody().toString();
	}
}