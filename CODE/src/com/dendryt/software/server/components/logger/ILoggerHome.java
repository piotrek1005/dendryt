package com.dendryt.software.server.components.logger;

import org.apache.log4j.Logger;

public interface ILoggerHome {
	
	public final static String LOG_MESSAGE_ENTER = "[ENTER]";
	public final static String LOG_MESSAGE_EXIT = "[EXIT]";
	public final static String LOG_MESSAGE_RETURN_VALUE = "[RETURN]";
	public final static String LOG_MESSAGE_PARAM_VALUE = "[PARAM]";
	

	/**
	 * Gets reference to logger in dendryt system manner;
	 * 
	 * Usage:
	 * from other classes just use: getLogger(this); and thats all
	 * @param o - reference to the object that wants to use logger
	 * @return 
	 */
	public abstract Logger getLogger(Object o);

}