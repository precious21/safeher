<!DOCTYPE html>
<html>
<head>
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBRUsra6RF9BXfWSdke4cGPn49bEwYbbLw">
	
</script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>

<script>
	var map;
	var infowindow;
	var marker;
	var myCenter = new google.maps.LatLng(31.5451,  74.3407);  //USA(40.730610, -73.935242);//Pakistan(31.5451,  74.3407);
	var personId = 0;
	var appUserType = -1;

	function initialize() {
		var mapProp = {
			center : myCenter,
			zoom : 15,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		//AIzaSyBRUsra6RF9BXfWSdke4cGPn49bEwYbbLw

		map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

		google.maps.event.addListener(map, 'click', function(event) {
			//marker.setVisible(false);
			closeInfoAndMarker();
			placeMarker(event.latLng);
			//alert(event.latLng.lat());
			//alert(event.latLng.lng());
		});
	}

	function placeMarker(location) {
		marker = new google.maps.Marker({
			position : location,
			map : map,
		});
		infowindow = new google.maps.InfoWindow(
				{
					content : '<div id="content">'
							+ '<div id="siteNotice">'
							+ '<h3 id="firstHeading" class="firstHeading">Save Location</h3>'
							+ '<div id="bodyContent">'
							+ '<p><b>Latitude: </b>'
							+ location.lat()
							+ '</p>'
							+ '<p><b>Longitude: </b>'
							+ location.lng()
							+ '</p>'
							+ '</div>' 
							+ '<div><span><b>App User Type: </b>'
							+ '</span>'
							+ '<select id="appUserTypeSlct" onchange="getDriverPassenger();">'
							+' <option value="-1">---Select---</option><option value="1">Driver</option>'
							+ '<option value="0">Passenger</option></select></div>'
							+ '<div><span><b>Person: </b>'
							+ '</span>'
							+ '<select id="personSlct" onchange="onchangePersonSelect();">'
							+' <option value="-1">---Select---</option></div>'
							+ '<br><input type="button" value="Save Location" onclick="saveLocation('
							+ location.lat() + ',' + location.lng() + ');" />'
							+ '</div>'
				//'Latitude: ' + location.lat() + '<br>Longitude: ' + location.lng()
				});
		infowindow.open(map, marker);
		/*var circle = new google.maps.Circle({
			map : map,
			radius : 1000, // 10 miles in metres
			//fillColor : '#AA0000'
		});
		circle.bindTo('center', marker, 'position');*/
	}

	google.maps.event.addDomListener(window, 'load', initialize);
	
	function closeInfoAndMarker(){
		typeof marker !== 'undefined' && marker.setVisible(false);
		typeof infowindow !== 'undefined' && infowindow.close();
	}
	
	function onchangePersonSelect(){
		personId = $("#personSlct option:selected" ).val();
		//alert(personId);
	}

	function saveLocation(lat, lng) {
		//alert(lat);
		//alert(lng);
		var json = {};
		if(personId*1 > 0){
			json.appUserId = personId;
			json.lng = lng;
			json.lat = lat;
			json.isDriver = appUserType;
			json.isPhysical = "0";
			//alert(JSON.stringify(json));
			$.ajax({
	            type: 'post',
	            contentType: "application/json",
	            url: "http://192.168.0.141:8080/Safeher/savePassangerDriver",
	            data: JSON.stringify(json),
	            success: function(data){
	            	//alert(data.returnType);
	            	if(data.returnType+"" == "SUCCESS"){
		           		var jsonDat = JSON.stringify(data.returnData.address);
		            	alert("Location: "+jsonDat+ " has been saved.");
						closeInfoAndMarker();
						personId = 0;
						appUserType = -1;
	            	}else{
	            		alert("There is some error, Please try again");
	            	}
				}
        	});
		}else{
			alert("please select app user and person");
			return;
		}
	}
	function getDriverPassenger() {
	
		appUserType = $("#appUserTypeSlct option:selected" ).val();
		var json = {};
		var html = "";
		var select = $('#personSlct');
		
		if(appUserType*1 >= 0){
			json.isDriver = appUserType;
			$.ajax({
	            type: 'post',
	            contentType: "application/json",
	            url: "http://192.168.0.141:8080/Safeher/getAllPassangerDriver",
	            data: JSON.stringify(json),
	            success: function(data){ 
	            	var data = data.returnData.personList;
					//alert(JSON.stringify(data));
					personId = data[0].appUserId;
	            	$.each(data, function(i, item) {
	            		html += '<option value="'+item.appUserId+'">'+item.personName+'</option>'
					});
					select.html(html);
				}
        	});
		}
	}
</script>
</head>

<body>
	<h3 style="color: green">Click on map to save specific location</h3>
	<div id="googleMap" style="width:80%;height:500px;float: left;"></div>
	<div id="content" style="float: left;margin-left: 20px;display: none;">
		<div id="siteNotice"></div>
		<h3 id="firstHeading" class="firstHeading">Save Location</h3>
		<div id="bodyContent">
			<p>
				<b>Latitude: </b> <span id="lat"></span>
			</p>
			<p>
				<b>Longitude: </b> <span id="lng"></span>
			</p>
			<input type="button" value="Save Location" />
		</div>
	</div>

</body>
</html>