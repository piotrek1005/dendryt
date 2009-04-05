package com.dendryt.software.client.client;

import java.util.List;

import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IClientAsync{
	public void addNewZgloszenie(Zgloszenie z, AsyncCallback<?> callback);
	public void getZgloszenia(AsyncCallback<List<Zgloszenie>> callback);
	public void getProduktList(AsyncCallback<List<Produkt>> callback);
}
