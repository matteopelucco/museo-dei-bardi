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
	<div class="piantina-container">
		<div class="piantina">
			<% for (int i = 1; i < 10; i++) { %>
 				<a href="stanza.jsp?sensor=<%=i %>">
	 				<div id="stanza-<%=i %>" class="stanza stanza-absolute off">
	 					<table class="table table-condensed">
							<tr><td colspan="2"><h3>Stanza <%=i %></h3></td></tr>
							<tr><td>Visitatori:</td><td>-</td></tr>
							<tr><td>Temperatura:</td><td>-</td></tr>
							<tr><td>Umidità:</td><td>-</td></tr>
	 					</table>
	 					<span class="stato-sensore">Sensore non rilevato</span>
	 				</div>
 				</a>
			<% } %>
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
    <script src="js/moment-with-locales.js"></script>
    
    <script src="js/script.js"></script>
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