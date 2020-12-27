package com.univpm.ProgrammaOW.Exceptions;

public class NonExistingForecastDataException extends Exception {
	
	public NonExistingForecastDataException() {
		super();
		System.out.println("Non existing Forecast Data");
	}

}
