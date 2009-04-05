package com.dendryt.software.client.login;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Data implements IsSerializable{
	private String login;
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	private String password;
	public Data()
	{
		this(null,null);
	}
	public Data(String n, String p)
	{
		super();
		login=n;
		password=p;
	}

}
