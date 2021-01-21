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
import com.univpm.ProgrammaOW.Filters.getWeeklyForecast;
import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;

/** Classe per la visualizzazione delle statistiche settimanali dell'umidità 
 * di una città data
 * @author Endri Sefa
 * @author Micol Zazzarini
 */
public class StatisticsWeeklyHumidity {
	
	
	/**
	 * Double con il valore dell'umidità attuale
	 */
	private double umiditaReale;
	/**
	 * Stringa contenente ciò che vogliamo far vedere all' utente riguardo le statistiche
	 */
	private String risultato;
	/**
	 * Double con il valore dell' accuratezza inserita dall' utente
	 */
	private double valore;
	
	/**Overloading
	 * Primo caso: Stringa per il nome della città
	 * Costruttore della classe delle statistiche settimanali relative all'umidità
	 * @param cityName Stringa con il nome della città
	 * @param precision Double con il valore della precisione
	 * @throws InvalidPrecisionException  Eccezione personalizzata nel caso in cui la precisione che
	 * inserisce l'utente sia inferiore di 0 o superiore di 100
	 * (in caso non venga inserito si sceglie il 5% di default)
	 * @throws NonExistingPredictionDataException Eccezione personalizzata nel caso in cui 
	 * mancassero i dati relativi alle previsioni passate (basta che manchi un giorno)
	 */
	public StatisticsWeeklyHumidity(String cityName,double precision) throws InvalidPrecisionException, NonExistingPredictionDataException{
		
		if(precision < 0 || precision >= 100) throw new InvalidPrecisionException();
		
		this.valore = precision;
		
		getDataCity getDataCity = new getDataCity(cityName);
		
		getWeatherPredictions getForecast = new getWeatherPredictions(getDataCity.getMeteo());
		
        JSONObject dailyWeather = getForecast.getDailyWeather();
		
		Vector<JSONObject> previsioni = getForecast.getPredictions();
		
		DecimalFormat df = new DecimalFormat("#.00");
		Number app = (Number)dailyWeather.get("Umidita");
		
		this.umiditaReale = app.doubleValue();
		String data_oggi = (String) dailyWeather.get("Data");
		String citta = (String) dailyWeather.get("Citta");
		
		risultato = "Città: "+ citta + "\nData di oggi: " + data_oggi + "\n";
		
		risultato +="Umidità attuale: "+ df.format(umiditaReale) + "\n"+"\n";
		
		//contatore per i giorni delle previsioni
		int i=1
				;
		for(JSONObject o : previsioni) {
			
			
			
			
			LocalDate data = LocalDate.now( ZoneId.of( "Europe/Rome" ) ).minusDays( i );
			i++;
			
			app = (Number)o.get("Umidita");
	        double umiditaPrevista = app.doubleValue();
			double percentuale = 100*(Math.abs(umiditaReale-umiditaPrevista))/umiditaReale;
			if(percentuale <= precision) { 
			                              risultato+= "Giorno in cui è stata fatta la previsione : "+data+"\n"
			                                       + "Umidità prevista: "+ df.format(umiditaPrevista)+"\n"
			                                       +"Le previsioni del giorno erano attendibili con un margine inferiore del "+ valore+"% ( "+ df.format(percentuale) +"%)" + "\n";}
			else {
			      risultato+="Giorno in cui è stata fatta la previsione : "+data+"\n"
			      +"Umidità prevista: "+ df.format(umiditaPrevista)+"\n"
			      +"Le previsioni del giorno non erano attendibili con un margine superiore del "+ valore+"% ( "+ df.format(percentuale) +"%)" + "\n";
			     }
			risultato += "\n";
			
		}
		
		
		
		
		
	}
	
	/** Overloading
	 * Secondo caso: Stringhe per lo Zip Code ed il Country Code
	 * Costruttore della classe delle statistiche settimanali relative all'umidità
	 * @param ZipCode Stringa contenente lo Zip Code
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
	public StatisticsWeeklyHumidity(String ZipCode, String CountryCode,double precision) throws InvalidPrecisionException, InvalidZipCodeException, NonExistingPredictionDataException{
		
		if(precision < 0 || precision >= 100) throw new InvalidPrecisionException();
		
		this.valore = precision;
        getDataZipCode getDataZipCode = new getDataZipCode(ZipCode,CountryCode);
		
		getWeatherPredictions getWeeklyForecast = new getWeatherPredictions(getDataZipCode.getMeteo());
		
        JSONObject dailyWeather = getWeeklyForecast.getDailyWeather();
		
		Vector<JSONObject> previsioni = getWeeklyForecast.getPredictions();
		
		DecimalFormat df = new DecimalFormat("#.00");
		Number app = (Number)dailyWeather.get("Umidita");
		
		this.umiditaReale = app.doubleValue();
		
		String data_oggi = (String) dailyWeather.get("Data");
		
		risultato = "Data di oggi: " + data_oggi + "\n";
		
		risultato +="Umidità attuale: "+ df.format(umiditaReale) + "\n"+"\n";
		
		//contatore per i giorni delle previsioni
		int i=1
				;
		for(JSONObject o : previsioni) {
			
			
			
			
			LocalDate data = LocalDate.now( ZoneId.of( "Europe/Rome" ) ).minusDays( i );
			i++;
			
			app = (Number)o.get("Umidita");
	        double umiditaPrevista = app.doubleValue();
			double percentuale = 100*(Math.abs(umiditaReale-umiditaPrevista))/umiditaReale;
			if(percentuale <= precision) { 
			                              risultato+= "Giorno in cui è stata fatta la previsione : "+data+"\n"
			                                       + "Umidità prevista: "+ df.format(umiditaPrevista)+"\n"
			                                       +"Le previsioni del giorno erano attendibili con un margine inferiore del "+ valore+"% ( "+ df.format(percentuale) +"%)" + "\n";}
			else {
			      risultato+="Giorno: "+data+"\n"
			      +"Umidità prevista: "+ df.format(umiditaPrevista)+"\n"
			      +"Le previsioni del giorno non erano attendibili con un margine superiore del "+ valore+"% ( "+ df.format(percentuale) +"%)" + "\n";
			     }
			risultato += "\n";
			
		}
	}
	
	/**
	 *Overriding del metodo toString per restituire le statistiche giornaliere relative alla temperatura
	 */
	public String toString() { return risultato; }
			
	}


	
	

  