package com.univpm.ProgrammaOW.Controller;

import java.util.Vector;

import com.univpm.ProgrammaOW.Exceptions.InvalidPrecisionException;
import com.univpm.ProgrammaOW.Exceptions.InvalidZipCodeException;
import com.univpm.ProgrammaOW.Exceptions.NonExistingPredictionDataException;
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



/** Classe che gestisce tutte le chiamate fatte dal nostro Server
 * @author Endri Sefa
 * @author Micol Zazzarini
 */
@RestController
public class Controller {
	
	
	
	//stampa il meteo corrente ricevendo come parametro il nome della città
	/** Rotta di tipo GET che ci restituisce il meteo corrente della città desiderata.
	 * La stringa della città viene ricavata da una RequestParam
	 * @param nomeCitta Stringa contenente il nome della città (Londra o Chicago)
	 * @return meteo JSONObject contenente i dati del meteo correnti relativi alla città 
	 */
	@GetMapping("/meteoCorrente")
	
	public JSONObject DataCity(@RequestParam(name = "nomeCitta") String nomeCitta) {
	
		getDataCity meteo = new getDataCity(nomeCitta);
		return meteo.getMeteo();
		}
	
	//stampa il meteo corrente ricevendo come parametro lo Zip Code
	/** Rotta di tipo POST che ci da il meteo corrente della città di cui abbiamo il Country Code
	 * e lo Zip Code (questi due dati vengono ricavati dal RequestBody)
	 * @param componentiURL Body da inserire con stringhe relative al "zipCode" e al "countryCode"
	 * @return meteo JSONObject contenente il meto corrente della città
	 * @throws InvalidZipCodeException Eccezione personalizzata in caso lo Zip Code e/o il Country
	 * code fossero sbagliati  
	 */
	@PostMapping("/meteoCorrente")
	
	public JSONObject DataZipCode(@RequestBody JSONObject componentiURL) throws InvalidZipCodeException {
		
		String zip = (String) componentiURL.get("zipCode");
		String country = (String) componentiURL.get("countryCode");
		
		getDataZipCode appoggio = new getDataZipCode(zip,country) ;
		
		 
		
		return appoggio.getMeteo();
		
	}
	
	//stampa le previsioni dando come parametro il nome della città
	/** Rotta di tipo GET che ci restituisce le previsioni del meteo della settimana.
	 * La Stringa della città la ricaviamo con una RequestParam.
	 * @param citta Stringa con il nome della città disponibile (Londra o Chicago)
	 * @return previsioni Vettore di JSONObject contenente le previsioni meteo della settimana
	 */
	@GetMapping("/Previsioni")
	
	public Vector<JSONObject> forecast(@RequestParam(name = "citta") String citta){
		
	getDataCity appoggio1 = new getDataCity(citta);
	
	JSONObject appoggio2 = appoggio1.getMeteo();
		
	getWeeklyForecast previsioni = new getWeeklyForecast(appoggio2);
		
	return previsioni.getForecast();
	}
	
	//stampa le previsioni dando come parametri lo Zip Code e il Country Code
	/**Rotta di tipo POST che ci da le previsioni del meteo della città di cui abbiamo il Country 
	 * Code e lo Zip Code (questi due dati vengono ricavati dal RequestBody)
	 * @param componentiURL Body da inserire con stringhe relative al "zipCode" e al "countryCode"
	 * @return previsioni Vector di JSONObject contenente le previsioni meteo della settimana
	 * @throws InvalidZipCodeException Eccezione personalizzata in caso lo Zip Code e/o il Country
	 * code fossero sbagliati 
	 */
	@PostMapping("/Previsioni")
	
