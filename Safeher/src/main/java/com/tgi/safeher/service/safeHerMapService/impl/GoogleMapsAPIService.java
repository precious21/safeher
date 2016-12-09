package com.tgi.safeher.service.safeHerMapService.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressType;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import com.tgi.safeher.API.thirdParty.Andriod.PushAndriod;
import com.tgi.safeher.API.thirdParty.IOS.PushIOS;
import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.beans.RideCriteriaBean;
import com.tgi.safeher.beans.RideSearchResultBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.PushNotificationStatus;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.common.exception.GenericRuntimeException;
import com.tgi.safeher.dao.GoogleMapDao;
import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.UserLoginEntity;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.jms.Producer;
import com.tgi.safeher.repo.ActiveDriverLocationRepository;
import com.tgi.safeher.service.converter.DistanceAPIConverter;
import com.tgi.safeher.service.safeHerMapService.IGoogleMapsAPIService;
import com.tgi.safeher.utils.CollectionUtil;
import com.tgi.safeher.utils.Common;
import com.tgi.safeher.utils.StringUtil;

@Service
@Transactional
public class GoogleMapsAPIService implements IGoogleMapsAPIService {

	@Value("${safeHerApp.GOOGLE_API_KEY_6}")
	private String apiKey;

	@Autowired
	private Producer producer;
	
	@Autowired
	private GoogleMapDao mapDao;

	@Autowired
	private DistanceAPIConverter distanceAPIConverter;
	
	@Autowired
	private ActiveDriverLocationRepository activeDriverLocationRepository;
	
	

	// private static final String apiKey =
	// "AIzaSyBRUsra6RF9BXfWSdke4cGPn49bEwYbbLw";
	private static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/maps";
	private static final String GOOGLE_PLACES_API_URL = "/api/place/nearbysearch/";
	private static final Double APPROXIMATE_DISTANCE = 100000d;// TODO: to
																// Calculate
																// using Google
																// API
	private static final Logger logger = Logger.getLogger(GoogleMapsAPIService.class);
	private static final Double LEAST_TIME = 60d;
	private GeoApiContext context;

	/**
	 * This Methods Gets Origins and Destinations in String as Parameters and it
	 * returns the DistanceMatrix Containing total distance and in meters and
	 * duration Restrictions Currently avoiding Tolls
	 * 
	 * @param origins
	 *            starting point
	 * @param destinations
	 *            ending point
	 * @return DistanceAPIBean
	 */
	public DistanceAPIBean googleDistanceAPI(DistanceAPIBean distanceAPIBean) {
		logger.info("******Entering in googleDistanceAPI  ******");
		context = new GeoApiContext().setApiKey(apiKey);
		DistanceMatrix matrix = null;
		try {

			matrix = DistanceMatrixApi.newRequest(context)
					.origins(distanceAPIBean.getOrigins())
					.destinations(distanceAPIBean.getDestinations())
					.mode(TravelMode.DRIVING).language("en-AU")
					.avoid(RouteRestriction.TOLLS).units(Unit.METRIC).await();

			if (matrix.rows[0].elements[0].distance != null) {

				distanceAPIBean
						.setTotalDistanceMeters((double) matrix.rows[0].elements[0].distance.inMeters);
				distanceAPIBean
						.setTotalTimeSeconds((double) matrix.rows[0].elements[0].duration.inSeconds);

			}
			logger.info("******DistanceAPIBean Object "+ distanceAPIBean+" ******");

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("******Exiting from googleDistanceAPI with exception "+ ex.getMessage()+" ******");
		}

		return distanceAPIBean;
	}

	/**
	 * This Methods Gets Origins and Destinations in LatLng as Parameters and it
	 * returns the DistanceMatrix Containing total distance and in meters and
	 * duration Restrictions Currently avoiding Tolls
	 * 
	 * @param origins
	 *            starting point
	 * @param destinations
	 *            ending point
	 * @return DistanceAPIBean
	 */
	public DistanceAPIBean googleDistanceAPIV2(DistanceAPIBean distanceAPIBean) {
		logger.info("******Entering in googleDistanceAPI  ******");
		context = new GeoApiContext().setApiKey(apiKey);
		DistanceMatrix matrix = null;
		try {

			matrix = DistanceMatrixApi.newRequest(context)
					.origins(distanceAPIBean.getLatLngOrigins())
					.destinations(distanceAPIBean.getLatLngDestinations())
					.mode(TravelMode.DRIVING).language("en-AU")
					.avoid(RouteRestriction.TOLLS).units(Unit.METRIC).await();

			if (matrix.rows[0].elements[0].distance != null) {

				distanceAPIBean
						.setTotalDistanceMeters((double) matrix.rows[0].elements[0].distance.inMeters);
				distanceAPIBean
						.setTotalTimeSeconds((double) matrix.rows[0].elements[0].duration.inSeconds);

			}
			logger.info("******DistanceAPIBean Object "+ distanceAPIBean+" ******");

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("******Exiting from googleDistanceAPIV2 with exception "+ ex.getMessage()+" ******");
		}

		return distanceAPIBean;
	}

