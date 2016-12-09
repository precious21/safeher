package com.tgi.safeher.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class TestService
{
	private static final Logger logger_c = Logger.getLogger(TestService.class);

	
	public void testServiceMethod()
	{
		Date date = new Date();

		logger_c.info("test service method invoked: " + date.toString());
	}
}