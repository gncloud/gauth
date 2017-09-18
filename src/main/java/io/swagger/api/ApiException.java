package io.swagger.api;

import org.springframework.http.ResponseEntity;

import java.security.AccessControlException;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

public class ApiException extends Exception{
	private int code;

	public ApiException (int code, String msg) {
		super(msg);
		this.code = code;
	}
}
