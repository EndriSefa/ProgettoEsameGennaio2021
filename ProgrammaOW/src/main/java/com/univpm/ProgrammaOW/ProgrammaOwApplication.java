package com.univpm.ProgrammaOW;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Classe principale che si occupa di avviare la SpringBootApplication
 * @author Endri Sefa
 * @author Micol Zazzarini
 */
@SpringBootApplication
public class ProgrammaOwApplication {

	/** Metodo main che avvia l'applicazione
	 * @param args array di stringhe che possono essere passate all'avvio dell'applicazione
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProgrammaOwApplication.class, args);
	}

}
