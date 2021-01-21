# ProgettoEsameGennaio2021
Repository GitHub per il progetto per la consegna dell' esame di "Programmazione ad Oggetti" di Gennaio 2021
# Progetto Open Weather
## Aspetti generali

L'applicazione è una Rest API che, prelevando dati dal servizio web di Open Weather, ne effettua un’analisi e un filtraggio sulla base delle informazioni richieste dall’utente. In particolare, esso ha la possibilità di effettuare una scelta tra una serie di città le cui informazioni sono predefinite e messe direttamente a disposizione dalla nostra applicazione ( abbiamo scelto a questo scopo Londra e Chicago), o, in alternativa, inserire direttamente lo Zip Code corrispondente alla città desiderata ( in questo caso per verificare il funzionamento è opportuno inserire lo Zip Code e i Country Code di Messina(98121,it) e Ancona(60126,it)). Si ha la possibilità di visualizzare informazioni relative al meteo attuale o relative alle previsioni per i prossimi 7 giorni. Per quanto riguarda in particolare tutti gli aspetti riguardanti temperatura e pressione, abbiamo messo a disposizione delle statistiche giornaliere e settimanali. L’utente sarà in grado di scegliere se visualizzare quelle relative alla temperatura, all’umidità, o visualizzare direttamente tutte quelle disponibili. Nello specifico le statistiche calcolano l’attendibilità delle previsioni effettuate nei giorni precedenti effettuando un confronto di dati, sulla base di una percentuale di precisione desiderata sall’utente ( nel caso in cui non lo richieda la percentuale utilizzata è del 5%, valore idoneo a mettere in evidenza errori significativi nelle previsioni effettuate). In aggiunta nel caso della temperatura mettiamo a disposizione i valori di media e varianza.

## Indicazioni d'uso


Le istruzioni qui riportate prevedono l’utilizzo di Eclipse (IDE predefinito).

Dopo aver scaricato il codice sorgente, cliccare il tasto destro del mouse sul progetto e selezionare la voce Run Us -> SpringBoot Application.

Se l’operazione sarà andata a buon fine, nel prompt dei comandi si potrà verificare l’avvio di un servizio Tomcat in ascolto sulla porta 8080.,rotta da cui effettuare le chiamate al Web Service. 

A questo punto si potrà iniziare ad utilizzare l’applicazione ( si raccomanda l’utilizzo di Postman).

Come già visto nel prompt dei comandi, l’URL di base sarà *http//localhost:8080*, mentre le varie rotte che l’utente potrà inserire per i propri scopi sono specificare nel Controller del programma. 

Esse sono le seguenti:

 Tipo di Richiesta| Rotta | Parametri | Body 
 ----------|--------|----------|---------
 GET| /meteoCorrente| ?nomeCitta|
 POST|/meteoCorrente||JSONObject
 GET|/Previsioni|?nomeCitta|
 POST|/Previsioni||JSONObject
 GET|/Statistiche|?periodo&nomeCitta&tipo&precisione|
 POST|/Statistiche|?periodo&tipo&precisione|JSONObject
 
 Ecco vari esempi dell'esecuzione di alcune chiamate fatte su Postman:

GET| http//localhost:8080/meteoCorrente?nomeCitta=Chicago
 


