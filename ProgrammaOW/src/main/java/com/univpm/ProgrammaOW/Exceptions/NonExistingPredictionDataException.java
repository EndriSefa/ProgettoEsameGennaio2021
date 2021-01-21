package com.univpm.ProgrammaOW.Exceptions;

/** Classe che estende Exception.
 *  Viene lanciata nel caso le previsioni per il giorno di oggi fatte nei giorni precedenti
 *  non siano 7 .
 *  (Non siamo in grado di distringuere i giorni in cui sono state fatte le previsioni passate
 *  quindi , nel caso delle statistiche giornaliere non possiamo sapere se il primo dato è relativo
 *  alle previsioni fatte il giorno prima o nei giorni precedenti. Se i dati sono 7 sappiamo che le
 *  previsioni passate sono complete, in caso contrario no e quindi viene lanciata l'eccezione)
 * @author Endri Sefa
 * @author Micol Zazzarini
 */
public class NonExistingPredictionDataException extends Exception{
	
	/**
	 * 
	 */
	public NonExistingPredictionDataException() {
		
		super();
		
		System.out.println("Dati relativi alle previsioni passate non presenti...");
	}

}
