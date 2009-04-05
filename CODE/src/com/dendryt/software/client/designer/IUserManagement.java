package com.dendryt.software.client.designer;

import com.google.gwt.user.client.rpc.RemoteService;

public interface IUserManagement extends RemoteService {
	public boolean isLoginAvaliable(String s);
	public String[] getAllUsersData();
	public void logoff();
}
