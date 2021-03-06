package com.dendryt.software.client.designer;

import java.util.List;

import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IDesignerAsync {
	void workerRegister(NewWorkerAccountData newWorkerAccountData, AsyncCallback<Integer> callback);
	void clientRegister(NewClientAccountData newClientAccountData, AsyncCallback<Integer> callback);
	void checkWorkerLoginAvailability(String login, AsyncCallback<Integer> callback);
	void checkClientLoginAvailability(String login, AsyncCallback<Integer> callback);
	void addNewGroup(String groupName, AsyncCallback<Integer> callback);
	void addNewProduct(NewProductData newProductData, AsyncCallback<Integer> callback);
	void getGroupsList(AsyncCallback<List<String>> callback);
	void deleteGroup(String groupName, AsyncCallback<Integer> callback);
	void getProductsList(AsyncCallback<List<String>> callback);
	void deleteProduct(String productName, AsyncCallback<Integer> callback);
	void getWorkersList(AsyncCallback<List<WorkerData>> callback);
	void deleteWorker(String workerLogin, AsyncCallback<Integer> callback);
	void getClientsList(AsyncCallback<List<ClientData>> callback);
	void deleteClient(String clientLogin, AsyncCallback<Integer> callback);
	void getNotificationsList(AsyncCallback<List<Zgloszenie>> callback);
	void takeNotification(Integer notificationNum, AsyncCallback<String> callback);
	void getSelfNotificationsList(AsyncCallback<List<Zgloszenie>> callback);
	void assignWorkers(int zgloszenie, String serwisant, String programista, String tester, AsyncCallback<Integer> callback);
	void addReport(int zgloszenie, String comment, boolean next, AsyncCallback<Integer> callback);
}
