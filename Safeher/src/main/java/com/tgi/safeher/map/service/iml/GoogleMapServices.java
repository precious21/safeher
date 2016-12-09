package com.tgi.safeher.map.service.iml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.map.service.IGoogleMapServices;
import com.tgi.safeher.utils.StringUtil;

@Component
public class GoogleMapServices implements IGoogleMapServices{
	@Value("${safeHerApp.GOOGLE_API_KEY_6}")
	private String apiKey;
//	private static final String apiKey = "AIzaSyBx6ndHcmgrYXawvyKg0LiJvFNaCN7SUoc";
	private static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/maps";
	private static final String GOOGLE_PLACES_API_URL = "/api/place/nearbysearch/";

	private GeoApiContext context;
	
	@Override
	public String getAddressFromLngLat(String lat, String lng)
			throws GenericException {
		context = new GeoApiContext().setApiKey(apiKey);
		String address = "";
		String city = "";
		String state = "";
		String country = "";
		String stateCode = "";
		String countryCode = "";
		String suburb = "";
		String neighborhood = "";
		String[] streateAdress;
		try {
			GeocodingResult result[]=GeocodingApi.newRequest(context).
					latlng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))).await();
			for(int i = 0; i< result.length;i++){
				System.out.println(result[i].formattedAddress);
			}
			if(result != null && result.length > 0){
				streateAdress = result[0].formattedAddress.toString().split(",");
				for(int i = 0; i < result[0].addressComponents.length; i++){
					if(result[0].addressComponents[i].types[0].toString().equalsIgnoreCase("country")){
						country = result[0].addressComponents[i].longName.toString();
						countryCode = result[0].addressComponents[i].shortName.toString();
					}
					if(result[0].addressComponents[i].types[0].toString().equalsIgnoreCase("locality")){
						city = streateAdress[1].substring(1);
						System.out.println(city+": localityCity");
					}
					if(StringUtil.isEmpty(city)){ 
						if(result[0].addressComponents[i].types[0].toString().equalsIgnoreCase("administrative_area_level_2")){
							city = streateAdress[1].substring(1);
							System.out.println(city+": adminCity");
						}
					}
					if(result[0].addressComponents[i].types[0].toString().equalsIgnoreCase("administrative_area_level_1")){
						state = result[0].addressComponents[i].longName.toString();
						stateCode = result[0].addressComponents[i].shortName.toString();
						
					}
					if(result[0].addressComponents[i].types.length >= 3){
						if(result[0].addressComponents[i].types[1].toString().equalsIgnoreCase("SUBLOCALITY")){
							suburb = result[0].addressComponents[i].longName.toString();
							System.out.println(suburb+": SubLocalitySUburb");
						}
					}
					if(result[0].addressComponents[i].types.length >= 2){
						if(result[0].addressComponents[i].types[0].toString().equalsIgnoreCase("neighborhood")){
							neighborhood = result[0].addressComponents[i].longName.toString();
							System.out.println(suburb+": neighborhoodSUburb");
						}
					}
				}
				address = streateAdress[0]+","+city+","+state+","+country+","+stateCode+","+countryCode+","+suburb+","+neighborhood;
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return address;
	}

	@Override
	public String getFormatedAddress(String lat, String lng)
			throws GenericException {
		context = new GeoApiContext().setApiKey(apiKey);
		String fromatedAddress = "";
		try {
			GeocodingResult result[]=GeocodingApi.newRequest(context).
					latlng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))).await();
			if(result != null && result.length > 0){

				fromatedAddress = result[0].formattedAddress;
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fromatedAddress;
	}

}
