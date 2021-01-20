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
	
	private getDataZipCode c1;
	private StatisticsDailyHumidity c2;
	private getWeatherPredictions c3;
	
    private	getDataZipCode parametro;

	@BeforeEach
	void setUp() throws Exception {
		//60020 cap sirolo
		parametro = new getDataZipCode("60020","it");
		c3 = new getWeatherPredictions(parametro.getMeteo());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test assert InvalidPrecisionException")
	void testInvalidPrecisionException() {
		
		InvalidPrecisionException exception = assertThrows(InvalidPrecisionException.class,() -> {c2 = new StatisticsDailyHumidity("Londra",-5.0);});

		
	}
	
	@Test
	@DisplayName("Test assert InvalidZipCodeException")
	void testInvalidZipCodeException() {
		
		InvalidZipCodeException exception = assertThrows(InvalidZipCodeException.class,() -> { c1 = new getDataZipCode("1234","5678");});
		
		
	}
	
	@Test
	@DisplayName("Test assert NonExistingPredictionDataException")
	void testNonExistingPredictionDataException() {
		
		NonExistingPredictionDataException exception = assertThrows(NonExistingPredictionDataException.class,() -> { c3.getPredictions();});
		
	
	}
	

}
