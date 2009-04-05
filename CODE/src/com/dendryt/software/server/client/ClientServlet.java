package com.dendryt.software.server.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.dendryt.software.client.client.IClient;
import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.dendryt.software.server.components.CommonTools;
import com.dendryt.software.server.components.inmemorydb.DendrytInMemoryDB;
import com.dendryt.software.server.components.logger.ILoggerHome;
import com.dendryt.software.server.components.logger.LoggerHome;
import com.dendrytsoftware.dendryt.database.classes.uzytkownik.Produkt;
import com.dendrytsoftware.dendryt.database.classes.uzytkownik.WersjaProduktu;
import com.dendrytsoftware.dendryt.databaseIntf.IClientSession;
import com.dendrytsoftware.dendryt.databaseIntf.IDesignerSession;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ClientServlet extends RemoteServiceServlet implements IClient{

	private Logger logger;
	DendrytInMemoryDB inMemoryDB;

	
	public ClientServlet(){		
		logger = LoggerHome.getInstance().getLogger(this);
		logger.info(ILoggerHome.LOG_MESSAGE_ENTER);
		inMemoryDB = DendrytInMemoryDB.getInstance();		
		logger.info(ILoggerHome.LOG_MESSAGE_EXIT);
	}
	public void addNewZgloszenie(Zgloszenie z) {
		logger.info(ILoggerHome.LOG_MESSAGE_PARAM_VALUE + " " + z.toString());
//		IDesignerSession session = (IDesignerSession) CommonTools.getUserSession(getHttpSession());
		
		inMemoryDB.addZgloszenie(z);
		
		logger.info(ILoggerHome.LOG_MESSAGE_EXIT);
	}

	public List<Zgloszenie> getZgloszenia() {
		List<Zgloszenie> l = inMemoryDB.getZgloszeniaList();
		return l;
	}
	
	private HttpSession getHttpSession(){
		return getThreadLocalRequest().getSession();
	}
	
	
	public List<com.dendryt.software.client.client.Produkt> getProduktList() {
		logger.debug(ILoggerHome.LOG_MESSAGE_ENTER);
		IClientSession session = (IClientSession) CommonTools.getUserSession(getHttpSession());
		logger.debug(ILoggerHome.LOG_MESSAGE_PARAM_VALUE + ": " + session);
		List<com.dendryt.software.client.client.Produkt> l = new LinkedList<com.dendryt.software.client.client.Produkt>();
		List<Produkt> listProdukt = session.listProdukt();
		if(listProdukt == null){
			return  null;
		}
		for(Produkt p : listProdukt){
			String name = p.getNazwaProduktu();
			List<WersjaProduktu> wersjeList = p.getWersje();
			String[] wersjeArray = new String[wersjeList.size()];
			logger.debug("addedToReturnList:" + name + "{");
			for(int i = 0; i < wersjeArray.length; i++){
				wersjeArray[i] = wersjeList.get(i).getWersja();
				logger.debug("     wersja: " + wersjeList.get(i));			
			}
			logger.debug("}");

			l.add(new com.dendryt.software.client.client.Produkt(name, wersjeArray));
			
		}
		return l;
	}

}
