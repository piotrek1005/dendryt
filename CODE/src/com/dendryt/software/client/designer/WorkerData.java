package com.dendryt.software.client.designer;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WorkerData implements IsSerializable {
	public String login;
	public String email;
	public String name;
	public String surname;
	public String telephone;
	public HashSet<Integer> functions;
}