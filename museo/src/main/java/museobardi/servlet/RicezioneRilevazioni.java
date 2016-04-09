package museobardi.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import museobardi.data.ArchivioRilevazioni;

/**
 * Servlet che rimane in ascolto di richieste in arrivo dai sensori. Una volta ricevuta la richiesta, memorizza i dati ricevuti
 * 
 * @author paolo
 *
 */
public class RicezioneRilevazioni extends HttpServlet {

	private static final long serialVersionUID = 570001612067633040L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final String codiceSensore = request.getParameter("sensore");
		final String visitatori = request.getParameter("visitatori");
		final String temperatura = request.getParameter("temperatura");
		final String umidita = request.getParameter("umidita");
		final String istante = request.getParameter("istante");

		ArchivioRilevazioni.getInstance().memorizzaRilevamento(codiceSensore, visitatori, temperatura, umidita, istante);

	}

}
