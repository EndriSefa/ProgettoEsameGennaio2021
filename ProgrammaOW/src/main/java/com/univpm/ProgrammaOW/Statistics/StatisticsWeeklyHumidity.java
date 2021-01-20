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

public class StatisticsWeeklyHumidity {
	
	
	private double umiditaReale;
	private String risultato;
	private double valore;
	
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
	
	public String toString() { return risultato; }
			
	}


	
	

  