	/**
	 * This Methods Gets Origins and Destinations in LatLng as Parameters and it
	 * returns the DistanceMatrix Containing total distance and in meters and
	 * duration Restrictions Currently avoiding Tolls
	 * 
	 * @param origins
	 *            starting point
	 * @param destinations
	 *            ending point
	 * @return DistanceAPIBean
	 */
	public String getArriveTime(LatLng driver, LatLng passenger) {
		logger.info("******Entering in googleDistanceAPI  ******");
		context = new GeoApiContext().setApiKey(apiKey);
		DistanceMatrix matrix = null;
		try {

			matrix = DistanceMatrixApi.newRequest(context)
					.origins(driver)
					.destinations(passenger)
					.mode(TravelMode.DRIVING).language("en-AU")
					.avoid(RouteRestriction.TOLLS).units(Unit.METRIC).await();

			return matrix.rows[0].elements[0].duration.inSeconds+"";

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("******Exiting from googleDistanceAPIV2 with exception "+ ex.getMessage()+" ******");
		}

		return null;
	}

	/**
	 * This method gives us the result string (JSON|XML) according to criteria
	 * parameters for Google Places API
	 * 
	 * @param latitude
	 * @param longitude
	 * @param format
	 *            e.g json/xml
	 * @param radius
	 *            to limit search
	 * @param type
	 *            e.g restaurant, bus or defined type
	 * @return Result String
	 */
	public String googlePlacesAPITest(String latitude, String longitude,
			String format, String radius, String type) {

		String targetURL = GOOGLE_MAPS_API_URL + GOOGLE_PLACES_API_URL + format;
		String resultString = "";
		String temp;
		try {

			GooglePlaces client = new GooglePlaces(apiKey);
			List<Place> places = client.getNearbyPlaces(
					Double.parseDouble(latitude),
					Double.parseDouble(longitude), Integer.parseInt(radius));
			URL url = new URL(targetURL + "?location=" + latitude + ','
					+ longitude + "&type=" + type + "&radius=" + radius
					+ "&key=" + apiKey);
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferResult = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()));

			while ((temp = bufferResult.readLine()) != null) {
				resultString += temp;
			}

