package com.univpm.ProgrammaOW.Exceptions;

public class NonExistingPredictionDataException extends Exception{
	
	public NonExistingPredictionDataException() {
		
		super();
		
		System.out.println("Dati relativi alle previsioni passate non presenti...");
	}

}
