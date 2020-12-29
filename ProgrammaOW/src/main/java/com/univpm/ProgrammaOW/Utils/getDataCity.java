package com.univpm.ProgrammaOW.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class getDataCity {
	
	private static JSONObject  meteo = null;
	
	public  getDataCity(String city) {
		
		//la  stringa di openweather per la chimata con lo zip code viene divisa
		//nelle seguenti due parti e queste vengono unite insieme al zip code e al country code
		
		String urlLondra= "http://api.openweathermap.org/data/2.5/weather?q=London&units=metric&appid=a623b59c7a3e36fdfa0a3fe39a7c0745";
		String urlChicago= "http://api.openweathermap.org/data/2.5/weather?q=Chicago&units=metric&appid=a623b59c7a3e36fdfa0a3fe39a7c0745";
		
		 String nomeCitta = null;
		
		 
		 /*
		  * ho aggiunto la stringa nomeCitta perchè OpenWeather restituisce come stringa la città London
		  * mentre a noi ci serve Londra
		  */
		JSONParser parser = new JSONParser();
		 URL url = null;
		try {
		//se la stringa inserita è londra utiliziamo il link di default con città Londra
			if(city.equals("Londra")) {
				url = new URL(urlLondra);
			nomeCitta = "Londra";
			}
		//stessa cosa per Chicago	
			if(city.equals("Chicago")) {
				url = new  URL(urlChicago);
				nomeCitta = "Chicago";
			}
		//in caso non si fosse scelta nessuna città (dobbiamo decidere cosa fare)
			
			
			
			
			
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
			
			
			
			JSONObject main =(JSONObject) jsonObject.get("main");
			Number temp  = (Number)  main.get("temp");
			Number tempM  = (Number)  main.get("temp_max");
			Number tempm  = (Number)  main.get("temp_min");
			Number tempF  = (Number)  main.get("feels_like");
			Number hum  = (Number)  main.get("humidity");
			Long dat  = (Long)  jsonObject.get("dt");
			Long timeZone = (Long) jsonObject.get("timezone");
			
			
			/*
			 * Secondi misurati dall epoch di Unix (1 Gennaio 1970)
			 * la timezone ci serve per calcolare il fuso orario delle città
			 */
			
			Instant instant = Instant.ofEpochSecond((dat + timeZone ));
			
			
			//formato per ottenere dalla ZoneDateTime la data
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			//formato per ottenere dalla ZoneDateTime l'ora
			DateTimeFormatter hour = DateTimeFormatter.ofPattern("HH:mm:ss");
			
			//la zona temporale su cui si svolgeranno i calcoli per determinare data e ora
			//Scegliendo Londra dove il fuso orario è +00:00 non dobbiamo aggiungere o 
			//togliere secondi nella conversione da UNIX a data
			
			ZoneId z = ZoneId.of("Europe/London") ;
			
			
			//Data completa
			ZonedDateTime zdt = instant.atZone( z );
			
			
			
			// stringa relativa alla data
			String dataFinale =(String) dtf.format(zdt);
			//stringa relativa all'ora
			String ora  = (String) hour.format(zdt);
			
		
			JSONObject finale = new JSONObject();
			finale.put("Città", nomeCitta);
			finale.put("Data", dataFinale);
			finale.put("Ora locale", ora);
			finale.put("Temperatura", temp);
			finale.put("Temperatura percepita", tempF);
			finale.put("Temperatura massima", tempM);
			finale.put("Temperatura minima", tempm);
			finale.put("Umidità", hum);
			
			meteo = finale;
		
	}
	
	public JSONObject getMeteo() {
		return meteo;
	}
}
