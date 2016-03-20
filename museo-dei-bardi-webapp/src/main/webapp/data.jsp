<%@ page import="com.pelucco.museobardi.sensor.SensorStorage" %>
<%@ page import="com.pelucco.museobardi.sensor.SensorData" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="favicon.ico">

    <title>Museo dei Bardi</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <link href="css/style.css" rel="stylesheet">
    
  </head>
  <body role="document">
  	<div class="container" role="main">
  	
<% 
	SensorStorage storage = SensorStorage.getInstance();
	List<Integer> sensors = storage.getSensors();
	
	for (Integer s : sensors) {
 %>
	<h3>Sensor <%=s %></h3>
	<pre>
	<%
	
		List<SensorData> data = storage.getSensorData(s);
		for (SensorData d : data) {
			
			%>
			Temperatura: <%=d.getTemperature() %>; Umidità: <%=d.getHumidity() %>; Visitatori: <%=d.getVisitors() %>; Timestamp: <%=d.getTimestamp() %>
			<%
			
		}
		
	%>
	</pre>
 
 <%
 
	} // end sensors loop
 %>
 </div>
 
 <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    
    <script src="js/script.js"></script>
 </body>
 </html>