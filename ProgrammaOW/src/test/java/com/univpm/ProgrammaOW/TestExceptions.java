package com.univpm.ProgrammaOW;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.univpm.ProgrammaOW.Filters.getWeatherPredictions;
import com.univpm.ProgrammaOW.Statistics.StatisticsDailyHumidity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;
import com.univpm.ProgrammaOW.Exceptions.InvalidPrecisionException;
import com.univpm.ProgrammaOW.Exceptions.InvalidZipCodeException;
import com.univpm.ProgrammaOW.Exceptions.NonExistingPredictionDataException;

class TestExceptions {
	
	getDataZipCode c1;
	StatisticsDailyHumidity c2;
	getWeatherPredictions c3;

	@BeforeEach
	void setUp() throws Exception {
		/*c3 = new getWeatherPredictions() questo lo inizializzo prima perchè l'eccezione sta nel metodo, ma dobbiamo inizializzarlo con un JSONObject 
		 * tale da far partire l'eccezione
		 */
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test assert InvalidPrecisionException")
	void testInvalidPrecisionException() {
		
		InvalidPrecisionException exception = assertThrows(InvalidPrecisionException.class,() -> {c2 = new StatisticsDailyHumidity("Londra",-5.0);});

		assertEquals("La percentuale inserita non è valida...", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test assert InvalidZipCodeException")
	void testInvalidZipCodeException() {
		
		InvalidZipCodeException exception = assertThrows(InvalidZipCodeException.class,() -> { c1 = new getDataZipCode("1234","5678");});
		
		assertEquals("Lo Zip Code inserito non risulta valido...",exception.getMessage());
	}
	
	@Test
	@DisplayName("Test assert NonExistingPredictionDataException")
	void testNonExistingPredictionDataException() {
		
		NonExistingPredictionDataException exception = assertThrows(NonExistingPredictionDataException.class,() -> { c3.getPredictions();});
		
		assertEquals("Dati relativi alle previsioni passate non presenti...", exception.getMessage());
	}
	

}
