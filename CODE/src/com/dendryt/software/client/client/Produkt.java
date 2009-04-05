package com.dendryt.software.client.client;


import com.google.gwt.user.client.rpc.IsSerializable;

public class Produkt implements IsSerializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Produkt(String nazwa, String[] wersjaList) {
		super();
		this.nazwa = nazwa;
		this.wersjaList = wersjaList;
	}
	public Produkt() {
	}
	private String nazwa;
	private String[] wersjaList;
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String[] getWersjaList() {
		return wersjaList;
	}
	public void setWersjaList(String[] wersjaList) {
		this.wersjaList = wersjaList;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder("Nazwa:" + nazwa + " Wersje:");
		for(String w : wersjaList){
			s.append(w + "; ");
		}
		return s.toString();		
	}
	
}
