package com.tgi.safeher.map.service.iml;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.entity.ActiveDriverLocationEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.CityEntity;
import com.tgi.safeher.entity.CountryEntity;
import com.tgi.safeher.entity.DriverLocationTrackEntity;
import com.tgi.safeher.entity.PassengerSourceLocationEntity;
import com.tgi.safeher.entity.StateProvinceEntity;
import com.tgi.safeher.entity.SuburbEntity;
import com.tgi.safeher.entity.mongo.ActiveDriverLocationMongoEntity;
import com.tgi.safeher.map.beans.MapBean;
import com.tgi.safeher.map.dao.MapDao;
import com.tgi.safeher.map.service.IGoogleMapServices;
import com.tgi.safeher.map.service.IMapService;
import com.tgi.safeher.map.service.converter.MapConverter;
import com.tgi.safeher.repo.ActiveDriverLocationRepository;
import com.tgi.safeher.service.impl.AsyncServiceImpl;
import com.tgi.safeher.utils.StringUtil;

@Service
@Transactional
public class MapService implements IMapService{
	
	@Autowired
	private MapDao mapDao;
	
	@Autowired
	private MapConverter mapConverter;
	
	@Autowired
	private IGoogleMapServices iGoogleMapServices;
	
	@Autowired
	private ActiveDriverLocationRepository activeDriverLocationRepository;
	
	@Autowired
	private AsyncServiceImpl asyncServiceImpl;
	
	@Override
	public void getAllPassangerDriver(SafeHerDecorator decorator)
			throws GenericException {

		MapBean bean = (MapBean) decorator.getDataBean();
		List<Object[]> list =  mapDao.getAllPassangerDriver(((MapBean)decorator.getDataBean()).getIsDriver());
		if(list != null && list.size() > 0){
			mapConverter.convertObjectToBean(decorator, list);
		}else{
			decorator.setResponseMessage("Empty List");
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}
	}

