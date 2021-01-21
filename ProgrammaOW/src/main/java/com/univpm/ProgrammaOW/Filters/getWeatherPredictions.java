package com.univpm.ProgrammaOW.Filters;

import java.util.Vector;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.univpm.ProgrammaOW.Utils.getDataCity;
import com.univpm.ProgrammaOW.Utils.getDataZipCode;
import com.univpm.ProgrammaOW.Utils.getForecast;
import com.univpm.ProgrammaOW.Exceptions.NonExistingPredictionDataException;



/**
 * @author Endri Sefa
 * @author Micol Zazzarini
 */

public class getWeatherPredictions {
	
	/** Oggetto getforecast che ci serve per prendere i dati relativi alle previsioni 
	 */
	
	getForecast getForecast = new getForecast();
	
	
	
	
	
	
	/**
	 * JSONObject dove salveremo il meteo corrente
	 */
	
	private  JSONObject dailyWeather;
	/**
	 * Vettore di JSONObject dove salveremo le previsioni passate relative al giorno di oggi
	 */
	
	private  Vector<JSONObject> PredictionsW = new Vector<JSONObject>();
	
	
	
	
	
	//primo costruttore, prende come parametro una stringa con il nome di una città e ci inizializza i metodi delle classi di utils
	/**
	 * Costruttore della classe delle previsioni passate relative al giorno di oggi
	 * @param parametro1 JSONObject contenente il meteo corrente
	 */
	
	public getWeatherPredictions( JSONObject parametro1 ) {
		
		
				
		String citta = (String)parametro1.get("Citta");
		String data_di_oggi = (String) parametro1.get("Data");
		
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
		 String data_previsone = (String) jsonObject.get("Data");
				
		 if(nome_citta2.equals(citta) && data_previsone.equals(data_di_oggi) ){
				        previsioni_filtrate_citta.add( appoggio.get(j));
			    		 }
		 }
		 
         
         //visto che abiamo sia la data che la città prendiamo 
         //tutte le previsioni per fare le statistiche
		 for(JSONObject o : previsioni_filtrate_citta) {
			 this.PredictionsW.add(o);
		 }
        
		 
		 dailyWeather = parametro1;
		 
	     }
	     
	    	
	     
	    
	    /**
	     * Metodo  get della classe che ci restituisce il meteo corrente 
	     * @return dailyWeather JSOObject contenente il meteo corrente 
	     */
	    public JSONObject getDailyWeather() {return dailyWeather;}
	     
	    
	    /**Metodo get della classe che ci restituisce le previsioni passate relative al giorno di oggi
	     * @return PredictionsW Vettore di JSONObject contenente le previsioni passate relative
	     * al giorno di oggi
	     * @throws NonExistingPredictionDataException Eccezione personalizzata nel caso in cui 
	 * mancassero i dati relativi alle previsioni passate (basta che manchi un giorno)
	     */
	    public Vector<JSONObject> getPredictions() throws NonExistingPredictionDataException {
	    	 
	    	 int i = this.PredictionsW.size();
	    	 
	    	 if(this.PredictionsW.size() != 7) throw new NonExistingPredictionDataException();
	    	 else return this.PredictionsW;
	     }
	     
}