package museobardi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import museobardi.data.SensorData;
import museobardi.data.SensorStorage;

public class InvioDati extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5511081859482625381L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final Map<Integer, SensorData> data = SensorStorage.getInstance().status();

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		StringBuffer sb = new StringBuffer();
		sb.append("[");

		int count = 0;
		for (Integer i : data.keySet()) {
			SensorData sensorData = data.get(i);
			if (count != 0) {
				sb.append(",");
			}
			sb.append("{");
			sb.append("\"sensor\": \"" + i + "\",");

			sb.append("\"visitors\": \"" + sensorData.getVisitors() + "\",");
			sb.append("\"temperature\": \"" + String.format("%.1f", sensorData.getTemperature()) + "\",");
			sb.append("\"humidity\": \"" + String.format("%.1f", sensorData.getHumidity()) + "\"");

			sb.append("}");

			count++;
		}
		sb.append("]");

		out.print(sb.toString());
		out.flush();

	}

}
