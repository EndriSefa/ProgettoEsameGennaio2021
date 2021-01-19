package com.univpm.ProgrammaOW.Statistics;

import java.text.DecimalFormat;
import java.util.Vector;

import org.json.simple.JSONObject;

import com.univpm.ProgrammaOW.Filters.getWeatherPredictions;
import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;

public class StatisticsDailyTemperature {
	
	private boolean Precisione;
	private double temperatura_attuale;
	private double temperatura_prevista;
	private double valore;
	private double media;
	private double varianza;
	private double percentualeEffettiva;
	
	
	public StatisticsDailyTemperature(String nomeCitta,double precision) {
		
		
		getDataCity getDataCity = new getDataCity(nomeCitta);
		
		getWeatherPredictions predictions = new getWeatherPredictions(getDataCity.getMeteo());
		
	    JSONObject dailyWeather = predictions.getDailyWeather();
		
		Vector<JSONObject> previsioni = predictions.getPredictions();
		
		 
		 
		 
		
		 
		
		//per prelevare i numeri facciamo un casting da number a long 
		//a loro volta ai dati viene fatto un parsing in double 
		
		this.temperatura_attuale = (Double) dailyWeather.get("Temperatura");
		this.temperatura_prevista = (Double) previsioni.firstElement().get("Temperatura");
		
		double percentuale = 100*Math.abs((this.temperatura_attuale-this.temperatura_prevista)/this.temperatura_attuale);
		this.percentualeEffettiva = percentuale;
		
		if(percentuale>precision) Precisione = false;
		else Precisione = true;
		
		this.valore = precision;
		
		double appoggio1 = (Double) dailyWeather.get("Temperatura massima");
		double appoggio2 = (Double) dailyWeather.get("Temperatura minima");
		
		this.media = (appoggio1 + appoggio2)/2;
		double media_quadratica =Math.pow((Math.pow((double)appoggio1, 2)+Math.pow((double)appoggio2,2))/2,1/2);
		this.varianza = (double) Math.pow(media, 2)-media_quadratica;
		
	}
	
	
	
	public StatisticsDailyTemperature(String zipCode, String CountryCode,double precision) {
		
		getDataZipCode getDataZipCode = new getDataZipCode(zipCode,CountryCode);
        getWeatherPredictions predictions = new getWeatherPredictions(getDataZipCode.getMeteo());
        
        JSONObject dailyWeather = predictions.getDailyWeather();
		
		Vector<JSONObject> previsioni = predictions.getPredictions();
		
		 
		 
		
		
		 
		this.temperatura_attuale = (Long) dailyWeather.get("Temperatura");
		this.temperatura_prevista = (Long) previsioni.firstElement().get("Temperatura");
		
		double percentuale = 100*(Math.abs(this.temperatura_attuale-this.temperatura_prevista))/this.temperatura_attuale;
		
		if(percentuale>precision) Precisione = false;
		else Precisione = true;
		
		this.valore = precision;
		
		Long appoggio1 = (Long) dailyWeather.get("Temperatura massima");
		Long appoggio2 = (Long) dailyWeather.get("Temperatura minima");
		
		this.media = (double) (appoggio1 + appoggio2)/2;
		double media_quadratica =Math.pow((Math.pow((double)appoggio1, 2)+Math.pow((double)appoggio2,2))/2,1/2);
		this.varianza = (double) Math.pow(media, 2)-media_quadratica;
		
		
		
		
		
	}
	
	
	public String toString() {
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		if(Precisione) return "Temperatura attuale: "+ temperatura_attuale +"\n" 
							  +"Media: "+df.format(media)+"\n"
	                          +"Varianza"+df.format(varianza)+ "\n"
	                          + "Temperatura secondo le previsioni di ieri: "+ temperatura_prevista +"\n"
			                  +"Le previsioni erano attendibili con un margine inferiore del "+ valore+"% ( "+ df.format(this.percentualeEffettiva) + ")"+"\n"
	                                 ;
		
		else return "Temperatura attuale: "+ temperatura_attuale+"\n" 
				   +"Media: "+df.format(media)+"\n"
                   +"Varianza: "+df.format(varianza) +"\n"
	               + "Temperatura secondo le previsioni di ieri: "+ temperatura_prevista+"\n"
			       + "Le previsioni non erano attendibili con un margine superiore del "+ valore+"% ( "+df.format(this.percentualeEffettiva) + ")"+"\n"
		          ;
	}
	
	

}
