package com.tgi.safeher.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tgi.safeher.beans.AddressBean;
import com.tgi.safeher.beans.CityBean;
import com.tgi.safeher.beans.StateProvinceBean;
import com.tgi.safeher.beans.ZipCodeBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.entity.AddressEntity;
import com.tgi.safeher.entity.AddressTypeEntity;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.CityEntity;
import com.tgi.safeher.entity.CountryEntity;
import com.tgi.safeher.entity.PersonAddressEntity;
import com.tgi.safeher.entity.PersonAddressHistoryEntity;
import com.tgi.safeher.entity.PersonDetailEntity;
import com.tgi.safeher.entity.StateProvinceEntity;
import com.tgi.safeher.entity.ZipCodeEntity;
import com.tgi.safeher.service.IAddressService;
import com.tgi.safeher.service.converter.SignUpConverter;
import com.tgi.safeher.service.manager.impl.DriverManager;
import com.tgi.safeher.utils.DateUtil;

@Service
@Transactional
@Scope("prototype")
public class AddressService implements IAddressService {
	private static final Logger logger = Logger.getLogger(AddressService.class);
	@Autowired
	private SignUpConverter signUpConverter;
	
	@Autowired 
	private AppUserDao appUserDao;
	
	private String addressTypeID;
	
	
	@Override
	public void saveUserAddress(SafeHerDecorator decorator)
			throws GenericException {
		
		AddressBean bean = (AddressBean) decorator.getDataBean();
		logger.info("**************Entering in appUserVisibility WITH AddressBean "+bean +"*************");
		signUpConverter.validateAddressBean(decorator);
		if (decorator.getErrors().size() == 0) {
			AppUserEntity appUser = appUserDao.get(AppUserEntity.class,
					Integer.valueOf(bean.getAppUser()));
			PersonAddressEntity PersonAddEntity = appUserDao.findByOject(
					PersonAddressEntity.class,"personEntity".toString(),
					appUser.getPerson());

			AddressEntity entity = perpareAddressEntity(bean);
			if (appUserDao.save(entity)) {
				if (PersonAddEntity == null) {
					System.out.println(entity.getAddressId());
					setAddressTypeID(bean.getAddressType());
					PersonAddressEntity personEntity = preparePersonEntity(
							entity, appUser);
					if (appUserDao.save(personEntity)) {
						System.out.println(personEntity.getPersonAddressId());
						PersonAddressHistoryEntity personAddresshistoryEntity = preparePersonAddressHistory(
								entity, personEntity);
						appUserDao.save(personAddresshistoryEntity);
					} else {
						throw new GenericException(
								"Person Address Data is not Vaild ");
					}
				} else {
					AddressEntity ExistingAddress = appUserDao.get(
							AddressEntity.class, PersonAddEntity.getAddress()
									.getAddressId());
					ExistingAddress.setToDate(DateUtil.now());
					if (appUserDao.update(ExistingAddress)) {
						PersonAddressHistoryEntity personExistAddHistory = appUserDao
								.getCurrentPersonAddressHistory(PersonAddEntity);
						if (personExistAddHistory != null) {
							personExistAddHistory.setIsCurrent("0");
							personExistAddHistory.setToDate(DateUtil.now());
							appUserDao.update(personExistAddHistory);
						}
					}
					setAddressTypeID(bean.getAddressType());
					PersonAddEntity.setAddressType(appUserDao.get(
							AddressTypeEntity.class,
							Integer.valueOf(getAddressTypeID())));
					if (appUserDao.update(PersonAddEntity)) {
						PersonAddressHistoryEntity personAddresshistoryEntity = preparePersonAddressHistory(
								entity, PersonAddEntity);
						appUserDao.save(personAddresshistoryEntity);
					} else {
						throw new GenericException(
								"Existing Person Address Data is not Vaild ");
					}
				}

			} else {
				throw new GenericException("Address Data is not Vaild ");
			}
		} else {
			throw new GenericException("Person Address Information Error");
		}
	}

	private AddressEntity perpareAddressEntity(AddressBean bean) {
		logger.info("**************Entering in perpareAddressEntity with AddressBean "+bean);
		AddressEntity entity = new AddressEntity();
		entity.setAddressLineOne(bean.getAddressLineOne());
		entity.setAddressLineTwo(bean.getAddressLineTwo());
		entity.setFromDate(DateUtil.now());
		entity.setCity(appUserDao.get(CityEntity.class,
				Integer.valueOf(bean.getCity())));
		entity.setStateProvince(appUserDao.get(StateProvinceEntity.class,
				Integer.valueOf(bean.getStateProvince())));
		entity.setCountry(appUserDao.get(CountryEntity.class,
				Integer.valueOf(bean.getCountry())));
		entity.setZipCode(appUserDao.get(ZipCodeEntity.class,
				Integer.valueOf(bean.getZipCode())));
		return entity;
	}