	@Override
	public void savePassangerDriver(SafeHerDecorator decorator)
			throws GenericException {
		MapBean bean = (MapBean) decorator.getDataBean();
		if(StringUtil.isNotEmpty(bean.getAppUserId())){
			if(mapConverter.validatLngLat(decorator)){
				/*Please note that: we are using google map api to get address using lat lng.
					we are using this api because we are saving driver,s suburb, city, country etc 
					and we are on the basis of suburb and city we are searching drivers when passenger
					request for rid so if we remove this we will not be able to find any drivers*/
//				bean.setAddress(iGoogleMapServices.getAddressFromLngLat(bean.getLat(), bean.getLng()));
//				mapConverter.populateFullAddres(bean);
				if(bean.getIsDriver().equals("1")){
					AppUserEntity driver = mapDao.findById(
							AppUserEntity.class, new Integer(bean.getAppUserId()));
					if(driver != null){
						if(driver.getIsActivated() != null && 
								driver.getIsActivated().equals("0")){
							throw new GenericException("You can not go online becuase your account is not activated");
						}
					}else{
						throw new GenericException("Driver not found");
					}
				}
				saveAddress(decorator);
				
				decorator.getResponseMap().put("address", bean.getAddress());
				decorator.setResponseMessage("Location saved successfully");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			}else{
				decorator.setResponseMessage("Please provide following information");
				decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			}
		}else{
			decorator.setResponseMessage("User don't exists");
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}
	}
	
	
	//Mongo
	@Override
	public void savePassangerDriverMongo(SafeHerDecorator decorator)
			throws GenericException {
		MapBean bean = (MapBean) decorator.getDataBean();
		if(StringUtil.isNotEmpty(bean.getAppUserId())){
			if(mapConverter.validatLngLat(decorator)){
				/*Please note that: we are using google map api to get address using lat lng.
					we are using this api because we are saving driver,s suburb, city, country etc 
					and we are on the basis of suburb and city we are searching drivers when passenger
					request for rid so if we remove this we will not be able to find any drivers*/
//				bean.setAddress(iGoogleMapServices.getAddressFromLngLat(bean.getLat(), bean.getLng()));
//				mapConverter.populateFullAddres(bean);
				if(bean.getIsDriver().equals("1")){
					AppUserEntity driver = mapDao.findById(
							AppUserEntity.class, new Integer(bean.getAppUserId()));
					if(driver != null){
						if(driver.getIsActivated() != null && 
								driver.getIsActivated().equals("0")){
							throw new GenericException("You can not go online becuase your account is not activated");
						}
					}else{
						throw new GenericException("Driver not found");
					}
				}
				saveAddress(decorator);
				//async saving in driver location track mongo
				//asyncServiceImpl.saveDriverLocationTrackIntoMongo(bean);
				
				
				decorator.getResponseMap().put("address", bean.getAddress());
				decorator.setResponseMessage("Location saved successfully");
				decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
			}else{
				decorator.setResponseMessage("Please provide following information");
				decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
			}
		}else{
			decorator.setResponseMessage("User don't exists");
			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
		}
	}
	
	
	
	
	
/*	public void saveAddress(SafeHerDecorator decorator){
		
		MapBean bean = (MapBean) decorator.getDataBean();
		CityEntity cityEntity = new CityEntity();
		StateProvinceEntity stateProvinceEntity = new StateProvinceEntity();
		CountryEntity countryEntity = new CountryEntity();
		PassengerSourceLocationEntity passengerSourceLocationEntity = new PassengerSourceLocationEntity();
		ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
		DriverLocationTrackEntity driverLocationTrackEntity = new DriverLocationTrackEntity();
		SuburbEntity suburbEntity = new SuburbEntity();
		AppUserEntity appUserEntity = new AppUserEntity();
		
		appUserEntity.setAppUserId(new Integer(bean.getAppUserId()));
//		savecityStateCountry(
//				cityEntity, stateProvinceEntity, countryEntity, suburbEntity, bean);
		
//		countryEntity = mapDao.findBy(CountryEntity.class, "name", bean.getCountry());
//		if(countryEntity == null){
//			countryEntity = new CountryEntity();
//			countryEntity.setAbbreviation(bean.getCountryCode());
//			countryEntity.setName(bean.getCountry());
//			mapDao.saveOrUpdate(countryEntity);
//		}
//		stateProvinceEntity = mapDao.findBy(StateProvinceEntity.class, "name", bean.getState());
//		if(stateProvinceEntity == null){
//			stateProvinceEntity = new StateProvinceEntity();
//			stateProvinceEntity.setName(bean.getState());
//			stateProvinceEntity.setCode(bean.getStateCode());
//			stateProvinceEntity.setCountry(countryEntity);
//			mapDao.saveOrUpdate(stateProvinceEntity);
//		}
//		cityEntity = mapDao.findBy(CityEntity.class, "name", bean.getCity());
//		if(cityEntity == null){
//			cityEntity = new CityEntity();
//			cityEntity.setName(bean.getCity());
//			cityEntity.setStateProvince(stateProvinceEntity);
//			cityEntity.setCountry(countryEntity);
//			mapDao.saveOrUpdate(cityEntity);
//		}
//		suburbEntity = mapDao.findBy(SuburbEntity.class, "name", bean.getSuburb());
//		if(suburbEntity == null){
//			suburbEntity = new SuburbEntity();
//			suburbEntity.setName(bean.getSuburb());
//			suburbEntity.setNeighborhood(bean.getNeighborHood());
//			suburbEntity.setCity(cityEntity);
//			suburbEntity.setStateProvince(stateProvinceEntity);
//			suburbEntity.setCountry(countryEntity);
//			mapDao.saveOrUpdate(suburbEntity);
//		}
		
		if(bean.getIsDriver().equals("1")){
			activeDriverLocationEntity = mapDao.findByParamId(
					ActiveDriverLocationEntity.class, "appUser.appUserId", new Integer(bean.getAppUserId()));
			if(activeDriverLocationEntity == null){
				activeDriverLocationEntity = new ActiveDriverLocationEntity();
			}	
			mapConverter.convertBeanToDriverLoc(activeDriverLocationEntity, bean, driverLocationTrackEntity);
			activeDriverLocationEntity.setAppUser(appUserEntity);
//			activeDriverLocationEntity.setSuburb(suburbEntity);
			activeDriverLocationEntity.setIsOnline("1");
			mapDao.saveOrUpdate(activeDriverLocationEntity);
			
//			mapConverter.convertBeanToDriverLoc(activeDriverLocationEntity, bean, driverLocationTrackEntity);
//			driverLocationTrackEntity.setActiveDriverLocatoin(activeDriverLocationEntity);
//			mapDao.saveOrUpdate(driverLocationTrackEntity);
		}else{
			passengerSourceLocationEntity = mapDao.findByParamId(
					PassengerSourceLocationEntity.class, "appUserPassenger.appUserId", new Integer(bean.getAppUserId()));
			if(passengerSourceLocationEntity == null){
				passengerSourceLocationEntity = new PassengerSourceLocationEntity();
			}
			mapConverter.convertBeanToPassengerLoc(passengerSourceLocationEntity, bean);
			passengerSourceLocationEntity.setAppUserPassenger(appUserEntity);
//			passengerSourceLocationEntity.setSuburb(suburbEntity);
			passengerSourceLocationEntity.setIsOnline("1");
			mapDao.saveOrUpdate(passengerSourceLocationEntity);
			System.out.println(passengerSourceLocationEntity.getAppUserPassenger().getAppUserId());
		}
		
		
	}*/
	
public void saveAddress(SafeHerDecorator decorator){
		
		MapBean bean = (MapBean) decorator.getDataBean();
		CityEntity cityEntity = new CityEntity();
		StateProvinceEntity stateProvinceEntity = new StateProvinceEntity();
		CountryEntity countryEntity = new CountryEntity();
		PassengerSourceLocationEntity passengerSourceLocationEntity = new PassengerSourceLocationEntity();
		ActiveDriverLocationEntity activeDriverLocationEntity = new ActiveDriverLocationEntity();
		ActiveDriverLocationMongoEntity activeDriverLocationMongoEntity = new ActiveDriverLocationMongoEntity();
		DriverLocationTrackEntity driverLocationTrackEntity = new DriverLocationTrackEntity();
		SuburbEntity suburbEntity = new SuburbEntity();
		AppUserEntity appUserEntity = new AppUserEntity();
		
		appUserEntity.setAppUserId(new Integer(bean.getAppUserId()));
//		savecityStateCountry(
//				cityEntity, stateProvinceEntity, countryEntity, suburbEntity, bean);
		
//		countryEntity = mapDao.findBy(CountryEntity.class, "name", bean.getCountry());
//		if(countryEntity == null){
//			countryEntity = new CountryEntity();
//			countryEntity.setAbbreviation(bean.getCountryCode());
//			countryEntity.setName(bean.getCountry());
//			mapDao.saveOrUpdate(countryEntity);
//		}
//		stateProvinceEntity = mapDao.findBy(StateProvinceEntity.class, "name", bean.getState());
//		if(stateProvinceEntity == null){
//			stateProvinceEntity = new StateProvinceEntity();
//			stateProvinceEntity.setName(bean.getState());
//			stateProvinceEntity.setCode(bean.getStateCode());
//			stateProvinceEntity.setCountry(countryEntity);
//			mapDao.saveOrUpdate(stateProvinceEntity);
//		}
//		cityEntity = mapDao.findBy(CityEntity.class, "name", bean.getCity());
//		if(cityEntity == null){
//			cityEntity = new CityEntity();
//			cityEntity.setName(bean.getCity());
//			cityEntity.setStateProvince(stateProvinceEntity);
//			cityEntity.setCountry(countryEntity);
//			mapDao.saveOrUpdate(cityEntity);
//		}
//		suburbEntity = mapDao.findBy(SuburbEntity.class, "name", bean.getSuburb());
//		if(suburbEntity == null){
//			suburbEntity = new SuburbEntity();
//			suburbEntity.setName(bean.getSuburb());
//			suburbEntity.setNeighborhood(bean.getNeighborHood());
//			suburbEntity.setCity(cityEntity);
//			suburbEntity.setStateProvince(stateProvinceEntity);
//			suburbEntity.setCountry(countryEntity);
//			mapDao.saveOrUpdate(suburbEntity);
//		}
		
		if(bean.getIsDriver().equals("1")){
			activeDriverLocationMongoEntity = activeDriverLocationRepository.findByAppUserId(new Integer(bean.getAppUserId()));
			/*activeDriverLocationEntity = mapDao.findByParamId(
					ActiveDriverLocationEntity.class, "appUser.appUserId", new Integer(bean.getAppUserId()));
			if(activeDriverLocationEntity == null){
				activeDriverLocationEntity = new ActiveDriverLocationEntity();
			}	*/
			if(activeDriverLocationMongoEntity == null){
				activeDriverLocationMongoEntity=new ActiveDriverLocationMongoEntity();
			}
			//mapConverter.convertBeanToDriverLoc(activeDriverLocationEntity, bean, driverLocationTrackEntity);
			mapConverter.convertBeanToDriverLocMongo(activeDriverLocationMongoEntity, bean, driverLocationTrackEntity);
			//activeDriverLocationEntity.setAppUser(appUserEntity);
//			activeDriverLocationEntity.setSuburb(suburbEntity);
			activeDriverLocationMongoEntity.setIsOnline("1");
			activeDriverLocationMongoEntity.setIsRequested("0");
			activeDriverLocationMongoEntity.setIsBooked("0");
			activeDriverLocationMongoEntity.setAppUserId(appUserEntity.getAppUserId());
			//activeDriverLocationEntity.setIsOnline("1");
			activeDriverLocationRepository.save(activeDriverLocationMongoEntity);
			//mapDao.saveOrUpdate(activeDriverLocationEntity);
			
//			mapConverter.convertBeanToDriverLoc(activeDriverLocationEntity, bean, driverLocationTrackEntity);
//			driverLocationTrackEntity.setActiveDriverLocatoin(activeDriverLocationEntity);
//			mapDao.saveOrUpdate(driverLocationTrackEntity);
		}else{
			passengerSourceLocationEntity = mapDao.findByParamId(
					PassengerSourceLocationEntity.class, "appUserPassenger.appUserId", new Integer(bean.getAppUserId()));
			if(passengerSourceLocationEntity == null){
				passengerSourceLocationEntity = new PassengerSourceLocationEntity();
			}
			mapConverter.convertBeanToPassengerLoc(passengerSourceLocationEntity, bean);
			passengerSourceLocationEntity.setAppUserPassenger(appUserEntity);
//			passengerSourceLocationEntity.setSuburb(suburbEntity);
			passengerSourceLocationEntity.setIsOnline("1");
			mapDao.saveOrUpdate(passengerSourceLocationEntity);
			System.out.println(passengerSourceLocationEntity.getAppUserPassenger().getAppUserId());
		}
		
		
	}
	
