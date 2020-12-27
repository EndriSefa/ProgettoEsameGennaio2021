package com.univpm.ProgrammaOW.Controller;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer.ConditionObject;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	public void DataCity(@RequestParam(value = "cityName") String cityName) {
		
		
		System.out.println(getDataCity(cityName));//come richiamo il metodo della classe se la classe non ha un costruttore? costruttore di default? 
		
		
		
		//fatto in questo modo non ho i problemi dati dall'uso di JSONObject, come sotto
		
		}
	
	//stampa il meteo corrente ricevendo come parametro lo zipcode
	@GetMapping("/currentWeather/{zip}/{countryCode}")
	//NB valore di ritorno JSONObject
	public JSONObject DataZipCode(@RequestParam(value = "zip") String zip,@RequestParam(value = "countryCode") String countryCode) {
		JSONObject result = new JSONObject();
		result = getDataZipCode(zip,countryCode);//idem sopra
		System.out.println(result);
		return result;
	}
	
	//stampa le previsioni dando come parametro il nome della città
	@GetMapping("/predictions/{city}")
	public JSONObject predictions(@RequestParam(value = "city") String city){
		setWeeklyForecast(city);//è get o set?
		getWeatherPredictions weatherPredictions= new getWeatherPredictions(getDataCity(city),getForecast());/*qui uso il costruttore e posso richiamare il metodo
		get predictions, ma per i metodi che uso per ottenere i parametri?*/
		System.out.println(weatherPredictions.getPredictions());
		return weatherPredictions.getPredictions();//sempre perchè non basta la stampa ma devo avere un valore di ritorno
	}
	
	//per fare le previsioni con lo zipCode?
		
	
	//metodo che stampa le statistiche, NB come valore di ritorno ho messo JSONObject
	@RequestMapping(value = "/dailyStats/{precision}/{cityName}",method = RequestMethod.POST)
	//richiedo come parametro anche il nome della città per passarlo come parametro ai vari metodi
	public JSONObject DailyStats(@RequestBody String tipo,@RequestParam(value = "precision") double precision,@RequestParam(value = "cityName") String cityName) {
		JSONObject result = new JSONObject();
		setWeeklyForecast(cityName);
		switch(tipo){
			case "temperature": {
			
			StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(getForecast(),precision);/*era metodo o costruttore?( nella
			classe ci sono due metodi/costruttori con lo stesso nome ma con parametri diversi e non sapevo quale usare)
			//se era costruttore bisogna fare un metodo che abbia come valore di ritorno un JSONObject*/
			DailyTemperature.toString();
			break;
			}
			case "humidity" : {
			
			StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(getForecast(),precision);//stesso dubbio di sopra
			DailyHumidity.toString();
			break;
			
			}
			
			case "all": {
				
				StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(getForecast(),precision);
				StatisticsDailyHumidity DailyHumidity = new StatisticsDailyHumidity(getForecast(),precision);
				String allStats = 	DailyTemperature.toString()+DailyHumidity.toString();//è corretta la forma?
				System.out.println(allStats);
				break;
			}
			default :{ 
			
	        StatisticsDailyTemperature DailyTemperature = new StatisticsDailyTemperature(getForecast(),precision);
			DailyTemperature.toString();
		    break;
	        }
		}
		return result;//questo è il JSONObject dove voglio salvare(nei diversi case) i valori ottenuti,per poter avere un valore di ritorno
					
			
		
	}
	
	//metodo che stampa le statistiche(come sopra ma settimanali)
	@RequestMapping(value = "/WeeklyStats/{precision}/{cityName}",method = RequestMethod.POST)
	public JSONObject WeeklyStats(@RequestBody String tipo,@RequestParam(value = "precision") double precision,@RequestParam(value = "cityName") String cityName) {
		JSONObject result = new JSONObject();
		setWeeklyForecast(cityName);//è get o set?
		getWeatherPredictions weatherPredictions= new getWeatherPredictions(getDataCity(city),getForecast());
		switch(tipo){
			case "temperature": {
			
			StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(getPredictions(),precision);
			
			WeeklyTemperature.toString();//stesso problema di sopra, come faccio a ottenere JSONObject?
			break;
			}
			case "humidity" : {
			
			StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(getPredictions(),precision);//stesso dubbio di sopra
			WeeklyHumidity.toString();
			break;
			
			}
			
			case "all": {
				StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(getPredictions(),precision);
				StatisticsWeeklyHumidity WeeklyHumidity = new StatisticsWeeklyHumidity(getPredictions(),precision);
				
				
				String allStats = 	WeeklyTemperature.toString()+WeeklyHumidity.toString();
				System.out.println(allStats);
				break;
			}
			default :{ 
				StatisticsWeeklyTemperature WeeklyTemperature = new StatisticsWeeklyTemperature(getPredictions(),precision);
			
			    WeeklyTemperature.toString();
		     	break;
			
	        }
		}
		return result;
					
			
		
	}
}
	
		
		
		
	
	


