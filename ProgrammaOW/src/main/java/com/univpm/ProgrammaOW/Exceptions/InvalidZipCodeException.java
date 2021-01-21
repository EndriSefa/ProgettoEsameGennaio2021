package com.univpm.ProgrammaOW.Exceptions;

/** Classe che estende Exception.
 *  Viene lanciata nel caso lo Zip Code e/o il Country Code fossero sbagliati.
 *  (In pratica quando l'URL relativo alla chiamata con lo API con ZIP Code non esiste)
 * @author Endri Sefa
 * @author Micol Zazzarini
 */
public class InvalidZipCodeException extends Exception {
	
	/**
	 * Costruttore dell' eccezione
	 */
	public InvalidZipCodeException() {
		
		super();
		
		System.out.println("Lo Zip Code inserito non risulta valido...");
	}

}
