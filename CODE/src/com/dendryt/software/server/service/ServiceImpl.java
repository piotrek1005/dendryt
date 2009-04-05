package com.dendryt.software.server.service;



/*import com.dendrytsoftware.dendryt.database.DatabaseSession;
import com.dendrytsoftware.dendryt.database.DesignerSession;
import com.dendrytsoftware.dendryt.database.WorkerSession;
import com.google.gwt.user.client.Window;
import javax.security.auth.login.LoginException;*/
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.dendryt.software.client.client.tmp.Report;
import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.dendryt.software.client.designer.ClientData;
import com.dendryt.software.client.designer.IDesigner;
import com.dendryt.software.client.designer.NewClientAccountData;
import com.dendryt.software.client.designer.NewProductData;
import com.dendryt.software.client.designer.NewWorkerAccountData;
import com.dendryt.software.client.designer.WorkerData;
import com.dendryt.software.client.login.IFunkcje;
import com.dendryt.software.server.components.inmemorydb.DendrytInMemoryDB;
import com.dendrytsoftware.dendryt.database.classes.projektant.Klient;
import com.dendrytsoftware.dendryt.database.classes.projektant.Pracownik;
import com.dendrytsoftware.dendryt.database.classes.uzytkownik.Funkcja;
import com.dendrytsoftware.dendryt.database.classes.uzytkownik.Grupa;
import com.dendrytsoftware.dendryt.database.classes.uzytkownik.Produkt;
import com.dendrytsoftware.dendryt.database.classes.uzytkownik.WersjaProduktu;
import com.dendrytsoftware.dendryt.databaseIntf.IClientCreationResult;
import com.dendrytsoftware.dendryt.databaseIntf.IDesignerSession;
import com.dendrytsoftware.dendryt.databaseIntf.IUserSession;
import com.dendrytsoftware.dendryt.databaseIntf.IWorkerCreationResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ServiceImpl implements IDesigner {
	private IUserSession session;
	private List<Pracownik> workersListFromBase = null;
	private List<Klient> clientsListFromBase = null;
	private List<Produkt> productsListFromBase = null;
	private List<Grupa> groupsListFromBase = null;
	private String userName;
	
	
	
	ServiceImpl() {
	}
	
	
	/**
	 * return:
	 * 0 - niezarejestrowal
	 * 1 - zarejestrowal
	 */
	public int workerRegister(NewWorkerAccountData newWorkerAccountData) {
		int result = IWorkerCreationResult.DUPLICATE_LOGIN;
		
		if(checkWorkerLoginAvailability(newWorkerAccountData.login) == 1) {
			int functions[] = new int[newWorkerAccountData.functions.size()];
			
			for(int i = 0; i < functions.length; i++)
				functions[i] = (Integer)newWorkerAccountData.functions.toArray()[i];
			session.openTransaction();
			
			try {
				result = ((IDesignerSession)session).createNewWorker(newWorkerAccountData.name, newWorkerAccountData.surname, newWorkerAccountData.telephone,
					newWorkerAccountData.email, newWorkerAccountData.login, newWorkerAccountData.password, functions);
			}
			catch(Exception e) {
				System.err.println("exception");
				System.out.println(e.getMessage());

				result = IWorkerCreationResult.OTHER_ERROR;
			}
			
			switch(result) {
			case IWorkerCreationResult.USER_CREATED:
				session.getCurrentTransaction().commit();
				//result = 1;
				break;
			default:
				session.getCurrentTransaction().rollback();
			}
		}
		
		return result;
	}
	
	public int clientRegister(NewClientAccountData newClientAccountData) {
		int result = IClientCreationResult.DUPLICATE_LOGIN;
		
		if(checkClientLoginAvailability(newClientAccountData.login) == 1) {
			session.openTransaction();
			Date date = new Date();
			
			date.setDate(Integer.parseInt(newClientAccountData.policyExpirationDate));
			date.setMonth(Integer.parseInt(newClientAccountData.policyExpirationMonth));
			date.setYear(Integer.parseInt(newClientAccountData.policyExpirationYear));
			/*date.setDate(1);
			date.setMonth(1);
			date.setYear(2010);*/
			
			/*System.out.println(newClientAccountData.login + ", " + newClientAccountData.password + ", " + newClientAccountData.clientName + ", " + 
					newClientAccountData.email + ", " + newClientAccountData.town + ", " + newClientAccountData.street + ", " + newClientAccountData.houseNum + ", " + newClientAccountData.flatNum + ", " + 
					newClientAccountData.postCode + ", " + newClientAccountData.postTown + ", " + newClientAccountData.region + ", " + newClientAccountData.country);*/
			try {
				result = ((IDesignerSession)session).createNewClient(newClientAccountData.login, newClientAccountData.password, newClientAccountData.clientName,
					newClientAccountData.email, newClientAccountData.town, newClientAccountData.street, newClientAccountData.houseNum, newClientAccountData.flatNum,
					newClientAccountData.postCode, newClientAccountData.postTown, newClientAccountData.region, newClientAccountData.country, date);
			}
			catch(Exception e) {
				System.err.println("exception");
				System.out.println(e.getMessage());

				result = IClientCreationResult.OTHER_ERROR;
			}
			
			switch(result
					/*((IDesignerSession)session).createNewClient("klienttestowy", "haslohaslo", "a", "a@mail.pl", "a", "a", "1", "1",
							"11-111", "a", "a", "a", date)*/) {
			case IClientCreationResult.USER_CREATED:
				session.getCurrentTransaction().commit();
				break;
			/*case IUserCreationResult.DUPLICATE_LOGIN:
				System.err.println("duplicate login");
				//session.getCurrentTransaction().rollback();
				session.getCurrentTransaction().commit();
				result = 1;
				break;
			case IUserCreationResult.OTHER_ERROR:
				System.err.println("other error");
				//session.getCurrentTransaction().rollback();
				session.getCurrentTransaction().commit();
				result = 1;
				break;
			case IUserCreationResult.PASSWORD_TOO_SHORT:
				System.err.println("password to short");
				//session.getCurrentTransaction().rollback();
				session.getCurrentTransaction().commit();
				result = 1;
				break;
			case IUserCreationResult.WRONG_FUNCTION_NUMBER:
				System.err.println("wrong function number");
				//session.getCurrentTransaction().rollback();
				session.getCurrentTransaction().commit();
				result = 1;
				break;*/
			default:
				//System.err.println(tmp);
				session.getCurrentTransaction().rollback();
				//session.getCurrentTransaction().commit();
				//result = 0;
			}
		}
		return result;
	}
	
	/**
	 * return:
	 * 0 - niedostepny
	 * 1 - dostepny
	 */
	public int checkWorkerLoginAvailability(String login) {
		session.openTransaction();
		if(((IDesignerSession)session).isLoginAvaliable(login)) {
			session.getCurrentTransaction().commit();
			return 1;
		}
		else {
			session.getCurrentTransaction().commit();
			return 0;
		}
	}
	
	public int checkClientLoginAvailability(String login) {
		session.openTransaction();
		if(((IDesignerSession)session).isLoginAvaliable(login)) {
			session.getCurrentTransaction().commit();
			return 1;
		}
		else {
			session.getCurrentTransaction().commit();
			return 0;
		}
	}
	
	public int addNewProduct(NewProductData newProductData) {
		Produkt produkt = new Produkt();
		Grupa grupa = null;
		//LinkedList<WersjaProduktu> wersje = new LinkedList<WersjaProduktu>();
		WersjaProduktu wersja = new WersjaProduktu();
		//StringTokenizer tokenizer = new StringTokenizer(newProductData.version);
		boolean groupExists = false;
		
		session.openTransaction();
		/*List<Grupa>*/ //if(groupsListFromBase == null)
			groupsListFromBase = ((IDesignerSession)session).listGrupa();
		
		for(int i = 0; i < groupsListFromBase.size(); i++) {
			if(groupsListFromBase.get(i).getNazwaGrupy().compareTo(newProductData.group) == 0) {
				grupa = groupsListFromBase.get(i);
				groupExists = true;
				break;
			}
		}
		
		if(!groupExists) {
			session.getCurrentTransaction().commit();
			return -1;
		}
		
		
		//////////////////
		/*List<Produkt>*/ if(productsListFromBase == null) productsListFromBase = ((IDesignerSession)session).listProdukt();
		
		for(int i = 0; i < productsListFromBase.size(); i++) {
			if((productsListFromBase.get(i).getNazwaProduktu().compareTo(newProductData.product) == 0) && (productsListFromBase.get(i).getGrupa().getNazwaGrupy().compareTo(newProductData.group) == 0)) {
				produkt = productsListFromBase.get(i);

				List<WersjaProduktu> istniejaceWersje = produkt.getWersje();
				
				for(int j = 0; j < istniejaceWersje.size(); j++) {
					if(istniejaceWersje.get(j).getWersja().compareTo(newProductData.version) == 0) {
						
						session.getCurrentTransaction().rollback();
						
						return 0;
					}
				}
				
				
				wersja.setProdukt(produkt);
				wersja.setWersja(newProductData.version);
			
				produkt.addWersja(wersja);
				//((IDesignerSession)session).insertObject(wersja);
				
				session.getCurrentTransaction().commit();
			
				return 1;
			}
		}

		//////////////////
		
		
		
		/*while(tokenizer.hasMoreTokens()) {
			WersjaProduktu wersja = new WersjaProduktu();
			wersja.setWersja(tokenizer.nextToken());
			wersja.setProdukt(produkt);
			wersje.add(wersja);
		}*/

		
		
		//TODO GAZMEN: cos sie tu zjebalo po ostatniej zmianie warstwy bazy!!!!!
		
		//session.openTransaction();
		try {
			//((IDesignerSession)session).insertProdukt(produkt);
			
			wersja.setProdukt(produkt);
			wersja.setWersja(newProductData.version);
			
			produkt.setGrupa(grupa);
			produkt.setNazwaProduktu(newProductData.product);
			//produkt.setWersje(wersje);
			produkt.addWersja(wersja);
			
			((IDesignerSession)session).insertObject(produkt);
			((IDesignerSession)session).insertObject(wersja);
			productsListFromBase.add(produkt);
		}
		catch(Exception e) {
			System.err.println("exception");
			System.out.println(e.getMessage());
			session.getCurrentTransaction().rollback();
			return 0;
		}
		session.getCurrentTransaction().commit();
		return 1;
	}
	
	public int addNewGroup(String newGroup) {
		System.err.println("ServiceImpl");
		/*Grupa grupa = new Grupa();
		
		grupa.setNazwaGrupy(newGroup);
		
		
		
		for(int i = 0; i < groupsListFromBase.size(); i++)
			if(newGroup.compareTo(groupsListFromBase.get(i).getNazwaGrupy()) == 0) return 0;
		
		session.openTransaction();
		
		try {
			((IDesignerSession)session).insertObject(grupa);
			//groupsListFromBase.add(grupa);
		}
		catch(Exception e) {
			session.getCurrentTransaction().rollback();
			return 0;
		}
		session.getCurrentTransaction().commit();*/
		return 1;
	}
	
	
	public void setSession(IUserSession session) {
		this.session = session;
	}


	public List<String> getGroupsList() {
		List<String> groupsList = new LinkedList<String>();
		
		session.openTransaction();
		
		//if(groupsListFromBase == null)
			groupsListFromBase = ((IDesignerSession)session).listGrupa();
		int groupsNum = groupsListFromBase.size();
		
		for(int i = 0; i < groupsNum; i++) {
			groupsList.add(groupsListFromBase.get(i).getNazwaGrupy());
		}
		
		
		session.getCurrentTransaction().commit();
		
		return groupsList;
	}


	public int deleteGroup(String groupName) {
		Grupa grupa = null;
		
		session.openTransaction();
		//List<Grupa> groupsList = ((IDesignerSession)session).listGrupa();
		
		for(int i = 0; i < groupsListFromBase.size(); i++) {
			if(groupsListFromBase.get(i).getNazwaGrupy().compareTo(groupName) == 0) {
				grupa = groupsListFromBase.get(i);
				//groupsListFromBase.remove(i);
				break;
			}
		}
		
		if(grupa != null) {
			try {
				((IDesignerSession)session).deleteObject(grupa);
			}
			catch(Exception e) {
				session.getCurrentTransaction().rollback();
				
				System.err.println("exception");
				System.err.println(e.getMessage());
				
				return 0;
			}
			
			session.getCurrentTransaction().commit();
			
			return 1;
		}
		else {
			session.getCurrentTransaction().commit();
			
			return 0;
		}
	}


	public List<String> getProductsList() {
		List<String> productsList = new LinkedList<String>();
		
		
		session.openTransaction();
		
		//if(productsListFromBase == null)
			productsListFromBase = ((IDesignerSession)session).listProdukt();
			
		int productsNum = productsListFromBase.size();
		
		
		for(int i = 0; i < productsNum; i++) {
			String grupaProdukt = productsListFromBase.get(i).getGrupa().getNazwaGrupy();
			
			grupaProdukt += "; ";
			
			grupaProdukt += productsListFromBase.get(i).getNazwaProduktu();
			
			grupaProdukt += "; ";
			
			int versionsNum = productsListFromBase.get(i).getWersje().size();
			
			for(int j = 0; j < versionsNum; j++) {
				String grupaProduktWersja = new String(grupaProdukt);
				
				grupaProduktWersja += productsListFromBase.get(i).getWersje().get(j).getWersja();
				
				//System.out.println(grupaProduktWersja);
				
				productsList.add(grupaProduktWersja);
			}

		}
		
		session.getCurrentTransaction().commit();
		
		return productsList;
		
		/*List<String> groupsList = new LinkedList<String>();
		int groupsNum = ((IDesignerSession)session).listGrupa().size();
		
		session.openTransaction();
		
		for(int i = 0; i < groupsNum; i++) {
			groupsList.add(((IDesignerSession)session).listGrupa().get(i).getNazwaGrupy());
		}
		
		session.getCurrentTransaction().commit();
		
		return groupsList;*/
	}


	public int deleteProduct(String productName) {
		Produkt produkt = null;
		StringTokenizer tokenizer = new StringTokenizer(productName);
		String grupa = tokenizer.nextToken(), produktNazwa = tokenizer.nextToken(), wersja = tokenizer.nextToken();
		
		grupa = grupa.substring(0, grupa.length() - 1);
		produktNazwa = produktNazwa.substring(0, produktNazwa.length() - 1);
		
		session.openTransaction();
		/*List<Produkt>*/ productsListFromBase = ((IDesignerSession)session).listProdukt();
		
		for(int i = 0; i < productsListFromBase.size(); i++) {
			if((productsListFromBase.get(i).getNazwaProduktu().compareTo(produktNazwa) == 0) && (productsListFromBase.get(i).getGrupa().getNazwaGrupy().compareTo(grupa) == 0)) {
				int versionsNum = productsListFromBase.get(i).getWersje().size();
				
				for(int j = 0; j < versionsNum; j++)
					if(productsListFromBase.get(i).getWersje().get(j).getWersja().compareTo(wersja) == 0) {
						if(versionsNum == 1) produkt = productsListFromBase.get(i);
						else 
						{
							productsListFromBase.get(i).getWersje().remove(j);
							session.getCurrentTransaction().commit();
							
							return 1;
						}
						
						break;
					}
			}
		}
		
		if(produkt != null) {
			//((IDesignerSession)session).deleteProdukt(produkt);
			((IDesignerSession)session).deleteObject(produkt);
			//productsListFromBase.remove(produkt);
			session.getCurrentTransaction().commit();
			
			return 1;
		}
		else {
			session.getCurrentTransaction().commit();
			
			return 0;
		}
	}


	public List<WorkerData> getWorkersList() {
		List<WorkerData> workersList = new LinkedList<WorkerData>();
		
		session.openTransaction();
		
		//if(workersListFromBase == null)
			workersListFromBase = ((IDesignerSession)session).listPracownik();
		
		int workersNum = workersListFromBase.size();
		
		
		
		for(int i = 0; i < workersNum; i++) {
			WorkerData workerData = new WorkerData();
			
			workerData.login = workersListFromBase.get(i).getLogin();
			workerData.email = workersListFromBase.get(i).getEmail();
			workerData.name = workersListFromBase.get(i).getImie();
			workerData.surname = workersListFromBase.get(i).getNazwisko();
			workerData.telephone = workersListFromBase.get(i).getTelefonPracownika();
			workerData.functions = new HashSet<Integer>();
			
			/*for(int j = 0; j < ((IDesignerSession)session).listPracownik().get(i).getFunkcje().size(); j++)
				workerData.functions.add((Integer)((IDesignerSession)session).listPracownik().get(i).getFunkcje().toArray()[j]);*/
			
			//workersListFromBase.get(i).getFunkcje().addAll(workerData.functions);
		
			for(int j = 0; j < workersListFromBase.get(i).getFunkcje().size(); j++)
				workerData.functions.add(((Funkcja)workersListFromBase.get(i).getFunkcje().toArray()[j]).getNrFunkcji());
			
			workersList.add(workerData);
		}
		
		session.getCurrentTransaction().commit();
		
		return workersList;
	}


	public int deleteWorker(String workerLogin) {
		for(int i = 0; i < workersListFromBase.size(); i++) {
			
			Pracownik pracownik = workersListFromBase.get(i);
			
			if(pracownik.getLogin().compareTo(workerLogin) == 0) {
				//workersListFromBase.remove(i);
				
				session.openTransaction();
				
				((IDesignerSession)session).deleteObject(pracownik);
				
				session.getCurrentTransaction().commit();
				
				return 1;
			}
		}
		return 0;
	}


	public List<ClientData> getClientsList() {
		List<ClientData> clientsList = new LinkedList<ClientData>();
		
		session.openTransaction();
		
		//if(workersListFromBase == null)
			clientsListFromBase = ((IDesignerSession)session).listKlient();
		
		int clientsNum = clientsListFromBase.size();
		
		
		
		for(int i = 0; i < clientsNum; i++) {
			ClientData clientData = new ClientData();
			
			clientData.login = clientsListFromBase.get(i).getLogin();
			clientData.email = clientsListFromBase.get(i).getEmail();
			clientData.clientName = clientsListFromBase.get(i).getNazwaKlienta();
			clientData.city = clientsListFromBase.get(i).getAdres().getMiejscowosc();
			
			clientData.flatNum = clientsListFromBase.get(i).getAdres().getNumerMieszkania();
			clientData.houseNum = clientsListFromBase.get(i).getAdres().getNumerDomu();
			clientData.postCity = clientsListFromBase.get(i).getAdres().getMiejscowoscPoczty();
			clientData.postcode = clientsListFromBase.get(i).getAdres().getKodPocztowy();
			clientData.region = clientsListFromBase.get(i).getAdres().getWojewodztwo();
			clientData.street = clientsListFromBase.get(i).getAdres().getUlica();
			clientData.country = clientsListFromBase.get(i).getAdres().getKraj();
			
			clientData.policyDay = clientsListFromBase.get(i).isPolisa().getDate();
			clientData.policyMonth = clientsListFromBase.get(i).isPolisa().getMonth();
			clientData.policyYear = clientsListFromBase.get(i).isPolisa().getYear();
			
			clientsList.add(clientData);
		}
		
		session.getCurrentTransaction().commit();
		
		return clientsList;
	}


	public int deleteClient(String clientLogin) {
		for(int i = 0; i < clientsListFromBase.size(); i++) {
			
			Klient klient = clientsListFromBase.get(i);
			
			if(klient.getLogin().compareTo(clientLogin) == 0) {
				//workersListFromBase.remove(i);
				
				session.openTransaction();
				
				((IDesignerSession)session).deleteObject(klient);
				
				session.getCurrentTransaction().commit();
				
				return 1;
			}
		}
		return 0;
	}


	public List<Zgloszenie> getNotificationsList() {
		return DendrytInMemoryDB.getInstance().getZgloszeniaList();
	}


	public String takeNotification(Integer notificationNum) {
		if(DendrytInMemoryDB.getInstance().getZgloszeniaList().get(notificationNum).getProjektant() == null)
			DendrytInMemoryDB.getInstance().getZgloszeniaList().get(notificationNum).setProjektant(userName);
		else return null;
		
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public List<Zgloszenie> getSelfNotificationsList() {
		return DendrytInMemoryDB.getInstance().getPrzypisaneZgloszeniaList(userName);
	}


	public int assignWorkers(int zgloszenie, String serwisant, String programista, String tester) {
		DendrytInMemoryDB.getInstance().getPrzypisaneZgloszeniaList(userName).get(zgloszenie).setSerwisant(serwisant);
		DendrytInMemoryDB.getInstance().getPrzypisaneZgloszeniaList(userName).get(zgloszenie).setProgramista(programista);
		DendrytInMemoryDB.getInstance().getPrzypisaneZgloszeniaList(userName).get(zgloszenie).setTester(tester);
		
		return 1;
	}


	public int addReport(int zgloszenie, String comment, boolean next) {
		Report report = new Report();
		
		report.date = new Date();
		
		report.date.setMonth(report.date.getMonth() + 1);
		report.date.setYear(report.date.getYear() + 1900);
		report.comment = comment;
		report.userName = userName;
		report.userType = IFunkcje.SERWISANT;
		
		DendrytInMemoryDB.getInstance().getPrzypisaneZgloszeniaList(userName).get(zgloszenie).addReport(report);
		if(next) DendrytInMemoryDB.getInstance().getPrzypisaneZgloszeniaList(userName).get(zgloszenie).przekazDoPrzodu();
		else DendrytInMemoryDB.getInstance().getPrzypisaneZgloszeniaList(userName).get(zgloszenie).przekazDoTylu();
		
		return 1;
	}


	public int addNotification(Zgloszenie zgloszenie) {
		DendrytInMemoryDB.getInstance().addZgloszenie(zgloszenie);
		
		return 1;
	}
}
