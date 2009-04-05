package com.dendryt.software.server.components.logger;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;

/**
 * @author michal
 *
 */
public class LoggerHome implements ILoggerHome {
	public final static String tracePattern = "%d{ISO8601}-[%t]-[%p] %l %m%n";
	private PatternLayout traceLayout;
	private WriterAppender traceAppender;	
	private static ILoggerHome instance; 
	
	
	private LoggerHome(){
	}
	
	
	/**
	 * get this singleton's instance
	 * @return instance of this singleton
	 */
	public static synchronized ILoggerHome getInstance(){
		if(instance == null){
			instance = new LoggerHome();
		}
		
		return instance;
	}
	
	
	/* (non-Javadoc)
	 * @see com.dendryt.software.server.ILoggerHome#getLogger(java.lang.Object)
	 */
	public Logger getLogger(Object o){
		traceLayout = new PatternLayout(tracePattern);
		traceAppender = new ConsoleAppender(traceLayout);	
		Logger logger = Logger.getLogger(o.getClass());
		logger.addAppender(traceAppender);
		return logger;
	}
}
