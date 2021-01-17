package com.univpm.ProgrammaOW.Statistics;

import java.util.Vector;

import org.json.simple.JSONObject;

import com.univpm.ProgrammaOW.Filters.getWeatherPredictions;
import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;

public class StatisticsDailyTemperature {
	
	boolean Precisione;
	double temperatura_attuale;
	double temperatura_prevista;
	double valore;
	double media;
	double varianza;
	
	
	public StatisticsDailyTemperature(String nomeCitta,double precision) {
		
		
		getDataCity getDataCity = new getDataCity(nomeCitta);
		
		getWeatherPredictions predictions = new getWeatherPredictions(getDataCity.getMeteo());
		
	    JSONObject dailyWeather = predictions.getDailyWeather();
		
		Vector<JSONObject> previsioni = predictions.getPredictions();
		
		Number temperatura_attuale = (Number) dailyWeather.get("Temperatura");
		Number temp_max = (Number) dailyWeather.get("Temperatura massima");
		Number temp_min = (Number) dailyWeather.get("Temperatura minima");
		
		Number temperatura_prevista =(Number) previsioni.firstElement().get("Temperatura");
		
		//per prelevare i numeri facciamo un casting da number a long 
		//a loro volta ai dati viene fatto un parsing in double 
		
		this.temperatura_attuale = (Long) temperatura_attuale;
		this.temperatura_prevista = (Long) temperatura_prevista;
		
		double percentuale = 100*(Math.abs(this.temperatura_attuale-this.temperatura_prevista))/this.temperatura_attuale;
		
		if(percentuale>precision) Precisione = false;
		else Precisione = true;
		
		this.valore = precision;
		
		Long appoggio1 = (Long) temp_max;
		Long appoggio2 = (Long) temp_min;
		
		this.media = (double) (appoggio1 + appoggio2)/2;
		double media_quadratica =Math.pow((Math.pow((double)appoggio1, 2)+Math.pow((double)appoggio2,2))/2,1/2);
		this.varianza = (double) Math.pow(media, 2)-media_quadratica;
		
	}
	
	
	
	public StatisticsDailyTemperature(String zipCode, String CountryCode,double precision) {
		
		getDataZipCode getDataZipCode = new getDataZipCode(zipCode,CountryCode);
        getWeatherPredictions predictions = new getWeatherPredictions(getDataZipCode.getMeteo());
        
        JSONObject dailyWeather = predictions.getDailyWeather();
		
		Vector<JSONObject> previsioni = predictions.getPredictions();
		
		Number temperatura_attuale = (Number) dailyWeather.get("Temperatura");
		Number temp_max = (Number) dailyWeather.get("Temperatura massima");
		Number temp_min = (Number) dailyWeather.get("Temperatura minima");
		
		Number temperatura_prevista =(Number) previsioni.firstElement().get("Temperatura");
		
		
		this.temperatura_attuale = (Long) temperatura_attuale;
		this.temperatura_prevista = (Long) temperatura_prevista;
		
		double percentuale = 100*(Math.abs(this.temperatura_attuale-this.temperatura_prevista))/this.temperatura_attuale;
		
		if(percentuale>precision) Precisione = false;
		else Precisione = true;
		
		this.valore = precision;
		
		Long appoggio1 = (Long) temp_max;
		Long appoggio2 = (Long) temp_min;
		
		this.media = (double) (appoggio1 + appoggio2)/2;
		double media_quadratica =Math.pow((Math.pow((double)appoggio1, 2)+Math.pow((double)appoggio2,2))/2,1/2);
		this.varianza = (double) Math.pow(media, 2)-media_quadratica;
		
		
		
		
		
	}
	
	
	public String toString() {
		
		if(Precisione == true) return "Temperatura attuale: "+ temperatura_attuale +"\n"
	                                  + "Temperatura secondo le previsioni di ieri: "+ temperatura_prevista +"\n"
			                          +"Le previsioni erano attendibili con un margine inferiore del "+ valore+"%"+"\n"
	                                  +"Media: "+media+"\n"
	                                  +"Varianza"+varianza;
		
		else return "Temperatura attuale: "+ temperatura_attuale+"\n"
	               + "Temperatura secondo le previsioni di ieri: "+ temperatura_prevista+"\n"
			       + "Le previsioni non erano attendibili con un margine superiore del "+ valore+"%"+"\n"
		           +"Media: "+media+"\n"
                   +"Varianza"+varianza;
	}
	
	

}
