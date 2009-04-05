package com.dendryt.software.client.client.tmp;


import java.io.Serializable;

public class Stan implements Serializable {

	private short numerStanu;
	private String nazwaStanu;
	/**
	 * @return Numer stanu
	 */
	public short getNumerStanu() {
		return numerStanu;
	}
	/**
	 * @param numerStanu the numerStanu to set
	 */
	private void setNumerStanu(short numerStanu) {
		this.numerStanu = numerStanu;
	}
	/**
	 * @return nazwa stanu zgoszenia
	 */
	public String getNazwaStanu() {
		return nazwaStanu;
	}
	/**
	 * @param nazwaStanu the nazwaStanu to set
	 */
	public void setNazwaStanu(String nazwaStanu) {
		this.nazwaStanu = nazwaStanu;
	}

}
