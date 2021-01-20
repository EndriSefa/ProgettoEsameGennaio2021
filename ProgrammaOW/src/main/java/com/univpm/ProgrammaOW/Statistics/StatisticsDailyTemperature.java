package com.univpm.ProgrammaOW.Statistics;


import java.text.DecimalFormat;

import java.util.Vector;

import com.univpm.ProgrammaOW.Exceptions.InvalidPrecisionException;
import com.univpm.ProgrammaOW.Exceptions.InvalidZipCodeException;
import com.univpm.ProgrammaOW.Exceptions.NonExistingPredictionDataException;

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
	private double temperaturaMassima;
	private double temperaturaMinima;
	private String citta;
	
	
	
	public StatisticsDailyTemperature(String nomeCitta,double precision) throws InvalidPrecisionException, NonExistingPredictionDataException {
		
		if(precision <0 || precision >= 100) throw new InvalidPrecisionException();
		
		getDataCity getDataCity = new getDataCity(nomeCitta);
		
		getWeatherPredictions predictions = new getWeatherPredictions(getDataCity.getMeteo());
		
	    JSONObject dailyWeather = predictions.getDailyWeather();
		
		Vector<JSONObject> previsioni = predictions.getPredictions();
		 
		
		
		 
		 
		 
		
		 this.citta =(String) dailyWeather.get("Citta");
		
		//per prelevare i numeri facciamo un casting da number a long 
		//a loro volta ai dati viene fatto un parsing in double 
		
		Number app = (Number) dailyWeather.get("Temperatura");
		
		this.temperatura_attuale = app.doubleValue() ;
		app = (Number)previsioni.firstElement().get("Temperatura");
		this.temperatura_prevista =  app.doubleValue();
		
		double percentuale = 100*Math.abs((this.temperatura_attuale-this.temperatura_prevista)/this.temperatura_attuale);
		this.percentualeEffettiva = percentuale;
		
		if(percentuale>precision) Precisione = false;
		else Precisione = true;
		
		
		this.valore = precision;
		
		app = (Number)dailyWeather.get("Temperatura massima");
		
		this.temperaturaMassima =  app.doubleValue();
		
		app = (Number)dailyWeather.get("Temperatura minima");
		this.temperaturaMinima = app.doubleValue();
		
		this.media = (this.temperaturaMassima + this.temperaturaMinima)/2;
		
		this.varianza = (Math.pow(this.temperaturaMassima-media, 2)+Math.pow(this.temperaturaMinima-media, 2))/2;
		
		
		
		
	}
	
	
	
	public StatisticsDailyTemperature(String zipCode, String CountryCode,double precision) throws InvalidPrecisionException, InvalidZipCodeException, NonExistingPredictionDataException{
		
		if(precision < 0 || precision >= 100) throw new InvalidPrecisionException();
		
		getDataZipCode getDataZipCode = new getDataZipCode(zipCode,CountryCode);
        getWeatherPredictions predictions = new getWeatherPredictions(getDataZipCode.getMeteo());
        
        JSONObject dailyWeather = predictions.getDailyWeather();
		
		Vector<JSONObject> previsioni = predictions.getPredictions();
		
		this.citta =(String) dailyWeather.get("Citta"); 
		Number app = (Number) dailyWeather.get("Temperatura");
		
		this.temperatura_attuale = app.doubleValue() ;
		app = (Number)previsioni.firstElement().get("Temperatura");
		this.temperatura_prevista =  app.doubleValue();
		
		double percentuale = 100*Math.abs((this.temperatura_attuale-this.temperatura_prevista)/this.temperatura_attuale);
		this.percentualeEffettiva = percentuale;
		
		if(percentuale>precision) Precisione = false;
		else Precisione = true;
		
		
		
		this.valore = precision;
		
		app = (Number)dailyWeather.get("Temperatura massima");
		
		this.temperaturaMassima =  app.doubleValue();
		
		app = (Number)dailyWeather.get("Temperatura minima");
		this.temperaturaMinima = app.doubleValue();
		
		this.media = (this.temperaturaMassima + this.temperaturaMinima)/2;
		
		this.varianza = (Math.pow(this.temperaturaMassima-media, 2)+Math.pow(this.temperaturaMinima-media, 2))/2;
		
		
		
		
	}
	
	
	public String toString() {
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		if(Precisione) return "Città: "+ this.citta + "\nTemperatura attuale: "+ df.format(this.temperatura_attuale) +"\n" +
							"Temperatura Massima: " + df.format(this.temperaturaMassima) + 
							"\t Temperatura Minima: " + df.format(this.temperaturaMinima) + "\n"
							  +"Media: "+df.format(media)+'\t'
	                          +"Varianza: "+df.format(varianza)+ "\n"
	                          + "Temperatura secondo le previsioni di ieri: "+ df.format(temperatura_prevista) +"\n"
			                  +"Le previsioni erano attendibili con un margine inferiore del "+ valore+"% ( "+ df.format(this.percentualeEffettiva) + "%)"+"\n"
	                                 ;
		
		else return "Città: "+this.citta + "\nTemperatura attuale: "+ df.format(this.temperatura_attuale)+"\n" 
				+"Temperatura Massima: " + df.format(this.temperaturaMassima) + 
				"\t Temperatura Minima: " + df.format(this.temperaturaMinima) + "\n"
				   +"Temperatura Media: "+df.format(media)+"\t"
                   +"Varianza: "+df.format(varianza) +"\n"
	               + "Temperatura secondo le previsioni di ieri: "+ df.format(temperatura_prevista)+"\n"
			       + "Le previsioni non erano attendibili con un margine superiore del "+ valore+"% ( "+df.format(this.percentualeEffettiva) + "%)"+"\n"
		          ;
	}
	
	

}
