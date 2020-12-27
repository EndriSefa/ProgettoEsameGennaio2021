package com.univpm.ProgrammaOW.Exceptions;

public class InvalidCountryCodeException extends Exception{
	
	public InvalidCountryCodeException() {
		super();
		System.out.println("Invalid Country Code");
	}

}
