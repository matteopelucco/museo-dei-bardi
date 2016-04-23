package museobardi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import museobardi.data.ArchivioRilevazioni;
import museobardi.data.Rilevamento;

/**
 * Servlet utilizzata dalle pagine HTML per richiedere, tramite Javascript, gli ultimi dati memorizzati
 * 
 * @author paolo
 *
 */
public class LetturaDati extends HttpServlet {

	private static final long serialVersionUID = 5511081859482625381L;

	/**
	 * Metodo principale, GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final Map<Integer, Rilevamento> ultimeRilevazioni = ArchivioRilevazioni.getInstance().ultimeRilevazioni();

		// si utilizza JSON come formato di interscambio dati
		response.setContentType("application/json");

		// viene progressivamente creata la stringa di risposta, un array di rilevazioni
		// [{"sensor": "1", ...}, {"sensor": "2", ...}, ...]
		StringBuffer sb = new StringBuffer();

		// apertura array
		sb.append("[");

		int count = 0;
		for (Integer i : ultimeRilevazioni.keySet()) {
			Rilevamento rilevamento = ultimeRilevazioni.get(i);
			if (count != 0) {
				sb.append(",");
			}
			// oggetto "rilevazione"
			sb.append("{");
			sb.append("\"sensore\": \"" + i + "\",");

			sb.append("\"visitatori\": \"" + rilevamento.getVisitatori() + "\",");
			sb.append("\"temperatura\": \"" + String.format("%.1f", rilevamento.getTemperatura()) + "\",");
			sb.append("\"umidita\": \"" + String.format("%.1f", rilevamento.getUmidita()) + "\"");

			sb.append("}");

			count++;
		}

		// chiusura array
		sb.append("]");

		// viene scritta la stringa di risposta nel canale di OUT (PrintWriter)
		PrintWriter out = response.getWriter();
		out.print(sb.toString());
		out.flush();

	}

}
