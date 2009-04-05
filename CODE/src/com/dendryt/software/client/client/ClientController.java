package com.dendryt.software.client.client;

import java.util.List;

import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class ClientController implements IZgloszenieProvider {
	IClientAsync service;

	public ClientController() {
		GWT.log("ClientController.ClientController()", null);
		service = (IClientAsync) GWT.create(IClient.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "/IClient");
	}
	
	public void addNewZgloszenie(Zgloszenie z, AsyncCallback a){
		service.addNewZgloszenie(z, a);
	}
	
	public void getProductList(AsyncCallback<List<Produkt>> callback){
		service.getProduktList(callback);	
	}

	public void getZgloszenia(AsyncCallback<List<Zgloszenie>> callback) {
		service.getZgloszenia(callback);
	}
	
	
	
	
	
	
}
