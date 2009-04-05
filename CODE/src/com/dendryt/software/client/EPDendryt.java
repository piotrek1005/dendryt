package com.dendryt.software.client;

import com.dendryt.software.client.login.LogInInterface;
import com.google.gwt.core.client.EntryPoint;


public class EPDendryt implements EntryPoint {
		
	public void onModuleLoad() {		
		new LogInInterface().mainInterface();		 
	}
}
