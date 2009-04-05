package com.dendryt.software.client.tester;

import java.util.List;

import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.dendryt.software.client.designer.ClientData;
import com.dendryt.software.client.designer.NewClientAccountData;
import com.dendryt.software.client.designer.NewProductData;
import com.dendryt.software.client.designer.NewWorkerAccountData;
import com.dendryt.software.client.designer.WorkerData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

public interface ITester extends RemoteService {
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
	int addNotification(Zgloszenie zgloszenie);
}
