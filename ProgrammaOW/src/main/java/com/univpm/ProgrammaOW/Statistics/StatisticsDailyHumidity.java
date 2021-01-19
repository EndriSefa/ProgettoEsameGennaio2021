package com.univpm.ProgrammaOW.Statistics;
import java.math.*;
import java.text.DecimalFormat;
import java.util.Vector;

import org.json.simple.JSONObject;

import com.univpm.ProgrammaOW.Filters.getWeatherPredictions;
import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;

public class StatisticsDailyHumidity {
	
	private boolean Precisione;
	private double umiditaReale;
	private double umiditaPrevista;
	private double valore;
	
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
		
		Number appoggio1 = (Number) dailyWeather.get("Umidita");
		Number appoggio2 =(Number) previsioni.firstElement().get("Umidita");
		
		//per prelevare i numeri facciamo un casting da number a long 
		//a loro volta ai dati viene fatto un parsing in double 
		
		this.umiditaReale = (Long) appoggio1;
		this.umiditaPrevista = (Long) appoggio2;
		
		double percentuale = 100*(Math.abs(umiditaReale-umiditaPrevista))/umiditaReale;
		
		if(percentuale>precision) Precisione = false;
		else Precisione = true;
		
		this.valore = precision;
		
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
		
		this.valore = precision;
	}
	
	//metodo che stampa umidità reale, umidità prevista e dice se le previsioni erano giuste
	public String toString() {
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		if(Precisione == true) return "Umidità attuale: "+ umiditaReale +"\n"
	                                  + "Umidità secondo le previsioni di ieri: "+ umiditaPrevista +"\n"
			                          +"Le previsioni erano attendibili con un margine inferiore del "+ df.format(valore)+"%";
		else return "Umidità attuale: "+ umiditaReale+"\n"
	               + "Umidità secondo le previsioni di ieri: "+ umiditaPrevista+"\n"
			       + "Le previsioni non erano attendibili con un margine superiore del "+ df.format(valore)+"%";
	}
	
	

}
