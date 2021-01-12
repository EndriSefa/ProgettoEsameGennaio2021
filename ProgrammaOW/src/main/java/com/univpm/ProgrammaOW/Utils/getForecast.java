package com.univpm.ProgrammaOW.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class getForecast {
	
	private  JSONArray previsioni;
	
	public getForecast() {
		
		
		String  finalePrevisioni; //stringa dove metteremo le previsioni
		
		FileReader f= null;
        try {
            f =new FileReader("Previsioni.txt");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*
         * quello che facciamo è vedere se la data nel nostro file  cosrripsonde alla data di oggi
         */
 

        BufferedReader b;
        b=new BufferedReader(f);
        
        String MeteoCorrente = "";
        String line = "";
        
        try {
            while((line = b.readLine())!=null) {
                MeteoCorrente += line;
                if (MeteoCorrente.length()==19) break;
                // ci basta leggere questi caratteri per scoprire la data
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        try {
			b.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		
		
		String dataFile = MeteoCorrente.substring(9,19);
		
		//data di oggi
        
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();  
		
		String dataOggi = (String) dtf.format(now);
		
		//se le date sono diverse
		
		if (!(dataFile.equals(dataOggi))) {
		
		//si aggiorna il file di appoggio così da avere i dati sul meteo aggiornati	
		// essendo nello stesso package posso invocare il metodo senza dover importare la classe visto che sia
		//la classe che il metodo sono public
	    updateWeeklyForecast.update();
			
		String  parte1 = MeteoCorrente.substring(0,9);
		//si aggiorna la data
		String parte2 = dataOggi;
		//le nuove previsoni vengono aggiunte per prime così che ci aiuti nel prelevare i dati più aggiornati
		//nei metodi successivi
		String parte3 = MeteoCorrente.substring(19,35);
			
        
       
		String meteo2 = MeteoCorrente.substring(35);
		
		
		
		
		FileReader fi= null;
        try {
            fi =new FileReader("appoggio.txt");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //appoggio

        BufferedReader a;
        a=new BufferedReader(fi);
        
        String meteo = "";
        String lin = "";
        
        try {
            while((lin = a.readLine())!=null) {
                meteo += lin;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
       
        //essendo sotto forma di array rimuoviamo le parentesi quadre
        
        String meteo3 = meteo.substring(1, meteo.length()-1);
        
        File file = new File("Previsioni.txt");
		
		  FileWriter fileWriter = null;
		    try {
		         fileWriter = new FileWriter(file);
		    } catch (IOException e) {
		        
		        e.printStackTrace();
		    }
		    
		    BufferedWriter bufferedWriter = null;
		    bufferedWriter=new BufferedWriter(fileWriter);
		    
		    //uniamo le varie stringhe così da formare un JSONObject
		    //(a virgola ha questo scopo, infatti noi abbiamo già aggiunto i dati e questo ci  aiuta)
		    //(se invece il file fosse  vuoto la virgola ci avrebbe creato dei problemi nelle varie codifiche)
		    
		    finalePrevisioni = parte1 + parte2 + parte3 + meteo3 + ", " + meteo2;
		    
		    
		        try {
		        	bufferedWriter.write(finalePrevisioni);
		        	
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
		else{ // caso in cui il file è già agiornato
			
			
	        try {
	            f =new FileReader("Previsioni.txt");
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	 


	        BufferedReader c;
	        c=new BufferedReader(f);
	        
	        MeteoCorrente = "";
	        line = "";
	        
	        
	        try {
	            while((line = c.readLine())!=null) {
	                MeteoCorrente += line;
	                
	           }
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        finalePrevisioni = MeteoCorrente;
			
		}
		
		
		//prendiamo ora il JSONObject di Previsioni e preleviamo il JSONArry con le previsioni
		
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(finalePrevisioni);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject  jsonObject = (JSONObject) obj;
		
		this.previsioni =(JSONArray) jsonObject.get("previsioni");
		

	}
	
	
	public JSONArray getPrevisioni() {
		return this.previsioni;
	}
}
	
/*
 * la problematica iniziale era aggiornare i dati ogni ora, ma vedendo che spesso i dati risultavano uguali, 
 * infatti tendenzialmente i dati rimangono tali, in quanto previsioni e non vengono aggiornati se non da un giorno 
 * all' altro abbiamo deciso di aggiornare i dati ogni ora
 * 
 * Il controllo avviene tramite la data
 * Per ottimizzare i tempi ci limitiamo a leggere solo la data e non tutto il file che diventa man mano più grande
 * In terimini di efficienza a lungo andare è molto comodo.
 * Inoltre questo controllo viene fatto in automatico ogni volta che l'utente richiede delle previsioni
 * Inoltre vengono aggiornati tutti i dati relativi alle città che vogliamo osservare 
 * 
 * Nel dodicesimo commit all'interno del file "Previsioni.txt" vi sono le previsioni di ieri e di oggi.
 * Collezionando i dati di giorno in giorno potremmo arrivare tra 5 giorni a poter fare delle statistiche  settimanali
 */

