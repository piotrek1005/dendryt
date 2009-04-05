package com.dendryt.software.server.components.logger;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;


public class LoggerHomeTest extends TestCase{
	
	
	@Test
	public void testSimpleLoggerUsage(){
		ILoggerHome logger = LoggerHome.getInstance();
		Logger l = logger.getLogger(this);
		l.info("traceSomething HERE ;)");
	}
	
	
	public void testNullPointer(){
		try{
			ILoggerHome logger = LoggerHome.getInstance();
			Logger l = logger.getLogger(null);		
		}catch(Exception e){
			if(e.getClass() != NullPointerException.class){
				fail("it should be null pointer exception");
			}else{
				return;
			}
		}
		fail("it should be null pointer exception");
	}
	
	
	
	public void testD(){
		Date d = new Date();
		d.setYear(2002);
		System.out.println(d.toGMTString());
	}
	
	

}
