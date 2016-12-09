package com.tgi.safeher.service;

import java.io.Serializable;

import org.springframework.dao.DataAccessException;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface IUserRatingService extends Serializable{

	public void detailUserRating(SafeHerDecorator decorator) throws GenericException;

	public void reportUserRating(SafeHerDecorator decorator) throws GenericException, DataAccessException, Exception;

}
