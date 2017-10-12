package io.swagger.api;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

public class ApiException extends Exception{
	private int code;

	public ApiException (String msg) {
		super(msg);
	}

	public ApiException (int code, String msg) {
		this(msg);
		this.code = code;
	}

}
