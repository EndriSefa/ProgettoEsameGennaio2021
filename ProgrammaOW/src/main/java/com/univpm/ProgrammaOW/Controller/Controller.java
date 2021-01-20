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
	@GetMapping("/meteoCorrente")
	
	public JSONObject DataCity(@RequestParam(name = "nomeCitta") String nomeCitta) {
	
		getDataCity meteo = new getDataCity(nomeCitta);
		return meteo.getMeteo();
		}
	
	//stampa il meteo corrente ricevendo come parametro lo Zip Code
	@PostMapping("/meteoCorrente")
	
	public JSONObject DataZipCode(@RequestBody JSONObject componentiURL) {
		
		String zip = (String) componentiURL.get("zipCode");
		String country = (String) componentiURL.get("countryCode");
		
		getDataZipCode appoggio = new getDataZipCode(zip,country) ;
		
		 
		
		return appoggio.getMeteo();
		
	}
	
	//stampa le previsioni dando come parametro il nome della città
	@GetMapping("/previsioni")
	
	public Vector<JSONObject> forecast(@RequestParam(name = "citta") String citta){
		
	getDataCity appoggio1 = new getDataCity(citta);
	
	JSONObject appoggio2 = appoggio1.getMeteo();
		
	getWeeklyForecast previsioni = new getWeeklyForecast(appoggio2);
		
	return previsioni.getForecast();
	}
	
	//stampa le previsioni dando come parametri lo Zip Code e il Country Code
	@PostMapping("/previsioni")
	
	public Vector<JSONObject> forecast(@RequestBody JSONObject componentiURL){
		
		String zip = (String) componentiURL.get("zipCode");
		String country = (String) componentiURL.get("countryCode");
		
	getDataZipCode appoggio1 = new getDataZipCode(zip,country);
		
	
	getWeeklyForecast previsioni = new getWeeklyForecast(appoggio1.getMeteo());
		
	return previsioni.getForecast();
	}
	
	
		
	
	//metodo che stampa le statistiche ricevendo come parametro il nome della città,il periodo e la precisione
	@GetMapping(value = "/Statistiche")
	
	
	public String DailyStatsCityName(@RequestParam(name = "tipo") String tipo,@RequestParam(name = "periodo")String periodo,@RequestParam(name = "precisione",defaultValue = "5") String precisione,@RequestParam(name = "nomeCitta") String nomeCitta) {
		
		String risultato = " ";//variabile d'appoggio per il valore di ritorno
		double precision1 = Double.parseDouble(precisione);		
		switch(periodo) {
		case "giornaliere":
		
		switch(tipo){
		    //stampa le statistiche giornaliere relative alla temperatura
			case "temperatura": {
			
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(nomeCitta, precision1);
			risultato = DailyTemperature.toString();
			break;
			}
			//stampa le statistiche giornaliere relative all'umidità
			case "umidita" : {
			
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(nomeCitta,precision1);
			risultato = DailyHumidity.toString();
			break;
			
			}
			//stampa le statistiche giornaliere disponibili(temperatura e umidità)
			case "totali": {
				
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(nomeCitta,precision1);
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(nomeCitta,precision1);
			risultato = 	DailyTemperature.toString()+"\n"+DailyHumidity.toString();
				
			break;
			}
			//come valore di default mettiamo le statistiche della temperatura
			default :{ 
			
	        StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(nomeCitta,precision1);
			risultato = DailyTemperature.toString();
		    break;
	        }
		}
		break;
		
		case "settimanali":
			switch(tipo){
			
			case "temperatura" :{
			
				StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(nomeCitta,precision1);
				
				risultato= WeeklyTemperature.toString();
				
				break;
				}
				
				case "umidita" : {
				
				StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(nomeCitta,precision1);
				
				risultato = WeeklyHumidity.toString();
				
				break;
				}
				
				
				case "totali": {
				
				StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(nomeCitta,precision1);
				StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(nomeCitta,precision1);
					
				risultato = WeeklyTemperature.toString()+"\n"+WeeklyHumidity.toString();
				
				break;
				}
				
				default : { 
				
				StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(nomeCitta,precision1);
				
				risultato = WeeklyTemperature.toString();
			     	
			    break;
				
		        }
			}
			break;
			
			//se il periodo non viene specificato metto come default quelle giornaliere
			default: 
				switch(tipo){
			    //stampa le statistiche giornaliere relative alla temperatura
				case "temperatura": {
				
				StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(nomeCitta, precision1);
				risultato = DailyTemperature.toString();
				break;
				}
				//stampa le statistiche giornaliere relative all'umidità
				case "umidita" : {
				
				StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(nomeCitta,precision1);
				risultato= DailyHumidity.toString();
				break;
				
				}
				//stampa le statistiche giornaliere disponibili(temperatura e umidità)
				case "totali": {
					
				StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(nomeCitta,precision1);
				StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(nomeCitta,precision1);
				risultato = 	DailyTemperature.toString()+"\n"+DailyHumidity.toString();
					
				break;
				}
				//come valore di default mettiamo le statistiche della temperatura
				default :{ 
				
		        StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(nomeCitta,precision1);
				risultato = DailyTemperature.toString();
			    break;
		        }
			}
			break;
		}
				
			
		return risultato;
		
	}
	
	
	
	//stampa le statistiche ricevendo come parametri  Zip Code e CountryCode,periodo e precisione
	@RequestMapping(value = "/Statistiche",method = RequestMethod.POST)
	
	public String DailyStatsZipCode(@RequestBody JSONObject componentiURL,@RequestParam (name = "tipo") String tipo,@RequestParam(name = "periodo")String periodo, @RequestParam(name = "precisione",defaultValue = "5") String precisione) {
		
		String zip = (String) componentiURL.get("zipCode");
		String country = (String) componentiURL.get("countryCode");
		double precision1 = Double.parseDouble(precisione);
		
        String risultato = " ";//variabile d'appoggio per il valore di ritorno
		
       switch(periodo) {
       case "giornaliere":
       
		switch(tipo){
		    //stampa le statistiche giornaliere relative alla temperatura
			case "temperatura": {
			
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(zip,country,precision1);
			risultato = DailyTemperature.toString();
			break;
			}
			//stampa le statistiche giornaliere relative all'umidità
			case "umidita" : {
			
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(zip,country,precision1);
			risultato = DailyHumidity.toString();
			break;
			
			}
			//stampa le statistiche giornaliere disponibili(temperatura e umidità)
			case "totali": {
				
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(zip, country,precision1);
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(zip,country,precision1);
			risultato = 	DailyTemperature.toString()+"\n"+DailyHumidity.toString();
				
			break;
			}
			//come valore di default mettiamo le statistiche della temperatura
			default :{ 
			
	        StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(zip, country,precision1);
			risultato = DailyTemperature.toString();
		    break;
	        }
		}
		break;
		
       case "settimanali":
    	   switch(tipo){
			
		    case "temperatura": {
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(zip,country,precision1);
			
			risultato= WeeklyTemperature.toString();
			
			break;
			}
			
			case "umidita" : {
			
			StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(zip,country,precision1);//stesso dubbio di sopra
			
			risultato = WeeklyHumidity.toString();
			
			break;
			}
			
			
			case "totali": {
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(zip,country,precision1);
			StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(zip,country,precision1);
				
			risultato = WeeklyTemperature.toString()+"\n" +WeeklyHumidity.toString();
			
			break;
			}
			
			default : { 
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(zip,country,precision1);
			
			risultato = WeeklyTemperature.toString();
		     	
		    break;
			
	        }
		}
    	   break;
    	   
    	 //se il periodo non viene specificato metto come deault le giornaliere
    	   default:
    		   switch(tipo){
   		    //stampa le statistiche giornaliere relative alla temperatura
   			case "temperatura": {
   			
   			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(zip,country,precision1);
   			risultato = DailyTemperature.toString();
   			break;
   			}
   			//stampa le statistiche giornaliere relative all'umidità
   			case "umidita" : {
   			
   			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(zip,country,precision1);
   			risultato = DailyHumidity.toString();
   			break;
   			
   			}
   			//stampa le statistiche giornaliere disponibili(temperatura e umidità)
   			case "totali": {
   				
   			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(zip, country,precision1);
   			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(zip,country,precision1);
   			risultato = 	DailyTemperature.toString()+"\n"+DailyHumidity.toString();
   				
   			break;
   			}
   			//come valore di default mettiamo le statistiche della temperatura
   			default :{ 
   			
   	        StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(zip, country,precision1);
   			risultato = DailyTemperature.toString();
   		    break;
   	        }
   		}
   		break;
       }
       
       return risultato;
   		
    		   
    	   
		
		}
}
	
	
		
	
	


