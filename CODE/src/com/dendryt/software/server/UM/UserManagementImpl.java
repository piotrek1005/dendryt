package com.dendryt.software.server.UM;

import javax.security.auth.login.LoginException;

import com.dendryt.software.client.designer.IUserManagement;
import com.dendrytsoftware.dendryt.databaseImpl.DatabaseSession;
import com.dendrytsoftware.dendryt.databaseIntf.IDesignerSession;

public class UserManagementImpl implements IUserManagement{

	public boolean isLoginAvaliable(String s){
		
		boolean result = false;
		
		//TODO - zmienic to!, only for testing purposes
		IDesignerSession session;
		try {
			session = (IDesignerSession)DatabaseSession.openSession("heniek", "heniek1");
			result = session.isLoginAvaliable(s);
			session.close();
		} catch (LoginException e) {
			e.printStackTrace();
			System.out.println("!!!!!!!!!!!!!!");
		}
		return result;
	}
	public String[] getAllUsersData() {
		return new String[]{"skok", "skokDwa"};
	}
	public void logoff() {
		System.out.println("WYLOGOWANY (TODO)");
	}

}
