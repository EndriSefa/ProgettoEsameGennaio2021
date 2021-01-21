package com.univpm.ProgrammaOW.Statistics;

import java.text.DecimalFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Vector;

import com.univpm.ProgrammaOW.Exceptions.InvalidPrecisionException;
import com.univpm.ProgrammaOW.Exceptions.InvalidZipCodeException;
import com.univpm.ProgrammaOW.Exceptions.NonExistingPredictionDataException;

import org.json.simple.JSONObject;

import com.univpm.ProgrammaOW.Filters.getWeatherPredictions;
import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;

/** Classe per la visualizzazione delle statistiche della temperatura
 *  settimanali di una data città 
 * @author Endri Sefa
 * @author Micol Zazzarini
 */
public class StatisticsWeeklyTemperature {
	
	
	/**
	 * Double con il valore della temperatura attuale
	 */
	private double temperatura_attuale;
	/**
	 * Double con il valore della precisione inserita dall' utente
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
	 * Stringa contenente ciò che vogliamo far vedere all' utente riguardo le statistiche
	 */
	private String risultato;
	/**
	 * Double con il valore della temperatura Massima
	 */
	private double temperaturaMassima;
	/**
	 * Double con il valore della temperatua Minima
	 */
	private double temperaturaMinima;
	
	/**Overloading
	 * Primo caso : stringa della città
	 * Costruttore della classe delle statistiche settimanali relative alla temperatura
	 * @param nomeCitta Stringa con il nome della città 
	 * @param precision Double con il valore della precisione
	 * @throws InvalidPrecisionException  Eccezione personalizzata nel caso in cui la precisione che
	 * inserisce l'utente sia inferiore di 0 o superiore di 100
	 * (in caso non venga inserito si sceglie il 5% di default)
	 * @throws NonExistingPredictionDataException Eccezione personalizzata nel caso in cui 
	 * mancassero i dati relativi alle previsioni passate (basta che manchi un giorno)
	 */
	public StatisticsWeeklyTemperature(String nomeCitta, double precision) throws InvalidPrecisionException, NonExistingPredictionDataException{
		
		if(precision <0 || precision >= 100) throw new InvalidPrecisionException();
		
		getDataCity getDataCity = new getDataCity(nomeCitta);
		
		getWeatherPredictions predictions = new getWeatherPredictions(getDataCity.getMeteo());
		
	    JSONObject dailyWeather = predictions.getDailyWeather();
		
		Vector<JSONObject> previsioni = predictions.getPredictions();
		
		Number app = (Number)dailyWeather.get("Temperatura");
		DecimalFormat df = new DecimalFormat("#.00");
		
		this.temperatura_attuale = app.doubleValue();
		String data_oggi = (String) dailyWeather.get("Data");
		String citta = (String) dailyWeather.get("Citta") ;
		
		
		this.valore = precision;
		
		app = (Number)dailyWeather.get("Temperatura massima");
		
		this.temperaturaMassima = app.doubleValue() ;
		
		app = (Number)dailyWeather.get("Temperatura minima");
		this.temperaturaMinima = app.doubleValue() ;
		
		this.media =  (this.temperaturaMassima + this.temperaturaMinima)/2;
		
		this.varianza =  (Math.pow(this.temperaturaMassima-media, 2)+Math.pow(this.temperaturaMinima-media, 2))/2;
		
		
		
		risultato ="Città: " + citta + "\nData di oggi: " + data_oggi + "\n";
		
		risultato +="Temperatura attuale: "+ df.format(this.temperatura_attuale) + "\n"
					+ "Temperatura massima: " + df.format(this.temperaturaMassima) + "\t"+
				"Temperatura minima: " + df.format(this.temperaturaMinima) + "\n" +
					"Temperatura media: " + df.format(this.media) + "\t Varianza: " + df.format(this.varianza) + "\n\n";
						;
		
		
		int i=1
				;
		for(JSONObject o : previsioni) {
			
			
			
			LocalDate data = LocalDate.now( ZoneId.of( "Europe/Rome" ) ).minusDays( i );
			i++;
			
			app = (Number)o.get("Temperatura");
			
			double temperatura_prevista = app.doubleValue() ;
			double percentuale = 100*(Math.abs(this.temperatura_attuale-temperatura_prevista))/this.temperatura_attuale;
		
		
			if(percentuale <= precision) risultato += 
               "Temperatura secondo le previsioni del " +data +": "+ df.format(temperatura_prevista) +"\n"
              +"Le previsioni erano attendibili con un margine inferiore del "+ valore+"% ( "+ df.format(percentuale) + ")"+"\n"
                     ;

				else  risultato +=  "Temperatura secondo le previsioni del " + data +": "+ df.format(temperatura_prevista)+"\n"
						+ "Le previsioni non erano attendibili con un margine superiore del "+ valore+"% ( "+df.format(percentuale) + ")"+"\n";
		
			}
		
		
	}
	
/** Overloading
 * Secondo caso: Stringhe per lo Zip Code ed il Country Code
 * Costruttore della classe delle statistiche settimanali relative alla temperatura
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
 * 
 */
public StatisticsWeeklyTemperature(String zipCode,String countryCode, double precision) throws InvalidPrecisionException, InvalidZipCodeException, NonExistingPredictionDataException{
		
	    if(precision <0 || precision >= 100) throw new InvalidPrecisionException();
	    
		getDataZipCode getDataZipCode = new getDataZipCode(zipCode,countryCode);
		
		getWeatherPredictions predictions = new getWeatherPredictions(getDataZipCode.getMeteo());
		
	    JSONObject dailyWeather = predictions.getDailyWeather();
		
		Vector<JSONObject> previsioni = predictions.getPredictions();
		
		
		Number app = (Number)dailyWeather.get("Temperatura");
		DecimalFormat df = new DecimalFormat("#.00");
		
		this.temperatura_attuale = app.doubleValue();
		String data_oggi = (String) dailyWeather.get("Data");
		String citta = (String) dailyWeather.get("Citta") ;
		
		
		this.valore = precision;
		
		app = (Number)dailyWeather.get("Temperatura massima");
		
		this.temperaturaMassima = app.doubleValue() ;
		
		app = (Number)dailyWeather.get("Temperatura minima");
		this.temperaturaMinima = app.doubleValue() ;
		
		this.media =  (this.temperaturaMassima + this.temperaturaMinima)/2;
		
		this.varianza =  (Math.pow(this.temperaturaMassima-media, 2)+Math.pow(this.temperaturaMinima-media, 2))/2;
		
		
		
		risultato ="Città: " + citta + "\nData di oggi: " + data_oggi + "\n";
		
		risultato +="Temperatura attuale: "+ df.format(this.temperatura_attuale) + "\n"
					+ "Temperatura massima: " + df.format(this.temperaturaMassima) + "\n"+
				"Temperatura minima: " + df.format(this.temperaturaMinima) + "\t" +
					"Temperatura media: " + df.format(this.media) + "\t Varianza: " + df.format(this.varianza) + "\n\n";
						;
		
		
		int i=1
				;
		for(JSONObject o : previsioni) {
			
			
			
			LocalDate data = LocalDate.now( ZoneId.of( "Europe/Rome" ) ).minusDays( i );
			i++;
			
			app = (Number)o.get("Temperatura");
			
			double temperatura_prevista = app.doubleValue() ;
			double percentuale = 100*(Math.abs(this.temperatura_attuale-temperatura_prevista))/this.temperatura_attuale;
		
		
			if(percentuale <= precision) risultato += 
               "Temperatura secondo le previsioni del " +data +": "+ df.format(temperatura_prevista) +"\n"
              +"Le previsioni erano attendibili con un margine inferiore del "+ valore+"% ( "+ df.format(percentuale) + ")"+"\n\n"
                     ;

				else  risultato +=  "Temperatura secondo le previsioni del " + data +": "+ df.format(temperatura_prevista)+"\n"
						+ "Le previsioni non erano attendibili con un margine superiore del "+ valore+"% ( "+df.format(percentuale) + ")"+"\n\n";
		
			}
		
		
	}
	
	
	
	
	/**
	 *Overriding del toString in modo da far restituire la stringa Risultato
	 */
	public String toString() {return this.risultato;}

}
