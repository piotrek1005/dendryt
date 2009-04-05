package com.dendryt.software.client.client.tmp;

import java.io.Serializable;

public class WagaProblemu implements Serializable {

	private short nrWagi;
	private String nazwaWagi;
	/**
	 * @return the nrWagi
	 */
	public short getNrWagi() {
		return nrWagi;
	}
	/**
	 * @param nrWagi the nrWagi to set
	 */
	public void setNrWagi(short nrWagi) {
		this.nrWagi = nrWagi;
	}
	/**
	 * @return the nazwaWagi
	 */
	public String getNazwaWagi() {
		return nazwaWagi;
	}
	/**
	 * @param nazwaWagi the nazwaWagi to set
	 */
	public void setNazwaWagi(String nazwaWagi) {
		this.nazwaWagi = nazwaWagi;
	}
}
