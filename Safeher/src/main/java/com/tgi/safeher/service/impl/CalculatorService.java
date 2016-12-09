package com.tgi.safeher.service.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.service.ICalculatorService;
@Service
@Transactional
@Scope("prototype")
public class CalculatorService implements ICalculatorService {

	 private static final Logger logger = Logger.getLogger(CalculatorService.class);
	 private static final double BASE_RATE = 3.0;
	 private static final double RATE_PER_KILOMETER = 1.0; 
	 private static final double TIME_RATE = 0.5;
	 private static final double IDLE_RATE = 0.25;
	 private static final double IDLE_REST_RATE = 0;
	 private static final double mastercoefficient = 1.0;//calcweather()  calctimeofday()  calcdemand() from DB
	 
	@Override
	public void calculateFare(DistanceAPIBean distanceAPIBean) {		
		logger.info("**********Entering in calculateFare Start ************* ");	
		Double totalFare = BASE_RATE + (mastercoefficient*((distanceAPIBean.getTotalDistanceMeters()/1000)*RATE_PER_KILOMETER)+ (distanceAPIBean.getTotalTimeSeconds()/60)*TIME_RATE);
		if(totalFare <=5.0){
			distanceAPIBean.setTotalFare(5.0);
			totalFare = 5.0;
		}else{
			distanceAPIBean.setTotalFare(totalFare);
		}
		logger.info("**********Total Calculated fare against DistanceAPIBean "+distanceAPIBean +" is "+totalFare +" ********");
			
					
	}
	

}
