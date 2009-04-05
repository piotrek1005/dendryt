package com.dendryt.software.server.login;

//import java.sql.Date;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dendryt.software.client.client.tmp.Klient;
import com.dendryt.software.client.client.tmp.Report;
import com.dendryt.software.client.client.tmp.WagaProblemu;
import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.dendryt.software.client.login.Data;
import com.dendryt.software.client.login.IAuthenticateUser;
import com.dendryt.software.client.login.IFunkcje;
import com.dendryt.software.server.components.inmemorydb.DendrytInMemoryDB;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AuthenticationServlet extends RemoteServiceServlet implements IAuthenticateUser {

	private static final long serialVersionUID = 1L;
	AuthenticateUserImpl impl = new AuthenticateUserImpl();

	public AuthenticationServlet() {
		
		//tmp
		Zgloszenie zgloszenie = new Zgloszenie();
		
		Date date = new Date();
		date.setYear(2009);
		date.setMonth(1);
		date.setDate(27);
		zgloszenie.setProdukt("produkt1");
		zgloszenie.setDataZgloszenia(date);
		zgloszenie.setImieZglaszajacego("imieZglaszajacego1");
		zgloszenie.setNazwiskoZglaszajacego("nazwiskoZglaszajacego1");
		zgloszenie.setOpisProblemu("opisProblemu1");
		zgloszenie.setTelefonZglaszajacego("123456789");
		zgloszenie.setProjektant("den");
		
		WagaProblemu wagaKlienta = new WagaProblemu();
		wagaKlienta.setNrWagi((short)1);
		
		zgloszenie.setWagaKlienta(wagaKlienta);
		
		Report report = new Report();
		report.userName = "serwisant1";
		report.userType = IFunkcje.SERWISANT;
		report.comment = "comment1";
		date = new Date();
		date.setYear(2009);
		date.setMonth(1);
		date.setDate(27);
		report.date = date;
		zgloszenie.addReport(report);
		
		report = new Report();
		report.userName = "den";
		report.userType = IFunkcje.PROJEKTANT;
		report.comment = "comment2";
		date = new Date();
		date.setYear(2009);
		date.setMonth(1);
		date.setDate(27);
		report.date = date;
		zgloszenie.addReport(report);
		//zgloszenie.przekazDoPrzodu();
		
		report = new Report();
		report.userName = "programista1";
		report.userType = IFunkcje.PROGRAMISTA;
		report.comment = "comment3";
		date.setYear(2009);
		date.setMonth(1);
		date.setDate(27);
		report.date = date;
		zgloszenie.addReport(report);
		//zgloszenie.przekazDoTylu();
		zgloszenie.przekazDoPrzodu();
		
		/*report = new Report();
		report.userName = "tester1";
		report.userType = IFunkcje.TESTER;
		report.comment = "comment3";
		date.setYear(2009);
		date.setMonth(7);
		date.setDate(28);
		report.date = date;
		zgloszenie.addReport(report);*/
		
		
		DendrytInMemoryDB.getInstance().addZgloszenie(zgloszenie);
		
		zgloszenie = new Zgloszenie();
		
		date = new Date();
		date.setYear(2009);
		date.setMonth(1);
		date.setDate(28);
		zgloszenie.setProdukt("produkt2");
		zgloszenie.setDataZgloszenia(date);
		zgloszenie.setImieZglaszajacego("imieZglaszajacego2");
		zgloszenie.setNazwiskoZglaszajacego("nazwiskoZglaszajacego2");
		zgloszenie.setOpisProblemu("opisProblemu2");
		zgloszenie.setTelefonZglaszajacego("123456789");
		
		wagaKlienta = new WagaProblemu();
		wagaKlienta.setNrWagi((short)2);
		
		zgloszenie.setWagaKlienta(wagaKlienta);
		
		DendrytInMemoryDB.getInstance().addZgloszenie(zgloszenie);
		//
		
		//super();
	}

	public int authenticate(Data person) {
		impl.setHttpSession(getHttpSession());
		int authenticate = impl.authenticate(person);

		return authenticate;
	}
	
	private HttpSession getHttpSession(){
		return getThreadLocalRequest().getSession();
	}

}
