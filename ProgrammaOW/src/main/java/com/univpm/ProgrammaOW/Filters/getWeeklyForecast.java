package com.univpm.ProgrammaOW.Filters;

import java.util.Vector;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;
import com.univpm.ProgrammaOW.Utils.getForecast;


public class getWeeklyForecast {
	
	getForecast getForecast = new getForecast();
	
	
	
	
	
	
	private  JSONObject dailyWeather;
	private  Vector<JSONObject> forecastW = new Vector<JSONObject>();
	
	
	
	
	
	//primo costruttore, prende come parametro una stringa con il nome di una città e ci inizializza i metodi delle classi di utils
	public getWeeklyForecast( JSONObject parametro1 ) {
		
		
				
		String citta = (String)parametro1.get("Citta");
		
		Vector<JSONObject> appoggio = new Vector<JSONObject>();
		
		JSONArray array = getForecast.getPrevisioni();
		
		
		for(int i = 0; i< array.size(); i++) {
			
		JSONObject elemento = (JSONObject) array.get(i);
		appoggio.add(i, elemento);
		}
		
		
		Vector<JSONObject> previsioni_filtrate_citta= new Vector<JSONObject>();
        
        
         for(int j=0; j<appoggio.size();j++)
		 {
		 JSONObject jsonObject = appoggio.get(j);
		 String nome_citta2 = (String) jsonObject.get("Citta");
				
		 if(nome_citta2.equals(citta) ){
				        previsioni_filtrate_citta.add( appoggio.get(j));
			    		 }
		 }
		 
         
         //vogliamo solo le prime 7 previsioni che essendo fatte giornalmente sono le
         //ultime previsioni (sono le privisioni di questa settimana)
		 for(JSONObject o : previsioni_filtrate_citta) {
			if(this.forecastW.size() != 7) this.forecastW.add(o);
		 }
        
		 
		 dailyWeather = parametro1;
		 
	     }
	     
	    	
	     
	     public JSONObject getDailyWeather() {return dailyWeather;}
	     
	     public Vector<JSONObject> getForecast() { return this.forecastW;}
	     
}
