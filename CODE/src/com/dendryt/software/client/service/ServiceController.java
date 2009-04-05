package com.dendryt.software.client.service;

import java.util.List;

import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.dendryt.software.client.designer.ClientData;
import com.dendryt.software.client.designer.DesignerInterface;
import com.dendryt.software.client.designer.IDesigner;
import com.dendryt.software.client.designer.IDesignerAsync;
import com.dendryt.software.client.designer.IUserManagement;
import com.dendryt.software.client.designer.IUserManagementAsync;
import com.dendryt.software.client.designer.NewClientAccountData;
import com.dendryt.software.client.designer.NewProductData;
import com.dendryt.software.client.designer.NewWorkerAccountData;
import com.dendryt.software.client.designer.WorkerData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class ServiceController {
	private IServiceAsync designerService;
	private IUserManagementAsync UM; 

	private ServiceInterface designerInterface;
		
		private AsyncCallback<Integer> addWorkerCallback = new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught) {
				Window.alert("Error"+caught.toString());
				
			}

			public void onSuccess(Integer result) {
				switch((Integer)result) {
				case 1:
					Window.alert("Podany login juz istnieje!");
					break;
				case 0:
					Window.alert("Konto dodane");
					break;
				case 4:
					Window.alert("Nieznany blad!");
					break;
				case 2:
					Window.alert("Za krotkie haslo!");
					break;
				case 3:
					Window.alert("Zly wybor funkcji pracownika!");
					break;
				}
				
			}
			
		};
		
		private AsyncCallback<Integer> addClientCallback = new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught) {
				Window.alert("Error"+caught.toString());
				
			}

			public void onSuccess(Integer result) {
				switch((Integer)result) {
				case 1:
					Window.alert("Podany login juz istnieje!");
					break;
				case 3:
					Window.alert("Podana nazwa klienta juz istnieje!");
					break;
				case 4:
					Window.alert("Nieznany blad!");
					break;
				case 2:
					Window.alert("Za krotkie haslo!");
					break;
				case 0:
					Window.alert("Konto dodane");
					break;
				}
				
			}
			
		};
		
		private AsyncCallback<Integer> addProductCallback = new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught) {
				Window.alert("Error"+caught.toString());
				
			}

			public void onSuccess(Integer result) {
				switch((Integer)result) {
				case -1:
					Window.alert("Podana grupa nie istnieje!");
					break;
				case 0:
					Window.alert("Podany produkt juz istnieje!");
					break;
				case 1:
					Window.alert("Produkt dodany");
					break;
				}
				
			}
			
		};
		
		private AsyncCallback<Integer> addGroupCallback = new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught) {
				Window.alert("Error"+caught.toString());
				
			}

			public void onSuccess(Integer result) {
				switch((Integer)result) {
				case 0:
					Window.alert("Podana grupa juz istnieje!");
					break;
				case 1:
					Window.alert("Grupa dodana");
					break;
				}
				
			}
			
		};
		
		private AsyncCallback<Integer> checkLoginAvailabilityCallback = new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught) {
				Window.alert("Error"+caught.toString());
				
			}

			public void onSuccess(Integer result) {
				switch((Integer)result) {
				case 0:
					Window.alert("Login niedostepny");
					break;
				case 1:
					Window.alert("Login dostepny");
					break;
				}
				
			}
			
		};
		
		private AsyncCallback<Integer> logOffCallback = new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught) {
				
			}

			public void onSuccess(Integer result) {
				
				}
				
			};
			
			
		private AsyncCallback<List<String>> getGroupsListCallback = new AsyncCallback<List<String>>(){
				
			public void onFailure(Throwable caught) {
				Window.alert("Blad pobierania listy grup!");
			}

			public void onSuccess(List<String> result) {
				// TODO Auto-generated method stub
				designerInterface.setGroupsRevisionData(result);
			}

					
		};
		
		private AsyncCallback<Integer> deleteGroupCallback = new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught) {
				Window.alert("Error"+caught.toString());
			}

			public void onSuccess(Integer result) {
				switch((Integer)result) {
				case 0:
					Window.alert("Podana grupa nie istnieje!");
					break;
				case 1:
					designerInterface.remGroupFromListWidget();
					Window.alert("Grupa usunieta");
					break;
				}
				}
				
		};
			
			private AsyncCallback<List<String>> getProductsListCallback = new AsyncCallback<List<String>>(){
				
				public void onFailure(Throwable caught) {
					Window.alert("Blad pobierania listy produktów!");
				}

				public void onSuccess(List<String> result) {
					// TODO Auto-generated method stub
					designerInterface.setProductsRevisionData(result);
				}

						
			};
			
			private AsyncCallback<Integer> deleteProductCallback = new AsyncCallback<Integer>(){

				public void onFailure(Throwable caught) {
					Window.alert("Error"+caught.toString());
				}

				public void onSuccess(Integer result) {
					switch((Integer)result) {
					case 0:
						Window.alert("Podany produkt nie istnieje!");
						break;
					case 1:
						designerInterface.remProductFromListWidget();
						Window.alert("Produkt usuniety");
						break;
					}
					}
					
				};
				
				private AsyncCallback<List<WorkerData>> getWorkersListCallback = new AsyncCallback<List<WorkerData>>(){
					
					public void onFailure(Throwable caught) {
						System.err.println(caught.getMessage());
						Window.alert("Blad pobierania listy pracownikow!");
					}

					public void onSuccess(List<WorkerData> result) {
						// TODO Auto-generated method stub
						designerInterface.setWorkersRevisionData(result);
					}

							
				};

				private AsyncCallback<Integer> deleteWorkerCallback = new AsyncCallback<Integer>(){

					public void onFailure(Throwable caught) {
						Window.alert("Error"+caught.toString());
					}

					public void onSuccess(Integer result) {
						switch((Integer)result) {
						case 0:
							Window.alert("Podany login nie istnieje!");
							break;
						case 1:
							designerInterface.remWorkerFromListWidget();
							Window.alert("Pracownik usuniety");
							break;
						}
					}
						
				};
				
				private AsyncCallback<List<ClientData>> getClientsListCallback = new AsyncCallback<List<ClientData>>(){
					
					public void onFailure(Throwable caught) {
						System.err.println(caught.getMessage());
						Window.alert("Blad pobierania listy klientow!");
					}

					public void onSuccess(List<ClientData> result) {
						// TODO Auto-generated method stub
						designerInterface.setClientsRevisionData(result);
					}

							
				};
				
				private AsyncCallback<Integer> deleteClientCallback = new AsyncCallback<Integer>(){

					public void onFailure(Throwable caught) {
						Window.alert("Error"+caught.toString());
					}

					public void onSuccess(Integer result) {
						switch((Integer)result) {
						case 0:
							Window.alert("Podany login nie istnieje!");
							break;
						case 1:
							designerInterface.remClientFromListWidget();
							Window.alert("Klient usuniety");
							break;
						}
					}
						
				};
				
				private AsyncCallback<List<Zgloszenie>> getNotificationsListCallback = new AsyncCallback<List<Zgloszenie>>(){
					
					public void onFailure(Throwable caught) {
						Window.alert("Blad pobierania listy zgloszen!");
					}

					public void onSuccess(List<Zgloszenie> result) {
						// TODO Auto-generated method stub
						designerInterface.setNotificationsRevisionData(result);
					}

							
				};
				
				private AsyncCallback<String> takeNotificationCallback = new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						Window.alert("Error"+caught.toString());
					}

					public void onSuccess(String result) {
						if(result == null) Window.alert("Przejecie zgloszenia nie powiodlo sie!");
						else {
							designerInterface.notificationTaken(result);
							Window.alert("Zgloszenie przejete");
						}
					}
				};
				
				private AsyncCallback<List<Zgloszenie>> getSelfNotificationsListCallback = new AsyncCallback<List<Zgloszenie>>(){
					
					public void onFailure(Throwable caught) {
						Window.alert("Blad pobierania listy przydzielonych zgloszen!");
					}

					public void onSuccess(List<Zgloszenie> result) {
						// TODO Auto-generated method stub
						designerInterface.setSelfNotificationsRevisionData(result);
					}

							
				};
				
				private AsyncCallback<Integer> assignWorkersCallback = new AsyncCallback<Integer>(){
					
					public void onFailure(Throwable caught) {
						Window.alert("Blad przydzielania pracownikow!");
					}

					public void onSuccess(Integer result) {
						switch((Integer)result) {
						case 0:
							Window.alert("Blad przydzielania pracownikow!");
							break;
						case 1:
							designerInterface.workersAssigned();
							Window.alert("Pracownicy przydzieleni");
							break;
						}
					}

							
				};
				
				private AsyncCallback<Integer> addReportCallback = new AsyncCallback<Integer>(){
					
					public void onFailure(Throwable caught) {
						Window.alert("Blad dodawania raportu!");
					}

					public void onSuccess(Integer result) {
						switch((Integer)result) {
						case 0:
							Window.alert("Blad dodawania raportu!");
							break;
						case 1:
							designerInterface.reportAdded();
							Window.alert("Raport dodany");
							break;
						}
					}

							
				};
				
				private AsyncCallback<Integer> addNotificationCallback = new AsyncCallback<Integer>(){
					
					public void onFailure(Throwable caught) {
						Window.alert("Blad dodawania zgloszenia!");
					}

					public void onSuccess(Integer result) {
						switch((Integer)result) {
						case 0:
							Window.alert("Blad dodawania zgloszenia!");
							break;
						case 1:
							designerInterface.notificationAdded();
							Window.alert("Zgloszenie dodane");
							break;
						}
					}

							
				};
				
		ServiceController(ServiceInterface designerInterface) {
			designerService=(IServiceAsync) GWT.create(IService.class);
			ServiceDefTarget endpoint = (ServiceDefTarget) designerService;
			endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "/IService");
			
			UM = (IUserManagementAsync) GWT.create(IUserManagement.class);
			ServiceDefTarget endpoint2 = (ServiceDefTarget) UM;
			endpoint2.setServiceEntryPoint(GWT.getModuleBaseURL() + "/IUserManagement");
			
			this.designerInterface = designerInterface;
		}
		

		void checkWorkerLoginAvailability(String login) {
			if(login == "") Window.alert("Login nie moze byc pusty!");
			else designerService.checkWorkerLoginAvailability(login, checkLoginAvailabilityCallback);
		}
		
		void checkClientLoginAvailability(String login) {
			if(login == "") Window.alert("Login nie moze byc pusty!");
			else designerService.checkClientLoginAvailability(login, checkLoginAvailabilityCallback);
		}
		
		void workerRegister(NewWorkerAccountData newWorkerAccountData) {
			designerService.workerRegister(newWorkerAccountData, addWorkerCallback);
		}
		
		void clientRegister(NewClientAccountData newClientAccountData) {
			designerService.clientRegister(newClientAccountData, addClientCallback);
		}
		
		void addNewGroup(String newGroupName) {
			System.err.println("ServiceController");
			designerService.addNewGroup(newGroupName, addGroupCallback);
		}
		
		void addNewProduct(NewProductData newProductData) {
			designerService.addNewProduct(newProductData, addProductCallback);
		}
		
		void logOff() {
			UM.logoff(logOffCallback);
		}
		
		void getGroupsList() {
			designerService.getGroupsList(getGroupsListCallback);
		}
		
		void deleteGroup(String groupName) {
			designerService.deleteGroup(groupName, deleteGroupCallback);
		}
		
		void getProductsList() {
			designerService.getProductsList(getProductsListCallback);
		}
		
		void deleteProduct(String productName) {
			designerService.deleteProduct(productName, deleteProductCallback);
		}


		public void getWorkersList() {
			designerService.getWorkersList(getWorkersListCallback);
		}


		public void deleteWorker(String workerLogin) {
			designerService.deleteWorker(workerLogin, deleteWorkerCallback);
		}


		public void getClientsList() {
			designerService.getClientsList(getClientsListCallback);
		}


		public void deleteClient(String clientLogin) {
			designerService.deleteClient(clientLogin, deleteClientCallback);
		}


		public void getNotificationsList() {
			designerService.getNotificationsList(getNotificationsListCallback);
		}


		public void takeNotification(Integer notificationNum) {
			designerService.takeNotification(notificationNum, takeNotificationCallback);
		}


		public void getSelfNotificationsList() {
			designerService.getSelfNotificationsList(getSelfNotificationsListCallback);
		}


		public void assignWorkers(int zgloszenie, String serwisant, String programista, String tester) {
			designerService.assignWorkers(zgloszenie, serwisant, programista, tester, assignWorkersCallback);
		}


		public void addReport(int zgloszenie, String comment, boolean next) {
			designerService.addReport(zgloszenie, comment, next, addReportCallback);
		}


		public void addNotification(Zgloszenie zgloszenie) {
			designerService.addNotification(zgloszenie, addNotificationCallback);
		}
		
}
