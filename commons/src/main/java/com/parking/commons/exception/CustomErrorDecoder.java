package com.parking.commons.exception;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import com.jayway.jsonpath.JsonPath;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		System.out.println("CustomErrorDecoder.decode()");
		try {
			System.out.println("CustomErrorDecoder.decode()+try");
			String res = (IOUtils.toString(response.body().asInputStream(), Charsets.UTF_8));
		     List<String> done=JsonPath.read(res, "$..message");
		     System.out.println(done.toString());
			return new ResourceNotFoundException(done.stream().findFirst().orElse("Service Down"));
			// new ObjectMapper().readValue(res,StorageFile::class.java);
		} catch (IOException e) {
			System.out.println("CustomErrorDecoder.decode()+catch");
			e.printStackTrace();
			return new Exception();
		}
	}

}
