// avvio dell'applicazione
$(function(){
	
	// impostazioni per l'orario in italiano
	moment.locale('it', {
		weekdays : "Domenica_Lunedi_Martedi_Mercoledi_Giovedi_Venerdi_Sabato".split("_"),
	});
	
	// invocazione della funzione di aggiornamento orario
	aggiornaOrario();
	
	// invocazione della funzione di aggiornamento sensori
	aggiornaSensori();
});

// funzione di aggiornamento ora, viene invocata all'avvio
function aggiornaOrario(){
	// viene impostato un timer (ogni secondo)
	setTimeout(function(){
		$(".dati-generici .now").html(moment().format('dddd D MMMM YYYY, HH:mm:ss'));
		aggiornaOrario();
	}, 1000);
}

// funzione di aggiornamento sensori
function aggiornaSensori(){
	
	// viene invocato un timer (ogni secondo)
	setTimeout(function(){
		
		// viene chiamata la servlet di invio dati
		$.ajax({
			url: '/museo/servlets/invio', 		// url relativo alla pagina .html
			type : 'get',						// protocollo GET
			dataType :	'json', 				// formato di invio / ricezione dati
			cache : false, 						// evita di avere risultati non freschi
			error : function (jqXHR, textStatus, error) {
				// in caso di errore, non viene fatto nulla
			},
			complete : function(){
				// sia che la richiesta abbia successo o meno, viene nuovamente invocata la funzione di aggiornamento
				aggiornaSensori();
			}, 
			success : function (elencoSensori) {
				// quando la rchiesta ha successo, vengono aggiornate le caselle relative ai sensori ricevuti
				
				// a priori, non si sa quali sensori sono presenti nel sistema
				var sensoriAggiornati = "-";
				
				// viene aggiornato anche il numero di visitatori totali
				var visitatoriTotali = 0;
				
				// per ognuno dei sensori ricevuti, si va ad aggiornare la casella relativa
				$(elencoSensori).each(function(index, item){
					
					// codice sensore
					var codiceSensore = item.sensore;
					if (codiceSensore != 0){
						visitatoriTotali = visitatoriTotali + parseInt(item.visitatori);
					}
					
					sensoriAggiornati = sensoriAggiornati + codiceSensore + "-";
					if ($("#stanza-" + codiceSensore).length > 0) {

						// cambiando classe da off --> on verrà applicato uno stile CSS diverso
						$("#stanza-" + codiceSensore).removeClass("off").addClass("on");
						
						$("#stanza-" + codiceSensore + " .sensore").html("sensore rilevato");
						$("#stanza-" + codiceSensore + " .visitatori").html(item.visitatori);
						$("#stanza-" + codiceSensore + " .umidita").html(item.umidita + "&#37;");
						$("#stanza-" + codiceSensore + " .temperatura").html(item.temperatura + "  &#8451;");
					}
					
					
				});
				
				// viene inserito il numero di visitatori totale
				if ($("#stanza-0").length > 0) {
					$("#stanza-0 .visitatori").html(visitatoriTotali);
				}
				
				// è possibile che alla richiesta 20 venga ricevuto qualche sensore in meno di quelli ricevuti alla richiesta 19 (es: un sensore non funziona, ha perso connettività di rete..)
				// in questo modo, lo segnaliamo visivamente, resettando i valori della stanza e facendo cambiare colore
				for (var i = 0; i < 10; i++){
					if (sensoriAggiornati.indexOf("-" + i + "-") < 0){
						if ($("#stanza-" + i).length > 0) {

							// la stanza è di nuovo spenta, come all'inizio
							$("#stanza-" + i).removeClass("on").addClass("off");
							
							$("#stanza-" + i + " .sensore").html("sensore non rilevato");
							$("#stanza-" + i + " .visitatori").html("-");
							$("#stanza-" + i + " .umidita").html("-");
							$("#stanza-" + i + " .temperatura").html("-");
						}	
					}
				}
			}
		});
		
	}, 1000);
}