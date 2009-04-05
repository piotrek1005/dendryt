package com.dendryt.software.client.designer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IUserManagementAsync {
	public void isLoginAvaliable(String s, AsyncCallback<Boolean> callback);
	public void getAllUsersData(AsyncCallback<String[]> callback);
	public void logoff(AsyncCallback<?> callback);
}
