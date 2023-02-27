package com.parking.commons.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignInterceptor implements RequestInterceptor {

	private static final String AUTHORIZATION_HEADER = "Authorization";

	public static String getBearerTokenHeader() {
		System.out.println("FeignInterceptor.getBearerTokenHeader()"+((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("Authorization"));
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("Authorization");
	}

	@Override
	public void apply(RequestTemplate requestTemplate) {

		requestTemplate.header(AUTHORIZATION_HEADER, getBearerTokenHeader());

	}
}