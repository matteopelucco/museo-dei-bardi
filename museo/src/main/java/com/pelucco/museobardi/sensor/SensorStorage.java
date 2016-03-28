package com.pelucco.museobardi.sensor;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class SensorStorage {

	final static Logger log = Logger.getLogger(SensorStorage.class);

	private static final SensorStorage INSTANCE = new SensorStorage();
	private Map<Integer, List<SensorData>> sensorsData = new LinkedHashMap<Integer, List<SensorData>>();

	private static final long SENSOR_THRESHOLD_IN_MILLIS = 5000;

	public static SensorStorage getInstance() {
		return INSTANCE;
	}

	private SensorStorage() {

	}

	public void store(String sensor, String visitors, String temperature, String humidity, String timestamp) {

		log.debug("STORE [sensor = " + sensor + "]: v:" + visitors + "; t:" + temperature + "; h:" + humidity);

		try {
			int sensorI = Integer.parseInt(sensor);
			float humidityF = Float.parseFloat(humidity);
			float temperatureF = Float.parseFloat(temperature);
			int visitorsI = Integer.parseInt(visitors);
			long timestampL = Long.parseLong(timestamp);

			SensorData data = new SensorData();
			data.setHumidity(humidityF);
			data.setTemperature(temperatureF);
			data.setVisitors(visitorsI);
			data.setTimestamp(timestampL);

			log.debug("STORING " + data.toString() + " GIVEN BY SENSOR " + sensorI);

			if (!sensorsData.containsKey(sensorI)) {
				sensorsData.put(sensorI, new LinkedList<SensorData>());
			}

			sensorsData.get(sensorI).add(data);

		} catch (NumberFormatException e) {
			log.error("NumberFormatException caught: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public List<Integer> getSensors() {

		List<Integer> sensors = new LinkedList<Integer>();
		for (Integer i : sensorsData.keySet()) {
			sensors.add(i);
		}

		return sensors;
	}

	public List<SensorData> getSensorData(Integer sensor) {
		return sensorsData.containsKey(sensor) ? sensorsData.get(sensor) : Collections.EMPTY_LIST;
	}

	public Map<Integer, SensorData> status() {
		final Map<Integer, SensorData> status = new LinkedHashMap<Integer, SensorData>();
		for (Integer i : sensorsData.keySet()) {
			List<SensorData> tmp = sensorsData.get(i);
			SensorData sensorData = tmp.get(tmp.size() - 1);
			if (tmp.size() > 2) {
				long now = Calendar.getInstance().getTimeInMillis();

				// if sensor is not recent, skip this, it is old
				if (now - sensorData.getTimestamp() > SENSOR_THRESHOLD_IN_MILLIS) {
					continue;
				}
			}

			status.put(i, sensorData);
		}
		return status;

	}

}