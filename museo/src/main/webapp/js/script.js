$(function(){
	
	moment.locale('it', {
		weekdays : "Domenica_Lunedi_Martedi_Mercoledi_Giovedi_Venerdi_Sabato".split("_"),
	});
	
	updateTime();
});

function updateTime(){
	setTimeout(function(){
		$(".dati-generici .now").html(moment().format('D MMMM YYYY, HH:mm:ss'));
		updateTime();
	}, 1000);
}

function sensorUpdate(){
	setTimeout(function(){
		
		
		$.ajax({
			url: '/museo/servlets/invio',
			type : 'get',
			dataType :	'json',
			cache : false,
			error : function (jqXHR, textStatus, error) {
				alert("Si è verificato un errore inatteso");
			},
			complete : function(){
				sensorUpdate();
			}, 
			success : function (data, textStatus, jqXHR) {
				
				var updatedSensors = "-";
				var totalVisitors = 0;
				
				$(data).each(function(index, item){
					var sensor = item.sensore;
					if (sensor != 0){
						totalVisitors = totalVisitors + parseInt(item.visitatori);
					}
					
					updatedSensors = updatedSensors + sensor + "-";
					if ($("#stanza-" + sensor).length > 0) {
						$("#stanza-" + sensor).removeClass("off").addClass("on");
						
						$("#stanza-" + sensor + " .stato-sensore").html("sensore rilevato");
						
						$("#stanza-" + sensor + " .visitatori").html(item.visitatori);
						$("#stanza-" + sensor + " .umidita").html(item.umidita + "&#37;");
						$("#stanza-" + sensor + " .temperatura").html(item.temperatura + "  &#8451;");
					}
					
					
				});
				
				if ($("#stanza-0").length > 0) {
					$("#stanza-0 .visitatori").html(totalVisitors);
				}
				
				for (var i = 0; i < 10; i++){
					if (updatedSensors.indexOf("-" + i + "-") < 0){
						if ($("#stanza-" + i).length > 0) {
							$("#stanza-" + i).removeClass("on").addClass("off");
							
							$("#stanza-" + i + " .stato-sensore").html("sensore non rilevato");
							
							$("#stanza-" + i + " .visitatori").html("-");
							$("#stanza-" + i + " .umidita").html("-");
							$("#stanza-" + i + " .temperatura").html("-");
						}	
					}
				}
			}
		});
		
	}, 1000);
}

$(function(){
	sensorUpdate();
});