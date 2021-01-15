package com.univpm.ProgrammaOW.Statistics;

import java.math.*;

import java.util.Vector;

import org.json.simple.JSONObject;

import com.univpm.ProgrammaOW.Filters.getWeeklyForecast;
import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;

public class StatisticsWeeklyHumidity {
	
	boolean Precisione;
	double umiditaReale;
	String risultato;
	
	public StatisticsWeeklyHumidity(String cityName,double precision) {
		
		getDataCity getDataCity = new getDataCity(cityName);
		
		getWeeklyForecast getWeeklyForecast = new getWeeklyForecast(getDataCity.getMeteo());
		
        JSONObject dailyWeather = getWeeklyForecast.getDailyWeather();
		
		Vector<JSONObject> previsioni = getWeeklyForecast.getForecast();
		
		this.umiditaReale = (double) dailyWeather.get("Umidita");
		
		System.out.println("Umidità attuale: "+ umiditaReale);
		for(JSONObject o : previsioni) {
			String data = (String)o.get("data");
	        double umiditaPrevista = (double)o.get("Umidita");
			double percentuale = 100*(Math.abs(umiditaReale-umiditaPrevista))/umiditaReale;
			if(percentuale <= precision) { Precisione = true;
			                              risultato+= "Giorno: "+data+"\n"
			                                       + "Umidità prevista: "+ umiditaPrevista+"\n"
			                                       +"Le previsioni del giorno erano attendibili";}
			else {Precisione = false;
			      risultato+="Giorno: "+data+"\n"
			      +"Umidità prevista: "+ umiditaPrevista+"\n"
			      +"Le previsioni del giorno non erano attendibili";
			     }
		}
		
		
		
		
		
	}
	
	public StatisticsWeeklyHumidity(String ZipCode, String CountryCode,double precision) {
        getDataZipCode getDataZipCode = new getDataZipCode(ZipCode,CountryCode);
		
		getWeeklyForecast getWeeklyForecast = new getWeeklyForecast(getDataZipCode.getMeteo());
		
        JSONObject dailyWeather = getWeeklyForecast.getDailyWeather();
		
		Vector<JSONObject> previsioni = getWeeklyForecast.getForecast();
		
		this.umiditaReale = (double) dailyWeather.get("Umidita");
		
		int cont = 0;
		for( JSONObject o : previsioni ) {
			
			String data = (String)o.get("data");
	        double umiditaPrevista = (double)o.get("Umidita");
			double percentuale = 100*(Math.abs(umiditaReale-umiditaPrevista))/umiditaReale;
			if(percentuale <= precision) { Precisione = true;
			                              risultato+= "Giorno: "+data+"\n"
			                                       + "Umidità prevista: "+ umiditaPrevista+"\n"
			                                       +"Le previsioni del giorno erano attendibili";}
			else {Precisione = false;
			      risultato+="Giorno: "+data+"\n"
			      +"Umidità prevista: "+ umiditaPrevista+"\n"
			      +"Le previsioni del giorno non erano attendibili";
			     }
		}
	}
	
	public String toString() { return risultato; }
			
	}


	
	

  