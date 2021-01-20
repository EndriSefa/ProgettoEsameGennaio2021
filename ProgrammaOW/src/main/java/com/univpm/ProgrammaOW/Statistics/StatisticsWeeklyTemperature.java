package com.univpm.ProgrammaOW.Statistics;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Vector;

import org.json.simple.JSONObject;

import com.univpm.ProgrammaOW.Filters.getWeatherPredictions;
import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;

public class StatisticsWeeklyTemperature {
	
	
	private double temperatura_attuale;
	private double valore;
	private double media;
	private double varianza;
	private String risultato;
	private double temperaturaMassima;
	private double temperaturaMinima;
	
	public StatisticsWeeklyTemperature(String nomeCitta, double precision) {
		
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
	
public StatisticsWeeklyTemperature(String zipCode,String countryCode, double precision) {
		
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
	
	
	
	
	public String toString() {return this.risultato;}

}