	private PersonAddressHistoryEntity preparePersonAddressHistory(
			AddressEntity entity, PersonAddressEntity personEntity) {
		logger.info("**************Entering in preparePersonAddressHistory ******** "); 
		PersonAddressHistoryEntity personHistoryEntity = new PersonAddressHistoryEntity();
		personHistoryEntity.setStateProvince(entity.getStateProvince());
		personHistoryEntity.setCity(entity.getCity());
		personHistoryEntity.setCountry(entity.getCountry());
		personHistoryEntity.setZipCode(entity.getZipCode());
		personHistoryEntity.setAddressLineOne(entity.getAddressLineOne());
		personHistoryEntity.setAddressLineTwo(entity.getAddressLineTwo());
		personHistoryEntity.setAddressType(personEntity.getAddressType());
		personHistoryEntity.setIsCurrent("1");
		personHistoryEntity.setFromDate(entity.getFromDate());
		personHistoryEntity.setPerson(personEntity.getPerson());
		personHistoryEntity.setPersonAddress(personEntity);

		return personHistoryEntity;

	}


	private PersonAddressEntity preparePersonEntity(AddressEntity entity ,AppUserEntity appUser) {
		logger.info("**************Entering in preparePersonEntity ******** "); 
		PersonAddressEntity personEntity = new PersonAddressEntity();
		personEntity.setAddress(entity);
		personEntity.setFromDate(DateUtil.now());
		personEntity.setAddressType(appUserDao.get(AddressTypeEntity.class,
				Integer.valueOf(getAddressTypeID())));
		personEntity.setPerson(appUser.getPerson());
		personEntity.setPersonDetail(appUserDao.findByOject(
				PersonDetailEntity.class, "personEntity", appUser.getPerson()));
		personEntity.setIsActive("1");
		return personEntity;

	}


	public String getAddressTypeID() {
		return addressTypeID;
	}


	public void setAddressTypeID(String addressTypeID) {
		this.addressTypeID = addressTypeID;
	}

	@Override
	public void getCountryList(SafeHerDecorator decorator)
			throws GenericException {
		logger.info("**************Entering in getCountryList ******** "); 
		List<CountryEntity> lsCountryList = appUserDao
				.getAll(CountryEntity.class);
		decorator.setDataBean(signUpConverter
				.convertEntiyToCountryBean(lsCountryList));
		decorator.getResponseMap().put("data", decorator.getDataBean());
		decorator.setResponseMessage("Country List");
		decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
	
	}

	@Override
	public void getStateList(SafeHerDecorator decorator)
			throws GenericException {
		StateProvinceBean bean = (StateProvinceBean) decorator.getDataBean();
		logger.info("**************Entering in getStateList with StateProvinceBean "+bean+" ******** "); 
		signUpConverter.validateState(decorator);
		if (decorator.getErrors().size() == 0) {
			CountryEntity countryInstance = appUserDao.findById(CountryEntity.class,
					Integer.valueOf(bean.getCountryId()));
			if(countryInstance == null){
				throw new GenericException("Please check you country id ");
			}
			decorator.setDataBean(signUpConverter
					.convertEntiyToStateBean(appUserDao
							.getStateByCountry(countryInstance)));
			decorator.getResponseMap().put("data", decorator.getDataBean());
			decorator.setResponseMessage("State List");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
		} else {
			throw new GenericException("State Data is not Vaild ");
		}
	}

	@Override
	public void getCityList(SafeHerDecorator decorator) throws GenericException {
		CityBean bean= (CityBean) decorator.getDataBean();
		logger.info("**************Entering in getCityList with CityBean "+bean+" ******** "); 
		signUpConverter.validateCity(decorator);
		if (decorator.getErrors().size() == 0) {
		StateProvinceEntity stateInstance=appUserDao.get(StateProvinceEntity.class,
					Integer.valueOf(bean.getCityId()));
			decorator.setDataBean(signUpConverter
					.convertEntiyToCityBean(appUserDao
							.getCityByState(stateInstance)));
			decorator.getResponseMap().put("data", decorator.getDataBean());
			decorator.setResponseMessage("City List");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
		} else {
			throw new GenericException("City Data is not Vaild ");
		}
	}

	@Override
	public void getZipCodeList(SafeHerDecorator decorator)
			throws GenericException {
		ZipCodeBean bean= (ZipCodeBean) decorator.getDataBean();
		logger.info("**************Entering in getZipCodeList with ZipCodeBean "+bean+" ******** "); 
		signUpConverter.validateZipCode(decorator);
		if (decorator.getErrors().size() == 0) {
		CityEntity CityInstance=appUserDao.get(CityEntity.class,
					Integer.valueOf(bean.getCityId()));
			decorator.setDataBean(signUpConverter
					.convertEntiyToZipCode(appUserDao
							.getZipByCity(CityInstance)));
			decorator.getResponseMap().put("data", decorator.getDataBean());
			decorator.setResponseMessage("ZipCode List");
			decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
		} else {
			throw new GenericException("City Data is not Vaild ");
		}
		
	}
	
	

}
