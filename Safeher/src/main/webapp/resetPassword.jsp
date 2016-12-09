<!DOCTYPE html>
<html>
<head>
<title>Saferher</title>
<link rel="shortcut icon" href="/images/favicon(2).ico" type="image/x-icon">
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script>

$(document).ready(function(){
    var pathname = window.location.pathname; // Returns path only
	var url      = window.location.href;     // Returns full URL
    //alert(url);
    var emailId = url.substring(url.indexOf("=")+1, url.length);
    //alert(emailId);
    
    $('#resetPasswordButton').click(function(){
    	var json = {}; 
    	var password = $('#password').val();
    	var passwordConfirm = $('#passwordConfirm').val();
    	if(password == ""){
    		alert("Please enter your new password");
    		$('#password').focus();
    		return;
    	}
    	if(passwordConfirm == ""){
    		alert("Please enter your new confirm password");
    		$('#passwordConfirm').focus();
    		return;
    	}
    	if(password != passwordConfirm){
    		alert("Password not matched");
    		$('#passwordConfirm').focus();
    		return;
    	}
    	
    	 if (password.length < 5 ) {
       		 alert("Password length should not be less than 5");
       		 $('#password').focus();
    		 return
    		}
    	
    	
    	json.email = emailId;
    	json.password = password;
    	//alert(JSON.stringify(json));
    	
    	$.ajax({
            type: 'post',
            contentType: "application/json",
            /*  url: "http://13.89.59.204:8080/Safeher/resetPassword", */
            url: "http://192.168.0.137:8080/Safeher/resetPassword",
            data: JSON.stringify(json),
            success: function(data) {
            	$('#messageDivId').show();
            	$('#fieldsetForm').hide();
            	//setInterval(function(){ window.close(); }, 5000);
			}/*,
		    error: function(XMLHttpRequest, textStatus, errorThrown) { 
		         
		    }*/ 
        });
    }); 
});
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
</style>
</head>

<body>

	<form class="form-horizontal">
		<fieldset id="fieldsetForm">

			<!-- Form Name -->
			<legend>Reset Password</legend>

			<!-- Password input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="passwordinput">Password</label>
				<div class="col-md-4">
					<input id="password" name="password"
						placeholder="Password" class="form-control input-md"
						type="password">

				</div>
			</div>

			<!-- Password input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="passwordinput">Confirm
					Password</label>
				<div class="col-md-4">
					<input id="passwordConfirm" name="passwordConfirm"
						placeholder="Confirm Password" class="form-control input-md"
						type="password">

				</div>
			</div>

			<!-- Button -->
			<div class="form-group">
				<label class="col-md-4 control-label" for="singlebutton"></label>
				<div class="col-md-4">
					<input type="button" id="resetPasswordButton" name="singlebutton"
						class="btn btn-success" value="Save">
				</div>
			</div>

		</fieldset>
		<div id="messageDivId" class="messageDiv">
			<h3>New Password Saved Successfully</h3>
		</div>
	</form>

</body>
</html>
