package com.univpm.ProgrammaOW.Statistics;
import java.math.*;
import java.util.Vector;

import org.json.simple.JSONObject;

import com.univpm.ProgrammaOW.Filters.getWeatherPredictions;
import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;

public class StatisticsDailyHumidity {
	
	boolean Precisione;
	double umiditaReale;
	double umiditaPrevista;
	double valore;
	
	//costruttore che prende come parametro il nome della città
	public StatisticsDailyHumidity(String nomeCitta,double precision) {
		
		//creo l'oggetto getDataCity dando come parametro il parametro del costruttore
		getDataCity getDataCity = new getDataCity(nomeCitta);
		
		
		//do il meteo riportato da getDataCity come parametro per getWeatherPredictions
		getWeatherPredictions predictions = new getWeatherPredictions(getDataCity.getMeteo());
		
		//metodo di getWeatherPredictions
		JSONObject dailyWeather = predictions.getDailyWeather();
		
		
		//metodo di getWeatherPredictions
		Vector<JSONObject> previsioni = predictions.getPredictions();
		
		this.umiditaReale = (double) dailyWeather.get("Umidita");
		this.umiditaPrevista=(double) previsioni.firstElement().get("Umidita");
		
		double percentuale = 100*(Math.abs(umiditaReale-umiditaPrevista))/umiditaReale;
		
		if(percentuale>precision) Precisione = false;
		else Precisione = true;
		
		this.valore = percentuale;
		
	}
	
	
	//costruttore che prende come parametri zipCode e CountryCode
	public StatisticsDailyHumidity(String zipCode, String CountryCode,double precision) {
		
		getDataZipCode getDataZipCode = new getDataZipCode(zipCode,CountryCode);
        getWeatherPredictions predictions = new getWeatherPredictions(getDataZipCode.getMeteo());
		
		JSONObject dailyWeather = predictions.getDailyWeather();
		
		Vector<JSONObject> previsioni = predictions.getPredictions();
		
		this.umiditaReale = (double) dailyWeather.get("Umidita");
		this.umiditaPrevista=(double) previsioni.firstElement().get("Umidita");
		
		double percentuale = 100*(Math.abs(umiditaReale-umiditaPrevista))/umiditaReale;
		
		if(percentuale>precision) Precisione = false;
		else Precisione = true;
		
		this.valore = percentuale;
	}
	
	//metodo che stampa umidità reale, umidità prevista e dice se le previsioni erano giuste
	public String toString() {
		
		if(Precisione == true) return "Umidità attuale: "+ umiditaReale +"\n"
	                                  + "Umidità secondo le previsioni di ieri: "+ umiditaPrevista +"\n"
			                          +"Le previsioni erano attendibili con un errore del "+ valore+"%";
		else return "Umidità attuale: "+ umiditaReale+"\n"
	               + "Umidità secondo le previsioni di ieri: "+ umiditaPrevista+"\n"
			       + "Le previsioni non erano attendibili, errore del "+ valore+"%";
	}
	
	

}
