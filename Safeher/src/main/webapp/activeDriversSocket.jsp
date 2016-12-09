<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8" />

<title>Demo Chat</title>


<style>
body {
	padding: 20px;
}

#console {
	height: 400px;
	overflow: auto;
}

.username-msg {
	color: orange;
}

.connect-msg {
	color: green;
}

.disconnect-msg {
	color: red;
}

.send-msg {
	color: #888
}
</style>


<script src="https://cdn.socket.io/socket.io-1.4.5.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.14.1/locale/af.js"></script>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

<script>
        
        var userName = 'user' + Math.floor((Math.random()*1000)+1);
		var socket =  io.connect('http://10.0.0.13:52984');
		alert(123);
		alert(socket.connected);
		
		socket.on('connect', function() {
			$('#message').html('<span class="connect-msg">Client has connected to the server!</span>');
		});
		
		socket.on('message', function(data, ackServerCallback) {
			$('#message').html('<span class="username-msg">' + data + ':</span> ' + data);
			if (ackServerCallback) {
                // send ack data to server
            	ackServerCallback('server message was delivered to client!');
            }
		});
		
		socket.on('disconnect', function() {
			$('#message').html('<span class="connect-msg">Client has disconnected to the server!</span>');
		});
		
		socket.on('getNavigationResponse', function(data, ackServerCallback, arg1) {
			alert(JSON.stringify(data))
			$('#message').html('<span class="username-msg">' + data + ':</span> ' + data);
            if (ackServerCallback) {
               ackServerCallback('server message was delivered to client!');
            }
		});
		
		socket.on('getNavigation2', function(data, ackServerCallback) {
			$('#message').html('<span class="username-msg">' + data + ':</span> ' + data);
	            if (ackServerCallback) {
	               ackServerCallback();
	            }
		});
		
        function sendDisconnect() {
        	socket.disconnect();
        }
        
		function sendMessage() {
			var json = {appUserId: "382", lat: "31.447638", lng: "74.321312"};
            var message = $('#msg').val();
            socket.emit('getNavigation', json, function(arg1, arg2) {
                //alert("ack from server! arg1: " + arg1 + ", arg2: " + arg2);
                output(message);
            });
		}
		
		function output(message) {
              var currentTime = "<span class='time'>" +  moment().format('HH:mm:ss.SSS') + "</span>";
              var element = $("<div>" + currentTime + " " + message + "</div>");
			$('#console').append(element);
		}
		
        $(document).keydown(function(e){
            if(e.keyCode == 13) {
                $('#send').click();
            }
        });
        
        $(document).ready(function(e){
           
        });
	</script>
</head>

<body>
	<div>
		<div>
			<h1>activeDriverSocket</h1>
		
			<br />
			<div id="message"></div>
		
			<div id="console" class="well"></div>
		
			<form class="well form-inline" onsubmit="return false;">
				<input id="msg" class="input-xlarge" type="text"
					placeholder="Type something..." />
				<button type="button" onClick="sendMessage()" class="btn" id="send">Send</button>
				<button type="button" onClick="sendDisconnect()" class="btn">Disconnect</button>
			</form>
		</div>
	</div>

</body>

</html>