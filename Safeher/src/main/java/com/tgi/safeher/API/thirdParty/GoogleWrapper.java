package com.tgi.safeher.API.thirdParty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.maps.model.LatLng;
import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.service.ICalculatorService;
import com.tgi.safeher.service.safeHerMapService.IGoogleMapsAPIService;
import com.tgi.safeher.utils.CollectionUtil;
import com.tgi.safeher.utils.StringUtil;

@Component
public class GoogleWrapper implements Serializable {

	/**
	 * Wrapper Class for GoogleAPI use
	 */
	private static final Logger logger = Logger.getLogger(GoogleWrapper.class);
	private static final long serialVersionUID = 1L;
	@Autowired
	private IGoogleMapsAPIService googleMapsAPIService;
	@Autowired
	private ICalculatorService calculatorService;

	/**
	 * Perform operation using origins and destination
	 */
	public void googleDistanceAPI(SafeHerDecorator safeHerDecorator) {
		logger.info("******Entering in googleDistanceAPI  ******");
		// googleMapsAPIService.getSuburbFromLngLat("31.554606", "74.357158");
		DistanceAPIBean distanceAPIBean = googleMapsAPIService
				.googleDistanceAPIV2((DistanceAPIBean) safeHerDecorator
						.getDataBean());
		logger.info("******In googleDistanceAPI DistanceAPIBean with origins "+ distanceAPIBean.getOrigins() +"and destinition "+distanceAPIBean.getDestinations()+" ******");
		calculatorService.calculateFare(distanceAPIBean);
		safeHerDecorator.getResponseMap().put("data",
				safeHerDecorator.getDataBean());
		logger.info("******In googleDistanceAPI DistanceAPIBean response " + Arrays.asList(safeHerDecorator.getResponseMap()));
		safeHerDecorator
				.setResponseMessage("Successfully executed Google DistanceAPI");
		safeHerDecorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());

	}

	/**
	 * Perform operation to get Estimated Arrival Time
	 * 
	 * @param safeHerDecorator
	 */
	public void getEstimatedArrivalTime(SafeHerDecorator safeHerDecorator) {
		logger.info("****** Entering in getEstimatedArrivalTime  ******");
		Map<String, Object> map = new HashMap<String, Object>();
		DistanceAPIBean distanceAPIBean = (DistanceAPIBean) safeHerDecorator
				.getDataBean();
		//TODO: To be Tested
		/*String suburb = googleMapsAPIService.getSuburbFromLngLat(new LatLng(
				distanceAPIBean.getLatDestinations(), distanceAPIBean
						.getLngDestinations()));*/
		//if (!StringUtil.isEmpty(suburb)) {
		if(StringUtil.isNotEmpty(distanceAPIBean.getLatDestinations().toString())&&StringUtil.isNotEmpty(distanceAPIBean.getLngDestinations().toString())){
			/*List<DistanceAPIBean> distanceAPIBeanList = googleMapsAPIService
					.getActiveDriverMinDistanceAPI(distanceAPIBean.getLatDestinations().toString(), distanceAPIBean.getLngDestinations().toString());*/
			List<DistanceAPIBean> distanceAPIBeanList = googleMapsAPIService
					.getActiveDriverMinDistanceAPIMongo(distanceAPIBean.getLatDestinations().toString(), distanceAPIBean.getLngDestinations().toString());
			if(CollectionUtil.isNotEmpty(distanceAPIBeanList) || distanceAPIBeanList !=null){
			map = googleMapsAPIService.getLeastPickupTime(distanceAPIBeanList,
					safeHerDecorator,
					new LatLng(distanceAPIBean.getLatDestinations(),
							distanceAPIBean.getLngDestinations()), map);
			}
		}
		//}
		logger.info("****** In getEstimatedArrivalTime with LatLng "+distanceAPIBean.getLatDestinations().toString()+" "+distanceAPIBean.getLngDestinations().toString()+" response Map "+ Arrays.asList(map)+ " ******");	
		safeHerDecorator.getResponseMap().put("data", map);
		safeHerDecorator
				.setResponseMessage("Successfully Executed Estimate Arrival Time");
		safeHerDecorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());

	}

	/**
	 * Perform operation to get Active Drivers within Distance
	 * 
	 * @param safeHerDecorator
	 */
	public void getActiveDriversList(SafeHerDecorator safeHerDecorator) {
		logger.info("****** Entering in getActiveDriversList  ******");
		DistanceAPIBean distanceAPIBean = (DistanceAPIBean) safeHerDecorator
				.getDataBean();
		/*List<DistanceAPIBean> distanceAPIBeansList = googleMapsAPIService
				.getActiveDriverstoDistanceAPI();*/
		List<DistanceAPIBean> distanceAPIBeansList = googleMapsAPIService
				.getActiveDriverstoDistanceAPIMongo();
		/*List<Map<String, Object>> mapList = googleMapsAPIService
				.getDriversWithinDistance(distanceAPIBeansList,
						new LatLng(distanceAPIBean.getLatDestinations(),
								distanceAPIBean.getLngDestinations()));*/
		List<Map<String, Object>> mapList = googleMapsAPIService
				.getDriversWithinDistanceMongo(distanceAPIBeansList,
						new LatLng(distanceAPIBean.getLatDestinations(),
								distanceAPIBean.getLngDestinations()));
		safeHerDecorator.getResponseMap().put("data", mapList);
		logger.info("****** In getActiveDriversList with LatLng "+distanceAPIBean.getLatDestinations().toString()+" "+distanceAPIBean.getLngDestinations().toString()+" response Map "+ Arrays.asList(mapList)+ " ******");	
		safeHerDecorator
				.setResponseMessage("Successfully Executed Active Driver List");
		safeHerDecorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
	}

	/**
	 * Perform operation to get Active Drivers within Distance
	 * 
	 * @param safeHerDecorator
	 */
	public void getActiveDriversListFromQuery(SafeHerDecorator safeHerDecorator) {
		logger.info("****** Entering in getActiveDriversListFromQuery  ******");
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		DistanceAPIBean distanceAPIBean = (DistanceAPIBean) safeHerDecorator
				.getDataBean();
		List<DistanceAPIBean> distanceAPIBeansList = googleMapsAPIService.
				getActiveDriverMinDistanceAPI(distanceAPIBean.getLatDestinations().toString(),
						distanceAPIBean.getLngDestinations().toString());
		System.out.println("SIzeeeeeeeeeeeeeeeeeeeeActiveDriversssssss: "+distanceAPIBeansList.size());
		if(distanceAPIBeansList != null && distanceAPIBeansList.size() > 0){
			for (DistanceAPIBean distanceBean : distanceAPIBeansList) {
				map = new HashMap<String, Object>();
				map.put("driver_id", distanceBean.getAppUserId());
				map.put("driverLatitude", distanceBean.getLatDestinations());
				map.put("driverLongitude",
						distanceBean.getLngDestinations());
				map.put("preLat", distanceBean.getPreLat());
				map.put("preLng",
						distanceBean.getPreLng());
				map.put("direction", distanceBean.getDirection());
				map.put("driverStatus", distanceBean.getDriverStatus());
				mapList.add(map);
			}
			
			safeHerDecorator.getResponseMap().put("data", mapList);
			logger.info("****** In getActiveDriversList with LatLng "+distanceAPIBean.getLatDestinations().toString()+" "+distanceAPIBean.getLngDestinations().toString()+" response Map "+ Arrays.asList(mapList)+ " ******");	
			safeHerDecorator
					.setResponseMessage("Successfully Executed Active Driver List");
			safeHerDecorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
		}else{
			safeHerDecorator.setResponseMessage("No active driver found near by you");
			safeHerDecorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
		}
	}
	
	
	//Mongo
	/**
	 * Perform operation to get Active Drivers within Distance
	 * 
	 * @param safeHerDecorator
	 */
	public void getActiveDriversListFromQueryMongo(SafeHerDecorator safeHerDecorator) {
		logger.info("****** Entering in getActiveDriversListFromQuery  ******");
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		DistanceAPIBean distanceAPIBean = (DistanceAPIBean) safeHerDecorator
				.getDataBean();
		/*List<DistanceAPIBean> distanceAPIBeansList = googleMapsAPIService.
				getActiveDriverMinDistanceAPI(distanceAPIBean.getLatDestinations().toString(),
						distanceAPIBean.getLngDestinations().toString());*/
		List<DistanceAPIBean> distanceAPIBeansList = googleMapsAPIService.
				getActiveDriverMinDistanceAPIMongo(distanceAPIBean.getLatDestinations().toString(),
						distanceAPIBean.getLngDestinations().toString());
		
		System.out.println("SIzeeeeeeeeeeeeeeeeeeeeActiveDriversssssss: "+distanceAPIBeansList.size());
		if(distanceAPIBeansList != null && distanceAPIBeansList.size() > 0){
			for (DistanceAPIBean distanceBean : distanceAPIBeansList) {
				map = new HashMap<String, Object>();
				map.put("driver_id", distanceBean.getAppUserId());
				map.put("driverLatitude", distanceBean.getLatDestinations());
				map.put("driverLongitude",
						distanceBean.getLngDestinations());
				map.put("preLat", distanceBean.getPreLat());
				map.put("preLng",
						distanceBean.getPreLng());
				map.put("direction", distanceBean.getDirection());
				map.put("driverStatus", distanceBean.getDriverStatus());
				mapList.add(map);
			}
			
			safeHerDecorator.getResponseMap().put("data", mapList);
			logger.info("****** In getActiveDriversList with LatLng "+distanceAPIBean.getLatDestinations().toString()+" "+distanceAPIBean.getLngDestinations().toString()+" response Map "+ Arrays.asList(mapList)+ " ******");	
			safeHerDecorator
					.setResponseMessage("Successfully Executed Active Driver List");
			safeHerDecorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
		}else{
			safeHerDecorator.setResponseMessage("No active driver found near by you");
			safeHerDecorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
		}
	}
	
	
	
	
	
	public void getNotifyActiveDriversList(SafeHerDecorator safeHerDecorator) throws GenericException {
		logger.info("****** Entering in getActiveDriversListFromQuery  ******");
		/*googleMapsAPIService.pushNotificationsToActiveDrivers(safeHerDecorator);*/
		googleMapsAPIService.pushNotificationsToActiveDriversV2(safeHerDecorator);
		logger.info("****** Exiting from getActiveDriversListFromQuery  ******");
		
	}
	
	
}
