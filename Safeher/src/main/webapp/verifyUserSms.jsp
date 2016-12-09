
<!DOCTYPE html>
<head>
  <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
  <meta name='viewport' content='width=device-width, initial-scale=1' />
  <title>Phone Verification</title>
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
            	//$('.loader').hide();
            	
            	$('#load_data').html(data);
          		
			},
		    error: function(data) { 
		         alert("Error in calling Service");
		    }
        });
     
    
   }

</script>
  <style type='text/css'>
    @import url(https://fonts.googleapis.com/css?family=Lato:300,400,700);

    /* Take care of image borders and formatting */

    img {
      max-width: 600px;
      outline: none;
      text-decoration: none;
      -ms-interpolation-mode: bicubic;
    }
    a {
      text-decoration: none;
      border: 0;
      outline: none;
	  color:#21728e;
    }

    a img {
      border: none;
    }

    /* General styling */

    td, h1, h2, h3  {
      font-family: Helvetica, Arial, sans-serif;
      font-weight: 400;
    }

    body {
      -webkit-font-smoothing:antialiased;
      -webkit-text-size-adjust:none;
      width: 100%;
      height: 100%;
      color: #37302d;
      background: #ffffff;
    }

     table {
      border-collapse: collapse !important;
    }


    h1, h2, h3 {
      padding: 0;
      margin: 0;
      color: #ffffff;
      font-weight: 400;
    }

    h3 {
      color: #21c5ba;
      font-size: 24px;
    }

    .important-font {
      color: #3f3f3f;
	  text-transform:uppercase;
      font-weight: 300;
	  font-size:21px;
	  padding-bottom:30px;
	  display:block;
    }

    .hide {
      display: none !important;
    }

    .force-full-width {
      width: 100% !important;
    }
  </style>

  <style type='text/css' media='screen'>
    @media screen {
       /* Thanks Outlook 2013! http://goo.gl/XLxpyl*/
      td, h1, h2, h3 {
        font-family: 'Lato', 'Helvetica Neue', 'Arial', 'sans-serif' !important;
      }
    }
  </style>

  <style type='text/css' media='only screen and (max-width: 480px)'>
    /* Mobile styles */
    @media only screen and (max-width: 480px) {
      table[class='w320'] {
        width: 320px !important;
      }

      table[class='w300'] {
        width: 300px !important;
      }

      table[class='w290'] {
        width: 290px !important;
      }

      td[class='w320'] {
        width: 320px !important;
      }

      td[class='mobile-center'] {
        text-align: center !important;
      }

      td[class='mobile-padding'] {
        padding-left: 20px !important;
        padding-right: 20px !important;
        padding-bottom: 20px !important;
      }

      td[class='mobile-block'] {
        display: block !important;
        width: 100% !important;
        text-align: left !important;
        padding-bottom: 20px !important;
      }

      td[class='mobile-border'] {
        border: 0 !important;
      }

      td[class*='reveal'] {
        display: block !important;
      }
    }
  </style>
</head>
<body class='body' style='padding:0; margin:0; display:block; background:#ffffff;  -webkit-text-size-adjust:none' bgcolor='#ffffff' onload="verify()">
<table align='center' cellpadding='0' cellspacing='0' width='100%' height='100%' >
  <tr>
    <td align='center' valign='top' bgcolor='#ffffff'  width='100%'>

    <table cellspacing='0' cellpadding='0' width='100%'>
      <tr>
        <td>
          <center>
            <table cellspacing='0' cellpadding='0' width='600' class='w320'>
              <tr>
                <td valign='top' style='padding:10px 0; text-align:center;' class='mobile-center'>
                  <img width='250' src='http://www.gosafr.com/marketing/2016-11-17-img/logo.png'>
                </td>
              </tr>
            </table>
          </center>
        </td>
      </tr>
      <tr>
        <td  bgcolor='#8b8284' valign='top' style='no-repeat center; background-color: #fe0196; background-position: center;'>
          <!--[if gte mso 9]>
          <v:rect xmlns:v='urn:schemas-microsoft-com:vml' fill='true' stroke='false' style='mso-width-percent:1000;height:303px;'>
            <v:fill type='tile' src='https://www.filepicker.io/api/file/kmlo6MonRpWsVuuM47EG' color='#8b8284' />
            <v:textbox inset='0,0,0,0'>
          <![endif]--><!--[if gte mso 9]>
            </v:textbox>
          </v:rect>
          <![endif]-->
        </td>
      </tr>
      <tr>
        <td valign='top'>

          <center>
            <table cellspacing='0' cellpadding='0' width='500' class='w320'>
              <tr>
                <td valign='top'>


                <table cellspacing='0' cellpadding='0' width='100%'>
                  <tr>
                    <td style='padding: 15px 0 30px;' class='mobile-padding'>

                    <table class='force-full-width' cellspacing='0' cellpadding='0'>
                      <tr>
                        <td style='text-align: center;'>
                          <span class='important-font'>
                           Phone Verification
                          </span>
                          
                        </td>
                        
                      </tr>
                    </table>

                    </td>
                  </tr>
                  
                  <tr>
                    <td class='mobile-padding'>

                      <table cellspacing='0' cellpadding='0' width='100%'>
                        <tr>
                          <td style='text-align: left;'>
                           
                            <p  style='font-weight:200; font-size:16px; line-height:24px;'> Dear Safr User,<br><br> </p>
                          </td>
                        </tr>
                      </table>

                    </td>
                  </tr>
                </table>
                </td>
              </tr>
            </table>
          </center>
        </td>
      </tr>
      <tr>
        <td>
          <center>
          <div id="load_data" class="centered" ></div>
            <table cellspacing='0' cellpadding='0' width='500' class='w320'>
              <tr>
                <td>
                  <table cellspacing='0' cellpadding='30' width='100%'>
                    <tr>
                      <td style='text-align:center;'>
                      <p style='font-weight:200; font-size:16px; line-height:24px; padding-bottom:0;'>
                      	We look forward to going places with you!<br />
Your Safr Team
                      </p>
                        <a href='#'>
                          <img width='48' src='http://www.gosafr.com/marketing/2016-11-17-img/footer-logo.png' alt='footer-logo' />
                        </a>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <center>
                    
                  </center>
                </td>
              </tr>
            </table>
          </center>
        </td>
      </tr>
    </table>
    </td>
  </tr>
</table>
</body>
</html>