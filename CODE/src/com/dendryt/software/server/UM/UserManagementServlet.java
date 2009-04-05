package com.dendryt.software.server.UM;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import com.dendryt.software.client.designer.IUserManagement;
import com.dendryt.software.server.IType;
import com.dendryt.software.server.components.logger.LoggerHome;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserManagementServlet  extends RemoteServiceServlet implements IUserManagement{

	private Logger logger;
	public UserManagementServlet() {
		logger = LoggerHome.getInstance().getLogger(this);	
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserManagementImpl impl = new UserManagementImpl();
	public String[] getAllUsersData() {
		return impl.getAllUsersData();
	}
	
	
	public boolean isLoginAvaliable(String s) {
		return impl.isLoginAvaliable(s);
	}


	public void logoff() {
		//impl.logoff();
		
		HttpSession httpSession = getHttpSession();
		String login = (String)httpSession.getAttribute(IType.USERNAME);
		httpSession.setAttribute(IType.USERNAME, "");
		httpSession.setAttribute(IType.USER_SESSION, null);
		
		logger.info("user with login=" + login + " has logged out");		
	}
	
	private HttpSession getHttpSession(){
		return getThreadLocalRequest().getSession();
	}
}