	public Vector<JSONObject> forecast(@RequestBody JSONObject componentiURL) throws InvalidZipCodeException{
		
		String zip = (String) componentiURL.get("zipCode");
		String country = (String) componentiURL.get("countryCode");
		
	getDataZipCode appoggio1 = new getDataZipCode(zip,country);
		
	
	getWeeklyForecast previsioni = new getWeeklyForecast(appoggio1.getMeteo());
		
	return previsioni.getForecast();
	}
	
	
		
	
	//metodo che stampa le statistiche ricevendo come parametro il nome della città,il periodo e la precisione
	/**
	 * @param tipo Parametro (Stringa) contenente la categoria delle statistiche che vogliamo vedere:
	 * "umidita" per vedere le statistiche dell' umidità , "temperatura" per vedere le statistiche
	 * della temperatura o "totali" per vedere le statistiche sia di umitità che di temperatura.
	 * In caso il tipo non corrisponde a nessuno dei precedenti il programma fa vedere di default
	 * le statistiche della temperatura.
	 * @param periodo Parametro (Stringa) contenente il periodo del quale vogliamo vedere le statistiche:
	 * "giornaliere" se vogliamo le statistiche relative alla previsione fatta ieri per oggi, o 
	 * "settimanali" se vogliamo vedere le statistiche su tutte le previsioni per 
	 * oggi relative alla settimana passata
	 * @param precisione  Parametro (Double) che inserisce l'utente con il quale vuole vedere l'
	 * accuratezza delle previsioni (in caso non venga inserito si sceglie il 5% di default)
	 * @param nomeCitta Parametro (Stringa) contenente il nome della città (Londra o Chicago)
	 * @return risultato Stringa contenente le statistiche richieste
	 * @throws InvalidPrecisionException Eccezione personalizzata nel caso in cui la precisione che
	 * inserisce l'utente sia inferiore di 0 o superiore di 100
	 * @throws NonExistingPredictionDataException Eccezione personalizzata nel caso in cui 
	 * mancassero i dati relativi alle previsioni passate (basta che manchi un giorno)
	 */
	@GetMapping(value = "/Statistiche")
	
	
	public String DailyStatsCityName(@RequestParam(name = "tipo") String tipo,@RequestParam(name = "periodo")String periodo,@RequestParam(name = "precisione",defaultValue = "5") String precisione,@RequestParam(name = "nomeCitta") String nomeCitta) throws InvalidPrecisionException, NonExistingPredictionDataException {
		
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
	/**
	 * @param componentiURL Body da inserire con stringhe relative al "zipCode" e al "countryCode"
	 * @param tipo Stringa contenente la categoria delle statistiche che vogliamo vedere:
	 * "umidita" per vedere le statistiche dell' umidità , "temperatura" per vedere le statistiche
	 * della temperatura o "totali" per vedere le statistiche sia di umitità che di temperatura.
	 * In caso il tipo non corrisponde a nessuno dei precedenti il programma fa vedere di default
	 * le statistiche della temperatura.
	 * @param periodo Parametro (Stringa) contenente il periodo del quale vogliamo vedere le statistiche:
	 * "giornaliere" se vogliamo le statistiche relative alla previsione fatta ieri per oggi, o 
	 * "settimanali" se vogliamo vedere le statistiche su tutte le previsioni per 
	 * oggi relative alla settimana passata
	 * @param precisione Parametro (Double) che inserisce l'utente con il quale vuole vedere l'
	 * accuratezza delle previsioni (in caso non venga inserito si sceglie il 5% di default)
	 * @return risultato Stringa contenente le statistiche richieste
	 * @throws InvalidPrecisionException Eccezione personalizzata nel caso in cui la precisione che
	 * inserisce l'utente sia inferiore di 0 o superiore di 100
	 * @throws InvalidZipCodeException Eccezione personalizzata in caso lo Zip Code e/o il Country
	 * code fossero sbagliati 
	 * @throws NonExistingPredictionDataException Eccezione personalizzata nel caso in cui 
	 * mancassero i dati relativi alle previsioni passate (basta che manchi un giorno)
	 */
	@RequestMapping(value = "/Statistiche",method = RequestMethod.POST)
	
	public String DailyStatsZipCode(@RequestBody JSONObject componentiURL,@RequestParam (name = "tipo") String tipo,@RequestParam(name = "periodo")String periodo, @RequestParam(name = "precisione",defaultValue = "5") String precisione) throws InvalidPrecisionException, InvalidZipCodeException, NonExistingPredictionDataException {
		
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
	
	
		
	
	


