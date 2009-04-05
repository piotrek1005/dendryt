package com.dendryt.software.client.designer;

import java.util.HashSet;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NewWorkerAccountData implements IsSerializable{
	public String login;
	public String password;
	public String name;
	public String surname;
	public String telephone;
	public String email;
	public HashSet<Integer> functions;
	
	
	public String toString(){
		return "login:" + login + ", pass:" + password;
	}

}