	public void savecityStateCountry(CityEntity cityEntity, StateProvinceEntity stateProvinceEntity, 
			CountryEntity countryEntity, SuburbEntity suburbEntity, MapBean bean){

//		countryEntity = mapDao.findBy(CountryEntity.class, "name", bean.getCountry());
//		if(countryEntity == null){
//			countryEntity = new CountryEntity();
//			countryEntity.setAbbreviation(bean.getCountryCode());
//			countryEntity.setName(bean.getCountry());
//			mapDao.saveOrUpdate(countryEntity);
//		}
//		stateProvinceEntity = mapDao.findBy(StateProvinceEntity.class, "name", bean.getState());
//		if(stateProvinceEntity == null){
//			stateProvinceEntity = new StateProvinceEntity();
//			stateProvinceEntity.setName(bean.getState());
//			stateProvinceEntity.setCode(bean.getStateCode());
//			stateProvinceEntity.setCountry(countryEntity);
//			mapDao.saveOrUpdate(stateProvinceEntity);
//		}
//		cityEntity = mapDao.findBy(CityEntity.class, "name", bean.getCity());
//		if(cityEntity == null){
//			cityEntity = new CityEntity();
//			cityEntity.setName(bean.getCity());
//			stateProvinceEntity.setStateId(stateProvinceEntity.getStateId());
//			stateProvinceEntity.setCountry(countryEntity);
//			mapDao.saveOrUpdate(cityEntity);
//		}
//		suburbEntity = mapDao.findBy(SuburbEntity.class, "name", bean.getStreet());
//		if(suburbEntity == null){
//			suburbEntity = new SuburbEntity();
//			suburbEntity.setName(bean.getStreet());
//			suburbEntity.setCity(cityEntity);
//			suburbEntity.setStateProvince(stateProvinceEntity);
//			suburbEntity.setCountry(countryEntity);
//			mapDao.saveOrUpdate(suburbEntity);
//		}
	}
}
