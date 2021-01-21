package com.univpm.ProgrammaOW.Statistics;
import java.math.*;

import java.text.DecimalFormat;
import java.util.Vector;

import com.univpm.ProgrammaOW.Exceptions.InvalidPrecisionException;
import com.univpm.ProgrammaOW.Exceptions.InvalidZipCodeException;
import com.univpm.ProgrammaOW.Exceptions.NonExistingPredictionDataException;

import org.json.simple.JSONObject;

import com.univpm.ProgrammaOW.Exceptions.InvalidPrecisionException;
import com.univpm.ProgrammaOW.Filters.getWeatherPredictions;
import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;

/** Classe per la visualizzazione delle statistiche giornaliere dell'umidità 
 * di una città data
 * @author Endri Sefa
 * @author Micol Zazzarini
 */
public class StatisticsDailyHumidity {
	
	/**
	 * Boolean che  ci indica se la previsione fatta è stata accurata o meno
	 */
	private boolean Precisione;
	/**
	 * Double con il valore dell' umidità attuale
	 */
	private double umiditaReale;
	/**
	 * Double con il valore dell'umidità prevista ieri per oggi
	 */
	private double umiditaPrevista;
	/**
	 * Double con il valore di accuratezza inserita dall'utente
	 */
	private double valore;
	/**
	 * Double con il valore della temperatura massima
	 */
	private double percentuale;
	/**
	 * Stringa con il nome della città
	 */
	private String citta;
	
	//costruttore che prende come parametro il nome della città
	/**Overloading
	 * Primo caso: Stringa per il nome della città
	 * Costruttore della classe delle statistiche giornaliere relative all'umidità
	 * @param nomeCitta Stringa con il nome della città
	 * @param precision Double con il valore della precisione
	 * @throws InvalidPrecisionException  Eccezione personalizzata nel caso in cui la precisione che
	 * inserisce l'utente sia inferiore di 0 o superiore di 100
	 * (in caso non venga inserito si sceglie il 5% di default)
	 * @throws NonExistingPredictionDataException Eccezione personalizzata nel caso in cui 
	 * mancassero i dati relativi alle previsioni passate (basta che manchi un giorno)
	 */
	public StatisticsDailyHumidity(String nomeCitta,double precision) throws InvalidPrecisionException, NonExistingPredictionDataException {
		
		//creo l'oggetto getDataCity dando come parametro il parametro del costruttore
		getDataCity getDataCity = new getDataCity(nomeCitta);
		
		
		//do il meteo riportato da getDataCity come parametro per getWeatherPredictions
		getWeatherPredictions predictions = new getWeatherPredictions(getDataCity.getMeteo());
		
		//metodo di getWeatherPredictions
		JSONObject dailyWeather = predictions.getDailyWeather();
		
		this.citta = (String) dailyWeather.get("Citta");
		//metodo di getWeatherPredictions
		Vector<JSONObject> previsioni = predictions.getPredictions();
		
		Number appoggio1 = (Number) dailyWeather.get("Umidita");
		Number appoggio2 =(Number) previsioni.firstElement().get("Umidita");
		
		//per prelevare i numeri facciamo un casting da number a long 
		//a loro volta ai dati viene fatto un parsing in double 
		
		this.umiditaReale =  appoggio1.doubleValue();
		this.umiditaPrevista =  appoggio2.doubleValue();
	
		this.percentuale = 100*(Math.abs(umiditaReale-umiditaPrevista))/umiditaReale;
		
		if(percentuale>precision) Precisione = false;
		else Precisione = true;
		
		if(precision<0 || precision >=100) throw new InvalidPrecisionException();
	
		this.valore = precision;
		
		
		
	}
	
	
	//costruttore che prende come parametri zipCode e CountryCode
	/**Overloading
	 * Secondo Caso: Stringhe per lo Zip Code ed il Country Code
	 * Costruttore della classe delle statistiche giornaliere relative all'umidità
	 * @param zipCode Stringa contenente lo Zip Code
	 * @param CountryCode Stringa contenente il Country Code
	 * @param precision Double con il valore della precisione
	 * @throws InvalidPrecisionException  Eccezione personalizzata nel caso in cui la precisione che
	 * inserisce l'utente sia inferiore di 0 o superiore di 100 
	 * (in caso non venga inserito si sceglie il 5% di default)
	 * @throws NonExistingPredictionDataException Eccezione personalizzata nel caso in cui 
	 * mancassero i dati relativi alle previsioni passate (basta che manchi un giorno)
	 * @throws InvalidZipCodeException Eccezione personalizzata in caso lo Zip Code e/o il Country
	 * code fossero sbagliati
	 */
	public StatisticsDailyHumidity(String zipCode, String CountryCode,double precision) throws InvalidPrecisionException, InvalidZipCodeException, NonExistingPredictionDataException {
		
		if(precision <0 || precision >= 100) throw new InvalidPrecisionException();
		
		getDataZipCode getDataZipCode = new getDataZipCode(zipCode,CountryCode);
        getWeatherPredictions predictions = new getWeatherPredictions(getDataZipCode.getMeteo());
		
		JSONObject dailyWeather = predictions.getDailyWeather();
		this.citta = (String) dailyWeather.get("Citta");
		
		Vector<JSONObject> previsioni = predictions.getPredictions();
		
		Number app = (Number)dailyWeather.get("Umidita");
		
		this.umiditaReale = app.doubleValue();
		app = (Number)previsioni.firstElement().get("Umidita");
		this.umiditaPrevista= app.doubleValue();
		
		this.percentuale = 100*(Math.abs(umiditaReale-umiditaPrevista))/umiditaReale;
		
		if(percentuale>precision) Precisione = false;
		else Precisione = true;
		
		
		
		this.valore = precision;
	}
	
	//metodo che stampa umidità reale, umidità prevista e dice se le previsioni erano giuste
	/**
	 *Overriding del metodo toString per restituire le statistiche giornaliere relative alla temperatura
	 */
	public String toString() {
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		if(Precisione == true) return "Città: "+this.citta+ "\nUmidità attuale: "+ df.format(umiditaReale) +"\n"
	                                  + "Umidità secondo le previsioni di ieri: "+ df.format(umiditaPrevista) +"\n"
			                          +"Le previsioni erano attendibili con un margine inferiore del "+ valore+"% ("+ df.format(this.percentuale)+"%)";
		else return "Città: "+this.citta+ "\nUmidità attuale: "+ df.format(umiditaReale)+"\n"
	               + "Umidità secondo le previsioni di ieri: "+ df.format(umiditaPrevista)+"\n"
			       + "Le previsioni non erano attendibili con un margine superiore del "+ valore+"% ("+ df.format(this.percentuale) +"%)";
	}
	
	

}
