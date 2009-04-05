package com.dendryt.software.client.designer;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NewClientAccountData implements IsSerializable{
	public String login;
	public String password;
	public String clientName;
	public String email;
	public String town;
	public String street;
	public String houseNum;
	public String flatNum;
	public String postCode;
	public String postTown;
	public String region;
	public String country;
	public String policyExpirationDate;
	public String policyExpirationMonth;
	public String policyExpirationYear;
}
