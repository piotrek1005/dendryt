package com.dendryt.software.client.client.tmp;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Zgloszenie implements IsSerializable {

	private int numerZgloszenia;
	private String imieZglaszajacego;
	private String nazwiskoZglaszajacego;
	private String telefonZglaszajacego;
	private Date dataZgloszenia;
	private Klient zglaszajacy;
	private String opisProblemu;
	private Stan stan;
	private WagaProblemu wagaKlienta;
	private List<Report> reports = new LinkedList<Report>();
	private String serwisant;
	private String projektant;
	private String programista;
	private String tester;
	private boolean next = true;


	private static int LAST_ID = 0; // ... crap
	private int id;
	
	
	private String programmerUsername;
	public String getProgrammerUsername() {
		return programmerUsername;
	}
	public void setProgrammerUsername(String programmerUsername) {
		this.programmerUsername = programmerUsername;
	}
	public int getId() {
		return id;
	}
	private String produkt;
	
	public Zgloszenie() {
			id = generateId();
	}
	private static synchronized int generateId() {
		return LAST_ID++;
	}
	public String getProdukt() {
		return produkt;
	}
	public void setProdukt(String produkt) {
		this.produkt = produkt;
	}

	public WagaProblemu getWagaKlienta() {
		return wagaKlienta;
	}

	public Zgloszenie setWagaKlienta(WagaProblemu wagaKlienta) {
		this.wagaKlienta = wagaKlienta;
		return this;
	}

	public int getNumerZgloszenia() {
		return numerZgloszenia;
	}

	private Zgloszenie setNumerZgloszenia(int numerZgloszenia) {
		this.numerZgloszenia = numerZgloszenia;
		return this;
	}

	public String getImieZglaszajacego() {
		return imieZglaszajacego;
	}

	public Zgloszenie setImieZglaszajacego(String imieZglaszajacego) {
		this.imieZglaszajacego = imieZglaszajacego;
		return this;
	}

	public String getNazwiskoZglaszajacego() {
		return nazwiskoZglaszajacego;
	}

	public Zgloszenie setNazwiskoZglaszajacego(String nazwiskoZglaszajacego) {
		this.nazwiskoZglaszajacego = nazwiskoZglaszajacego;
		return this;
	}

	public String getTelefonZglaszajacego() {
		return telefonZglaszajacego;
	}

	public Zgloszenie setTelefonZglaszajacego(String telefonZglaszajacego) {
		this.telefonZglaszajacego = telefonZglaszajacego;
		return this;
	}

	public Date getDataZgloszenia() {
		return dataZgloszenia;
	}

	public Zgloszenie setDataZgloszenia(Date dataZgloszenia) {
		this.dataZgloszenia = dataZgloszenia;
		return this;
	}

	public Klient getZglaszajacy() {
		return zglaszajacy;
	}

	public Zgloszenie setZglaszajacy(Klient zglaszajacy) {
		this.zglaszajacy = zglaszajacy;
		return this;
	}

	public String getOpisProblemu() {
		return opisProblemu;
	}

	public Zgloszenie setOpisProblemu(String opisProblemu) {
		this.opisProblemu = opisProblemu;
		return this;
	}

	public Stan getStan() {
		return stan;
	}

	public Zgloszenie setStan(Stan stan) {
		this.stan = stan;
		return this;
	}
	
	public void addReport(Report report) {
		reports.add(report);
	}
	
	public List<Report> getReports() {
		return reports;
	}
	
	public void setSerwisant(String serwisant) {
		this.serwisant = serwisant;
	}
	
	public String getSerwisant() {
		return serwisant;
	}
	
	public void setProjektant(String projektant) {
		this.projektant = projektant;
	}
	
	public String getProjektant() {
		return projektant;
	}
	
	public void setProgramista(String programista) {
		this.programista = programista;
	}
	
	public String getProgramista() {
		return programista;
	}
	
	public void setTester(String tester) {
		this.tester = tester;
	}
	
	public String getTester() {
		return tester;
	}
	
	public void przekazDoPrzodu() {
		next = true;
	}
	
	public void przekazDoTylu() {
		next = false;
	}
	
	public boolean doPrzodu() {
		return next;
	}
}
