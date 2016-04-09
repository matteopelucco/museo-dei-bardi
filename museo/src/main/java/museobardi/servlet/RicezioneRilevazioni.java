package museobardi.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import museobardi.data.SensorStorage;

public class RicezioneRilevazioni extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 570001612067633040L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final String sensor = request.getParameter("sensor");
		final String visitors = request.getParameter("visitors");
		final String temperature = request.getParameter("temperature");
		final String humidity = request.getParameter("humidity");
		final String timestamp = request.getParameter("timestamp");

		SensorStorage.getInstance().store(sensor, visitors, temperature, humidity, timestamp);

	}

}
