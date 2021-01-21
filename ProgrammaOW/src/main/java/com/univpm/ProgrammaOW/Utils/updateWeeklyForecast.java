package com.univpm.ProgrammaOW.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**Classe contenente un metodo il quale quando viene eseguito 
 * aggiorna i dati relativi alle previsoni e li mette sul file "appoggio.txt" 
 * @author Endri Sefa
 * @author Micol Zazzarini
 */
public class updateWeeklyForecast {
	/**
	 * Metodo che aggiorna i dati delle previsioni e li salva nel file "appoggio.txt"
	 * * I dati vengono  filtrati dalla API di OpenWeather, i JSONObject delle previsioni
	 * vengono presi singolarmente , filtrati e poi messi nel JSONArray.
	 */
	public static void update() {
		
		String[] urlCity = {"https://api.openweathermap.org/data/2.5/onecall?lat=51.5085&lon=-0.1257&exclude=current,minutely,hourly&units=metric&appid=a623b59c7a3e36fdfa0a3fe39a7c0745&="
				,"https://api.openweathermap.org/data/2.5/onecall?lat=33.441792&lon=-94.037689&exclude=current,minutely,hourly&units=metric&appid=a623b59c7a3e36fdfa0a3fe39a7c0745"
				,"https://api.openweathermap.org/data/2.5/onecall?lat=43.5982&lon=13.5101&exclude=current,minutely,hourly&units=metric&appid=a623b59c7a3e36fdfa0a3fe39a7c0745&=",
				"https://api.openweathermap.org/data/2.5/onecall?lat=38.183&lon=15.566&exclude=current,minutely,hourly&units=metric&appid=a623b59c7a3e36fdfa0a3fe39a7c0745&="
				}; 
		
		String[] nomi = {"Londra","Chicago","Ancona","Messina"};
		
		String appoggioPrevisioni = null;
		
		//ho creato un vettore dove metteremo le previsioi finali
		
		Vector<JSONObject> finale  = new Vector<JSONObject>();
		
		for(int i =0; i < urlCity.length ;i++ ) {
			

			JSONParser parser = new JSONParser();
			 URL url = null;
			try {
				
				
				url = new URL(urlCity[i]);
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		        BufferedReader in = null;
				try {
					in = new BufferedReader(
					new InputStreamReader(url.openStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        String line ="";
		        String data = "";
		        try {
					while ( ( line = in.readLine() ) != null ) {
						   data += line;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        

			
				
				Object obj = null;
				try {
					obj = parser.parse(data);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				JSONObject  jsonObject = (JSONObject) obj;
				
				
				//fino a qui è uguale all' altro programma, serve solo a prendere la stringa
				// contentente tutte le informazioni date dall'API
				
				
				


				
				//le previsioni che abbiamo ricvavato dall' API sono un JSONArray
				//quindi per utilizzarle devo prima ricavrmi l'array
				
				JSONArray prew = (JSONArray) jsonObject.get("daily");
				
				
				//poi dall'array ci andaimo a prendere le varie previsioni con un ciclo for
				
				for (int j = 1; j < prew.size(); j++) {
					//iniziamo da 1 così non prendiamo la prima previsone che corrisponde
					//in realtà al meteo di oggi
					
					
					//questo metodo ci permettere di prendere l'oggetto in posizione i dell' array
					//noi sappiamo che quell'oggetto è un object quindi otterrò un object
					JSONObject appoggio = (JSONObject) prew.get((j));
					
					//una volta qui faccio la stessa cosa per il meteo corrente quindi
					//ottengo i dati singoli
					
					JSONObject appTemp =(JSONObject) appoggio.get("temp");
					JSONObject feels =(JSONObject) appoggio.get("feels_like");
					Number temp  = (Number)  appTemp.get("day");
					Number tempM  = (Number)  appTemp.get("max");
					Number tempm  = (Number)  appTemp.get("min");
					Number tempF  = (Number)  feels.get("day");
					Number hum  = (Number)  appoggio.get("humidity");
					//correzione ad un errore nel prelevamento dei dati
					//ora il funzionamento è corretto e i dati si aggiornano automaticamente
					//e vengono prelevati correttamente
					Number dat  = (Number)  appoggio.get("dt");
					String nom   = (String)  jsonObject.get("timezone");
					
					//purtroppo dava dei problemi la conversione in long quindi ho dovuto fare un parsing
					// da Number  a Long
					
					Long dataData = (Long) dat;
					
					Instant instant = Instant.ofEpochSecond(dataData);
					
					
					
					
					
					//questa volta l'API ci formisce la time zone, quindi possiamo sfruttarla per 
					//calcolare l'ora
					
					ZoneId z = ZoneId.of(nom) ;
					ZonedDateTime zdt = instant.atZone( z );
					
					//i primi 10 char corrispondono alla data
					
					String da = zdt.toString().substring(0, 10);
					
					
					
					
					//e infine i dati singoli li inserisco in previsioni
					// che funge da appoggio per poi aggiungerli al vettore
					
					JSONObject previsioni = new JSONObject(); 
					
					previsioni.put("Citta", nomi[i]);
					previsioni.put("Data", da);
					previsioni.put("Temperatura", temp);
					previsioni.put("Temperatura percepita", tempF);
					previsioni.put("Temperatura massima", tempM);
					previsioni.put("Temperatura minima", tempm);
					previsioni.put("Umidita", hum);
					
					finale.add(previsioni);
					
					
					
				}
				
				
			
				
				File file = new File("appoggio.txt");
			
				  FileWriter fileWriter = null;
				    try {
				         fileWriter = new FileWriter(file);
				    } catch (IOException e) {
				        
				        e.printStackTrace();
				    }
				    
				    BufferedWriter bufferedWriter = null;
				    bufferedWriter=new BufferedWriter(fileWriter);
				    
				    
				        try {
				            bufferedWriter.write((finale.toString()));
				        } catch (IOException e) {
				            // TODO Auto-generated catch block
				            e.printStackTrace();
				        }
				         try
				           {
				               if ( bufferedWriter != null)
				               bufferedWriter.close( );
				           }
				           catch ( IOException e) {e.printStackTrace();}
				try {
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
		}
			
			
			
			
		}
		
	}


