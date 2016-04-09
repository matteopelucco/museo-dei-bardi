package museobardi.data;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Classe Java responsabile dell'archiviazione dei dati dei sensori
 * 
 * @author paolo
 *
 */
public class ArchivioRilevazioni {

	final static Logger log = Logger.getLogger(ArchivioRilevazioni.class);

	private static final ArchivioRilevazioni INSTANCE = new ArchivioRilevazioni();
	private Map<Integer, List<Rilevamento>> archivioInMemoria = new LinkedHashMap<Integer, List<Rilevamento>>();

	private static final long SOGLIA_SENSORE_ATTIVO = 5000;

	public static ArchivioRilevazioni getInstance() {
		return INSTANCE;
	}

	private ArchivioRilevazioni() {
	}

	/**
	 * Funzione che memorizza i dati relativi ad una rilevazione di un sensore. Esegue una conversione in numeri per verificare l'esattezza del dato.
	 * 
	 * @param codiceSensore
	 * @param visitatori
	 * @param temperatura
	 * @param umidita
	 * @param istante
	 */
	public void memorizzaRilevamento(String codiceSensore, String visitatori, String temperatura, String umidita, String istante) {

		log.debug("MEMORIZZA RILEVAMENTO [sensore = " + codiceSensore + "]: v:" + visitatori + "; t:" + temperatura + "; h:" + umidita);

		try {
			int codiceSensoreI = Integer.parseInt(codiceSensore);
			float umiditaF = Float.parseFloat(umidita);
			float temperaturaF = Float.parseFloat(temperatura);
			int visitatoriI = Integer.parseInt(visitatori);
			long istanteL = Long.parseLong(istante);

			Rilevamento rilevamento = new Rilevamento();
			rilevamento.setUmidita(umiditaF);
			rilevamento.setTemperatura(temperaturaF);
			rilevamento.setVisitatori(visitatoriI);
			rilevamento.setIstante(istanteL);

			log.debug("STO MEMORIZZANDO IL RILEVAMENTO " + rilevamento.toString() + " RELATIVA AL SENSORE " + codiceSensoreI);

			if (!archivioInMemoria.containsKey(codiceSensoreI)) {
				archivioInMemoria.put(codiceSensoreI, new LinkedList<Rilevamento>());
			}

			archivioInMemoria.get(codiceSensoreI).add(rilevamento);

		} catch (NumberFormatException e) {
			log.error("NumberFormatException caught: " + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Funzione cre restituisce l'elenco dei sensori che hanno inviato almeno un dato
	 * 
	 * @return
	 */
	public List<Integer> elencoSensori() {

		List<Integer> elencoSensori = new LinkedList<Integer>();
		for (Integer i : archivioInMemoria.keySet()) {
			elencoSensori.add(i);
		}

		return elencoSensori;
	}

	/**
	 * Funzione che restituisce l'elenco delle rilevazioni di un sensore
	 * 
	 * @param codiceSensore
	 * @return
	 */
	public List<Rilevamento> rilevazioniDelSensore(Integer codiceSensore) {
		return archivioInMemoria.containsKey(codiceSensore) ? archivioInMemoria.get(codiceSensore) : Collections.EMPTY_LIST;
	}

	/**
	 * Funzione che fornisce l'elenco delle ultime rilevazioni, di tutti i sensori attualmente in memoria
	 * 
	 * @return
	 */
	public Map<Integer, Rilevamento> ultimeRilevazioni() {
		final Map<Integer, Rilevamento> ultimeRilevazioni = new LinkedHashMap<Integer, Rilevamento>();
		for (Integer i : archivioInMemoria.keySet()) {
			List<Rilevamento> storicoRilevazioni = archivioInMemoria.get(i);
			Rilevamento rilevazione = storicoRilevazioni.get(storicoRilevazioni.size() - 1);
			if (storicoRilevazioni.size() > 2) {
				long ora = Calendar.getInstance().getTimeInMillis();

				// if sensor is not recent, skip this, it is old
				if (ora - rilevazione.getIstante() > SOGLIA_SENSORE_ATTIVO) {
					continue;
				}
			}

			ultimeRilevazioni.put(i, rilevazione);
		}
		return ultimeRilevazioni;

	}

}
