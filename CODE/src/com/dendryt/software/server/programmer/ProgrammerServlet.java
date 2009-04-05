package com.dendryt.software.server.programmer;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.dendryt.software.client.designer.ClientData;
import com.dendryt.software.client.designer.IDesigner;
import com.dendryt.software.client.designer.NewClientAccountData;
import com.dendryt.software.client.designer.NewProductData;
import com.dendryt.software.client.designer.NewWorkerAccountData;
import com.dendryt.software.client.designer.WorkerData;
import com.dendryt.software.client.programmer.IProgrammer;
import com.dendryt.software.client.service.IService;
import com.dendryt.software.server.IType;
import com.dendryt.software.server.components.logger.LoggerHome;
import com.dendrytsoftware.dendryt.database.classes.uzytkownik.Grupa;
import com.dendrytsoftware.dendryt.databaseIntf.IUserSession;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProgrammerServlet extends RemoteServiceServlet implements IProgrammer {

	private Logger logger;	
	private static final long serialVersionUID = 1L;
	ProgrammerImpl impl = new ProgrammerImpl();
	
	public ProgrammerServlet()
	{
		logger = LoggerHome.getInstance().getLogger(this);
	}
	
	
	private HttpSession getHttpSession(){
		return getThreadLocalRequest().getSession();
	}
	
	private IUserSession getUserSession(){
		return (IUserSession) getHttpSession().getAttribute("userSession");
	}
	

	public int workerRegister(NewWorkerAccountData newWorkerAccountData) {
		impl.setSession(getUserSession());
		int res = impl.workerRegister(newWorkerAccountData);

		//for megatesting ;) purposes only:
		String testTextValue = (String)getHttpSession().getAttribute("testText");
		logger.info("registering new worker : " + newWorkerAccountData.toString());
	
		return res;

	}
	
	public int clientRegister(NewClientAccountData newClientAccountData) {
		impl.setSession(getUserSession());
		int res = impl.clientRegister(newClientAccountData);

		//for megatesting ;) purposes only:
		String testTextValue = (String)getHttpSession().getAttribute("testText");
		logger.info("registering new worker : " + newClientAccountData.toString());
	
		return res;

	}
	
	public int checkWorkerLoginAvailability(String login) {
		logger.info("Checking availability for login: " + login);
		impl.setSession(getUserSession());
		int res = impl.checkWorkerLoginAvailability(login);
		
		
		/*if(authenticate > 0)
			getThreadLocalRequest().getSession().setAttribute("login", "true");*/
		return res;

	}
	
	public int checkClientLoginAvailability(String login) {
		impl.setSession(getUserSession());
		int res = impl.checkClientLoginAvailability(login);
		
		
		/*if(authenticate > 0)
			getThreadLocalRequest().getSession().setAttribute("login", "true");*/
		return res;

	}
	
	public int addNewGroup(String newGroup) {
		System.err.println("ServiceServlet");
		impl.setSession(getUserSession());
		int res = impl.addNewGroup(newGroup);
		
		return res;
	}
	
	public int addNewProduct(NewProductData newProductData) {
		impl.setSession(getUserSession());
		int res = impl.addNewProduct(newProductData);
		
		return res;
	}
	
	public List<String> getGroupsList() {
		impl.setSession(getUserSession());
		List<String> res = impl.getGroupsList();
		
		return res; 
	}


	public int deleteGroup(String groupName) {
		impl.setSession(getUserSession());
		int res = impl.deleteGroup(groupName);
		
		return res;
	}


	public List<String> getProductsList() {
		impl.setSession(getUserSession());
		List<String> res = impl.getProductsList();
		
		return res; 
	}


	public int deleteProduct(String productName) {
		impl.setSession(getUserSession());
		int res = impl.deleteProduct(productName);
		
		return res;
	}


	public List<WorkerData> getWorkersList() {
		impl.setSession(getUserSession());
		List<WorkerData> res = impl.getWorkersList();
		
		return res; 
	}


	public int deleteWorker(String workerLogin) {
		impl.setSession(getUserSession());
		int res = impl.deleteWorker(workerLogin);
		
		return res;
	}


	public List<ClientData> getClientsList() {
		impl.setSession(getUserSession());
		List<ClientData> res = impl.getClientsList();
		
		return res; 
	}


	public int deleteClient(String clientLogin) {
		impl.setSession(getUserSession());
		int res = impl.deleteClient(clientLogin);
		
		return res;
	}


	public List<Zgloszenie> getNotificationsList() {
		impl.setSession(getUserSession());
		List<Zgloszenie> res = impl.getNotificationsList();
		
		return res;
	}


	public String takeNotification(Integer notificationNum) {
		impl.setSession(getUserSession());
		impl.setUserName((String)getHttpSession().getAttribute(IType.USERNAME));
		String res = impl.takeNotification(notificationNum);
		
		return res;
	}


	public List<Zgloszenie> getSelfNotificationsList() {
		impl.setSession(getUserSession());
		impl.setUserName((String)getHttpSession().getAttribute(IType.USERNAME));
		List<Zgloszenie> res = impl.getSelfNotificationsList();
		
		return res;
	}


	public int assignWorkers(int zgloszenie, String serwisant, String programista, String tester) {
		impl.setSession(getUserSession());
		int res = impl.assignWorkers(zgloszenie, serwisant, programista, tester);
		
		return res;
	}


	public int addReport(int zgloszenie, String comment, boolean next) {
		impl.setSession(getUserSession());
		int res = impl.addReport(zgloszenie, comment, next);
		
		return res;
	}


	public int addNotification(Zgloszenie zgloszenie) {
		impl.setSession(getUserSession());
		int res = impl.addNotification(zgloszenie);
		
		return res;
	}

}