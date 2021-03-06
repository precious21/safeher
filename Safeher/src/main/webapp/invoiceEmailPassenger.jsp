<!DOCTYPE html>
<head>
  <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
  <meta name='viewport' content='width=device-width, initial-scale=1' />
  
  <title>Safr-Invoice Email</title>
  <style type='text/css'>
    @import url(https://fonts.googleapis.com/css?family=Lato:300,400,700);

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
    }

    a img {
      border: none;
    }


    td, h1, h2, h3  {
      font-family: 'Lato', sans-serif;
      font-weight: 300;
    }
	.source td{
		font-size:13px;
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
	h2{
		font-size:18px;
	}

    h3 {
      color: #fff;
      font-size: 16px;
	  text-align:center;
	  margin:5px 0 0;
	  font-weight:300;
    }

    .important-font {
      color: #21BEB4;
      font-weight: 400;
    }

    .hide {
      display: none !important;
    }

    .force-full-width {
      width: 100% !important;
    }
	.source{
		background:#f9f9f9;
	}
	.mobile-center{
		margin:0 auto;
		text-align:center !important;
	}
	.price{
		font-size:55px;
		font-weight:300;
	}
	.pin{
		width:10px;
	}
	.profile{
		border-radius:100%;
	
	}
	.driver-detail{
		border-top:1px solid #E9E9E9;
		border-bottom:1px solid #E9E9E9;
		padding-top:10px;
	}
	.driver-detail p{
		padding:0;
	}
  </style>

  <style type='text/css' media='screen'>
    @media screen {
    
      td, h1, h2, h3 {
        font-family: 'Lato', sans-serif;
      }
    }
  </style>

  <style type='text/css' media='only screen and (max-width: 480px)'>
    
    @media only screen and (max-width: 480px) {
		h1{
			font-size:18px;
		}
		h3{
			font-size:14px;
		}
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
	  .mobile-check{
		  margin-top:50px;
	  }
    }
  </style>
</head>
<body class='body' style='padding:0; margin:0; display:block; background:#ffffff; -webkit-text-size-adjust:none' bgcolor='#ffffff'>
<table align='center' cellpadding='0' cellspacing='0' width='100%' height='100%' >
  <tr>
    <td align='center' valign='top' bgcolor='#ffffff'  width='100%'>

    <table cellspacing='0' cellpadding='0' width='100%'>
      <tr>
        <td style='border-bottom: 3px solid #20728e;' width='100%'>
          <center>
            <table cellspacing='0' cellpadding='0' width='500' class='w320'>
              <tr>
                <td valign='top' style='padding:10px 0; text-align:left;' class='mobile-center'>
                  <a href='http://www.gosafr.com/' target='_blank'>
                  	<img width='150'  src='http://www.gosafr.com/marketing/2016-12-02-img/passenger-logo.png'>
                  </a>	
                </td>
              </tr>
            </table>
          </center>
        </td>
      </tr>
      <tr>
        <td  bgcolor='#20728e' valign='top' style='no-repeat center; background-color: #20728e; background-position: center;'>
          
          <div>
            <center>
              <table cellspacing='0' cellpadding='0' width='530' height='100' class='w320'>
                <tr>
                  <td valign='middle' style='vertical-align:middle;  text-align:center;' height='100'>

                    <table cellspacing='0' cellpadding='0' width='100%'>
                      <tr>
                        <td height='50' align='center' valign='middle'>
                          <h1>Thank you for riding with Safr</h1>
                          <h3>Here is your invoice for your ride </h3>
                          
                        </td>
                      </tr>
                    </table></td>
                </tr>
              </table>
            </center>
          </div>
        </td>
      </tr>
      <tr>
        <td valign='top'>

          <center>
          
            <table cellspacing='0' cellpadding='0' width='500' class='w320'>
              <tr>
                <td valign='top' style='border-bottom:1px solid #a1a1a1;'>


                <table cellspacing='0' cellpadding='0' width='100%'>
                
                  <tr>
                    <td style='padding: 15px 0;' class='mobile-padding'>
					
                    <table class='force-full-width' cellspacing='0' cellpadding='0'>
                    	
                      <tr>
                        <td style='text-align: left;'>
                          <span class='important-font'>
                            Amount Charged<br>
                          </span>
                          <span class='price'>$@totalAmount</span>
                        </td>
                        <td style='text-align: right; vertical-align:top;'>
                          <span class='important-font'>
                            Invoice: @invoice<br>
                          </span>
                          @Date

                        </td>
                      </tr>
                    </table>

                    </td>
                  </tr>
                  	<tr>
                    	<td>
                       	  <table cellpadding='0' cellspacing='0' class='force-full-width'>
                    	<tr>
                    	<td width='13%' ><img img src='@driverImageUrl' alt='driverImageUrl' width='50px' height='50px' class='profile'/></td>
                        <td width='87%'>
                        	<table class='force-full-width source' cellspacing='0' cellpadding='0' >
                        <tr>

                          <td width='9%' height='36' align='center' valign='middle' class='mobile-block mobile-block-2 '><img src='http://www.gosafr.com/marketing/2016-12-02-img/car-2.png' alt='car' width='20px'/></td>

                          <td class='mobile-block' width='91%'>You rode with @driverName</td>
                        </tr>
                        <tr>

                          <td width='9%' height='37' align='center' valign='middle' class='mobile-block mobile-block-2'><img src='http://www.gosafr.com/marketing/2016-12-02-img/stars.png' alt='start' width='12px' /></td>

                          <td class='mobile-block' width='91%'>@driverOverallRating Rating</td>
                        </tr>
                      </table>
                        </td>
                    </tr>
                    <tr>
                    	<td width='13%' height='93'>
                        	<img src='http://www.gosafr.com/marketing/2016-12-02-img/car.png' alt='car' width='50px' /></td>
                        <td width='87%'>
                        	<table class='force-full-width source' cellspacing='0' cellpadding='0' >
                        <tr>

                          <td width='9%' height='36' align='center' valign='middle' class='mobile-block mobile-block-2 '><img src='http://www.gosafr.com/marketing/2016-12-02-img/num.png' alt='pin' class=''  width='20px'/></td>

                          <td class='mobile-block' width='91%'>@driverVehicleNumber</td>
                        </tr>
                        <tr>

                          <td width='9%' height='37' align='center' valign='middle' class='mobile-block mobile-block-2'><img src='http://www.gosafr.com/marketing/2016-12-02-img/car-3.png' alt='pin' width='20px' /></td>

                          <td class='mobile-block' width='91%'>@driverVehicleName </td>
                        </tr>
                      </table>
                        </td>
                    </tr>
                    <tr>
                    	<td width='13%'>
                        	<img src='http://www.gosafr.com/marketing/2016-12-02-img/route.png' alt='car' width='50px' />
                        </td>
                        <td width='87%'>
                        	<table class='force-full-width source' cellspacing='0' cellpadding='0' >
                        <tr>

                          <td width='9%' height='36' align='center' valign='middle' class='mobile-block mobile-block-2 '><img src='http://www.gosafr.com/marketing/2016-12-02-img/source.png' alt='pin' width='10px'  /></td>

                          <td class='mobile-block' width='91%'>
                            @startTime | @startAddress
                          </td>
                        </tr>
                        <tr>

                          <td width='9%' height='37' align='center' valign='middle' class='mobile-block mobile-block-2'><img src='http://www.gosafr.com/marketing/2016-12-02-img/des.png' alt='pin' width='10px' /></td>

                          <td class='mobile-block' width='91%'>@endTime | @endAddress</td>
                        </tr>
                      </table>
                        </td>
                    </tr>
                    </table>
                        </td>
                    </tr>
                  
                  <tr>
                    <td style='padding-bottom: 30px;' class='mobile-padding'>
						
                      <br />
                      
                      <table class='force-full-width' cellspacing='0' cellpadding='0'>
                        <tr>

                          <td width='33%' height='95' class='mobile-block'>
                            <table width='100%' border='0' class='driver-detail' >
  <tbody>
    <tr>
      
      <td height='21' colspan='4' align='left' >&nbsp;</td>
    </tr>
    
    <tr>
      <td width='25%' style=' border-right: 1px solid #f9f9f9; font-weight:400;'>@miles<br /></td>
      <td width='25%' style='border-right: 1px solid #f9f9f9; font-weight:400;'>@tripTime<br /></td>
      <td width='25%' style=' font-weight:400;'>&nbsp;</td>
    </tr>
    <tr>
      <td height='32' valign='top'><span style=' border-right: 1px solid #f9f9f9;'>Miles</span><br /></td>
      <td valign='top'><span style='border-right: 1px solid #f9f9f9;'>Trip time</span><br /></td>
      <td valign='top'>&nbsp;</td>
    </tr>
    <tr>
      <td height='21' colspan='4' align='center' style='background-color: #3bcdc3; color: #ffffff; padding: 5px; border-right: 3px solid #ffffff; font-size:22px;'>Rating: <img src='http://www.gosafr.com/marketing/2016-12-02-img/stars.png' width='15px'/> @youRateDriver </td>
      </tr>
      <br />
      
      
  </tbody>
</table>
                          </td>
						 
                          </tr>
                          <tr>
                          	<td style='padding-bottom: 30px;' class='mobile-padding'>

                      <table class='force-full-width mobile-check' cellspacing='0' cellpadding='0'>
                        <tbody><br><tr>

                          <td class='mobile-block' width='33%'>
                            <table class='force-full-width' cellspacing='0' cellpadding='0'>
                              <tbody>
                              
                              <tr>
                                <td align='center' class='mobile-border' style='background-color: #3bcdc3; color: #ffffff; padding: 5px; border-right: 3px solid #ffffff;'>
                                  Charity Contribution</td>
                              </tr>
                              <tr>
                                <td align='center' style='background-color: #ebebeb; padding: 8px; border-top: 3px solid #ffffff;'>$@charityAmount | @charityName</td>
                              </tr>
                            </tbody></table>
                          </td>

                          

                          

                        </tr>
                      </tbody></table>

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
                            The amount of $@totalAmount has been charged on the credit card ending with ****@creditLastFourNumber.
                            <br><br>
                            <p ><small>Need help?
Tap Help in your app to contact us with questions about your trip.<br>
Leave something behind? <a href='#' style='color:#25c9d0'>Track it down.</a></small></p>
                            <br>
                          </td>
                          
                        </tr>
                        
                      </table>
                      

                    </td>
                  </tr>
                </table>
                </td>
              </tr>
            </table>
            
            <table cellspacing='0' cellpadding='0' width='500' class='w320'>
              <tr>
              	<td>
                	<table class='force-full-width' cellspacing='0' cellpadding='0'>
                              <tbody>
                              
                              
                              <tr>
                                <td align='center' style='background-color: #ebebeb; padding: 8px; border-top: 3px solid #ffffff;'>Invite your friends and family. Give friends free ride credit to try Safr. You'll get $100 off each of your next 3 rides when they start riding.<br />
<span style='font-size:35px; color:#1e7390;'>Share code: @referralCode</span></td>
                              </tr>
                            </tbody></table>
                </td>
              </tr>
              
              <tr>
                <td>
                  <table cellspacing='0' cellpadding='0' width='100%'>
                    <tr>
                      <td class='mobile-padding' style='text-align:center;'><br />
                      
                        We look forward to going places with you!<br />
Your Safr Team
<br /><br /><a href='http://www.gosafr.com/' target='_blank'>
                          <img width='48' src='http://www.gosafr.com/marketing/2016-12-02-img/footer-logo.png' alt='footer-logo'>
                        </a>
                        <br /><br />
                      
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
        <td style='background-color:#20728e;'>
          <center>
            <table cellspacing='0' cellpadding='0' width='500' class='w320'>
              <tr>
                <td>
                  <table cellspacing='0' cellpadding='30' width='100%'>
                    <tr>
                      <td style='text-align:center;'>
                        <a href='https://www.facebook.com/GoSafr/?fref=ts' target='_blank'>
                          <img width='30'  src='http://www.gosafr.com/marketing/2016-12-02-img/fb.png' alt='twitter' style='margin-right:10px;' />
                        </a>
                        <a href='https://twitter.com/gosafr' target='_blank'>
                          <img width='30' src='http://www.gosafr.com/marketing/2016-12-02-img/twitr.png' alt='google plus' />
                        </a>
                        
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                
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