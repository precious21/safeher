package com.tgi.safeher.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.DriverInfoBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.enumeration.StateEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.common.exception.GenericRuntimeException;
import com.tgi.safeher.dao.LicenceDao;

import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.DriverInfoEntity;
import com.tgi.safeher.entity.DriverLicenceHistoryEntity;
import com.tgi.safeher.entity.StateProvinceEntity;
import com.tgi.safeher.rws.DriverRws;
import com.tgi.safeher.service.ILicenceService;
import com.tgi.safeher.service.converter.SignUpConverter;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.StringUtil;

@Service
@Transactional
@Scope("prototype")
public class LicenceService implements ILicenceService {
	private static final Logger logger = Logger.getLogger(LicenceService.class);
	@Autowired
	private LicenceDao licenceDao;

	@Autowired
	private SignUpConverter signUpConverter;

	@Override
	public boolean updateDriverInfo(SafeHerDecorator decorator,
			String driverInfoId) throws GenericException {
		
		// TODO Auto-generated method stub
		DriverInfoBean driverBean = (DriverInfoBean) decorator.getDataBean();
		logger.info("******Entering in updateDriverInfo  with DriverInfoBean "+ driverBean + "******");
		if (decorator.getErrors().size() == 0) {
			if (StringUtil.isNotEmpty(driverBean.getLicenceNo())) {

				DriverInfoEntity info = getDriverInfoEntityById(driverBean
						.getLicenceNo());
				if (info == null) {
					driverBean.setDriverInfoId(driverInfoId);
					info = licenceDao.get(DriverInfoEntity.class,
							Integer.valueOf(driverInfoId));
					info.setCurrentLicenceNo(driverBean.getLicenceNo());
					info.setCurrentLicenceExpiry(DateUtil
							.parseTimestampFromFormats(driverBean
									.getExpiryDate()));
					info.setStateProvince(getStateProvinceById(Integer
							.valueOf(driverBean.getStateId())));
					licenceDao.update(info);
				} else {
					driverBean.setDriverInfoId(info.getDriverInfoId()
							.toString());
				}
				AppUserEntity appUserEntity = licenceDao.getAppUserById(Integer
						.valueOf(driverBean.getAppUserId()));

				appUserEntity.setDriverInfo(info);
				return licenceDao.update(appUserEntity);
			}
		}
		return false;

	}

	@Override
	public boolean addLicenceDetail(SafeHerDecorator decorator)
			throws GenericException {
		// TODO Auto-generated method stub
		DriverInfoBean driverBean = (DriverInfoBean) decorator.getDataBean();
		logger.info("******Entering in addLicenceDetail  with DriverInfoBean "+ driverBean + "******");
		DriverInfoEntity infoIntity = signUpConverter
				.validateBeanToDriverInfoEntity(driverBean);
		infoIntity.setStateProvince(getStateProvinceById(Integer
				.valueOf(driverBean.getStateId())));
		return licenceDao.save(infoIntity);
	}

	@Override
	public AppUserBean getAppUserById(String accountId) {
		// TODO Auto-generated method stub
		logger.info("******Entering in getAppUserById  with accountId "+ accountId + "******");
		return signUpConverter.convertEntitytoAppUserBeanForLicence(licenceDao
				.getAppUserById(Integer.valueOf(accountId)));

	}

	@Override
	public StateProvinceEntity getStateProvinceById(int StateId) {
		// TODO Auto-generated method stub
		return licenceDao.getStateProvinceById(StateId);

	}

	@Override
	public DriverInfoEntity getDriverInfoEntityById(String LicenceNo) {
		// TODO Auto-generated method stub
		return licenceDao.getByLicenceNo(LicenceNo);
	}

	@Override
	public boolean makeHistory(SafeHerDecorator decorator) {
		DriverLicenceHistoryEntity historyInstance = new DriverLicenceHistoryEntity();
		DriverInfoBean driverBean = (DriverInfoBean) decorator.getDataBean();
		logger.info("******Entering in makeHistory  with DriverInfoBean "+ driverBean + "******");
		
		if (decorator.getErrors().size() == 0) {
			DriverInfoEntity info = getDriverInfoEntityById(driverBean
					.getLicenceNo());
			historyInstance.setDriverInfo(info);
			historyInstance.setLicenceNo(driverBean.getLicenceNo());
			historyInstance.setLicenceExpiry(DateUtil
					.parseTimestampFromFormats(driverBean.getExpiryDate()));
			historyInstance.setProvideDate(DateUtil.now());
			historyInstance.setIsActive("1");
		}
		return licenceDao.save(historyInstance);
	}

	@Override
	public boolean checkLicenceNoIsUnique(String LicenceNo) {
		// TODO Auto-generated method stub
		return licenceDao.checkIfLicenceUnique(LicenceNo);
	}

