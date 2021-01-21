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

/** Classe per la visualizzazine delle statistiche giornaliere di una data città
 * @author Endri Sefa
 * @author Micol Zazzarini
 */
public class StatisticsDailyTemperature {
	
	/**
	 * Boolean che  ci indica se la previsione fatta è stata accurata o meno 
	 */
	
	private boolean Precisione;
	/**
	 * Double con il valore della temperatura attuale
	 */
	private double temperatura_attuale;
	/**
	 * Double con il valore della temperatura prevista ieri per oggi
	 */
	private double temperatura_prevista;
	/**
	 * Double con il valore di accuratezza inserito dall'utente
	 */
	private double valore;
	/**
	 * Double con il valore della temperatura media
	 */
	
	private double media;
	/**
	 * Double con il valore della varianza della temperatura
	 */
	private double varianza;
	/**
	 * Double con il valore effettivo dell' accuratezza
	 */
	private double percentualeEffettiva;
	/**
	 * Double con il valore della temperatura massima
	 */
	private double temperaturaMassima;
	/**
	 * Double con in valore della temperatura minima
	 */
	private double temperaturaMinima;
	/**
	 * Stringa contenente il nome della città
	 */
	private String citta;
	
	
	
	/**Overloadig
	 * Primo caso: Stringa della città
	 * @param nomeCitta Stringa con il nome della città
	 * @param precision Double con il valore della precisione
	 * @throws InvalidPrecisionException  Eccezione personalizzata nel caso in cui la precisione che
	 * inserisce l'utente sia inferiore di 0 o superiore di 100
	 * (in caso non venga inserito si sceglie il 5% di default)
	 * @throws NonExistingPredictionDataException Eccezione personalizzata nel caso in cui 
	 * mancassero i dati relativi alle previsioni passate (basta che manchi un giorno)
	 */
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
	
	
	
	/**Overloading
	 * Secondo Caso: Stringhe per lo Zip Code ed il Country Code
	 * @param zipCode Stringa contenente lo Zip Code
	 * @param countryCode Stringa contenente il Country Code
	 * @param precision Double con il valore della precisione
	 * @throws InvalidPrecisionException  Eccezione personalizzata nel caso in cui la precisione che
	 * inserisce l'utente sia inferiore di 0 o superiore di 100 
	 * (in caso non venga inserito si sceglie il 5% di default)
	 * @throws NonExistingPredictionDataException Eccezione personalizzata nel caso in cui 
	 * mancassero i dati relativi alle previsioni passate (basta che manchi un giorno)
	 * @throws InvalidZipCodeException Eccezione personalizzata in caso lo Zip Code e/o il Country
	 * code fossero sbagliati 
	 */
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
	
	
	/**
	 *Overriding del metodo toString per restituire le statistiche giornaliere relative alla temperatura
	 */
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
