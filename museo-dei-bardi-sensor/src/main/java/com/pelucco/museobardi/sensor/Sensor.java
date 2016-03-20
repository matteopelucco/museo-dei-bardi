package com.pelucco.museobardi.sensor;

import java.util.Calendar;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

public class Sensor {

	final static Logger log = Logger.getLogger(Sensor.class);

	public static void main(String[] args) {

		boolean run = true;
		int count = 0;
		while (run) {
			count++;

			String url = "http://localhost:8080/museo-dei-bardi-webapp/servlets/sensor";

			int visitors = 10;
			int sensor = RandomUtils.nextInt(9);
			float humidity = 35F;
			float temperature = 18.4F;
			long timestamp = Calendar.getInstance().getTimeInMillis();

			sendData(sensor, visitors, temperature, humidity, timestamp);

			try {
				if (count > 120) {
					Thread.sleep(1000);
				} else {
					Thread.sleep(50);
				}
			} catch (InterruptedException e) {

				run = false;
				e.printStackTrace();
			}

		}

	}

	private static void sendData(int sensor, int visitors, float temperature, float humidity, long timestamp) {

		log.info("SENDING DATA: sensor=" + sensor + "; visitors=" + visitors + "; temperature=" + temperature + "; humidity=" + humidity + "; timestamp=" + timestamp);

		try {
			String url = "http://localhost:8081/museo-dei-bardi-webapp/servlets/sensor";

			url = url + "?sensor=" + sensor;
			url = url + "&visitors=" + visitors;
			url = url + "&temperature=" + temperature;
			url = url + "&humidity=" + humidity;
			url = url + "&timestamp=" + timestamp;

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);

			// add request header
			HttpResponse response = client.execute(request);

			// System.out.println("Response Code : "
			// + response.getStatusLine().getStatusCode());
			//
			// BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			//
			// StringBuffer result = new StringBuffer();
			// String line = "";
			// while ((line = rd.readLine()) != null) {
			// result.append(line);
			// }
		} catch (Throwable t) {
			log.error("ERRORS WHILE SENDING DATA: " + t.getMessage());
		}

	}
}
