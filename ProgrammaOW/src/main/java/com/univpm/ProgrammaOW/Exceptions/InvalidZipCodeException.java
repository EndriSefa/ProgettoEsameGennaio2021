package com.univpm.ProgrammaOW.Exceptions;

public class InvalidZipCodeException extends Exception {
	
	public InvalidZipCodeException() {
		
		super();
		
		System.out.println("Lo Zip Code inserito non risulta valido...");
	}

}