	@Override
	public void licenceDetail(SafeHerDecorator decorator)
			throws GenericException {
		DriverInfoBean driverBean = (DriverInfoBean) decorator.getDataBean();
		logger.info("******Entering in licenceDetail  with DriverInfoBean "+ driverBean + "******");

		signUpConverter.validateDriverLicence(decorator);
		if (decorator.getErrors().size() == 0) {
			try{

				if (!checkLicenceNoIsUnique(driverBean.getLicenceNo())) {
					throw new GenericException(
							"This Licence is Already in our System");
				}
			// AppUserBean bean = getAppUserById(driverBean.getAppUserId());
			AppUserEntity entity = licenceDao.getAppUserById(Integer
					.valueOf(driverBean.getAppUserId()));
			if (entity.getIsDriver() == null) {
				throw new GenericException("Licence is For Only Driver");
			} else if (!entity.getIsDriver().equals("1")) {
				throw new GenericException("Licence is For Only Driver");
			}
			// driverBean.setStateObj(getStateProvinceById(Integer.valueOf(driverBean.getStateId())));
			if ((entity.getDriverInfo() == null)) {
				if (addLicenceDetail(decorator)) {
					if (updateDriverInfo(decorator, "")) {
						if (!makeHistory(decorator)) {
							throw new GenericException(
									"Licence History are Not Maintain");
						}
						decorator.getResponseMap().put("data",
								decorator.getDataBean());
						decorator
								.setResponseMessage("Licence Detail Added Successfully ");
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
								.getValue());
					}
				}

			} else {
//				throw new GenericException("Driver Aready has Licence Info");
				licenceHistoryUpdate(entity.getDriverInfo());
				if (updateDriverInfo(decorator, entity.getDriverInfo()
						.getDriverInfoId().toString())) {
					if (!makeHistory(decorator)) {
						throw new GenericException(
								"Licence History are Not Maintain");
					}
					decorator.getResponseMap().put("data",
							decorator.getDataBean());
					decorator
							.setResponseMessage("Licence Detail Inserted Successfully ");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
							.getValue());
				} else {
					throw new GenericException(
							"Licence Info Failed to Update in Driver Info");
				}
			}
			}catch (DataAccessException e) {
				e.printStackTrace();
				throw new GenericException(
						"Server is not responding right now");
			}
		} else {
			throw new GenericException("Licence Detail Entry failed");
		}

	}

	private void licenceHistoryUpdate(DriverInfoEntity driverInfo) {

		logger.info("******Entering in updateHistory  with DriverInfoEntity "
				+ driverInfo.getCurrentLicenceNo() + "******");
		DriverLicenceHistoryEntity historyInstance = licenceDao
				.findByDriverInfo(driverInfo);
		if(historyInstance!=null){
			historyInstance.setIsActive("0");
			historyInstance.setEndDate(DateUtil.now());
			licenceDao.update(historyInstance);
		}
	}

	@Override
	public void getDriverLicenceInfo(SafeHerDecorator decorator)
			throws GenericException {
		DriverInfoBean driverBean = (DriverInfoBean) decorator.getDataBean();
		logger.info("******Entering in getDriverLicenceInfo  with DriverInfoBean "+ driverBean + "******");

		signUpConverter.validateGetDriverIfo(decorator);
		if (decorator.getErrors().size() == 0) {
			AppUserEntity entity = licenceDao.getAppUserById(Integer
					.valueOf(driverBean.getAppUserId()));
			if (entity.getIsDriver() != null) {
				if (!entity.getIsDriver().equals("1")) {
					throw new GenericException("Licence is For Only Driver");
				}
				DriverInfoEntity driverInfoEnt = entity.getDriverInfo();
				if (driverInfoEnt != null) {
					driverBean = signUpConverter
							.convertEntityToDriverInfoBean(driverInfoEnt);
					driverBean.setAppUserId(entity.getAppUserId().toString());
					decorator.getResponseMap().put("data", driverBean);
					decorator.setResponseMessage("Licence Detail Infomation ");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
							.getValue());
				} else {
					throw new GenericException(
							"Please First Add Licence Info Then Fetch it!  ");
				}
			} else {
				throw new GenericException("Licence Detail Only For Driver App");
			}
		}

	}

	@Override
	public void updateDriverLicenceInfo(SafeHerDecorator decorator)
			throws GenericException {

		DriverInfoBean driverBean = (DriverInfoBean) decorator.getDataBean();
		logger.info("******Entering in updateDriverLicenceInfo  with DriverInfoBean "+ driverBean + "******");

		if (StringUtil.isEmpty(driverBean.getDriverInfoId())) {
			decorator.getErrors().add("Please provide Driver Info Id ");
		}
		signUpConverter.validateDriverLicence(decorator);
		if (decorator.getErrors().size() == 0) {
			DriverInfoEntity entity = licenceDao.get(DriverInfoEntity.class,
					Integer.valueOf(driverBean.getDriverInfoId()));
			if (!entity.getCurrentLicenceNo().equals(driverBean.getLicenceNo())) {
				if (!checkLicenceNoIsUnique(driverBean.getLicenceNo())) {
					throw new GenericException(
							"This Licence is Already in our System");
				} else {
					entity.setCurrentLicenceNo(driverBean.getLicenceNo());
				}
			}
				if (entity.getStateProvince().getStateId() != Integer
						.valueOf(driverBean.getStateId())) {
					entity.setStateProvince(licenceDao.get(
							StateProvinceEntity.class,
							Integer.valueOf(driverBean.getStateId())));
				}
				entity.setCurrentLicenceExpiry(DateUtil
						.parseTimestampFromFormats(driverBean.getExpiryDate()));
				if (!licenceDao.update(entity)) {
					throw new GenericException("Updation Error");
				} else {
					DriverLicenceHistoryEntity historyI = licenceDao
							.checkLicenceHistory(entity, "1");
					historyI.setEndDate(DateUtil.now());
					historyI.setIsActive("0");
					if (!licenceDao.update(historyI)) {
						throw new GenericException("History Updation Error");
					} else {
						if (!makeHistory(decorator)) {
							throw new GenericException(
									"Licence History are Not Maintain");
						}
						decorator.getResponseMap().put("data",
								decorator.getDataBean());
						decorator
								.setResponseMessage("Licence Detail Update Successfully ");
						decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL
								.getValue());
					}
				}
			

		}
	}

}
