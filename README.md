# Museo dei Bardi

In una notte d'inverno, un custode di un museo era stufo di andare ogni 10 minuti a controllare in ogni stanza quale fosse la temperatura e l'umidità.. ah, se ci fosse stato un sistema automatico per poter leggere questi dati dalla propria postazione, invece che.. 

..e così pensò di chiedere ad un suo amico informatico, smanettone, se fosse possibile realizzare a basso costo un sistema del genere. 

Questo progetto nasce per dimostrare quanto sia semplice risolvere problemi apparentemente complicati.
Si compone di due parti: 
- museo: applicazione web Java EE (.war), contenente la logica delle stanze, un paio di endpoint (servlet) d'ausilo e una pagina web per mostrare i dati in un browser
- sensori: client Java che permette la simulazione dell'invio dati di 9 sensori (9 sono le stanze) + uno ambientale (l'esterno del museo). 

## Installazione

1) Verificare l'applicazione e generare un .war con 
- mvn clean

## Utilizzo

1) Avviare il servlet container (es: Jetty) con
- mvn jetty:run

NB: l'applicazione è stata anche testata in Eclipse con Tomcat. 


2) Aprire il proprio browser all'indirizzo
- http://localhost:8080/museo

3) Avviare il simulatore dei sensori delle stanze (SimulatoreSensori.java)

## Contributing

1. Fork it!
2. Crea il tuo branch: `git checkout -b my-new-feature`
3. Esegui i tuoi commit: `git commit -am 'Add some feature'`
4. Push versio il branch: `git push origin my-new-feature`
5. Chiedi una pull request :D

## Un po' di storia

2016-04-29: readme.md update
2016-04-24: minor update, altri commenti
2016-04-10: commenti al codice in tutte le sue parti, rinominazione classi in italiano
2016-04-06: rimozione complessità inutili
2016-03-25: completamento prima versione
2016-03-21: inizio dello sviluppo

## Credits

Nulla in particolare

## License

Codice liberamente utilizzabile, utile per capire come muovere i primi passi con una web application Java EE.