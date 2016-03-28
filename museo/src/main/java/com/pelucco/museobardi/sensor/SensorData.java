package com.pelucco.museobardi.sensor;

public class SensorData {

	private float temperature;
	private long timestamp;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(humidity);
		result = prime * result + Float.floatToIntBits(temperature);
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + visitors;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SensorData other = (SensorData) obj;
		if (Float.floatToIntBits(humidity) != Float.floatToIntBits(other.humidity))
			return false;
		if (Float.floatToIntBits(temperature) != Float.floatToIntBits(other.temperature))
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (visitors != other.visitors)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SensorData [temperature=" + temperature + ", timestamp=" + timestamp + ", humidity=" + humidity + ", visitors=" + visitors + "]";
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public int getVisitors() {
		return visitors;
	}

	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}

	private float humidity;
	private int visitors;
}
