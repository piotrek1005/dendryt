package com.dendryt.software.client.client;

import java.util.List;

import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

public interface IClient extends RemoteService{
	public void addNewZgloszenie(Zgloszenie z);
	public List<Zgloszenie> getZgloszenia();
	public List<Produkt> getProduktList();
}
