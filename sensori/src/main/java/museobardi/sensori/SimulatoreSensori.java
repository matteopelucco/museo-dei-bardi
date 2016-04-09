package museobardi.sensori;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

/**
 * Classe Java il cui scopo è quello di simulare l'invio dei dati da parte di 10 sensori ambientali
 * 
 * @author matteo
 *
 */
public class SimulatoreSensori {

	final static Logger log = Logger.getLogger(SimulatoreSensori.class);

	public static void main(String[] args) {
		try {

			// vengono impostati dei limiti per evitare che il generatore dei numeri casuali dia risultati non verosimili
			int[] maxVisitors = new int[] { 0, 15, 21, 16, 18, 45, 13, 19, 18, 13 };
			float[] maxHumidity = new float[] { 150F, 150F, 150F, 150F, 150F, 150F, 150F, 150F, 150F, 150F };
			float[] maxTemperature = new float[] { 45F, 45F, 45F, 45F, 45F, 45F, 45F, 45F, 45F, 45F };

			int[] minVisitors = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			float[] minHumidity = new float[] { 10F, 15F, 15F, 15F, 15F, 15F, 15F, 15F, 15F, 15F, };
			float[] minTemperature = new float[] { 0F, 12F, 12F, 12F, 12F, 12F, 12F, 12F, 12F, 12F };

			int[] visitatori = new int[10];
			float[] umidita = new float[10];
			float[] temperatura = new float[10];

			// viene impostata la temperatura esterna di partenza (sensore 0)
			visitatori[0] = 0;
			umidita[0] = 63F;
			temperatura[0] = 16.1F;

			sendData(0, visitatori[0], temperatura[0], umidita[0], Calendar.getInstance().getTimeInMillis());
			Thread.sleep(1000 + RandomUtils.nextInt(2000));

			// vengono impostate le temerature interne di partenza (sensori 1-9)
			List<Integer> sensorInitOrder = new LinkedList<Integer>();
			for (int i = 1; i < 10; i++) {
				visitatori[i] = 0;
				umidita[i] = 55F;
				temperatura[i] = 18.1F;

				sensorInitOrder.add(i);
			}

			// simulazione di accensione "random" dei sensori (altrimenti sarebbe sequenziale)
			Collections.shuffle(sensorInitOrder);

			// inizializzazione: viene mandato il primo dato per ogni sensore
			for (int i : sensorInitOrder) {
				sendData(i, visitatori[i], temperatura[i], umidita[i], Calendar.getInstance().getTimeInMillis());

				// tra un invio e l'altro, un po' di pausa, effetto "show" dell'interfaccia web che si accende piano piano
				Thread.sleep(1000 + RandomUtils.nextInt(2000));
			}

			Thread.sleep(1000);

			// contatore di rilevazioni
			int count = 0;

			// ciclo infinito per l'invio continuo delle rilevazioni
			boolean continua = true;
			while (continua) {
				count++;

				// vengono create e inviate le rilevazioni di tutti i sensori interni (1-9)
				for (int i = 1; i < 10; i++) {

					// simulazione perdita segnale sensore 3
					if (i == 3 && count > 10 && count < 20) {
						continue;
					}

					// simulazione di perdita segnale sensore 5
					if (i == 5 && count > 20 && count < 30) {
						continue;
					}

					// simulazione di perdita segnale sensore 2
					if (i == 2 && count > 30 && count < 40) {
						continue;
					}

					// per ogni valore de sensore i-esimo, ne viene calcolata una ipotetica variazione
					visitatori[i] = variazione(visitatori[i], minVisitors[i], maxVisitors[i]);
					umidita[i] = variazione(umidita[i], minHumidity[i], maxHumidity[i]);
					temperatura[i] = variazione(temperatura[i], minTemperature[i], maxTemperature[i]);

					// istante della rilevazione
					long time = Calendar.getInstance().getTimeInMillis();

					// invio della rilevazione
					sendData(i, visitatori[i], temperatura[i], umidita[i], time);

					// pausa tra una comunicazione e l'altra
					Thread.sleep(100);

				}

				// tra un set di rilevazione e l'altro, pausa di 1 secondo, per permettere all'interfaccia di sincronizzarsi
				Thread.sleep(1000);

			}

		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	/**
	 * Questa funzione effettua una variazione (in positivo o in negativo, casualmente) di un valore e ne limita il suo valore finale tra un massimo ed un minimo Per numeri interi
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	private static float variazione(float value, float min, float max) {

		float inc = 0.1F;
		if (RandomUtils.nextBoolean()) {
			inc = inc * RandomUtils.nextInt(2);
		} else {
			inc = -inc * RandomUtils.nextInt(2);
		}

		float newValue = value + inc;

		if (newValue < min) {
			newValue = min;
		} else if (newValue > max) {
			newValue = max;
		}

		return newValue;
	}

	/**
	 * Questa funzione effettua una variazione (in positivo o in negativo, casualmente) di un valore e ne limita il suo valore finale tra un massimo ed un minimo Per numeri con decimali
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	private static int variazione(int value, int min, int max) {
		int inc = 1;
		if (RandomUtils.nextBoolean()) {
			inc = inc * RandomUtils.nextInt(3);
		} else {
			inc = -inc * RandomUtils.nextInt(2);
		}
		int newValue = value + inc;

		if (newValue < min) {
			newValue = min;
		} else if (newValue > max) {
			newValue = max;
		}

		return newValue;
	}

	/**
	 * Invio del dato al server dei sensori
	 * 
	 * @param codiceSensore
	 * @param visitatori
	 * @param temperatura
	 * @param umidita
	 * @param istante
	 */
	private static void sendData(int codiceSensore, int visitatori, float temperatura, float umidita, long istante) {

		log.info("SENDING DATA: sensor=" + codiceSensore + "; visitors=" + visitatori + "; temperature=" + temperatura + "; humidity=" + umidita + "; timestamp=" + istante);

		try {

			// costruzione dell'URL di chiamata
			String url = "http://localhost:8080/museo/servlets/ricezione";

			// accodamento dei parametri
			url = url + "?sensore=" + codiceSensore;
			url = url + "&visitatori=" + visitatori;
			url = url + "&temperatura=" + temperatura;
			url = url + "&umidita=" + umidita;
			url = url + "&istante=" + istante;

			// creazione del client HTTP (micro-browser) per poter poi inviare la richiesta
			HttpClient client = HttpClientBuilder.create().build();

			// creazione della richiesta
			HttpGet request = new HttpGet(url);

			// invio della richiesta al server
			HttpResponse response = client.execute(request);

		} catch (Throwable t) {
			log.error("ERRORS WHILE SENDING DATA: " + t.getMessage());
		}

	}
}
