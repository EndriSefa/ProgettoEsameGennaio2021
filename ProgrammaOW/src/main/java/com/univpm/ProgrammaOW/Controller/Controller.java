package com.univpm.ProgrammaOW.Controller;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer.ConditionObject;

import com.univpm.ProgrammaOW.Utils.*;
import com.univpm.ProgrammaOW.Filters.*;
import com.univpm.ProgrammaOW.Statistics.*;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

@RestController
public class Controller {
	
	
	
	//stampa il meteo corrente ricevendo come parametro il nome della città
	@GetMapping("/currentWeather/{cityName}")
	
	public JSONObject DataCity(@RequestParam(value = "cityName") String cityName) {
	
		return getDataGivenCity(cityName);
		
		}
	
	//stampa il meteo corrente ricevendo come parametro lo Zip Code
	@PostMapping("/currentWeather")
	
	public JSONObject DataZipCode(@RequestBody JSONObject URLcomponents) {
		
		String zip = (String) URLcomponents.get("zipCode");
		String country = (String) URLcomponents("countryCode");
		
		return getDataZip(zip,country);
		
	}
	
	//stampa le previsioni dando come parametro il nome della città
	@GetMapping("/forecast/{city}")
	
	public JSONArray forecast(@RequestParam(value = "city") String city){
		
	getWeeklyForecast previsioni = new getWeeklyForecast(getDataCity(city),getForecast());
		
	return previsioni.getForecast();
	}
	
	//stampa le previsioni dando come parametri lo Zip Code e il Country Code
	@PostMapping("/forecast")
	
	public JSONArray forecast(@RequestBody JSONObject URLcomponents){
		
		String zip = (String) URLcomponents.get("zipCode");
		String country = (String) URLcomponents("countryCode");
		
	
	getWeeklyForecast previsioni = new getWeeklyForecast(getDataZipCode(zip, country),getForecast());
		
	return previsioni.getForecast();
	}
	
	
		
	
	//metodo che stampa le statistiche giornaliere ricevendo come parametro il nome della città
	@RequestMapping(value = "/dailyStats/{precision}/{cityName}",method = RequestMethod.POST)
	
	
	public String DailyStatsCityName(@RequestBody String tipo,@RequestParam(value = "precision",defaultValue = 5) double precision,@RequestParam(value = "cityName") String cityName) {
		
		String result = " ";//variabile d'appoggio per il valore di ritorno
		
		getWeatherPredictions predictions = new getWeatherPredictions(getDataCity(cityName),getForecast());
		
		switch(tipo){
		    //stampa le statistiche giornaliere relative alla temperatura
			case "temperature": {
			
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(predictions,precision);
			result = DailyTemperature.toString();
			break;
			}
			//stampa le statistiche giornaliere relative all'umidità
			case "humidity" : {
			
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(predictions,precision);
			result = DailyHumidity.toString();
			break;
			
			}
			//stampa le statistiche giornaliere disponibili(temperatura e umidità)
			case "all": {
				
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(predictions,precision);
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(predictions,precision);
			result = 	DailyTemperature.toString()+DailyHumidity.toString();
				
			break;
			}
			//come valore di default mettiamo le statistiche della temperatura
			default :{ 
			
	        StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(predictions,precision);
			result = DailyTemperature.toString();
		    break;
	        }
		}
		return result;
		}
	
	
	//metodo che stampa le statistiche settimanali ricevendo come parametro il nome della città
	@RequestMapping(value = "/weeklyStats/{precision}/{cityName}",method = RequestMethod.POST)
	
	public String WeeklyStatsCityName(@RequestBody String tipo,@RequestParam(value = "precision") double precision,@RequestParam(value = "cityName") String cityName) {
		
		String result = " ";//variabile d'appoggio per il valore di ritorno
		
		getWeatherPredictions predictionsForecast= new getWeatherPredictions(getDataCity(cityName),getForecast());
		switch(tipo){
			case "temperature": {
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(predictionsForecast,precision);
			
			result = WeeklyTemperature.toString();
			
			break;
			}
			
			case "humidity" : {
			
			StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(predictionsForecast,precision);//stesso dubbio di sopra
			
			result = WeeklyHumidity.toString();
			
			break;
			}
			
			
			case "all": {
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(predictionsForecast,precision);
			StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(predictionsForecast,precision);
				
			result = WeeklyTemperature.toString()+WeeklyHumidity.toString();
			
			break;
			}
			
			default : { 
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(predictionsForecast,precision);
			
			result = WeeklyTemperature.toString();
		     	
		    break;
			
	        }
		}
		return result;
					
		}
	
	//stampa le statistiche giornaliere ricevendo Zip Code e CountryCode
	@RequestMapping(value = "/dailyStats/{precision}",method = RequestMethod.POST)
	
	public String DailyStatsZipCode(@RequestBody JSONObject URLcomponents, @RequestParam(value = "precision",defaultValue = 5) double precision) {
		
		String zip = (String) URLcomponents.get("zipCode");
		String country = (String) URLcomponents.get("countryCode");
		String tipo = (String) URLcomponents.get("tipo");
		
        String result = " ";//variabile d'appoggio per il valore di ritorno
		
		getWeatherPredictions predictions = new getWeatherPredictions(getDataZipCode(zip,country),getForecast());
		
		switch(tipo){
		    //stampa le statistiche giornaliere relative alla temperatura
			case "temperature": {
			
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(predictions,precision);
			result = DailyTemperature.toString();
			break;
			}
			//stampa le statistiche giornaliere relative all'umidità
			case "humidity" : {
			
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(predictions,precision);
			result = DailyHumidity.toString();
			break;
			
			}
			//stampa le statistiche giornaliere disponibili(temperatura e umidità)
			case "all": {
				
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(predictions,precision);
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(predictions,precision);
			result = 	DailyTemperature.toString()+DailyHumidity.toString();
				
			break;
			}
			//come valore di default mettiamo le statistiche della temperatura
			default :{ 
			
	        StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(predictions,precision);
			result = DailyTemperature.toString();
		    break;
	        }
		}
		return result;
		}
	
	//stampa le statistiche settimanali ricevendo Zip Code e CountryCode
    @RequestMapping(value = "/weeklyStats/{precision}",method = RequestMethod.POST)
	
	public String WeeklyStatsZipCode(@RequestBody JSONObject URLcomponents,@RequestParam(value = "precision",defaultValue = 5) double precision) {
    	
    	String zip = (String) URLcomponents.get("zipCode");
		String country = (String) URLcomponents.get("countryCode");
		String tipo = (String) URLcomponents.get("tipo");
		
		String result = " ";//variabile d'appoggio per il valore di ritorno
		
		getWeatherPredictions predictionsForecast= new getWeatherPredictions(getDataZipCode(zip, country),getForecast());
		
		switch(tipo){
			
		    case "temperature": {
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(predictionsForecast,precision);
			
			result = WeeklyTemperature.toString();
			
			break;
			}
			
			case "humidity" : {
			
			StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(predictionsForecast,precision);//stesso dubbio di sopra
			
			result = WeeklyHumidity.toString();
			
			break;
			}
			
			
			case "all": {
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(predictionsForecast,precision);
			StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(predictionsForecast,precision);
				
			result = WeeklyTemperature.toString()+WeeklyHumidity.toString();
			
			break;
			}
			
			default : { 
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(predictionsForecast,precision);
			
			result = WeeklyTemperature.toString();
		     	
		    break;
			
	        }
		}
		return result;
					
		}
	
	
	}
	
	
	

	
		
		
		
	
	


