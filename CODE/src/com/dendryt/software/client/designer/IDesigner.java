package com.dendryt.software.client.designer;

import java.util.List;

import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

public interface IDesigner extends RemoteService {
	int workerRegister(NewWorkerAccountData newWorkerAccountData);
	int clientRegister(NewClientAccountData newClientAccountData);
	int checkWorkerLoginAvailability(String login);
	int checkClientLoginAvailability(String login);
	int addNewGroup(String groupName);
	int addNewProduct(NewProductData newProductData);
	List<String> getGroupsList();
	int deleteGroup(String groupName);
	List<String> getProductsList();
	int deleteProduct(String productName);
	List<WorkerData> getWorkersList();
	int deleteWorker(String workerLogin);
	List<ClientData> getClientsList();
	int deleteClient(String clientLogin);
	List<Zgloszenie> getNotificationsList();
	String takeNotification(Integer notificationNum);
	List<Zgloszenie> getSelfNotificationsList();
	int assignWorkers(int zgloszenie, String serwisant, String programista, String tester);
	int addReport(int zgloszenie, String comment, boolean next);
}