			bufferResult.close();
			return resultString;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * This methods return directions and time to cover the desired distance
	 * 
	 * @param origins
	 *            starting point
	 * @param destinations
	 *            ending point
	 * @return DirectionsRoute[]
	 */
	public DirectionsRoute[] googleDirectionAPITest(String origins,
			String destinations) {
		context = new GeoApiContext().setApiKey(apiKey);
		try {
			DirectionsRoute[] d = DirectionsApi.newRequest(context)
					.origin("paris metro bibliotheque francois mitterrand")
					.destination("paris metro pyramides").await();
			return DirectionsApi.getDirections(context, origins, destinations)
					.await();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	/**
	 * This method will give us suburb on the base of longitude and latitude
	 */
	public String getSuburbFromLngLat(LatLng latLng) {
		context = new GeoApiContext().setApiKey(apiKey);
		String suburb = null;
		GeocodingResult result[] = GeocodingApi.newRequest(context)
				.latlng(latLng).resultType(AddressType.SUBLOCALITY)
				.awaitIgnoreError();
		try {
			suburb = result[0].addressComponents[0].longName;
		} catch (Exception e) {
			GeocodingResult r[] = GeocodingApi.newRequest(context)
					.latlng(latLng).resultType(AddressType.LOCALITY)
					.awaitIgnoreError();
			if(r.length == 0){
				return "";
			}
			return r[0].addressComponents[0].longName;
		}
		
		return result[0].addressComponents[0].longName;

	}
	
	
	/**
	 * This method will give us city on the base of longitude and latitude
	 */
	public String  getCityFromLngLat(LatLng latLng){
		
		GeocodingResult r[] = GeocodingApi.newRequest(context).latlng(latLng).resultType(AddressType.LOCALITY).awaitIgnoreError();
		return r[0].addressComponents[0].longName;
		
	}

	/**
	 * This method provide us with Map<AppUserID,MinTime> for available driver
	 */
	public Map<String, Object> getLeastPickupTime(List<DistanceAPIBean> list,
			SafeHerDecorator decorator, LatLng latLng, Map<String, Object> map) {
		logger.info("******Entering in getLeastPickupTime with LatLng ******"+latLng.toUrlValue());
		Double minTime = 0d;
		
		googleDistanceAPILatLng(list, latLng);
		if (!list.isEmpty() || list != null) {
			minTime = list.get(0).getTotalTimeSeconds() ;
		}
		for (DistanceAPIBean distanceBean : list) {
			if (distanceBean.getTotalTimeSeconds() != null) {
				if (minTime >= distanceBean.getTotalTimeSeconds()) {
					minTime = distanceBean.getTotalTimeSeconds();
					map.put("driver_id", distanceBean.getAppUserId());
					if(minTime < LEAST_TIME){
						map.put("estimatedTime", LEAST_TIME);
					}else{
						map.put("estimatedTime", minTime);
					}

				}
			}
		}
		logger.info("******Exiting from getLeastPickupTime with map " +Arrays.asList(map)+"******");
		return map;

	}

	/**
	 * This method will call Distance API on the base of Longitude and Latitude
	 * 
	 * @param distanceAPIBeans
	 * @param latLngOrigins
	 * @return
	 */
	private List<DistanceAPIBean> googleDistanceAPILatLng(
			List<DistanceAPIBean> distanceAPIBeans, LatLng latLngOrigins) {
		logger.info("******Entering in googleDistanceAPILatLng with LatLng origins and Distance API Bean******"+latLngOrigins.toUrlValue()+" "+distanceAPIBeans);
		context = new GeoApiContext().setApiKey(apiKey);
		DistanceMatrix matrix = null;
		List<DistanceAPIBean> tempDistanceAPIBean = new ArrayList<DistanceAPIBean>();
		for (DistanceAPIBean distanceAPIBean : distanceAPIBeans) {
			try {

				matrix = DistanceMatrixApi.newRequest(context)
						.origins(latLngOrigins)
						.destinations(distanceAPIBean.getLatLngDestinations())
						.mode(TravelMode.DRIVING).language("en-AU")
						.avoid(RouteRestriction.TOLLS).units(Unit.METRIC)
						.await();

				if (matrix.rows[0].elements[0].distance != null) {

					distanceAPIBean
							.setTotalDistanceMeters((double) matrix.rows[0].elements[0].distance.inMeters);
					distanceAPIBean
							.setTotalTimeSeconds((double) matrix.rows[0].elements[0].duration.inSeconds);
					tempDistanceAPIBean.add(distanceAPIBean);

				}
				logger.info("******DistanceAPIBean googleDistanceAPILatLng "+ distanceAPIBean+" ******");

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.info("******Exiting from getLeastPickupTime with exception " +ex.getMessage()+"******");
			}
		}

		return distanceAPIBeans;
	}

	/**
	 * This method provides us with Active Drivers Converted in Distance API
	 * Bean
	 */
	public List<DistanceAPIBean> getActiveDriverDistanceAPI(String suburb) {
		List<ActiveDriverLocationEntity> activeDriverLocationEntities = mapDao
				.getDriverList(suburb);
		/*if (activeDriverLocationEntities.isEmpty()
				|| activeDriverLocationEntities == null) {
			activeDriverLocationEntities = mapDao.getDriverListEtn(suburb);
		}*/
		return distanceAPIConverter
				.convertActiveDriverEntityToDistanceBean(activeDriverLocationEntities);
	}

	public List<DistanceAPIBean> getActiveDriversPhysicaltoDistanceAPI() {

		List<ActiveDriverLocationEntity> activeDriversList = mapDao
				.getAllByAttribute(ActiveDriverLocationEntity.class,
						"isPhysical", "0");
		return distanceAPIConverter
				.convertActiveDriverEntityToDistanceBean(activeDriversList);

	}

	/**
	 * This method provides us with Active Drivers with Max Distance Converted in Distance API
	 * Bean
	 */
	public List<DistanceAPIBean> getActiveDriverMaxDistanceAPI(String lat, String lng) {
		logger.info("******Entering in getActiveDriverMaxDistanceAPI with Lat Lng "+lat+" "+lng+"******");
		String maxDistance = Common.getValueFromSpecificPropertieFile("/properties/distance.properties", "MaxDistance");
		List<ActiveDriverLocationEntity> activeDriverLocationEntities = mapDao.getDriverListFromQuery(lat, lng, maxDistance);
		logger.info("******Active Drivers in getActiveDriverMaxDistanceAPI with size "+activeDriverLocationEntities.size()+"******");
		return distanceAPIConverter
				.convertActiveDriverEntityToDistanceBean(activeDriverLocationEntities);
	}
	
	/**
	 * This method provides us with Active Drivers Converted in Distance API
	 * Bean
	 */
	public List<DistanceAPIBean> getActiveDriverMinDistanceAPI(String lat, String lng) {
		logger.info("******Entering in getActiveDriverMinDistanceAPI with Lat Lng "+lat+" "+lng+"******");
		String minDistance = Common.getValueFromSpecificPropertieFile("/properties/distance.properties", "MinDistance");
		List<ActiveDriverLocationEntity> activeDriverLocationEntities = mapDao.getDriverListFromQuery(lat, lng, minDistance);
		logger.info("******Active Drivers in getActiveDriverMinDistanceAPI with size "+activeDriverLocationEntities.size()+" and List of ActiveDriverLocationEntity "+activeDriverLocationEntities.toArray().toString()+"******");
		return distanceAPIConverter
				.convertActiveDriverEntityToDistanceBean(activeDriverLocationEntities);
	}
	
	public List<DistanceAPIBean> getActiveDriverstoDistanceAPI() {
		logger.info("******Entering in getActiveDriverstoDistanceAPI with Lat Lng ******");
		List<ActiveDriverLocationEntity> activeDriversList = mapDao
				.getAllActiveDrivers();
		logger.info("******Active Drivers in getActiveDriverstoDistanceAPI with size "+activeDriversList.size()+" and List of ActiveDriverLocationEntity "+activeDriversList.toArray().toString()+"******");
		return distanceAPIConverter
				.convertActiveDriverEntityToDistanceBean(activeDriversList);

	}

	public List<DistanceAPIBean> getActiveDriverstoDistanceAPIMongo() {
		logger.info("******Entering in getActiveDriverstoDistanceAPI with Lat Lng ******");
		/*List<ActiveDriverLocationEntity> activeDriversList = mapDao
				.getAllActiveDrivers();*/
		List<ActiveDriverLocationMongoEntity> activeDriversList = activeDriverLocationRepository.findAll();
		logger.info("******Active Drivers in getActiveDriverstoDistanceAPI with size "+activeDriversList.size()+" and List of ActiveDriverLocationEntity "+activeDriversList.toArray().toString()+"******");
		return distanceAPIConverter
				.convertActiveDriverEntityToDistanceBeanMongo(activeDriversList);

	}
	
	public List<Map<String, Object>> getDriversWithinDistance(
			List<DistanceAPIBean> list, LatLng latLng) {
		logger.info("******Entering in getDriversWithinDistance with LatLng " +latLng.toUrlValue()+ "******");
		googleDistanceAPILatLng(list, latLng);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (DistanceAPIBean distanceBean : list) {
			if (distanceBean.getTotalTimeSeconds() != null) {
				map = new HashMap<String, Object>();
				if (distanceBean.getTotalDistanceMeters() <= APPROXIMATE_DISTANCE) {
					map.put("driver_id", distanceBean.getAppUserId());
					map.put("driverLatitude", distanceBean.getLatDestinations());
					map.put("driverLongitude",
							distanceBean.getLngDestinations());
					map.put("driverStatus", distanceBean.getDriverStatus());
					mapList.add(map);
				}
			}
		}
		return mapList;
	}

	/**
	 * This method takes Decorator and Send Notifications to Active Drivers
	 * within suburb
	 * 
	 * @param decorator
	 * @throws GenericException
	 */
	@SuppressWarnings("unused")
	public List<RideSearchResultBean> pushNotificationsToActiveDrivers(
			SafeHerDecorator decorator) throws GenericException {
		logger.info("******Entering in pushNotificationsToActiveDrivers ******** ");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		List<RideSearchResultBean> rideSearchResultList = new ArrayList<RideSearchResultBean>();
		RideSearchResultBean rideSearchResultBean = null;
		List<DistanceAPIBean> activeDriversList = null;
		RideCriteriaBean rideCriteriaBean = (RideCriteriaBean) decorator
				.getDataBean();
		String PassengerId = rideCriteriaBean.getAppUserId();
		logger.info("Push Notification to Active Drivers Start from Passenger Id "+ PassengerId +" with source Latitude "+rideCriteriaBean.getSourceLat()+" and Longitude "+rideCriteriaBean.getSourceLong());
		if (StringUtil.isNotEmpty(rideCriteriaBean.getSourceLat()) && StringUtil.isNotEmpty(rideCriteriaBean.getSourceLong())) {
			activeDriversList = getActiveDriverMinDistanceAPI(rideCriteriaBean.getSourceLat(),rideCriteriaBean.getSourceLong());
		}else{
			logger.info("Push Notification to Active Drivers End with Exception ");
			throw new GenericRuntimeException("Your Souce Lat and long is not valid");
		}
		
		if (CollectionUtil.isEmpty(activeDriversList)
				&& activeDriversList.size() == 0 ) {
			
			UserLoginEntity userLoginEntity = mapDao.findByIdParam2(Integer
					.parseInt(PassengerId));

			if (StringUtil.isNotEmpty(userLoginEntity.getOsType())
					&& userLoginEntity.getOsType().compareTo("0") != 0) {

				try {
					logger.info("No Active Drivers, Looking Deeper Details");
					pushAndriod
							.pushAndriodPassengerNotification(
									userLoginEntity.getKeyToken(),
									PassengerId,
									"",
									"",
									"",
									PushNotificationStatus.NoActiveDriverInSuburb
											.toString(),
									"Drivers not found, please wait");
					

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.info("Active Drivers, Ended with Exception "+e.getMessage());
					throw new GenericRuntimeException(
							"Error in GCM Google APi");
				}
				
			} else {
				try {
					logger.info("No Active Drivers, Looking Deeper Details");
					pushIOS.pushIOSPassenger(userLoginEntity.getKeyToken(),
							userLoginEntity.getIsDev(), PassengerId, "", "",
							"", PushNotificationStatus.NoActiveDriverInSuburb
									.toString(),
							"Drivers not found, please wait", userLoginEntity.getFcmToken());
					

				} catch (Exception e) {
					logger.info("Active Drivers, Ended with Exception "+e.getMessage());
					throw new GenericRuntimeException("Error in APNS APi");
				}
			}
			
			try{
				
				activeDriversList = getActiveDriverMaxDistanceAPI(rideCriteriaBean.getSourceLat(), rideCriteriaBean.getSourceLong());
			} catch (Exception ex) {
				throw new GenericException(
						"Sql query Error For getting Active Drivers");
			}
		}

		if (CollectionUtil.isNotEmpty(activeDriversList)
				&& activeDriversList.size() > 0) {
			logger.info("Notifying Active Drivers");
			for (DistanceAPIBean distanceBean : activeDriversList) {

				UserLoginEntity userLoginEntity = mapDao
						.findByIdParam2(distanceBean.getAppUserId());
				if (userLoginEntity != null) {
					// TODO: Manage Device Token
					if (!StringUtil.isEmpty(userLoginEntity.getKeyToken())
							&& userLoginEntity.getKeyToken() != null) {
						rideSearchResultBean = new RideSearchResultBean();
						rideSearchResultBean.setAppUserId(distanceBean
								.getAppUserId().toString());
						rideSearchResultBean.setDriverLat(distanceBean
								.getLatDestinations().toString());
						rideSearchResultBean.setDriverLong(distanceBean
								.getLngDestinations().toString());
						if (StringUtil.isNotEmpty(userLoginEntity.getOsType())
								&& userLoginEntity.getOsType().compareTo("0") != 0) {

							try {
								logger.info("Notifying Active Drivers KeyToken:"+userLoginEntity.getKeyToken()+" Driver App User Id"+distanceBean
										.getAppUserId());
								pushAndriod
								.pushAndriodDriverNotification(
										userLoginEntity.getKeyToken(),
										PassengerId,
										distanceBean
										.getAppUserId().toString(),
										rideCriteriaBean.getRequestNo(),"",
										PushNotificationStatus.searchRideCriteria
												.toString(),
										"Passenger Has Searched Request In Your Area!");

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new GenericRuntimeException(
										"Error in GCM Google APi");
							}
						} else {
							logger.info("Notifying Active Drivers KeyToken:"+userLoginEntity.getKeyToken()+" Driver App User Id"+distanceBean
									.getAppUserId());
							rideSearchResultBean.setOsType("0");
							pushIOS.pushIOSDriver(userLoginEntity.getKeyToken(),
									userLoginEntity.getIsDev(), PassengerId, distanceBean
									.getAppUserId().toString(), rideCriteriaBean.getRequestNo(),
									"", PushNotificationStatus.searchRideCriteria
											.toString(),
											"Passenger Has Searched Request In Your Area!", userLoginEntity.getFcmToken());
							
						}
						rideSearchResultList.add(rideSearchResultBean);
					}
				} else {
					// PushNotificationsIOS
					// .pushIOS(
					// "ee73fc39622a16d3e1003fde7f55aac1e72734357384ac7f6f02bd42789512b4",
					// "Driver", "dev");
				}
				// return rideSearchResultList;

			}
			return rideSearchResultList;
		} else {
			decorator.getErrors().add("Driver is not available in requested pickup location");
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
			return null;

		}

	}
	
	/**
	 * This V2 method takes Decorator and Send Notifications to Active Drivers
	 * within suburb
	 * 
	 * @param decorator
	 * @throws GenericException
	 */
	@SuppressWarnings("unused")
	public List<RideSearchResultBean> pushNotificationsToActiveDriversV2(
			SafeHerDecorator decorator) throws GenericException {
		logger.info("******Entering in pushNotificationsToActiveDriversV2 ******** ");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		List<RideSearchResultBean> rideSearchResultList = new ArrayList<RideSearchResultBean>();
		RideSearchResultBean rideSearchResultBean = null;
		List<DistanceAPIBean> activeDriversList = null;
		RideCriteriaBean rideCriteriaBean = (RideCriteriaBean) decorator
				.getDataBean();
		logger.info("******RideCriteriaBean is "+rideCriteriaBean+"******** ");
		String PassengerId = rideCriteriaBean.getAppUserId();
		logger.info("Push Notification to Active Drivers Start from Passenger Id "+ PassengerId +" with source Latitude "+rideCriteriaBean.getSourceLat()+" and Longitude "+rideCriteriaBean.getSourceLong());
		if (StringUtil.isNotEmpty(rideCriteriaBean.getSourceLat()) && StringUtil.isNotEmpty(rideCriteriaBean.getSourceLong())) {
			/*activeDriversList = getActiveDriverMinDistanceAPI(rideCriteriaBean.getSourceLat(),rideCriteriaBean.getSourceLong());*/
			activeDriversList = getActiveDriverMinDistanceAPIMongo(rideCriteriaBean.getSourceLat(),rideCriteriaBean.getSourceLong());
		}else{
			logger.info("Push Notification to Active Drivers End with Exception ");
			throw new GenericRuntimeException("Your Souce Lat and long is not valid");
		}
		
		if (CollectionUtil.isEmpty(activeDriversList)
				&& activeDriversList.size() == 0 ) {
			
			UserLoginEntity userLoginEntity = mapDao.findByIdParam2(Integer
					.parseInt(PassengerId));

			if (StringUtil.isNotEmpty(userLoginEntity.getOsType())
					&& userLoginEntity.getOsType().compareTo("0") != 0) {

				try {
					logger.info("No Active Drivers in pushNotificationsToActiveDriversV2, Looking Deeper Details");
					pushAndriod
							.pushAndriodPassengerNotification(
									userLoginEntity.getKeyToken(),
									PassengerId,
									"",
									"",
									"",
									PushNotificationStatus.NoActiveDriverInSuburb
											.toString(),
									"Drivers not found, please wait");
					

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.info("Active Drivers pushNotificationsToActiveDriversV2, Ended with Exception "+e.getMessage());
					throw new GenericRuntimeException(
							"Error in GCM Google APi");
				}
				
			} else {
				try {
					logger.info("No Active Drivers pushNotificationsToActiveDriversV2, Looking Deeper Details");
					pushIOS.pushIOSPassenger(userLoginEntity.getKeyToken(),
							userLoginEntity.getIsDev(), PassengerId, "", "",
							"", PushNotificationStatus.NoActiveDriverInSuburb
									.toString(),
							"Drivers not found, please wait", userLoginEntity.getFcmToken());
					

				} catch (Exception e) {
					logger.info("Active Drivers pushNotificationsToActiveDriversV2, Ended with Exception "+e.getMessage());
					throw new GenericRuntimeException("Error in APNS APi");
				}
			}
			
			try{
				
				/*activeDriversList = getActiveDriverMaxDistanceAPI(rideCriteriaBean.getSourceLat(), rideCriteriaBean.getSourceLong());*/
				activeDriversList = getActiveDriverMaxDistanceAPIMongo(rideCriteriaBean.getSourceLat(),rideCriteriaBean.getSourceLong());
			} catch (Exception ex) {
				throw new GenericException(
						"Sql query Error For getting Active Drivers");
			}
		}

		if (CollectionUtil.isNotEmpty(activeDriversList)
				&& activeDriversList.size() > 0) {
			logger.info("Notifying Active Drivers in pushNotificationsToActiveDriversV2");
			for (DistanceAPIBean distanceBean : activeDriversList) {

				UserLoginEntity userLoginEntity = mapDao
						.findByIdParam2(distanceBean.getAppUserId());
				if (userLoginEntity != null) {
					// TODO: Manage Device Token
					if (!StringUtil.isEmpty(userLoginEntity.getKeyToken())
							&& userLoginEntity.getKeyToken() != null) {
						rideSearchResultBean = new RideSearchResultBean();
						rideSearchResultBean.setAppUserId(distanceBean
								.getAppUserId().toString());
						rideSearchResultBean.setDriverLat(distanceBean
								.getLatDestinations().toString());
						rideSearchResultBean.setDriverLong(distanceBean
								.getLngDestinations().toString());
						if (StringUtil.isNotEmpty(userLoginEntity.getOsType())
								&& userLoginEntity.getOsType().compareTo("0") != 0) {

							try {
								logger.info("Notifying Active Drivers in pushNotificationsToActiveDriversV2 KeyToken:"+userLoginEntity.getKeyToken()+" Driver App User Id"+distanceBean
										.getAppUserId());
								pushAndriod
								.pushAndriodDriverNotification(
										userLoginEntity.getKeyToken(),
										PassengerId,
										distanceBean
										.getAppUserId().toString(),
										rideCriteriaBean.getRequestNo(),"",
										PushNotificationStatus.searchRideCriteria
												.toString(),
										"Passenger Has Searched Request In Your Area!");

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new GenericRuntimeException(
										"Error in GCM Google APi");
							}
						} else {
							logger.info("Notifying Active Drivers in pushNotificationsToActiveDriversV2 KeyToken:"+userLoginEntity.getKeyToken()+" Driver App User Id"+distanceBean
									.getAppUserId());
							rideSearchResultBean.setOsType("0");
							pushIOS.pushIOSDriver(userLoginEntity.getKeyToken(),
									userLoginEntity.getIsDev(), PassengerId, distanceBean
									.getAppUserId().toString(), rideCriteriaBean.getRequestNo(),
									"", PushNotificationStatus.searchRideCriteria
											.toString(),
											"Passenger Has Searched Request In Your Area!", userLoginEntity.getFcmToken());
							
						}
						rideSearchResultList.add(rideSearchResultBean);
					}
				} else {
					// PushNotificationsIOS
					// .pushIOS(
					// "ee73fc39622a16d3e1003fde7f55aac1e72734357384ac7f6f02bd42789512b4",
					// "Driver", "dev");
				}
				// return rideSearchResultList;

			}
			return rideSearchResultList;
		} else {
			decorator.getErrors().add("Driver is not available in requested pickup location");
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
			return null;

		}

	}
	
	/**
	 * This method provides us with Active Drivers with Max Distance Converted in Distance API
	 * Bean
	 */
	public List<DistanceAPIBean> getActiveDriverMaxDistanceAPIMongo(String lat, String lng) {
		logger.info("******Entering in getActiveDriverMaxDistanceAPI with Lat Lng "+lat+" "+lng+"******");
		String maxDistance = Common.getValueFromSpecificPropertieFile("/properties/distance.properties", "MaxDistance");
		String totalDrivers = Common.getValueFromSpecificPropertieFile("/properties/distance.properties", "TotalDrivers");
		Pageable pageable = new PageRequest(0, Integer.parseInt(totalDrivers));
		List<ActiveDriverLocationMongoEntity> activeDriverLocationMongoEntities = activeDriverLocationRepository.findByLocNearAndIsPhysicalAndIsOnlineAndIsRequestedAndIsBooked(new Point(Double.parseDouble(lat), Double.parseDouble(lng)),new Distance(Double.parseDouble(maxDistance), Metrics.KILOMETERS),"1","1","0","0",pageable);
		logger.info("******Active Drivers in getActiveDriverMaxDistanceAPI with size "+activeDriverLocationMongoEntities.size()+"******");
		 return distanceAPIConverter
			.convertActiveDriverEntityToDistanceBeanMongo(activeDriverLocationMongoEntities);
	}
	
	/**
	 * This method provides us with Active Drivers Converted in Distance API
	 * Bean
	 */
	public List<DistanceAPIBean> getActiveDriverMinDistanceAPIMongo(String lat, String lng) {
		
		logger.info("******Entering in getActiveDriverMinDistanceAPI with Lat Lng "+lat+" "+lng+"******");
		String minDistance = Common.getValueFromSpecificPropertieFile("/properties/distance.properties", "MinDistance");
		String totalDrivers = Common.getValueFromSpecificPropertieFile("/properties/distance.properties", "TotalDrivers");
		Pageable pageable = new PageRequest(0, Integer.parseInt(totalDrivers));
		List<ActiveDriverLocationMongoEntity> activeDriverLocationMongoEntities = activeDriverLocationRepository.findByLocNearAndIsPhysicalAndIsOnlineAndIsRequestedAndIsBooked(new Point(Double.parseDouble(lat), Double.parseDouble(lng)),new Distance(Double.parseDouble(minDistance), Metrics.KILOMETERS),"1","1","0","0",pageable);
		/*List<ActiveDriverLocationEntity> activeDriverLocationEntities = mapDao.getDriverListFromQuery(lat, lng, minDistance);
		logger.info("******Active Drivers in getActiveDriverMinDistanceAPI with size "+activeDriverLocationEntities.size()+" and List of ActiveDriverLocationEntity "+activeDriverLocationEntities.toArray().toString()+"******");*/
		return distanceAPIConverter
				.convertActiveDriverEntityToDistanceBeanMongo(activeDriverLocationMongoEntities);
	}
	
	/**
	 * This method takes Decorator and Send Notifications to Active Drivers
	 * within suburb
	 * 
	 * @param decorator
	 * @throws GenericException
	 */
	@SuppressWarnings("unused")
	public List<RideSearchResultBean> pushNotificationsToActiveDriversMongo(
			SafeHerDecorator decorator) throws GenericException {
		logger.info("******Entering in pushNotificationsToActiveDrivers ******** ");
		PushIOS pushIOS = new PushIOS();
		PushAndriod pushAndriod = new PushAndriod();
		List<RideSearchResultBean> rideSearchResultList = new ArrayList<RideSearchResultBean>();
		RideSearchResultBean rideSearchResultBean = null;
		List<DistanceAPIBean> activeDriversList = null;
		RideCriteriaBean rideCriteriaBean = (RideCriteriaBean) decorator
				.getDataBean();
		String PassengerId = rideCriteriaBean.getAppUserId();
		logger.info("Push Notification to Active Drivers Start from Passenger Id "+ PassengerId +" with source Latitude "+rideCriteriaBean.getSourceLat()+" and Longitude "+rideCriteriaBean.getSourceLong());
		if (StringUtil.isNotEmpty(rideCriteriaBean.getSourceLat()) && StringUtil.isNotEmpty(rideCriteriaBean.getSourceLong())) {
			activeDriversList = getActiveDriverMinDistanceAPIMongo(rideCriteriaBean.getSourceLat(),rideCriteriaBean.getSourceLong());
		}else{
			logger.info("Push Notification to Active Drivers End with Exception ");
			throw new GenericRuntimeException("Your Souce Lat and long is not valid");
		}
		
		if (CollectionUtil.isEmpty(activeDriversList)
				&& activeDriversList.size() == 0 ) {
			
			UserLoginEntity userLoginEntity = mapDao.findByIdParam2(Integer
					.parseInt(PassengerId));

			if (StringUtil.isNotEmpty(userLoginEntity.getOsType())
					&& userLoginEntity.getOsType().compareTo("0") != 0) {

				try {
					logger.info("No Active Drivers, Looking Deeper Details");
					pushAndriod
							.pushAndriodPassengerNotification(
									userLoginEntity.getKeyToken(),
									PassengerId,
									"",
									"",
									"",
									PushNotificationStatus.NoActiveDriverInSuburb
											.toString(),
									"Drivers not found, please wait");
					

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.info("Active Drivers, Ended with Exception "+e.getMessage());
					throw new GenericRuntimeException(
							"Error in GCM Google APi");
				}
				
			} else {
				try {
					logger.info("No Active Drivers, Looking Deeper Details");
					pushIOS.pushIOSPassenger(userLoginEntity.getKeyToken(),
							userLoginEntity.getIsDev(), PassengerId, "", "",
							"", PushNotificationStatus.NoActiveDriverInSuburb
									.toString(),
							"Drivers not found, please wait", userLoginEntity.getFcmToken());
					

				} catch (Exception e) {
					logger.info("Active Drivers, Ended with Exception "+e.getMessage());
					throw new GenericRuntimeException("Error in APNS APi");
				}
			}
			
			try{
				
				activeDriversList = getActiveDriverMaxDistanceAPIMongo(rideCriteriaBean.getSourceLat(), rideCriteriaBean.getSourceLong());
			} catch (Exception ex) {
				throw new GenericException(
						"Sql query Error For getting Active Drivers");
			}
		}

		if (CollectionUtil.isNotEmpty(activeDriversList)
				&& activeDriversList.size() > 0) {
			logger.info("Notifying Active Drivers");
			for (DistanceAPIBean distanceBean : activeDriversList) {

				UserLoginEntity userLoginEntity = mapDao
						.findByIdParam2(distanceBean.getAppUserId());
				if (userLoginEntity != null) {
					// TODO: Manage Device Token
					if (!StringUtil.isEmpty(userLoginEntity.getKeyToken())
							&& userLoginEntity.getKeyToken() != null) {
						rideSearchResultBean = new RideSearchResultBean();
						rideSearchResultBean.setAppUserId(distanceBean
								.getAppUserId().toString());
						rideSearchResultBean.setDriverLat(distanceBean
								.getLatDestinations().toString());
						rideSearchResultBean.setDriverLong(distanceBean
								.getLngDestinations().toString());
						rideSearchResultBean.setLoc(new Point(Double.parseDouble(distanceBean
								.getLatDestinations().toString()),Double.parseDouble(distanceBean
								.getLngDestinations().toString())));
						if (StringUtil.isNotEmpty(userLoginEntity.getOsType())
								&& userLoginEntity.getOsType().compareTo("0") != 0) {

							try {
								logger.info("Notifying Active Drivers KeyToken:"+userLoginEntity.getKeyToken()+" Driver App User Id"+distanceBean
										.getAppUserId());
								pushAndriod
								.pushAndriodDriverNotification(
										userLoginEntity.getKeyToken(),
										PassengerId,
										distanceBean
										.getAppUserId().toString(),
										rideCriteriaBean.getRequestNo(),"",
										PushNotificationStatus.searchRideCriteria
												.toString(),
										"Passenger Has Searched Request In Your Area!");

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new GenericRuntimeException(
										"Error in GCM Google APi");
							}
						} else {
							logger.info("Notifying Active Drivers KeyToken:"+userLoginEntity.getKeyToken()+" Driver App User Id"+distanceBean
									.getAppUserId());
							rideSearchResultBean.setOsType("0");
							pushIOS.pushIOSDriver(userLoginEntity.getKeyToken(),
									userLoginEntity.getIsDev(), PassengerId, distanceBean
									.getAppUserId().toString(), rideCriteriaBean.getRequestNo(),
									"", PushNotificationStatus.searchRideCriteria
											.toString(),
											"Passenger Has Searched Request In Your Area!", userLoginEntity.getFcmToken());
							
						}
						rideSearchResultList.add(rideSearchResultBean);
					}
				} else {
					// PushNotificationsIOS
					// .pushIOS(
					// "ee73fc39622a16d3e1003fde7f55aac1e72734357384ac7f6f02bd42789512b4",
					// "Driver", "dev");
				}
				// return rideSearchResultList;

			}
			return rideSearchResultList;
		} else {
			decorator.getErrors().add("Driver is not available in requested pickup location");
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
			return null;

		}

	}
	
	public List<Map<String, Object>> getDriversWithinDistanceMongo(
			List<DistanceAPIBean> list, LatLng latLng) {
		logger.info("******Entering in getDriversWithinDistance with LatLng " +latLng.toUrlValue()+ "******");
		googleDistanceAPILatLng(list, latLng);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (DistanceAPIBean distanceBean : list) {
			if (distanceBean.getTotalTimeSeconds() != null) {
				map = new HashMap<String, Object>();
				if (distanceBean.getTotalDistanceMeters() <= APPROXIMATE_DISTANCE) {
					map.put("driver_id", distanceBean.getAppUserId());
					map.put("driverLatitude", distanceBean.getLatDestinations());
					map.put("driverLongitude",
							distanceBean.getLngDestinations());
					map.put("driverStatus", distanceBean.getDriverStatus());
					mapList.add(map);
				}
			}
		}
		return mapList;
	}
}