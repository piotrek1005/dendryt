package com.dendryt.software.client.designer;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NewAccountData implements IsSerializable{
	public String login;
	public String password;
	public String name;
	public String surname;
	public String address;
	public String telephone;
	public String email;
	public int userType;
}
