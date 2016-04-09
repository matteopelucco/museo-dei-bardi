package museobardi.data;

/**
 * Classe Java che serve a rappresentare tutti i dati di una singola rilevazione
 * 
 * @author paolo
 *
 */
public class Rilevamento {

	private float temperatura;
	private long istante;
	private float umidita;
	private int visitatori;

	public long getIstante() {
		return istante;
	}

	public void setIstante(long istante) {
		this.istante = istante;
	}

	public float getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(float temperatura) {
		this.temperatura = temperatura;
	}

	public float getUmidita() {
		return umidita;
	}

	public void setUmidita(float umidita) {
		this.umidita = umidita;
	}

	public int getVisitatori() {
		return visitatori;
	}

	public void setVisitatori(int visitatori) {
		this.visitatori = visitatori;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(umidita);
		result = prime * result + Float.floatToIntBits(temperatura);
		result = prime * result + (int) (istante ^ (istante >>> 32));
		result = prime * result + visitatori;
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
		Rilevamento other = (Rilevamento) obj;
		if (Float.floatToIntBits(umidita) != Float.floatToIntBits(other.umidita))
			return false;
		if (Float.floatToIntBits(temperatura) != Float.floatToIntBits(other.temperatura))
			return false;
		if (istante != other.istante)
			return false;
		if (visitatori != other.visitatori)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SensorData [temperature=" + temperatura + ", timestamp=" + istante + ", humidity=" + umidita + ", visitors=" + visitatori + "]";
	}

}
