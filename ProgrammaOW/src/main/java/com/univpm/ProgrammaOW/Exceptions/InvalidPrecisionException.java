package com.univpm.ProgrammaOW.Exceptions;

/** Classe che estende Exception.
 *  Viene lanciata nel caso la precisione fosse non valida (negativa o maggiore di 100)
 * @author Endri Sefa
 * @author Micol Zazzarini
 */
public class InvalidPrecisionException extends Exception{
	
	/**
	 * Costruttore dell' eccezione
	 */
	public InvalidPrecisionException() {
		
		super();
		
		System.out.println("La percentuale inserita non è valida...");
	}
	
	

}
