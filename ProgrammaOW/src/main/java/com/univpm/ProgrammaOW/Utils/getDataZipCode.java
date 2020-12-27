package com.univpm.ProgrammaOW.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class getDataZipCode {
	
	public JSONObject getDataZip(String zipCode, String countryCode) {
		
		//la  stringa di openweather per la chimata con lo zip code viene divisa
		//nelle seguenti due parti e queste vengono unite insieme al zip code e al country code
		
		String parteUrl1= "api.openweathermap.org/data/2.5/weather?zip=";
		String parteUrl2= "&appid=a623b59c7a3e36fdfa0a3fe39a7c0745";
		
		String urlCompleto = parteUrl1 + zipCode + ',' + countryCode + parteUrl2; 
		
		JSONParser parser = new JSONParser();
		 URL oracle = null;
		try {
			oracle = new URL(urlCompleto);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        BufferedReader in = null;
			try {
				in = new BufferedReader(
				new InputStreamReader(oracle.openStream()));
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
			System.out.println(obj);
			JSONObject main =(JSONObject) jsonObject.get("main");
			Number temp  = (Number)  main.get("temp");
			Number tempM  = (Number)  main.get("temp_max");
			Number tempm  = (Number)  main.get("temp_min");
			Number tempF  = (Number)  main.get("feels_like");
			Number hum  = (Number)  main.get("humidity");
			Number dat  = (Number)  jsonObject.get("dt");
			String nom   = (String)  jsonObject.get("name");
			
		
			JSONObject finale = new JSONObject();
			finale.put("Città", nom);
			finale.put("Data", dat);
			finale.put("Temperatura", temp);
			finale.put("Temperatura percepita", tempF);
			finale.put("Temperatura massima", tempM);
			finale.put("Temperatura minima", tempm);
			finale.put("Umidità", hum);
			
			return finale;
		
	}

}
