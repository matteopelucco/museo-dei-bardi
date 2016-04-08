package com.pelucco.museobardi.sensor;

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

public class Sensor {

	final static Logger log = Logger.getLogger(Sensor.class);

	public static void main(String[] args) {
		try {

			int[] maxVisitors = new int[] { 0, 15, 21, 16, 18, 45, 13, 19, 18, 13 };
			float[] maxHumidity = new float[] { 150F, 150F, 150F, 150F, 150F, 150F, 150F, 150F, 150F, 150F };
			float[] maxTemperature = new float[] { 45F, 45F, 45F, 45F, 45F, 45F, 45F, 45F, 45F, 45F };

			int[] minVisitors = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			float[] minHumidity = new float[] { 10F, 15F, 15F, 15F, 15F, 15F, 15F, 15F, 15F, 15F, };
			float[] minTemperature = new float[] { 0F, 12F, 12F, 12F, 12F, 12F, 12F, 12F, 12F, 12F };

			int[] visitors = new int[10];
			float[] humidity = new float[10];
			float[] temperature = new float[10];

			// out
			visitors[0] = 0;
			humidity[0] = 63F;
			temperature[0] = 16.1F;

			sendData(0, visitors[0], temperature[0], humidity[0], Calendar.getInstance().getTimeInMillis());
			Thread.sleep(1000 + RandomUtils.nextInt(2000));

			// in
			List<Integer> sensorInitOrder = new LinkedList<Integer>();
			for (int i = 1; i < 10; i++) {
				visitors[i] = 0;
				humidity[i] = 55F;
				temperature[i] = 18.1F;

				sensorInitOrder.add(i);
			}

			Collections.shuffle(sensorInitOrder);

			for (int i : sensorInitOrder) {
				sendData(i, visitors[i], temperature[i], humidity[i], Calendar.getInstance().getTimeInMillis());
				Thread.sleep(1000 + RandomUtils.nextInt(2000));
			}

			Thread.sleep(3000);

			boolean run = true;
			int count = 0;
			while (run) {
				count++;

				for (int i = 1; i < 10; i++) {

					if (i == 3 && count > 10 && count < 20) {
						continue;
					}

					if (i == 5 && count > 20 && count < 30) {
						continue;
					}

					if (i == 2 && count > 30 && count < 40) {
						continue;
					}

					visitors[i] = delta(visitors[i], minVisitors[i], maxVisitors[i]);

					humidity[i] = delta(humidity[i], minHumidity[i], maxHumidity[i]);
					temperature[i] = delta(temperature[i], minTemperature[i], maxTemperature[i]);
					sendData(i, visitors[i], temperature[i], humidity[i], Calendar.getInstance().getTimeInMillis());
					Thread.sleep(100);

				}

				Thread.sleep(1000);

			}

		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	private static float delta(float value, float min, float max) {

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

	private static int delta(int value, int min, int max) {
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

	private static void sendData(int sensor, int visitors, float temperature, float humidity, long timestamp) {

		log.info("SENDING DATA: sensor=" + sensor + "; visitors=" + visitors + "; temperature=" + temperature + "; humidity=" + humidity + "; timestamp=" + timestamp);

		try {
			String url = "http://localhost:8080/museo/servlets/sensor";

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
