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
  	
  	<h1>Museo dei Bardi Vicentini</h1>
  	<h2>Sistema di monitoraggio ambientale</h2>
  	
  	<hr/>
  	
  	<% 
  	
  		String sensorParam = request.getParameter("sensor");
  		int sensor = Integer.parseInt(sensorParam);
  		int previousSensor = -1;
  		int nextSensor = -1;
  		if (sensor > 1) {
  			previousSensor = sensor - 1;
  		}
  		if (sensor < 9) {
  			nextSensor = sensor + 1;
  		}
  	
  	%>
	<div class="navigation row">
		<div class="col-xs-2"><% if (previousSensor > 0) { %><a class="btn btn-primary btn-block" href="stanza.jsp?sensor=<%=previousSensor %>">Stanza <%=previousSensor %></a><% } %></div>
		<div class="col-xs-5"><h3>Stanza <%=sensor %></h3></div>
		<div class="col-xs-3"><a class="btn btn-primary btn-block" href="index.jsp">..torna alla mappa</a></div>
		<div class="col-xs-2"><% if (nextSensor > 0) { %><a class="btn btn-primary btn-block" href="stanza.jsp?sensor=<%=nextSensor %>">Stanza <%=nextSensor %></a><% } %></div>
  	</div>
  	
  	<hr/>

	<div class="stanza-container row">
		<div class="stanza off col-xs-12">
			<table class="table table-condensed">
				<tr><td>Visitatori:</td><td>-</td></tr>
				<tr><td>Temperatura:</td><td>-</td></tr>
				<tr><td>Umidità:</td><td>-</td></tr>
			</table>
			<span class="stato-sensore">Sensore non rilevato</span>
		</div>
	</div>
	
	<div class="chart-container row">
		<div class="col-xs-12">
			<div id="temperatura" style="height: 300px; width:100%;"></div>
		</div>
	</div>
	
	<div class="chart-container row">
		<div class="col-xs-12">
			<div id="umidita" style="height: 300px; width:100%;"></div>
		</div>
	</div>
	
	<table class=" clearfix table table-condensed dati-generici">
		<tr><td colspan="2"><h3 class="now"></h3></td></tr>
		<tr><td>Visitatori totali:</td><td><span class="visitatori">-</span></td></tr>
		<tr><td>Temperatura esterna:</td><td><span class="temperatura">-</span></td></tr>
		<tr><td>Umidità esterna:</td><td><span class="umidita">-</span></td></tr>
 	</table>
 	
	</div>
	
 
 <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.canvasjs.min.js"></script>
    <script src="js/moment-with-locales.js"></script>
    
    <script src="js/script.js"></script>
    
    <script type="text/javascript">
	$(function(){
		var dps = []; // dataPoints

		var chartTemperatura = new CanvasJS.Chart("temperatura",{
			title :{
				text: "Temperatura"
			},			
			data: [{
				type: "line",
				dataPoints: dps 
			}]
		});
		
		var chartUmidita = new CanvasJS.Chart("umidita",{
			title :{
				text: "Umidita"
			},			
			data: [{
				type: "line",
				dataPoints: dps 
			}]
		});

		var xVal = 0;
		var yVal = 100;	
		var updateInterval = 1000;
		var dataLength = 180; // number of dataPoints visible at any point

		var updateChart = function (count) {
			count = count || 1;
			// count is number of times loop runs to generate random dataPoints.
			
			for (var j = 0; j < count; j++) {	
				yVal = yVal +  Math.round(5 + Math.random() *(-5-5));
				dps.push({
					x: xVal,
					y: yVal
				});
				xVal++;
			};
			if (dps.length > dataLength)
			{
				dps.shift();				
			}
			
			chartTemperatura.render();
			chartUmidita.render();

		};

		// generates first set of dataPoints
		updateChart(dataLength); 

		// update chart after specified time. 
		setInterval(function(){updateChart()}, updateInterval); 		
	});

	</script>
	
	<script>
    $(function(){
    	moment.locale('it', {
    		weekdays : "Domenica_Lunedi_Martedi_Mercoledi_Giovedi_Venerdi_Sabato".split("_"),
    	});
    	$(".dati-generici .now").html(moment().format('LLLL'));
    });
    </script>
 </body>
 </html>