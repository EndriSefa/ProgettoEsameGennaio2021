package com.univpm.ProgrammaOW.Controller;

import java.util.Vector;



import com.univpm.ProgrammaOW.Filters.*;
import com.univpm.ProgrammaOW.Statistics.*;

import org.json.simple.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;



@RestController
public class Controller {
	
	
	
	//stampa il meteo corrente ricevendo come parametro il nome della città
	@GetMapping("/currentWeather")
	
	public JSONObject DataCity(@RequestParam(name = "cityName") String cityName) {
	
		getDataCity meteo = new getDataCity(cityName);
		return meteo.getMeteo();
		}
	
	//stampa il meteo corrente ricevendo come parametro lo Zip Code
	@PostMapping("/currentWeather")
	
	public JSONObject DataZipCode(@RequestBody JSONObject URLcomponents) {
		
		String zip = (String) URLcomponents.get("zipCode");
		String country = (String) URLcomponents.get("countryCode");
		
		getDataZipCode appoggio = new getDataZipCode(zip,country) ;
		
		 
		
		return appoggio.getMeteo();
		
	}
	
	//stampa le previsioni dando come parametro il nome della città
	@GetMapping("/forecast")
	
	public Vector<JSONObject> forecast(@RequestParam(name = "city") String city){
		
	getDataCity appoggio1 = new getDataCity(city);
	
	JSONObject appoggio2 = appoggio1.getMeteo();
		
	getWeeklyForecast previsioni = new getWeeklyForecast(appoggio2);
		
	return previsioni.getForecast();
	}
	
	//stampa le previsioni dando come parametri lo Zip Code e il Country Code
	@PostMapping("/forecast")
	
	public Vector<JSONObject> forecast(@RequestBody JSONObject URLcomponents){
		
		String zip = (String) URLcomponents.get("zipCode");
		String country = (String) URLcomponents.get("countryCode");
		
	getDataZipCode appoggio1 = new getDataZipCode(zip,country);
		
	
	getWeeklyForecast previsioni = new getWeeklyForecast(appoggio1.getMeteo());
		
	return previsioni.getForecast();
	}
	
	
		
	
	//metodo che stampa le statistiche giornaliere ricevendo come parametro il nome della città
	@GetMapping(value = "/dailyStats")
	
	
	public String DailyStatsCityName(@RequestParam(name = "tipo") String tipo,@RequestParam(name = "precision",defaultValue = "5") String precision,@RequestParam(name = "cityName") String cityName) {
		
		String result = " ";//variabile d'appoggio per il valore di ritorno
		double precision1 = Double.parseDouble(precision);		
		
		switch(tipo){
		    //stampa le statistiche giornaliere relative alla temperatura
			case "temperature": {
			
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(cityName, precision1);
			result = DailyTemperature.toString();
			break;
			}
			//stampa le statistiche giornaliere relative all'umidità
			case "humidity" : {
			
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(cityName,precision1);
			result = DailyHumidity.toString();
			break;
			
			}
			//stampa le statistiche giornaliere disponibili(temperatura e umidità)
			case "all": {
				
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(cityName,precision1);
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(cityName,precision1);
			result = 	DailyTemperature.toString()+"\n"+DailyHumidity.toString();
				
			break;
			}
			//come valore di default mettiamo le statistiche della temperatura
			default :{ 
			
	        StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(cityName,precision1);
			result = DailyTemperature.toString();
		    break;
	        }
		}
		return result;
		
	}
	
	//metodo che stampa le statistiche settimanali ricevendo come parametro il nome della città
	@RequestMapping(value = "/weeklyStats",method = RequestMethod.GET)
	
	public String WeeklyStatsCityName(@RequestParam(name = "tipo") String tipo,@RequestParam(name = "precision", defaultValue = "5") String precision,@RequestParam(name = "cityName") String cityName) {
		
		String result = " ";//variabile d'appoggio per il valore di ritorno
		double precision1 = Double.parseDouble(precision);
		
		
		switch(tipo){
			
		case "temperature" :{
		
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(cityName,precision1);
			
			result = WeeklyTemperature.toString();
			
			break;
			}
			
			case "humidity" : {
			
			StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(cityName,precision1);
			
			result = WeeklyHumidity.toString();
			
			break;
			}
			
			
			case "all": {
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(cityName,precision1);
			StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(cityName,precision1);
				
			result = WeeklyTemperature.toString()+"\n"+WeeklyHumidity.toString();
			
			break;
			}
			
			default : { 
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(cityName,precision1);
			
			result = WeeklyTemperature.toString();
		     	
		    break;
			
	        }
		}
		return result;
					
		}
	
	//stampa le statistiche giornaliere ricevendo Zip Code e CountryCode
	@RequestMapping(value = "/dailyStats",method = RequestMethod.POST)
	
	public String DailyStatsZipCode(@RequestBody JSONObject URLcomponents,@RequestParam (name = "tipo") String tipo, @RequestParam(name = "precision",defaultValue = "5") String precision) {
		
		String zip = (String) URLcomponents.get("zipCode");
		String country = (String) URLcomponents.get("countryCode");
		double precision1 = Double.parseDouble(precision);
		
        String result = " ";//variabile d'appoggio per il valore di ritorno
		
       
		switch(tipo){
		    //stampa le statistiche giornaliere relative alla temperatura
			case "temperature": {
			
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(zip,country,precision1);
			result = DailyTemperature.toString();
			break;
			}
			//stampa le statistiche giornaliere relative all'umidità
			case "humidity" : {
			
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(zip,country,precision1);
			result = DailyHumidity.toString();
			break;
			
			}
			//stampa le statistiche giornaliere disponibili(temperatura e umidità)
			case "all": {
				
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(zip, country,precision1);
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(zip,country,precision1);
			result = 	DailyTemperature.toString()+"\n"+DailyHumidity.toString();
				
			break;
			}
			//come valore di default mettiamo le statistiche della temperatura
			default :{ 
			
	        StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(zip, country,precision1);
			result = DailyTemperature.toString();
		    break;
	        }
		}
		return result;
		}
	
	//stampa le statistiche settimanali ricevendo Zip Code e CountryCode
    @RequestMapping(value = "/weeklyStats",method = RequestMethod.POST)
	
	public String WeeklyStatsZipCode(@RequestBody JSONObject URLcomponents,@RequestParam (name = "tipo", defaultValue = "5") String tipo, @RequestParam(name = "precision",defaultValue = "5") String precision) {
    	
    	String zip = (String) URLcomponents.get("zipCode");
		String country = (String) URLcomponents.get("countryCode");
		double precision1 = Double.parseDouble(precision);
		
		String result = " ";//variabile d'appoggio per il valore di ritorno
		
		
		
		switch(tipo){
			
		    case "temperature": {
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(zip,country,precision1);
			
			result = WeeklyTemperature.toString();
			
			break;
			}
			
			case "humidity" : {
			
			StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(zip,country,precision1);//stesso dubbio di sopra
			
			result = WeeklyHumidity.toString();
			
			break;
			}
			
			
			case "all": {
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(zip,country,precision1);
			StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(zip,country,precision1);
				
			result = WeeklyTemperature.toString()+"\n" +WeeklyHumidity.toString();
			
			break;
			}
			
			default : { 
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(zip,country,precision1);
			
			result = WeeklyTemperature.toString();
		     	
		    break;
			
	        }
		}
		return result;
					
		}
	
	
	}
	
	
	

	
		
		
		
	
	


