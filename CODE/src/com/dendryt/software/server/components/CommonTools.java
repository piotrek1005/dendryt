package com.dendryt.software.server.components;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.dendryt.software.server.components.logger.ILoggerHome;
import com.dendrytsoftware.dendryt.databaseIntf.IUserSession;

public class CommonTools {

	private static final String USER_SESSION = "userSession";

	public static IUserSession getUserSession(HttpSession h){
		return (IUserSession) h.getAttribute(USER_SESSION);
	}
	
}
