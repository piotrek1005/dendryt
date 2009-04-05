package com.dendryt.software.server.login;

import java.util.List;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.dendryt.software.client.login.Data;
import com.dendryt.software.client.login.IAuthenticateUser;
import com.dendryt.software.client.login.IFunkcje;
import com.dendryt.software.server.IType;
import com.dendryt.software.server.components.logger.ILoggerHome;
import com.dendryt.software.server.components.logger.LoggerHome;
import com.dendryt.software.server.designer.DesignerServlet;
import com.dendrytsoftware.dendryt.database.classes.klient.Zgloszenie;
import com.dendrytsoftware.dendryt.databaseImpl.DatabaseSession;
import com.dendrytsoftware.dendryt.databaseIntf.IClientSession;
import com.dendrytsoftware.dendryt.databaseIntf.IDesignerSession;
import com.dendrytsoftware.dendryt.databaseIntf.IUserSession;


public class AuthenticateUserImpl implements IAuthenticateUser{
	
	private Logger logger;		
	private HttpSession httpSession;
	
	public AuthenticateUserImpl(){	
		logger = LoggerHome.getInstance().getLogger(this);		
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	
	private static final String AUTHENTICATE = "AuthenticateUserImpl.authenticate()";
	/**
	 * return:
	 * -1 - niepoprawny login / haslo
	 * 0 - serwisant
	 * 1 - projektant
	 * 2 - programista
	 * 3 - tester
	 * 4 - klient
	 */
	public int authenticate(Data person) {
		logger.info(ILoggerHome.LOG_MESSAGE_ENTER);
		int result;
		result = IFunkcje.PROJEKTANT;
		/*try {
			IUserSession session =    DatabaseSession.openSession(person.getLogin(), person.getPassword());
			result = session.getSessionTypes()[0];
			
			httpSession.setAttribute(IType.USERNAME, person.getLogin());
			httpSession.setAttribute(IType.USER_SESSION, session);
			logger.info("User has loged-in successfully, login=" + person.getLogin() + "  password=" + person.getPassword() + " userType=" + result);
			
			
			
			StringBuilder b = new StringBuilder();
			
//			IClientSession s = ((IClientSession) session);
//			List l = s.listZgloszenie();
//			
//			s.insertObject(new Zgloszenie().setImieZglaszajacego("SKOK").se);
//			logger.debug("listPracownik.size=" + l.size());
//			
//			for(Object o : l){
//				logger.debug(o.toString());
//			}
			
		} catch (LoginException e) {
			logger.info("Exception on user's attempt to login the dendryt, login=" + person.getLogin() + "  password=" + person.getPassword());
			httpSession.setAttribute(IType.USERNAME, person.getLogin());
			httpSession.setAttribute(IType.USER_SESSION, null);
			
			result = -1;
		}
		
		logger.info("RETURN");*/
		return result;

	}
	
	

}