![Screenshot (4)](https://user-images.githubusercontent.com/75529879/105379357-08fc1300-5c0d-11eb-84df-2c18f30bc9dc.png)

POST|http//localhost:8080/previsioni

![Screenshot (15)](https://user-images.githubusercontent.com/75529879/105380380-1b2a8100-5c0e-11eb-8071-7735d86e2e2c.png)

GET| http//localhost:8080/Statistiche?periodo=giornaliere&tipo=totali&precisione=10&nomeCitta=Chicago

![Screenshot (16)](https://user-images.githubusercontent.com/75529879/105380400-1fef3500-5c0e-11eb-8509-e96a64fb3488.png)



GET|http//localhost:8080/Statistiche?periodo=settimanali&tipo=umidita&precisione=10&nomeCitta=Londra

![Screenshot (24)](https://user-images.githubusercontent.com/75529879/105382377-4b731f00-5c10-11eb-9afb-5f6fe14c1358.png)

GET|http//localhost:8080/Statistiche?periodo=giornaliere&tipo=totali&precisione=10&nomeCitta=Londra

![Screenshot (23)](https://user-images.githubusercontent.com/75529879/105382672-9db44000-5c10-11eb-86e3-f8b2ff8473be.png)
GET|http//localhost:8080/Statistiche?periodo=giornaliere&tipo=totali&precisione=10&nomeCitta=Londra

![Screenshot (21)](https://user-images.githubusercontent.com/75529879/105382935-e4a23580-5c10-11eb-8a24-2e635e6a85d1.png)
POST|http//localhost:8080/Statistiche?periodo=settimanali&tipo=totali&precisione=10

![Screenshot (20)](https://user-images.githubusercontent.com/75529879/105382951-e9ff8000-5c10-11eb-89d4-39b260cf6fb7.png)

GET|http//localhost:8080/Statistiche?periodo=settimanali&tipo=temperatura&precisione=10&nomeCitta=Londra

![Screenshot (26)](https://user-images.githubusercontent.com/75529879/105383618-95103980-5c11-11eb-927d-24d89eaea891.png)

GET|http//localhost:8080/previsioni?citta=Chicago

![Screenshot (11)](https://user-images.githubusercontent.com/75529879/105383601-90e41c00-5c11-11eb-8468-2c206741a2d6.png)


POST|http//localhost:8080/meteoCorrente

![Screenshot (8)](https://user-images.githubusercontent.com/75529879/105383571-89247780-5c11-11eb-9f64-c1dba33917f3.png)

POST|http//localhost:8080/meteoCorrente

![Screenshot (7)](https://user-images.githubusercontent.com/75529879/105383564-86298700-5c11-11eb-99dc-39135001870b.png)
POST|http//localhost:8080/meteoCorrente

![Screenshot (6)](https://user-images.githubusercontent.com/75529879/105383546-832e9680-5c11-11eb-96d4-4d97ec0c920b.png)

GET|http//localhost:8080/meteoCorrente?nomeCitta=Londra

![Screenshot (5)](https://user-images.githubusercontent.com/75529879/105383523-7f027900-5c11-11eb-854d-827cb6c9d471.png)








 
 Dopo aver inserito le rotte desiderate, eventuali parametri e bodies, premere Send.
 
 ### Eccezioni
 Per quanto riguarda i parametri, c’è la possibilità che l’utente inserisca valori non validi per la ricerca, ci riferiamo in particolare a una percentuale non valida( inferiore allo zero o superiore al 100) o a uno Zip Code errato. In questo caso il programma lancerà delle eccezioni(*InvalidZipCodeException* e *InvalidPrecisionException*). Un’ulteriore eccezione verrà lanciata nel momento in cui i dati messi a disposizione non saranno sufficienti per effettuare tutte le previsioni(*NonExistingPredictionDataException*). 

 ![Screenshot (18)](https://user-images.githubusercontent.com/75529879/105383638-9a6d8400-5c11-11eb-9c2a-e3688cf4d492.png)
 
 ![Screenshot (30)](https://user-images.githubusercontent.com/75529879/105383632-980b2a00-5c11-11eb-8b9e-ef5378f74992.png)
 
 ![Screenshot (9)](https://user-images.githubusercontent.com/75529879/105383580-8b86d180-5c11-11eb-916a-42247c06defa.png)
 
 ![Screenshot (17)](https://user-images.githubusercontent.com/75529879/105380428-267dac80-5c0e-11eb-8dcf-8c36df8440ea.png)
 
 
 ### Test
 Ciò che abbiamo testato per la nostra applicazione, è il corretto funzionamento delle eccezioni, con esito positivo come mostrato nell'immagine allegata:
 
 ![Screenshot (3)](https://user-images.githubusercontent.com/75529879/105378551-372d2300-5c0c-11eb-91df-4ec69b14ffe1.png)

 
 
 
 ## Note aggiuntive
 
 L’idea iniziale del nostro progetto era quella di testare la ricerca delle informazioni richieste tramite immissione di Zip Code e Country Code della nostra applicazione, utilizzando quelli delle città di Ancona e Tirana, ma abbiamo riscontrato dei problemi. Nonostante riuscissimo a prelevare dati relativi allo storico di Tirana tramite latitudine e longitudine, questo non era possibile tramite lo Zip Code, per problemi dovuti a Open Weather stesso. La ricerca di dati tramite Zip Code risulta sicura per quanto riguarda città degli Stati Uniti, mentre come confermato nella email allegata non risulta possibile per tutte le città di altri stati, o addirittura per nessuna. Abbiamo dunque sostituito Tirana con Messina
 
 ![Screenshot (2) (17)](https://user-images.githubusercontent.com/75529879/105379066-c33f4a80-5c0c-11eb-8c4f-93e5f006d25a.png)


Inoltre, la problematica iniziale era quella di aggiornare i dati ogni ora, ma in questo modo i dati risultavano spesso uguali, senza la possibilità di assistere a cambiamenti significativi, infatti tendenzialmente  a differenza di poche ora i dati rimangono tali e non vengono aggiornati se non da un giorno all' altro. Abbiamo così deciso di aggiornare i dati ogni giorno.

Il controllo sull'aggiornamento dei dati avviene tramite la data presente sul file "Previsioni.txt". Per ottimizzare i tempi ci limitiamo a leggere solo la data e non tutto il file che cresce con il prelievo dei dati. In terimini di efficienza a lungo andare è molto comodo.Questo controllo viene fatto in automatico ogni volta che l'utente richiede delle previsioni. Inoltre vengono aggiornati tutti i dati relativi alle città che vogliamo osservare.

Nonostante noi monitoriamo l'attività metereologica di Ancona,Messina,Londra e Chicago possiamo tramite Zip Code e Country Code verificare il meteo corrente delle città di cui Open Weather ha i Zip Code funzionanti

![Screenshot (10)](https://user-images.githubusercontent.com/75529879/105383590-8e81c200-5c11-11eb-9902-e96b04cc72ae.png)

### Diagrammi UML



Diagramma dei casi d'uso
![Diagramma dei casi d'uso](https://user-images.githubusercontent.com/75529879/105388913-7f057780-5c17-11eb-8cc6-e9409b240d9f.jpg)
Diagramma delle classi
![Diagrama delle classi](https://user-images.githubusercontent.com/75529879/105388379-de16bc80-5c16-11eb-8d7a-7f764f6f0d45.jpg)
Diagramma delle sequenze
![Screenshot (7)](https://user-images.githubusercontent.com/75529879/105388091-85472400-5c16-11eb-8895-fc249e3d9178.png)
![Screenshot (8)](https://user-images.githubusercontent.com/75529879/105388102-88daab00-5c16-11eb-8601-d20ed3f43a6e.png)
![Screenshot (9)](https://user-images.githubusercontent.com/75529879/105388113-8d06c880-5c16-11eb-94b4-45326334aa32.png)




 
