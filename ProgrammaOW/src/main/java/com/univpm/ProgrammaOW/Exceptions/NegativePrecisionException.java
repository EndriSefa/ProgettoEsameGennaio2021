package com.univpm.ProgrammaOW.Exceptions;

public class NegativePrecisionException extends Exception {
	
	public NegativePrecisionException() {
		super();
		System.out.println("Invalid Precision value. Negative Precision.");
	}

}
