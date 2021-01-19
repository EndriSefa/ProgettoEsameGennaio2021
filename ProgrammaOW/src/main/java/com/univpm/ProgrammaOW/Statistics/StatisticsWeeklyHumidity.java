package com.univpm.ProgrammaOW.Statistics;

import java.math.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Vector;

import org.json.simple.JSONObject;

import com.univpm.ProgrammaOW.Filters.getWeeklyForecast;
import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;

public class StatisticsWeeklyHumidity {
	
	
	private double umiditaReale;
	private String risultato;
	private double valore;
	
	public StatisticsWeeklyHumidity(String cityName,double precision) {
		
		this.valore = precision;
		
		getDataCity getDataCity = new getDataCity(cityName);
		
		getWeeklyForecast getWeeklyForecast = new getWeeklyForecast(getDataCity.getMeteo());
		
        JSONObject dailyWeather = getWeeklyForecast.getDailyWeather();
		
		Vector<JSONObject> previsioni = getWeeklyForecast.getForecast();
		
		this.umiditaReale = (Long) dailyWeather.get("Umidita");
		String data_oggi = (String) dailyWeather.get("Data");
		
		risultato = "Data di oggi: " + data_oggi + "\n";
		
		risultato +="Umidità attuale: "+ umiditaReale + "\n"+"\n";
		
		//contatore per i giorni delle previsioni
		int i=1
				;
		for(JSONObject o : previsioni) {
			
			DecimalFormat df = new DecimalFormat("#.00");
			
			
			LocalDate data = LocalDate.now( ZoneId.of( "Europe/Rome" ) ).minusDays( i );
			i++;
	        double umiditaPrevista = (Long)o.get("Umidita");
			double percentuale = 100*(Math.abs(umiditaReale-umiditaPrevista))/umiditaReale;
			if(percentuale <= precision) { 
			                              risultato+= "Giorno in cui è stata fatta la previsione : "+data+"\n"
			                                       + "Umidità prevista: "+ umiditaPrevista+"\n"
			                                       +"Le previsioni del giorno erano attendibili con un margine inferiore del "+ valore+"% ( "+ df.format(percentuale) +"%)" + "\n";}
			else {
			      risultato+="Giorno: "+data+"\n"
			      +"Umidità prevista: "+ umiditaPrevista+"\n"
			      +"Le previsioni del giorno non erano attendibili con un margine superiore del "+ valore+"% ( "+ df.format(percentuale) +"%)" + "\n";
			     }
			risultato += "\n";
			
		}
		
		
		
		
		
	}
	
	public StatisticsWeeklyHumidity(String ZipCode, String CountryCode,double precision) {
		
		this.valore = precision;
        getDataZipCode getDataZipCode = new getDataZipCode(ZipCode,CountryCode);
		
		getWeeklyForecast getWeeklyForecast = new getWeeklyForecast(getDataZipCode.getMeteo());
		
        JSONObject dailyWeather = getWeeklyForecast.getDailyWeather();
		
		Vector<JSONObject> previsioni = getWeeklyForecast.getForecast();
		
		this.umiditaReale = (double) dailyWeather.get("Umidita");
		
		String data_oggi = (String) dailyWeather.get("Data");
		
		risultato += "Data di oggi: " + data_oggi;
		
		risultato +="Umidità attuale: "+ umiditaReale + "\n";
		
		int cont = 0;
		for( JSONObject o : previsioni ) {
			
			DecimalFormat df = new DecimalFormat("#.00");
			
			int i=1;
			LocalDate data = LocalDate.now( ZoneId.of( "Europe/Rome" ) ).minusDays( i );
	        double umiditaPrevista = (double)o.get("Umidita");
			double percentuale = 100*(Math.abs(umiditaReale-umiditaPrevista))/umiditaReale;
			if(percentuale <= precision) { 
			                              risultato+= "Giorno della previsione di oggi: "+data+"\n"
			                                       + "Umidità prevista: "+ umiditaPrevista+"\n"
			                                       +"Le previsioni del giorno erano attendibili con un margine inferiore del "+ valore +"% (" + df.format(percentuale) + ")\n";}
			else {
			      risultato+="Giorno: "+data+"\n"
			      +"Umidità prevista: "+ umiditaPrevista+"\n"
			      +"Le previsioni del giorno non erano attendibili con un margine superiore del "+ valore+"% (" + df.format(percentuale) + ")\n";
			     }
			i++;
		}
	}
	
	public String toString() { return risultato; }
			
	}


	
	

  