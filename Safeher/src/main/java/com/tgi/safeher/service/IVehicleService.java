package com.tgi.safeher.service;

import java.io.Serializable;
import java.util.List;

import com.tgi.safeher.beans.VehicleInfoBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.entity.AppUserEntity;
import com.tgi.safeher.entity.VehicleInfoEntity;
import com.tgi.safeher.entity.VehicleMakeEntity;
import com.tgi.safeher.entity.VehicleModelEntity;

public interface IVehicleService extends Serializable{

	public void getVehicleMake(SafeHerDecorator decorator);
	
	List<VehicleModelEntity> getVehicleModelByMake(VehicleMakeEntity vehicle);
	
	public VehicleMakeEntity getVehicleMakeById(String id);
	
	public void getVehicleModel(SafeHerDecorator decorator);
	
	public void getVehicleColor(SafeHerDecorator decorator) throws GenericException;
	
	public void saveVehicleInfo(SafeHerDecorator decorator) throws GenericException;
	
	public void saveAppUserVehicleInfo(VehicleInfoEntity entity ,VehicleInfoBean bean) throws GenericException;
	
	public boolean checkVehicleRegistration(String vehicleNum) throws GenericException;
	
	public boolean checkAppUserVehicleInfo(AppUserEntity appUser)throws GenericException;
	
	public VehicleInfoEntity addAppUserInstance(VehicleInfoBean bean , VehicleInfoEntity entity)throws GenericException;
	
	public void getVehicleInfo(SafeHerDecorator decorator) throws GenericException;
	
	public void updateVehicleInfo(SafeHerDecorator decorator)throws GenericException;

	public void fetchDriverVehResEnd(SafeHerDecorator decorator)
			throws GenericException;

	public void saveDriverVehResEnd(SafeHerDecorator decorator)
			throws GenericException;

	public void getvehicleList(SafeHerDecorator decorator) throws GenericException;

	public void setDefaultVehicle(SafeHerDecorator decorator) throws GenericException;

	public void deleteVehicleInfo(SafeHerDecorator decorator) throws GenericException;
}
