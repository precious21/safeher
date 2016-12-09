<!DOCTYPE html>
<html>
<head>
<title>Saferher</title>
<link rel="shortcut icon" href="/images/favicon(2).ico" type="image/x-icon">
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script>

function verify(){
    var pathname = window.location.pathname; // Returns path only
	var urls = window.location.href;     // Returns full URL
   	urls = urls.replace('.jsp','');   
  
    	
    	$.ajax({

            url: urls,
            /* url: "http://192.168.0.137:8080/Safeher/resetPassword", */
            success: function(data) {
            	/* alert(data); */
            	$('.loader').hide();
            	$('#result').html(data);
          		
			},
		    error: function(data) { 
		         alert("Error in calling Service");
		    }
        });
     
    
   }

</script>	
<style>
	fieldset{
		float: left;
	    margin-left: 40%;
	    margin-top: 10%;
	}
	.messageDiv{
		float: left;
	    margin-left: 35%;
	    margin-top: 10%;
	    width: auto;
	    height: auto;
	    font-size: 18px;
	    color: green;
	    display: none;
	}
	
	
	

	.loader {
	  border: 16px solid #f3f3f3;
	  border-radius: 50%;
	  border-top: 16px solid #3498db;
	  width: 120px;
	  height: 120px;
	  -webkit-animation: spin 2s linear infinite;
	  animation: spin 2s linear infinite;
	}
	
	@-webkit-keyframes spin {
	  0% { -webkit-transform: rotate(0deg); }
	  100% { -webkit-transform: rotate(360deg); }
	}
	
	@keyframes spin {
	  0% { transform: rotate(0deg); }
	  100% { transform: rotate(360deg); }
	}

	
	
</style>
</head>

<body onload="verify()">

<div class="loader">

Please Wait Verification is in process

</div>

<div id="result"></div>

</body>
</html>
