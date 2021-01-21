package com.univpm.ProgrammaOW.Filters;

import java.util.Vector;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import com.univpm.ProgrammaOW.Utils.getForecast;


/**
 * @author Endri Sefa
 * @author Micol Zazzarini
 */
public class getWeeklyForecast {
	
	/**
	 * Oggetto getforecast che ci serve per prendere i dati relativi alle previsioni
	 */
	getForecast getForecast = new getForecast();
	
	
	
	
	
	
	/**
	 * JSONObject dove salveremo il meteo corrente
	 */
	private  JSONObject dailyWeather;
	/**
	 *  Vettore di JSONObject dove salveremo le previsioni per la settimana seguente
	 */
	private  Vector<JSONObject> forecastW = new Vector<JSONObject>();
	
	
	
	
	
	//primo costruttore, prende come parametro una stringa con il nome di una città e ci inizializza i metodi delle classi di utils
	/**
	 * Costruttore della classe delle previsioni per la settimana seguente
	 * @param parametro1 JSONObject contenente il meteo corrente
	 */
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
	     
	    	
	     
	     /**
	     *  Metodo  get della classe che ci restituisce il meteo corrente 
	     * @return dailyWeather JSOObject contenente il meteo corrente 
	     */
	    public JSONObject getDailyWeather() {return dailyWeather;}
	     
	     /**Metodo get della classe che ci restituisce le previsioni della settimana seguente
	     * @return forecastW Vettore di JSONObject contenente le previsioni della prossima settimana
	     */
	    public Vector<JSONObject> getForecast() { return this.forecastW;}
	     
}
