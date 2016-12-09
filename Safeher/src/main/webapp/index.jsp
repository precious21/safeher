<!DOCTYPE html>
<html> 
<head> 
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
  <title>Google Maps Multiple Markers</title> 
  <script src="http://maps.google.com/maps/api/js?key=AIzaSyBRUsra6RF9BXfWSdke4cGPn49bEwYbbLw" 
          type="text/javascript"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
</head> 
<body>
  <div id="map" style="width: 90%; height: 600px;"></div>

  <script type="text/javascript">
    var locations = [];
    
   /* $.ajax({
           type: 'get',
           contentType: "application/json",
           url: "http://192.168.0.141:8080/Safeher/getDrivers",
           success: function(data){
           	//alert(data.returnType);
           	if(data.returnType+"" == "SUCCESS"){
           		locations = data.returnData.list;
           		//alert(JSON.stringify(locations));
           		var map = new google.maps.Map(document.getElementById('map'), {
			      zoom: 6,
			      center: new google.maps.LatLng(31.54,  74.34),
			      mapTypeId: google.maps.MapTypeId.ROADMAP
			    });

			    var infowindow = new google.maps.InfoWindow();
			
			    var marker, i;
			    
			    
			    $.each(locations, function(i, val) {
			      marker = new google.maps.Marker({
			        position: new google.maps.LatLng(val.lat, val.lng),
			        map: map
			      });
			
			      google.maps.event.addListener(marker, 'click', (function(marker, i) {
			        return function() {
			          infowindow.setContent(locations[i][0]);
			          infowindow.open(map, marker);
			        }
			      })(marker, i));
			    });
   
           	}else{
           		alert("There is some error, Please try again");
           	}
		}
     });*/

  </script>
</body>
</html>