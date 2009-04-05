package com.dendryt.software.server.components.inmemorydb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.dendryt.software.server.components.logger.ILoggerHome;
import com.dendryt.software.server.components.logger.LoggerHome;


//sorry... ;)
public class DendrytInMemoryDB {
	private Logger logger;
	private static DendrytInMemoryDB instance;
	private List<Zgloszenie> zgloszeniaList = new LinkedList<Zgloszenie>();
	private List<Zgloszenie> przypisaneZgloszenia = new LinkedList<Zgloszenie>();
	
	
	private DendrytInMemoryDB(){
		logger = LoggerHome.getInstance().getLogger(this);
		logger.info("DendrytInMemoryDB : initialized");
	}
	
	public static synchronized DendrytInMemoryDB getInstance(){
		if(instance == null){
			instance = new DendrytInMemoryDB();
		}		
		return instance;
	}
	
	public void addZgloszenie(Zgloszenie z){
		logger.debug("ADD: " + z);
		zgloszeniaList.add(z);
		logger.debug("after add. zgloszeniaList.size=" + zgloszeniaList.size());
	}
	
	public List<Zgloszenie> getZgloszeniaList(){
		logger.debug(ILoggerHome.LOG_MESSAGE_ENTER);
		logger.debug("zgloszeniaList.size=" + zgloszeniaList.size());
		return zgloszeniaList;
	}
	
	public List<Zgloszenie> getPrzypisaneZgloszeniaList(String userName) {
		przypisaneZgloszenia.clear();
		
		for(int i = 0; i < zgloszeniaList.size(); i++) {
			if(((zgloszeniaList.get(i).getProjektant() != null) && zgloszeniaList.get(i).getProjektant().compareTo(userName) == 0) ||
					((zgloszeniaList.get(i).getProgramista() != null) && zgloszeniaList.get(i).getProgramista().compareTo(userName) == 0) ||
					((zgloszeniaList.get(i).getSerwisant() != null) && zgloszeniaList.get(i).getSerwisant().compareTo(userName) == 0) ||
					((zgloszeniaList.get(i).getTester() != null) && zgloszeniaList.get(i).getTester().compareTo(userName) == 0)) {
				przypisaneZgloszenia.add(zgloszeniaList.get(i));
			}
		}
		
		logger.debug(ILoggerHome.LOG_MESSAGE_ENTER);
		logger.debug("przypisaneZgloszeniaList.size=" + przypisaneZgloszenia.size());
		
		return przypisaneZgloszenia;//res;
	}
	
	/**CheckIn Zgloszenie that already exists in DB
	 * @param z
	 */
	public synchronized void checkinZgloszenie(Zgloszenie z){
		Zgloszenie zgloszenie;
		for(Zgloszenie i : zgloszeniaList){
			if(i.getId() == z.getId()){
				int listId = zgloszeniaList.indexOf(i);
				logger.debug("Processing Zgloszenie with ID:" + listId);
				zgloszeniaList.remove(listId);
				zgloszeniaList.add(z);
				return; // completed
			}
		}
		
		logger.error("There is no Zgloszenie with ID: " + z.getId());
		return;
		
	}
}
