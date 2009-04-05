package com.dendryt.software.client.client;

import java.util.List;

import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IZgloszenieProvider {
	public void getZgloszenia(AsyncCallback<List<Zgloszenie>> callback);
}