package com.tgi.safeher.service.manager;

import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

public interface IDriverManager {
	public void appUserVisibility(SafeHerDecorator decorator)
			throws GenericException;
	public void appUserVisibilityV2(SafeHerDecorator decorator)
			throws GenericException;
	
}
