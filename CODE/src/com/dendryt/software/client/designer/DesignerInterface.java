package com.dendryt.software.client.designer;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.dendryt.software.client.IUserInterface;
import com.dendryt.software.client.client.tmp.Report;
import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.dendryt.software.client.login.IFunkcje;
import com.dendryt.software.client.login.LogInInterface;
import com.dendryt.software.server.components.inmemorydb.DendrytInMemoryDB;
import com.dendrytsoftware.dendryt.database.classes.uzytkownik.Funkcja;
import com.dendrytsoftware.dendryt.database.classes.uzytkownik.Grupa;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class DesignerInterface implements IUserInterface {
	private RootPanel rootPanel;
	private DesignerController designerController;
	
	private List<WorkerData> workersList;
	private List<ClientData> clientsList;
	private List<Zgloszenie> notificationsList, selfNotificationsList;
	private List<Report> reportsList, selfReportsList;
	//List<String> groupsList;
	private ListBox groupsListWidget, productsListWidget, workersListWidget, clientsListWidget, notificationsListWidget, selfNotificationsListWidget, reportsListWidget, selfReportsListWidget, serwisantListWidget, programistaListWidget, testerListWidget;
	private int removedGroupNum, removedProductNum, removedWorkerNum, removedClientNum, takenNotification;
	private String userName;
	
	
	private StackPanel stackPanel = new StackPanel();
	
	
	public DesignerInterface(String userName) {
		
		this.userName = userName;	
		
//		UM.getAllUsersData(new AsyncCallback(){
//
//			public void onFailure(Throwable caught) {
//				Window.alert("BLAD!");				
//			}
//
//			public void onSuccess(Object result) {
//				StringBuilder s = new StringBuilder();
//				String[] resultArray = (String[])result;
//				for(String i : resultArray){
//					s.append(i);
//				}
//				Window.alert(s.toString());				
//			}
//			
//		});
		
		
		designerController = new DesignerController(this);
		rootPanel = RootPanel.get();
	}

	public void mainInterface() {
		Window.setTitle("Dendryt - Interfejs projektanta");
		
		rootPanel.clear();
		
		
		
		stackPanel.add(addUserInterface(), "Dodawanie uzytkownika");
		stackPanel.add(usersRevisionInterface(), "Przeglad uzytkownikow");
		stackPanel.add(addGroupInterface(), "Dodawanie grupy produktow");
		stackPanel.add(groupsRevisionInterface(), "Przeglad grup produktow");
		stackPanel.add(addProductInterface(), "Dodawanie produktu");
		stackPanel.add(productsRevisionInterface(), "Przeglad produktow");
		stackPanel.add(notificationsRevisionInterface(), "Przeglad wszystkich zgloszen (i raportow)");
		stackPanel.add(selfNotificationsRevisionInterface(), "Przeglad przydzielonych zgloszen (i raportow)");

		
		Button logOut = new Button("Wyloguj", new ClickListener(){

			public void onClick(Widget sender) {
					designerController.logOff();
					LogInInterface logInInterface = new LogInInterface();
					
					logInInterface.mainInterface();
				}
				 
			 });
		
		rootPanel.add(stackPanel);
		rootPanel.add(logOut);
		}
	
	private Grid addingWorker() {
		Grid addWorkerGrid = new Grid(8, 3);

		addWorkerGrid.setWidget(0, 0, new Label("Login:"));
		final TextBox login = new TextBox();
		addWorkerGrid.setWidget(0, 1, login);
		Button sprawdzDostepnosc = new Button("Sprawdz dostepnosc", new ClickListener(){

			public void onClick(Widget sender) {
					designerController.checkWorkerLoginAvailability(login.getText());
				}
				 
			 });
		addWorkerGrid.setWidget(0, 2, sprawdzDostepnosc);
		
		addWorkerGrid.setWidget(1, 0, new Label("Haslo:"));
		final PasswordTextBox haslo = new PasswordTextBox();
		addWorkerGrid.setWidget(1, 1, haslo);
		
		addWorkerGrid.setWidget(2, 0, new Label("Potwierdz haslo:"));
		final PasswordTextBox haslo2 = new PasswordTextBox();
		addWorkerGrid.setWidget(2, 1, haslo2);
		
		addWorkerGrid.setWidget(3, 0, new Label("Imie:"));
		final TextBox imie = new TextBox();
		addWorkerGrid.setWidget(3, 1, imie);
		
		addWorkerGrid.setWidget(4, 0, new Label("Nazwisko:"));
		final TextBox nazwisko = new TextBox();
		addWorkerGrid.setWidget(4, 1, nazwisko);
		
		addWorkerGrid.setWidget(5, 0, new Label("Telefon kontaktowy:"));
		final TextBox telefon = new TextBox();
		addWorkerGrid.setWidget(5, 1, telefon);
		
		addWorkerGrid.setWidget(6, 0, new Label("E-Mail:"));
		final TextBox email = new TextBox();
		addWorkerGrid.setWidget(6, 1, email);
		
		addWorkerGrid.setWidget(1, 2, new Label("Funkcje pracownika:"));
		
		final CheckBox cb[] = new CheckBox[4];
		
		cb[0] = new CheckBox("Serwisant");
		addWorkerGrid.setWidget(2, 2, cb[0]);
		
		cb[1] = new CheckBox("Projektant");
		addWorkerGrid.setWidget(3, 2, cb[1]);
		
		cb[2] = new CheckBox("Programista");
		addWorkerGrid.setWidget(4, 2, cb[2]);
		
		cb[3] = new CheckBox("Tester");
		addWorkerGrid.setWidget(5, 2, cb[3]);

		
		Button dodaj = new Button("Dodaj pracownika", new ClickListener(){

			public void onClick(Widget sender) {
				
					boolean userType = false;
					int i = 0;
					
					while(i < cb.length) {
						if(cb[i++].isChecked()) {
							userType = true;
							break;
						}
					}
					
					if(!userType)
						Window.alert("Nie wybrano funkcji pracownika!");
					else if((login.getText() == "") || (haslo.getText() == ""))
						Window.alert("Prosze podac login i haslo.");
					else if(haslo.getText().compareTo(haslo2.getText()) != 0)
						Window.alert("Blad potwierdzenia hasla!");
					else {
						NewWorkerAccountData newWorkerAccountData = new NewWorkerAccountData();
						
						newWorkerAccountData.login = login.getText();
						newWorkerAccountData.password = haslo.getText();
						newWorkerAccountData.name = imie.getText();
						newWorkerAccountData.surname = nazwisko.getText();
						newWorkerAccountData.telephone = telefon.getText();
						newWorkerAccountData.email = email.getText();
						newWorkerAccountData.functions = new HashSet<Integer>();
						for(i = 0; i < 4; i++) if(cb[i].isChecked()) newWorkerAccountData.functions.add(i);
						designerController.workerRegister(newWorkerAccountData);
					}
				}
				 
			 });
		addWorkerGrid.setWidget(7, 0, dodaj);
		
		return addWorkerGrid;
	}
	
	private Grid addingClient() {
		Grid addClientGrid = new Grid(15, 3);
		
		addClientGrid.setWidget(0, 0, new Label("Login:"));
		final TextBox login = new TextBox();
		addClientGrid.setWidget(0, 1, login);
		Button sprawdzDostepnosc = new Button("Sprawdz dostepnosc", new ClickListener(){

			public void onClick(Widget sender) {
					designerController.checkClientLoginAvailability(login.getText());
				}
				 
			 });
		addClientGrid.setWidget(0, 2, sprawdzDostepnosc);
		
		addClientGrid.setWidget(1, 0, new Label("Haslo:"));
		final PasswordTextBox haslo = new PasswordTextBox();
		addClientGrid.setWidget(1, 1, haslo);
		
		addClientGrid.setWidget(2, 0, new Label("Potwierdz haslo:"));
		final PasswordTextBox haslo2 = new PasswordTextBox();
		addClientGrid.setWidget(2, 1, haslo2);
		
		addClientGrid.setWidget(3, 0, new Label("Nazwa klienta:"));
		final TextBox nazwaKlienta = new TextBox();
		addClientGrid.setWidget(3, 1, nazwaKlienta);
		
		addClientGrid.setWidget(4, 0, new Label("E-mail:"));
		final TextBox email = new TextBox();
		addClientGrid.setWidget(4, 1, email);
		
		addClientGrid.setWidget(5, 0, new Label("Miejscowosc:"));
		final TextBox miejscowosc = new TextBox();
		addClientGrid.setWidget(5, 1, miejscowosc);
		
		addClientGrid.setWidget(6, 0, new Label("Ulica:"));
		final TextBox ulica = new TextBox();
		addClientGrid.setWidget(6, 1, ulica);
		
		addClientGrid.setWidget(7, 0, new Label("Nr. domu:"));
		final TextBox nrDomu = new TextBox();
		addClientGrid.setWidget(7, 1, nrDomu);
		
		addClientGrid.setWidget(8, 0, new Label("Nr. mieszkania:"));
		final TextBox nrMieszkania = new TextBox();
		addClientGrid.setWidget(8, 1, nrMieszkania);
		
		addClientGrid.setWidget(9, 0, new Label("Kod:"));
		final TextBox kod = new TextBox();
		addClientGrid.setWidget(9, 1, kod);
		
		addClientGrid.setWidget(10, 0, new Label("Miejscowosc poczty:"));
		final TextBox miejscPoczty = new TextBox();
		addClientGrid.setWidget(10, 1, miejscPoczty);
		
		addClientGrid.setWidget(11, 0, new Label("Wojewodztwo:"));
		final TextBox wojewodztwo = new TextBox();
		addClientGrid.setWidget(11, 1, wojewodztwo);
		
		addClientGrid.setWidget(12, 0, new Label("Kraj:"));
		final TextBox kraj = new TextBox();
		addClientGrid.setWidget(12, 1, kraj);
		
		addClientGrid.setWidget(13, 0, new Label("Data wygasniecia polisy:"));
		
		Grid polisa = new Grid(3, 2);
		
		polisa.setWidget(0, 0, new Label("Dzien:"));
		final TextBox polisaDzien = new TextBox();
		polisaDzien.setWidth("80px");
		polisa.setWidget(0, 1, polisaDzien);
		
		polisa.setWidget(1, 0, new Label("Miesiac:"));
		final TextBox polisaMiesiac = new TextBox();
		polisaMiesiac.setWidth("80px");
		polisa.setWidget(1, 1, polisaMiesiac);
		
		polisa.setWidget(2, 0, new Label("Rok:"));
		final TextBox polisaRok = new TextBox();
		polisaRok.setWidth("80px");
		polisa.setWidget(2, 1, polisaRok);

		addClientGrid.setWidget(13, 1, polisa);
		
		Button dodaj = new Button("Dodaj klienta", new ClickListener(){

			public void onClick(Widget sender) {
				if((login.getText() == "") || (haslo.getText() == ""))
					Window.alert("Prosze podac login i haslo.");
				else if(haslo.getText().compareTo(haslo2.getText()) != 0)
					Window.alert("Blad potwierdzenia hasla!");
				else {
						NewClientAccountData newClientAccountData = new NewClientAccountData();
						
						newClientAccountData.login = login.getText();
						newClientAccountData.password = haslo.getText();
						newClientAccountData.clientName = nazwaKlienta.getText();
						newClientAccountData.email = email.getText();
						newClientAccountData.town = miejscowosc.getText();
						newClientAccountData.street = ulica.getText();
						newClientAccountData.houseNum = nrDomu.getText();
						newClientAccountData.flatNum = nrMieszkania.getText();
						newClientAccountData.postCode = kod.getText();
						newClientAccountData.postTown = miejscPoczty.getText();
						newClientAccountData.region = wojewodztwo.getText();
						newClientAccountData.country = kraj.getText();
						
						newClientAccountData.policyExpirationDate = polisaDzien.getText();
						newClientAccountData.policyExpirationMonth = polisaMiesiac.getText();
						newClientAccountData.policyExpirationYear = polisaRok.getText();
						
						/*newClientAccountData.policyExpiration.setDate(Integer.parseInt(tokenizer.nextToken("-")));
						newClientAccountData.policyExpiration.setMonth(Integer.parseInt(tokenizer.nextToken("-")));
						newClientAccountData.policyExpiration.setYear(Integer.parseInt(tokenizer.nextToken("-")));*/
						
						designerController.clientRegister(newClientAccountData);
				}
			}
				 
		});
		addClientGrid.setWidget(14, 0, dodaj);
		
		return addClientGrid;
	}
	
	public VerticalPanel addUserInterface() {
		
		final VerticalPanel addUserPanel = new VerticalPanel();
		final Grid addWorker = addingWorker();
		final Grid addClient = addingClient();
		
		HorizontalPanel chooseUserGroupPanel = new HorizontalPanel();
		
		final RadioButton rb1[] = new RadioButton[2];
		
		rb1[0] = new RadioButton("pracownik/klient", "Pracownik");
		rb1[0].setChecked(true);
		rb1[0].addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				addUserPanel.remove(addClient);
				addUserPanel.add(addWorker);
			}
			
		});
		chooseUserGroupPanel.add(rb1[0]);
		
		rb1[1] = new RadioButton("pracownik/klient", "Klient");
		rb1[1].addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				addUserPanel.remove(addWorker);
				addUserPanel.add(addClient);
			}
			
		});
		chooseUserGroupPanel.add(rb1[1]);
		
		addUserPanel.add(chooseUserGroupPanel);
		addUserPanel.add(new Label(""));
		
		
		
		addUserPanel.add(addWorker);
		
		return addUserPanel;
	}
	
	/*VerticalPanel addUserInterface() {
		
		VerticalPanel addUserPanel = new VerticalPanel();

		Grid addUserGrid = new Grid(7, 5);

		addUserGrid.setWidget(0, 0, new Label("Login:"));
		final TextBox login = new TextBox();
		addUserGrid.setWidget(0, 1, login);
		Button sprawdzDostepnosc = new Button("Sprawdz dostepnosc", new ClickListener(){

			public void onClick(Widget sender) {
					designerController.checkLoginAvailability(login.getText());
				}
				 
			 });
		addUserGrid.setWidget(0, 2, sprawdzDostepnosc);
		
		addUserGrid.setWidget(1, 0, new Label("Haslo:"));
		final TextBox haslo = new TextBox();
		addUserGrid.setWidget(1, 1, haslo);
		
		addUserGrid.setWidget(2, 0, new Label("Imie:"));
		final TextBox imie = new TextBox();
		addUserGrid.setWidget(2, 1, imie);
		
		addUserGrid.setWidget(3, 0, new Label("Nazwisko:"));
		final TextBox nazwisko = new TextBox();
		addUserGrid.setWidget(3, 1, nazwisko);
		
		addUserGrid.setWidget(4, 0, new Label("Adres zamieszkania:"));
		final TextBox adres = new TextBox();
		addUserGrid.setWidget(4, 1, adres);
		
		addUserGrid.setWidget(5, 0, new Label("Telefon kontaktowy:"));
		final TextBox telefon = new TextBox();
		addUserGrid.setWidget(5, 1, telefon);
		
		addUserGrid.setWidget(6, 0, new Label("E-mail:"));
		final TextBox email = new TextBox();
		addUserGrid.setWidget(6, 1, email);
		
		addUserGrid.setWidget(1, 2, new Label("Typ uzytkownika:"));
		
		final RadioButton rb[] = new RadioButton[5];
		
		rb[0] = new RadioButton("users", "Klient");
		addUserGrid.setWidget(2, 2, rb[0]);
		
		rb[1] = new RadioButton("users", "Serwisant");
		addUserGrid.setWidget(3, 2, rb[1]);
		
		rb[2] = new RadioButton("users", "Projektant");
		addUserGrid.setWidget(4, 2, rb[2]);
		
		rb[3] = new RadioButton("users", "Programista");
		addUserGrid.setWidget(5, 2, rb[3]);
		
		rb[4] = new RadioButton("users", "Tester");
		addUserGrid.setWidget(6, 2, rb[4]);
		
		addUserGrid.setWidget(0, 3, new Label("    "));
		
		addUserPanel.add(addUserGrid);
		
		Button dodaj = new Button("Dodaj uzytkownika", new ClickListener(){

			public void onClick(Widget sender) {
					boolean userType = false;
					int i = 0;
					
					while(i < rb.length) {
						if(rb[i++].isChecked()) {
							userType = true;
							break;
						}
					}
					
					if(!userType)
						Window.alert("Nie wybrano typu uzytkownika!");
					else if((login.getText() == "") || (haslo.getText() == ""))
						Window.alert("Prosze podac login i haslo.");
					else {
						NewAccountData newAccountData = new NewAccountData();
						
						newAccountData.login = login.getText();
						newAccountData.password = haslo.getText();
						newAccountData.name = imie.getText();
						newAccountData.surname = nazwisko.getText();
						newAccountData.address = adres.getText();
						newAccountData.email = email.getText();
						newAccountData.userType = i;
						
						designerController.register(newAccountData);
					}
				}
				 
			 });
		addUserPanel.add(dodaj);
		
		return addUserPanel;
	}*/
	
	void setWorkersRevisionData(List<WorkerData> workersList) {
		this.workersList = workersList;
		
		workersListWidget.clear();
		
		for(int i = 0; i < workersList.size(); i++) {
			workersListWidget.addItem(workersList.get(i).login);
		}
		
		
		
		for(int i = 0; i < workersList.size(); i++) if(workersList.get(i).functions != null) {

			if(checkFunctions(workersList.get(i).functions, IFunkcje.SERWISANT))
				serwisantListWidget.addItem(workersList.get(i).login);
			
			if(checkFunctions(workersList.get(i).functions, IFunkcje.PROGRAMISTA))
				programistaListWidget.addItem(workersList.get(i).login);
			
			if(checkFunctions(workersList.get(i).functions, IFunkcje.TESTER))
				testerListWidget.addItem(workersList.get(i).login);
		}
	}
	
	void remWorkerFromListWidget() {
		workersListWidget.removeItem(removedWorkerNum);
	}
	
	private HorizontalPanel workersRevisionInterface() {
		HorizontalPanel workerRevisionPanel = new HorizontalPanel();
		Grid workerRevisionGrid = new Grid(8, 4);
		VerticalPanel listPanel = new VerticalPanel();
		
		/*final Button pobierzDane = new Button("Pobierz dane", new ClickListener(){

			public void onClick(Widget sender) {
				
					
				}
				 
			 });
		
		pobierzDane.setEnabled(false);
		
		workerRevisionGrid.setWidget(7, 2, pobierzDane);*/
		
		final Button usun = new Button("Usun pracownika", new ClickListener(){

			public void onClick(Widget sender) {
					designerController.deleteWorker(workersListWidget.getItemText(removedWorkerNum = workersListWidget.getSelectedIndex()));
				}
				 
			 });
		
		usun.setEnabled(false);
		
		workerRevisionGrid.setWidget(7, 2, usun);
		
		
		final Button pobierzListe = new Button("Pobierz liste", new ClickListener(){

			public void onClick(Widget sender) {
				
				designerController.getWorkersList();
				}
				 
			 });
		
		//workerRevisionGrid.setWidget(7, 0, pobierzListe);
		
		workerRevisionGrid.setWidget(0, 1, new Label("Login:"));
		final TextBox login = new TextBox();
		login.setEnabled(false);
		workerRevisionGrid.setWidget(0, 2, login);

		workersListWidget = new ListBox();
		workersListWidget.setVisibleItemCount(9);
		
		
		listPanel.add(workersListWidget);
		listPanel.add(pobierzListe);
		workerRevisionPanel.add(listPanel);
		workerRevisionPanel.add(workerRevisionGrid);
	
		/*workerRevisionGrid.setWidget(1, 1, (new Label("Haslo:")));
		final PasswordTextBox haslo = new PasswordTextBox();
		haslo.setEnabled(false);
		workerRevisionGrid.setWidget(1, 2, haslo);*/
		
		workerRevisionGrid.setWidget(1, 1, new Label("Imie:"));
		final TextBox imie = new TextBox();
		imie.setEnabled(false);
		workerRevisionGrid.setWidget(1, 2, imie);
		
		workerRevisionGrid.setWidget(2, 1, new Label("Nazwisko:"));
		final TextBox nazwisko = new TextBox();
		nazwisko.setEnabled(false);
		workerRevisionGrid.setWidget(2, 2, nazwisko);
		
		workerRevisionGrid.setWidget(3, 1, new Label("Telefon kontaktowy:"));
		final TextBox telefon = new TextBox();
		telefon.setEnabled(false);
		workerRevisionGrid.setWidget(3, 2, telefon);
		
		workerRevisionGrid.setWidget(4, 1, new Label("E-Mail:"));
		final TextBox email = new TextBox();
		email.setEnabled(false);
		workerRevisionGrid.setWidget(4, 2, email);
		
		workerRevisionGrid.setWidget(1, 3, new Label("Funkcje pracownika:"));
		
		final CheckBox cb[] = new CheckBox[4];
		
		cb[0] = new CheckBox("Serwisant");
		cb[0].setEnabled(false);
		workerRevisionGrid.setWidget(2, 3, cb[0]);
		
		cb[1] = new CheckBox("Projektant");
		cb[1].setEnabled(false);
		workerRevisionGrid.setWidget(3, 3, cb[1]);
		
		cb[2] = new CheckBox("Programista");
		cb[2].setEnabled(false);
		workerRevisionGrid.setWidget(4, 3, cb[2]);
		
		cb[3] = new CheckBox("Tester");
		cb[3].setEnabled(false);
		workerRevisionGrid.setWidget(5, 3, cb[3]);
		
		workersListWidget.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				// TODO Auto-generated method stub
				int selectedIndex = workersListWidget.getSelectedIndex();
				if((selectedIndex >= 0) && (selectedIndex < workersListWidget.getItemCount())) {
				
					login.setText(workersList.get(selectedIndex).login);
					imie.setText(workersList.get(selectedIndex).name);
					nazwisko.setText(workersList.get(selectedIndex).surname);
					telefon.setText(workersList.get(selectedIndex).telephone);
					email.setText(workersList.get(selectedIndex).email);
					
					for(int i = 0; i < 4; i++) cb[i].setChecked(false);
					//cb[1].setChecked(true);
					
					for(int i = 0; i < workersList.get(selectedIndex).functions.toArray().length; i++) {
						switch((Integer)workersList.get(selectedIndex).functions.toArray()[i]) {
						case 0: cb[0].setChecked(true); break;
						case 1: cb[1].setChecked(true); break;
						case 2: cb[2].setChecked(true); break;
						case 3: cb[3].setChecked(true); break;
						}
					}
					
					usun.setEnabled(true);
					
				}
				else usun.setEnabled(false);
					
			}
			
		});
		
		return workerRevisionPanel;
	}
	
	public void setClientsRevisionData(List<ClientData> clientsList) {
		this.clientsList = clientsList;
		
		clientsListWidget.clear();
		
		for(int i = 0; i < clientsList.size(); i++) {
			clientsListWidget.addItem(clientsList.get(i).login);
		}
	}
	
	void remClientFromListWidget() {
		clientsListWidget.removeItem(removedClientNum);
	}
	
	
	private HorizontalPanel clientRevisionInterface() {
		HorizontalPanel clientRevisionPanel = new HorizontalPanel();
		Grid clientRevisionGrid = new Grid(13, 2);
		VerticalPanel listPanel = new VerticalPanel();
		
		clientsListWidget = new ListBox();
		clientsListWidget.setVisibleItemCount(19);
		
		final Button pobierzListe = new Button("Pobierz liste", new ClickListener(){

			public void onClick(Widget sender) {
				
				designerController.getClientsList();
				}
				 
			 });
		
		listPanel.add(clientsListWidget);
		listPanel.add(pobierzListe);
		
		clientRevisionPanel.add(listPanel);
		clientRevisionPanel.add(clientRevisionGrid);
		
		final Button usun = new Button("Usun klienta", new ClickListener(){

			public void onClick(Widget sender) {
				
				designerController.deleteClient(clientsListWidget.getItemText(removedClientNum = clientsListWidget.getSelectedIndex()));
				}
				 
			 });
		
		usun.setEnabled(false);
		
		clientRevisionGrid.setWidget(12, 1, usun);
		
		clientRevisionGrid.setWidget(0, 0, new Label("Login:"));
		final TextBox login = new TextBox();
		login.setEnabled(false);
		clientRevisionGrid.setWidget(0, 1, login);
		/*Button sprawdzDostepnosc = new Button("Sprawdz dostepnosc", new ClickListener(){

			public void onClick(Widget sender) {
					designerController.checkClientLoginAvailability(login.getText());
				}
				 
			 });
		addClientGrid.setWidget(0, 2, sprawdzDostepnosc);*/
		
		/*clientRevisionGrid.setWidget(1, 0, new Label("Haslo:"));
		final PasswordTextBox haslo = new PasswordTextBox();
		clientRevisionGrid.setWidget(1, 1, haslo);
		
		clientRevisionGrid.setWidget(2, 0, new Label("Potwierdz haslo:"));
		final PasswordTextBox haslo2 = new PasswordTextBox();
		clientRevisionGrid.setWidget(2, 1, haslo2);*/
		
		clientRevisionGrid.setWidget(1, 0, new Label("Nazwa klienta:"));
		final TextBox nazwaKlienta = new TextBox();
		nazwaKlienta.setEnabled(false);
		clientRevisionGrid.setWidget(1, 1, nazwaKlienta);
		
		clientRevisionGrid.setWidget(2, 0, new Label("E-mail:"));
		final TextBox email = new TextBox();
		email.setEnabled(false);
		clientRevisionGrid.setWidget(2, 1, email);
		
		clientRevisionGrid.setWidget(3, 0, new Label("Miejscowosc:"));
		final TextBox miejscowosc = new TextBox();
		miejscowosc.setEnabled(false);
		clientRevisionGrid.setWidget(3, 1, miejscowosc);
		
		clientRevisionGrid.setWidget(4, 0, new Label("Ulica:"));
		final TextBox ulica = new TextBox();
		ulica.setEnabled(false);
		clientRevisionGrid.setWidget(4, 1, ulica);
		
		clientRevisionGrid.setWidget(5, 0, new Label("Nr. domu:"));
		final TextBox nrDomu = new TextBox();
		nrDomu.setEnabled(false);
		clientRevisionGrid.setWidget(5, 1, nrDomu);
		
		clientRevisionGrid.setWidget(6, 0, new Label("Nr. mieszkania:"));
		final TextBox nrMieszkania = new TextBox();
		nrMieszkania.setEnabled(false);
		clientRevisionGrid.setWidget(6, 1, nrMieszkania);
		
		clientRevisionGrid.setWidget(7, 0, new Label("Kod:"));
		final TextBox kod = new TextBox();
		kod.setEnabled(false);
		clientRevisionGrid.setWidget(7, 1, kod);
		
		clientRevisionGrid.setWidget(8, 0, new Label("Miejscowosc poczty:"));
		final TextBox miejscPoczty = new TextBox();
		miejscPoczty.setEnabled(false);
		clientRevisionGrid.setWidget(8, 1, miejscPoczty);
		
		clientRevisionGrid.setWidget(9, 0, new Label("Wojewodztwo:"));
		final TextBox wojewodztwo = new TextBox();
		wojewodztwo.setEnabled(false);
		clientRevisionGrid.setWidget(9, 1, wojewodztwo);
		
		clientRevisionGrid.setWidget(10, 0, new Label("Kraj:"));
		final TextBox kraj = new TextBox();
		kraj.setEnabled(false);
		clientRevisionGrid.setWidget(10, 1, kraj);
		
		clientRevisionGrid.setWidget(11, 0, new Label("Data wygasniecia polisy:"));
		
		Grid polisa = new Grid(3, 2);
		
		polisa.setWidget(0, 0, new Label("Dzien:"));
		final TextBox polisaDzien = new TextBox();
		polisaDzien.setEnabled(false);
		polisaDzien.setWidth("80px");
		polisa.setWidget(0, 1, polisaDzien);
		
		polisa.setWidget(1, 0, new Label("Miesiac:"));
		final TextBox polisaMiesiac = new TextBox();
		polisaMiesiac.setEnabled(false);
		polisaMiesiac.setWidth("80px");
		polisa.setWidget(1, 1, polisaMiesiac);
		
		polisa.setWidget(2, 0, new Label("Rok:"));
		final TextBox polisaRok = new TextBox();
		polisaRok.setEnabled(false);
		polisaRok.setWidth("80px");
		polisa.setWidget(2, 1, polisaRok);

		clientRevisionGrid.setWidget(11, 1, polisa);
		
		clientsListWidget.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				// TODO Auto-generated method stub
				int selectedIndex = clientsListWidget.getSelectedIndex();
				if((selectedIndex >= 0) && (selectedIndex < clientsListWidget.getItemCount())) {
				
					login.setText(clientsList.get(selectedIndex).login);
					nazwaKlienta.setText(clientsList.get(selectedIndex).clientName);
					email.setText(clientsList.get(selectedIndex).email);
					miejscowosc.setText(clientsList.get(selectedIndex).city);
					ulica.setText(clientsList.get(selectedIndex).street);
					nrDomu.setText(clientsList.get(selectedIndex).houseNum);
					nrMieszkania.setText(clientsList.get(selectedIndex).flatNum);
					kod.setText(clientsList.get(selectedIndex).postcode);
					miejscPoczty.setText(clientsList.get(selectedIndex).postCity);
					wojewodztwo.setText(clientsList.get(selectedIndex).region);
					kraj.setText(clientsList.get(selectedIndex).country);
					polisaDzien.setText(String.valueOf(clientsList.get(selectedIndex).policyDay));
					polisaMiesiac.setText(String.valueOf(clientsList.get(selectedIndex).policyMonth));
					polisaRok.setText(String.valueOf(clientsList.get(selectedIndex).policyYear));
					
					usun.setEnabled(true);
					
				}
				else usun.setEnabled(false);
					
			}
			
		});
		
		return clientRevisionPanel;
	}
	
	VerticalPanel usersRevisionInterface() {
		
		final VerticalPanel userRevisionPanel = new VerticalPanel();
		final HorizontalPanel workerRevision = workersRevisionInterface();
		final HorizontalPanel clientRevision = clientRevisionInterface();
		
		HorizontalPanel chooseUserGroupPanel = new HorizontalPanel();
		
		final RadioButton rb1[] = new RadioButton[2];
		
		rb1[0] = new RadioButton("pracownik/klient", "Pracownik");
		rb1[0].setChecked(true);
		rb1[0].addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				userRevisionPanel.remove(clientRevision);
				userRevisionPanel.add(workerRevision);
			}
			
		});
		chooseUserGroupPanel.add(rb1[0]);
		
		rb1[1] = new RadioButton("pracownik/klient", "Klient");
		rb1[1].addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				userRevisionPanel.remove(workerRevision);
				userRevisionPanel.add(clientRevision);
			}
			
		});
		chooseUserGroupPanel.add(rb1[1]);
		
		userRevisionPanel.add(chooseUserGroupPanel);
		userRevisionPanel.add(new Label(""));
		
		
		
		userRevisionPanel.add(workerRevision);
		
		return userRevisionPanel;
	}
	
	Grid addProductInterface() {
		Grid addProductGrid = new Grid(4, 2);
		
		addProductGrid.setWidget(0, 0, new Label("Grupa:"));
		addProductGrid.setWidget(1, 0, new Label("Produkt:"));
		addProductGrid.setWidget(2, 0, new Label("Wersja/wersje:"));
		
		final TextBox grupa = new TextBox();
		addProductGrid.setWidget(0, 1, grupa);
		
		final TextBox produkt = new TextBox();
		addProductGrid.setWidget(1, 1, produkt);
		
		final TextBox wersja = new TextBox();
		addProductGrid.setWidget(2, 1, wersja);

		Button dodaj = new Button("Dodaj", new ClickListener(){

			public void onClick(Widget sender) {
					if(produkt.getText() == "") Window.alert("Nazwa produktu nie moze byc pusta!");
					//else registerService.checkLoginAvailability(login.getText(), checkAvailabilityCallback);
					
					else if(wersja.getText() == "") Window.alert("Wersja produktu nie moze byc pusta!");
					//else registerService.checkLoginAvailability(login.getText(), checkAvailabilityCallback);
					
					else {
						NewProductData newProductData = new NewProductData();
						
						newProductData.group = grupa.getText();
						newProductData.product = produkt.getText();
						newProductData.version = wersja.getText();
						
						designerController.addNewProduct(newProductData);
					}
				}
				 
			 });
		addProductGrid.setWidget(3, 0, dodaj);
		
		return addProductGrid;
	}
	
	void setProductsRevisionData(List<String> productsList) {
		//this.groupsList = groupsList;
		
		productsListWidget.clear();
		
		for(int i = 0; i < productsList.size(); i++) {
			productsListWidget.addItem(productsList.get(i));
		}
	}
	
	void remProductFromListWidget() {
		productsListWidget.removeItem(removedProductNum);
	}
	
	Grid productsRevisionInterface() {	
		Grid productsRevisionGrid = new Grid(3, 2);
		
		productsRevisionGrid.setWidget(0, 0, new Label("Grupa; Produkt; Wersja"));
		
		productsListWidget = new ListBox();
		
		final Button delete = new Button("Usun", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.deleteProduct(productsListWidget.getItemText(removedProductNum = productsListWidget.getSelectedIndex()));
			}
		});
			
		delete.setEnabled(false);
		
		productsListWidget.setVisibleItemCount(10);
		productsListWidget.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				// TODO Auto-generated method stub
				if((productsListWidget.getSelectedIndex() >= 0) && (productsListWidget.getSelectedIndex() < productsListWidget.getItemCount()))
					delete.setEnabled(true);
				else delete.setEnabled(false);
					
			}
			
		});
		
		productsRevisionGrid.setWidget(1, 0, productsListWidget);
		
		Button refresh = new Button("Pobierz liste", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.getProductsList();
			}
			
		});
		
		productsRevisionGrid.setWidget(2, 0, refresh);
		
		
		productsRevisionGrid.setWidget(1, 1, delete);
		
		return productsRevisionGrid;
	}
	
	Grid addGroupInterface() {
		Grid addGroupGrid = new Grid(2, 2);
		
		addGroupGrid.setWidget(0, 0, new Label("Nazwa:"));

		
		final TextBox nazwa = new TextBox();
		addGroupGrid.setWidget(0, 1, nazwa);

		Button dodaj = new Button("Dodaj", new ClickListener(){

			public void onClick(Widget sender) {
					if(nazwa.getText() == "") Window.alert("Nazwa grupy nie moze byc pusta!");
					else {
						designerController.addNewGroup(nazwa.getText());
					}
				}
				 
			 });
		addGroupGrid.setWidget(1, 0, dodaj);
		
		return addGroupGrid;
	}
	
	void setGroupsRevisionData(List<String> groupsList) {
		//this.groupsList = groupsList;
		
		groupsListWidget.clear();
		
		for(int i = 0; i < groupsList.size(); i++) {
			groupsListWidget.addItem(groupsList.get(i));
		}
	}
	
	void remGroupFromListWidget() {
		groupsListWidget.removeItem(removedGroupNum);
	}
	
	Grid groupsRevisionInterface() {
		Grid groupsRevisionGrid = new Grid(2, 2);
		
		groupsListWidget = new ListBox();
		
		final Button delete = new Button("Usun", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.deleteGroup(groupsListWidget.getItemText(removedGroupNum = groupsListWidget.getSelectedIndex()));
			}
		});
			
		delete.setEnabled(false);
		
		groupsListWidget.setVisibleItemCount(10);
		groupsListWidget.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				// TODO Auto-generated method stub
				if((groupsListWidget.getSelectedIndex() >= 0) && (groupsListWidget.getSelectedIndex() < groupsListWidget.getItemCount()))
					delete.setEnabled(true);
				else delete.setEnabled(false);
					
			}
			
		});
		
		groupsRevisionGrid.setWidget(0, 0, groupsListWidget);
		
		Button refresh = new Button("Pobierz liste", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.getGroupsList();
			}
			
		});
		
		groupsRevisionGrid.setWidget(1, 0, refresh);
		
		
		groupsRevisionGrid.setWidget(0, 1, delete);
			
	

		return groupsRevisionGrid;
	}
	
	public void setNotificationsRevisionData(List<Zgloszenie> notificationsList) {
		notificationsListWidget.clear();
		
		this.notificationsList = notificationsList;
		
		for(int i = 0; i < notificationsList.size(); i++) {
			notificationsListWidget.addItem(notificationsList.get(i).getProdukt());
		}
	}
	
	HorizontalPanel reportsRevisionInterface() {
		HorizontalPanel reportsRevisionPanel = new HorizontalPanel();
		
		Grid reportsRevisionGridL = new Grid(1, 2), reportsRevisionGridR = new Grid(7, 2);
		reportsRevisionPanel.add(reportsRevisionGridL);
		reportsRevisionPanel.add(reportsRevisionGridR);
		
		reportsRevisionGridR.setWidget(0, 0, new Label("Produkt:"));
		reportsRevisionGridR.setWidget(1, 0, new Label("Pracownik:"));
		reportsRevisionGridR.setWidget(2, 0, new Label("Typ pracownika:"));
		reportsRevisionGridR.setWidget(3, 0, new Label("Data:"));
		reportsRevisionGridR.setWidget(4, 0, new Label("Komentarz:"));
		
		final TextBox produkt = new TextBox();
		produkt.setEnabled(false);
		reportsRevisionGridR.setWidget(0, 1, produkt);
		
		final TextBox pracownik = new TextBox();
		pracownik.setEnabled(false);
		reportsRevisionGridR.setWidget(1, 1, pracownik);
		
		final TextBox typ = new TextBox();
		typ.setEnabled(false);
		reportsRevisionGridR.setWidget(2, 1, typ);
		
		
		final Grid dataGrid = new Grid(3, 2);
		dataGrid.setWidget(0, 0, new Label("Dzien:"));
		dataGrid.setWidget(1, 0, new Label("Miesiac:"));
		dataGrid.setWidget(2, 0, new Label("Rok:"));
		
		final TextBox dzien = new TextBox();
		dzien.setEnabled(false);
		dataGrid.setWidget(0, 1, dzien);
		
		final TextBox miesiac = new TextBox();
		miesiac.setEnabled(false);
		dataGrid.setWidget(1, 1, miesiac);
		
		final TextBox rok = new TextBox();
		rok.setEnabled(false);
		dataGrid.setWidget(2, 1, rok);
		
		reportsRevisionGridR.setWidget(3, 1, dataGrid);
		
		final TextArea komentarz = new TextArea();
		komentarz.setEnabled(false);
		komentarz.setCharacterWidth(30);
		komentarz.setVisibleLines(10);
		reportsRevisionGridR.setWidget(4, 1, komentarz);
		
	
		
		//notificationsRevisionGridR.setWidget(4, 0, new Label("Data zgloszenia:"));
		
		
		//reportsListWidget = new ListBox();
		
		/*final Button delete = new Button("Usun", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.deleteGroup(groupsListWidget.getItemText(removedGroupNum = groupsListWidget.getSelectedIndex()));
			}
		});
			
		delete.setEnabled(false);*/
		
		reportsListWidget.setVisibleItemCount(10);
		
		final Button zgloszenia = new Button("Zgloszenia", new ClickListener() {

			public void onClick(Widget sender) {
				//designerController.deleteGroup(groupsListWidget.getItemText(removedGroupNum = groupsListWidget.getSelectedIndex()));
				stackPanel.remove(6);
				stackPanel.insert(notificationsRevisionInterface(), 6);
				stackPanel.setStackText(6, "Przeglad wszystkich zgloszen (i raportow)");
				stackPanel.showStack(6);
			}
		});
		
		//zgloszenia.setEnabled(false);
		
		reportsRevisionGridR.setWidget(6, 0, zgloszenia);
		
		if((reportsListWidget.getSelectedIndex() >= 0) && (reportsListWidget.getSelectedIndex() < reportsListWidget.getItemCount())) {
			
			produkt.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProdukt());
			pracownik.setText(reportsList.get(reportsListWidget.getSelectedIndex()).userName);
			
			switch(reportsList.get(reportsListWidget.getSelectedIndex()).userType) {
			case IFunkcje.SERWISANT :
				typ.setText("Serwisant");
				break;
			case IFunkcje.PROGRAMISTA :
				typ.setText("Programista");
				break;
			case IFunkcje.PROJEKTANT :
				typ.setText("Projektant");
				break;
			case IFunkcje.TESTER :
				typ.setText("Tester");
				break;
			}
			
			dzien.setText(String.valueOf(reportsList.get(reportsListWidget.getSelectedIndex()).date.getDate()));
			miesiac.setText(String.valueOf(reportsList.get(reportsListWidget.getSelectedIndex()).date.getMonth()));
			rok.setText(String.valueOf(reportsList.get(reportsListWidget.getSelectedIndex()).date.getYear()));
			komentarz.setText(reportsList.get(reportsListWidget.getSelectedIndex()).comment);
			
		}
		
		reportsListWidget.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				// TODO Auto-generated method stub
				if((reportsListWidget.getSelectedIndex() >= 0) && (reportsListWidget.getSelectedIndex() < reportsListWidget.getItemCount())) {
					
					produkt.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProdukt());
					pracownik.setText(reportsList.get(reportsListWidget.getSelectedIndex()).userName);
					
					switch(reportsList.get(reportsListWidget.getSelectedIndex()).userType) {
					case IFunkcje.SERWISANT :
						typ.setText("Serwisant");
						break;
					case IFunkcje.PROGRAMISTA :
						typ.setText("Programista");
						break;
					case IFunkcje.PROJEKTANT :
						typ.setText("Projektant");
						break;
					case IFunkcje.TESTER :
						typ.setText("Tester");
						break;
					}
					
					dzien.setText(String.valueOf(reportsList.get(reportsListWidget.getSelectedIndex()).date.getDate()));
					miesiac.setText(String.valueOf(reportsList.get(reportsListWidget.getSelectedIndex()).date.getMonth()));
					rok.setText(String.valueOf(reportsList.get(reportsListWidget.getSelectedIndex()).date.getYear()));
					komentarz.setText(reportsList.get(reportsListWidget.getSelectedIndex()).comment);
					
				}
					/*delete.setEnabled(true);
				else delete.setEnabled(false);*/
					
			}
			
		});
		
		reportsRevisionGridL.setWidget(0, 0, reportsListWidget);
		
		/*Button refresh = new Button("Pobierz liste", new ClickListener() {

			public void onClick(Widget sender) {
				for(int i = 0; i < reportsList.size(); i++) {
					reportsListWidget.addItem(reportsList.get(i).userName);
				}
			}
			
		});
		
		reportsRevisionGridL.setWidget(1, 0, refresh);*/
		
		
		//notificationsRevisionGrid.setWidget(0, 1, delete);
		
		return reportsRevisionPanel;
	}
	
	void notificationTaken(String login) {
		notificationsList.get(takenNotification).setProjektant(login);
	}
	
	HorizontalPanel notificationsRevisionInterface() {
		HorizontalPanel notificationsRevisionPanel = new HorizontalPanel();
		
		Grid notificationsRevisionGridL = new Grid(2, 2), notificationsRevisionGridR = new Grid(13, 2);
		notificationsRevisionPanel.add(notificationsRevisionGridL);
		notificationsRevisionPanel.add(notificationsRevisionGridR);
		
		notificationsRevisionGridR.setWidget(0, 0, new Label("Produkt:"));
		notificationsRevisionGridR.setWidget(1, 0, new Label("Imie zglaszajacego:"));
		notificationsRevisionGridR.setWidget(2, 0, new Label("Nazwisko zglaszajacego:"));
		notificationsRevisionGridR.setWidget(3, 0, new Label("Telefon zglaszajacego:"));
		notificationsRevisionGridR.setWidget(4, 0, new Label("Waga zglaszajacego:"));
		notificationsRevisionGridR.setWidget(5, 0, new Label("Data zgloszenia:"));
		notificationsRevisionGridR.setWidget(6, 0, new Label("PRZYDZIAL"));
		notificationsRevisionGridR.setWidget(7, 0, new Label("Serwisant:"));
		notificationsRevisionGridR.setWidget(8, 0, new Label("Projektant:"));
		notificationsRevisionGridR.setWidget(9, 0, new Label("Programista:"));
		notificationsRevisionGridR.setWidget(10, 0, new Label("Tester:"));
		
		final TextBox produkt = new TextBox();
		produkt.setEnabled(false);
		notificationsRevisionGridR.setWidget(0, 1, produkt);
		
		final TextBox imie = new TextBox();
		imie.setEnabled(false);
		notificationsRevisionGridR.setWidget(1, 1, imie);
		
		final TextBox nazwisko = new TextBox();
		nazwisko.setEnabled(false);
		notificationsRevisionGridR.setWidget(2, 1, nazwisko);
		
		final TextBox telefon = new TextBox();
		telefon.setEnabled(false);
		notificationsRevisionGridR.setWidget(3, 1, telefon);
		
		final TextBox waga = new TextBox();
		waga.setEnabled(false);
		notificationsRevisionGridR.setWidget(4, 1, waga);
		
		final Grid dataGrid = new Grid(3, 2);
		dataGrid.setWidget(0, 0, new Label("Dzien:"));
		dataGrid.setWidget(1, 0, new Label("Miesiac:"));
		dataGrid.setWidget(2, 0, new Label("Rok:"));
		
		final TextBox dzien = new TextBox();
		dzien.setEnabled(false);
		dataGrid.setWidget(0, 1, dzien);
		
		final TextBox miesiac = new TextBox();
		miesiac.setEnabled(false);
		dataGrid.setWidget(1, 1, miesiac);
		
		final TextBox rok = new TextBox();
		rok.setEnabled(false);
		dataGrid.setWidget(2, 1, rok);
		
		notificationsRevisionGridR.setWidget(5, 1, dataGrid);
		
		final TextBox serwisant = new TextBox();
		serwisant.setEnabled(false);
		notificationsRevisionGridR.setWidget(7, 1, serwisant);
		
		final TextBox projektant = new TextBox();
		projektant.setEnabled(false);
		notificationsRevisionGridR.setWidget(8, 1, projektant);
		
		final TextBox programista = new TextBox();
		programista.setEnabled(false);
		notificationsRevisionGridR.setWidget(9, 1, programista);
		
		final TextBox tester = new TextBox();
		tester.setEnabled(false);
		notificationsRevisionGridR.setWidget(10, 1, tester);
		
		//notificationsRevisionGridR.setWidget(4, 0, new Label("Data zgloszenia:"));
		
		notificationsRevisionGridR.setWidget(11, 0, new Label("Opis problemu:"));
		
		final TextArea opis = new TextArea();
		opis.setEnabled(false);
		opis.setCharacterWidth(30);
		opis.setVisibleLines(10);
		
		notificationsRevisionGridR.setWidget(11, 1, opis);
		
		final Button raporty = new Button("Raporty", new ClickListener() {

			public void onClick(Widget sender) {
				//designerController.deleteGroup(groupsListWidget.getItemText(removedGroupNum = groupsListWidget.getSelectedIndex()));
				
				reportsList = notificationsList.get(notificationsListWidget.getSelectedIndex()).getReports();
				
				reportsListWidget = new ListBox();
				
				for(int i = 0; i < reportsList.size(); i++)
					reportsListWidget.addItem(reportsList.get(i).userName);
				
				stackPanel.remove(6);
				stackPanel.insert(reportsRevisionInterface(), 6);
				stackPanel.setStackText(6, "Przeglad raportow (i zgloszen)");
				stackPanel.showStack(6);
			}
		});
		
		if(notificationsListWidget == null) notificationsListWidget = new ListBox();
		else {
			if((notificationsListWidget.getSelectedIndex() >= 0) && (notificationsListWidget.getSelectedIndex() < notificationsListWidget.getItemCount())) {
				
				if(notificationsList.get(notificationsListWidget.getSelectedIndex()).getReports().size() > 0) raporty.setEnabled(true);
				else raporty.setEnabled(false);
				
				produkt.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProdukt());
				imie.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getImieZglaszajacego());
				nazwisko.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getNazwiskoZglaszajacego());
				telefon.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getTelefonZglaszajacego());
				waga.setText(String.valueOf(notificationsList.get(notificationsListWidget.getSelectedIndex()).getWagaKlienta().getNrWagi()));
				
				if(notificationsList.get(notificationsListWidget.getSelectedIndex()).getSerwisant() != null)
					serwisant.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getSerwisant());
				else serwisant.setText("BRAK");
				
				if(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProjektant() != null)
					projektant.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProjektant());
				else projektant.setText("BRAK");
				
				if(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProgramista() != null)
					programista.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProgramista());
				else programista.setText("BRAK");
				
				if(notificationsList.get(notificationsListWidget.getSelectedIndex()).getTester() != null)
					tester.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getTester());
				else tester.setText("BRAK");
				
				dzien.setText(String.valueOf(notificationsList.get(notificationsListWidget.getSelectedIndex()).getDataZgloszenia().getDate()));
				miesiac.setText(String.valueOf(notificationsList.get(notificationsListWidget.getSelectedIndex()).getDataZgloszenia().getMonth()));
				rok.setText(String.valueOf(notificationsList.get(notificationsListWidget.getSelectedIndex()).getDataZgloszenia().getYear()));
				opis.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getOpisProblemu());
				
			}
		}
		
		/*final Button delete = new Button("Usun", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.deleteGroup(groupsListWidget.getItemText(removedGroupNum = groupsListWidget.getSelectedIndex()));
			}
		});
			
		delete.setEnabled(false);*/
		
		notificationsListWidget.setVisibleItemCount(15);
		
		
		
		if(notificationsList != null)
			if(notificationsList.get(notificationsListWidget.getSelectedIndex()).getReports().size() > 0) raporty.setEnabled(true);
			else raporty.setEnabled(false);
		else raporty.setEnabled(false);
		
		notificationsRevisionGridR.setWidget(12, 0, raporty);
		
		final Button przejmij = new Button("Przejmij zgloszenie", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.takeNotification(takenNotification = notificationsListWidget.getSelectedIndex());
			}
			
		});
		
		przejmij.setEnabled(false);
		
		notificationsRevisionGridR.setWidget(12, 1, przejmij);
		
		notificationsListWidget.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				// TODO Auto-generated method stub
				if((notificationsListWidget.getSelectedIndex() >= 0) && (notificationsListWidget.getSelectedIndex() < notificationsListWidget.getItemCount())) {
					
					if(notificationsList.get(notificationsListWidget.getSelectedIndex()).getReports().size() > 0) raporty.setEnabled(true);
					else raporty.setEnabled(false);
					
					produkt.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProdukt());
					imie.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getImieZglaszajacego());
					nazwisko.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getNazwiskoZglaszajacego());
					telefon.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getTelefonZglaszajacego());
					waga.setText(String.valueOf(notificationsList.get(notificationsListWidget.getSelectedIndex()).getWagaKlienta().getNrWagi()));
					
					if(notificationsList.get(notificationsListWidget.getSelectedIndex()).getSerwisant() != null)
						serwisant.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getSerwisant());
					else serwisant.setText("BRAK");
					
					if(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProjektant() != null)
						projektant.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProjektant());
					else projektant.setText("BRAK");
					
					if(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProgramista() != null)
						programista.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProgramista());
					else programista.setText("BRAK");
					
					if(notificationsList.get(notificationsListWidget.getSelectedIndex()).getTester() != null)
						tester.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getTester());
					else tester.setText("BRAK");
					
					dzien.setText(String.valueOf(notificationsList.get(notificationsListWidget.getSelectedIndex()).getDataZgloszenia().getDate()));
					miesiac.setText(String.valueOf(notificationsList.get(notificationsListWidget.getSelectedIndex()).getDataZgloszenia().getMonth()));
					rok.setText(String.valueOf(notificationsList.get(notificationsListWidget.getSelectedIndex()).getDataZgloszenia().getYear()));
					opis.setText(notificationsList.get(notificationsListWidget.getSelectedIndex()).getOpisProblemu());
					
				}
				
				if(notificationsList.get(notificationsListWidget.getSelectedIndex()).getProjektant() == null) przejmij.setEnabled(true);
				else przejmij.setEnabled(false);
				
					/*delete.setEnabled(true);
				else delete.setEnabled(false);*/
					
			}
			
		});
		
		notificationsRevisionGridL.setWidget(0, 0, notificationsListWidget);
		
		Button refresh = new Button("Pobierz liste", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.getNotificationsList();
			}
			
		});
		
		notificationsRevisionGridL.setWidget(1, 0, refresh);
		
		
		//notificationsRevisionGrid.setWidget(0, 1, delete);
			
	

		return notificationsRevisionPanel;
	}
	
	
	
	
	public void setSelfNotificationsRevisionData(List<Zgloszenie> selfNotificationsList) {
		selfNotificationsListWidget.clear();
		
		this.selfNotificationsList = selfNotificationsList;
		
		for(int i = 0; i < selfNotificationsList.size(); i++) {
			selfNotificationsListWidget.addItem(selfNotificationsList.get(i).getProdukt());
		}
	}
	
	private boolean checkFunctions(HashSet<Integer> functions, int function) {
		for(int i = 0; i < functions.size(); i++)
			if(((Integer)functions.toArray()[i]) == function) return true;
		
			return false;
		}
	
	
	private String komentarzDoRaportu;
	
	public void reportAdded() {
		Report report = new Report();
		
		report.date = new Date();
		
		report.date.setMonth(report.date.getMonth() + 1);
		report.date.setYear(report.date.getYear() + 1900);
		report.comment = komentarzDoRaportu;
		report.userName = userName;
		report.userType = IFunkcje.PROJEKTANT;
		
		selfReportsListWidget.addItem(userName);
		
		selfReportsList.add(report);
		
		stackPanel.remove(7);
		stackPanel.add(selfReportsRevisionInterface(), "Przeglad raportow (i przydzielonych zgloszen)");
		stackPanel.showStack(7);
		
		
		/*designerController.getSelfNotificationsList();
		selfReportsList = selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getReports();*/
	}
	
	Grid addingReport() {
		Grid addingReportGrid = new Grid(7, 2);
		
		addingReportGrid.setWidget(0, 0, new Label("Produkt:"));
		addingReportGrid.setWidget(1, 0, new Label("Pracownik:"));
		addingReportGrid.setWidget(2, 0, new Label("Typ pracownika:"));
		//addingReportGrid.setWidget(3, 0, new Label("Data:"));
		addingReportGrid.setWidget(4, 0, new Label("Komentarz:"));
		
		final TextBox produkt = new TextBox();
		produkt.setEnabled(false);
		addingReportGrid.setWidget(0, 1, produkt);
		
		final TextBox pracownik = new TextBox();
		pracownik.setEnabled(false);
		addingReportGrid.setWidget(1, 1, pracownik);
		
		final TextBox typ = new TextBox();
		typ.setEnabled(false);
		addingReportGrid.setWidget(2, 1, typ);
		
		
		/*final Grid dataGrid = new Grid(3, 2);
		dataGrid.setWidget(0, 0, new Label("Dzien:"));
		dataGrid.setWidget(1, 0, new Label("Miesiac:"));
		dataGrid.setWidget(2, 0, new Label("Rok:"));
		
		final TextBox dzien = new TextBox();
		dzien.setEnabled(false);
		dataGrid.setWidget(0, 1, dzien);
		
		final TextBox miesiac = new TextBox();
		miesiac.setEnabled(false);
		dataGrid.setWidget(1, 1, miesiac);
		
		final TextBox rok = new TextBox();
		rok.setEnabled(false);
		dataGrid.setWidget(2, 1, rok);
		
		addingReportGrid.setWidget(3, 1, dataGrid);*/
		
		final TextArea komentarz = new TextArea();
		komentarz.setCharacterWidth(40);
		komentarz.setVisibleLines(10);
		addingReportGrid.setWidget(4, 1, komentarz);
		
		/*Date date = new Date();
	
		dzien.setText(String.valueOf(date.getDate()));
		miesiac.setText(String.valueOf(date.getMonth() + 1));
		rok.setText(String.valueOf(date.getYear() + 1900));*/
		
		//notificationsRevisionGridR.setWidget(4, 0, new Label("Data zgloszenia:"));
		
		
		//reportsListWidget = new ListBox();
		
		/*final Button delete = new Button("Usun", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.deleteGroup(groupsListWidget.getItemText(removedGroupNum = groupsListWidget.getSelectedIndex()));
			}
		});
			
		delete.setEnabled(false);*/
		
		if(selfReportsListWidget == null) selfReportsListWidget = new ListBox();
		
		selfReportsListWidget.setVisibleItemCount(10);
		
		produkt.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProdukt());
		pracownik.setText(userName);
		typ.setText("Projektant");
		
		final Button raporty = new Button("Raporty", new ClickListener() {

			public void onClick(Widget sender) {
				//designerController.deleteGroup(groupsListWidget.getItemText(removedGroupNum = groupsListWidget.getSelectedIndex()));
				stackPanel.remove(7);
				stackPanel.add(selfReportsRevisionInterface(), "Przeglad raportow (i przydzielonych zgloszen)");
				stackPanel.showStack(7);
			}
		});
		
		//raporty.setEnabled(false);
		
		addingReportGrid.setWidget(6, 0, raporty);
		
		HorizontalPanel dodawanieRaportu = new HorizontalPanel();
		
		final Button dalej = new Button("Do programisty", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.addReport(selfNotificationsListWidget.getSelectedIndex(), komentarzDoRaportu = komentarz.getText(), true);
			}
		});
		
		final Button wczesniej = new Button("Do serwisanta", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.addReport(selfNotificationsListWidget.getSelectedIndex(), komentarzDoRaportu = komentarz.getText(), false);
			}
		});
		
		dodawanieRaportu.add(wczesniej);
		dodawanieRaportu.add(dalej);
		
		addingReportGrid.setWidget(6, 1, dodawanieRaportu);
		
		return addingReportGrid;
	}
	
	
	
	
	HorizontalPanel selfReportsRevisionInterface() {
		HorizontalPanel reportsRevisionPanel = new HorizontalPanel();
		
		Grid reportsRevisionGridL = new Grid(2, 2), reportsRevisionGridR = new Grid(7, 2);
		reportsRevisionPanel.add(reportsRevisionGridL);
		reportsRevisionPanel.add(reportsRevisionGridR);
		
		reportsRevisionGridR.setWidget(0, 0, new Label("Produkt:"));
		reportsRevisionGridR.setWidget(1, 0, new Label("Pracownik:"));
		reportsRevisionGridR.setWidget(2, 0, new Label("Typ pracownika:"));
		reportsRevisionGridR.setWidget(3, 0, new Label("Data:"));
		reportsRevisionGridR.setWidget(4, 0, new Label("Komentarz:"));
		
		final TextBox produkt = new TextBox();
		produkt.setEnabled(false);
		reportsRevisionGridR.setWidget(0, 1, produkt);
		
		final TextBox pracownik = new TextBox();
		pracownik.setEnabled(false);
		reportsRevisionGridR.setWidget(1, 1, pracownik);
		
		final TextBox typ = new TextBox();
		typ.setEnabled(false);
		reportsRevisionGridR.setWidget(2, 1, typ);
		
		
		final Grid dataGrid = new Grid(3, 2);
		dataGrid.setWidget(0, 0, new Label("Dzien:"));
		dataGrid.setWidget(1, 0, new Label("Miesiac:"));
		dataGrid.setWidget(2, 0, new Label("Rok:"));
		
		final TextBox dzien = new TextBox();
		dzien.setEnabled(false);
		dataGrid.setWidget(0, 1, dzien);
		
		final TextBox miesiac = new TextBox();
		miesiac.setEnabled(false);
		dataGrid.setWidget(1, 1, miesiac);
		
		final TextBox rok = new TextBox();
		rok.setEnabled(false);
		dataGrid.setWidget(2, 1, rok);
		
		reportsRevisionGridR.setWidget(3, 1, dataGrid);
		
		final TextArea komentarz = new TextArea();
		komentarz.setEnabled(false);
		komentarz.setCharacterWidth(30);
		komentarz.setVisibleLines(10);
		reportsRevisionGridR.setWidget(4, 1, komentarz);
		
	
		
		//notificationsRevisionGridR.setWidget(4, 0, new Label("Data zgloszenia:"));
		
		
		//reportsListWidget = new ListBox();
		
		/*final Button delete = new Button("Usun", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.deleteGroup(groupsListWidget.getItemText(removedGroupNum = groupsListWidget.getSelectedIndex()));
			}
		});
			
		delete.setEnabled(false);*/
		
		selfReportsListWidget.setVisibleItemCount(10);
		
		final Button zgloszenia = new Button("Zgloszenia", new ClickListener() {

			public void onClick(Widget sender) {
				//designerController.deleteGroup(groupsListWidget.getItemText(removedGroupNum = groupsListWidget.getSelectedIndex()));
				stackPanel.remove(7);
				stackPanel.add(selfNotificationsRevisionInterface(), "Przeglad przydzielonych zgloszen (i raportow)");
				stackPanel.showStack(7);
			}
		});
		
		//zgloszenia.setEnabled(false);
		
		reportsRevisionGridR.setWidget(6, 0, zgloszenia);
		
		if((selfReportsListWidget.getSelectedIndex() >= 0) && (selfReportsListWidget.getSelectedIndex() < selfReportsListWidget.getItemCount())) {
			
			produkt.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProdukt());
			pracownik.setText(selfReportsList.get(selfReportsListWidget.getSelectedIndex()).userName);
			
			switch(selfReportsList.get(selfReportsListWidget.getSelectedIndex()).userType) {
			case IFunkcje.SERWISANT :
				typ.setText("Serwisant");
				break;
			case IFunkcje.PROGRAMISTA :
				typ.setText("Programista");
				break;
			case IFunkcje.PROJEKTANT :
				typ.setText("Projektant");
				break;
			case IFunkcje.TESTER :
				typ.setText("Tester");
				break;
			}
			
			dzien.setText(String.valueOf(selfReportsList.get(selfReportsListWidget.getSelectedIndex()).date.getDate()));
			miesiac.setText(String.valueOf(selfReportsList.get(selfReportsListWidget.getSelectedIndex()).date.getMonth()));
			rok.setText(String.valueOf(selfReportsList.get(selfReportsListWidget.getSelectedIndex()).date.getYear()));
			komentarz.setText(selfReportsList.get(selfReportsListWidget.getSelectedIndex()).comment);
			
		}
		
		selfReportsListWidget.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				// TODO Auto-generated method stub
				if((selfReportsListWidget.getSelectedIndex() >= 0) && (selfReportsListWidget.getSelectedIndex() < selfReportsListWidget.getItemCount())) {
					
					produkt.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProdukt());
					pracownik.setText(selfReportsList.get(selfReportsListWidget.getSelectedIndex()).userName);
					
					switch(selfReportsList.get(selfReportsListWidget.getSelectedIndex()).userType) {
					case IFunkcje.SERWISANT :
						typ.setText("Serwisant");
						break;
					case IFunkcje.PROGRAMISTA :
						typ.setText("Programista");
						break;
					case IFunkcje.PROJEKTANT :
						typ.setText("Projektant");
						break;
					case IFunkcje.TESTER :
						typ.setText("Tester");
						break;
					}
					
					dzien.setText(String.valueOf(selfReportsList.get(selfReportsListWidget.getSelectedIndex()).date.getDate()));
					miesiac.setText(String.valueOf(selfReportsList.get(selfReportsListWidget.getSelectedIndex()).date.getMonth()));
					rok.setText(String.valueOf(selfReportsList.get(selfReportsListWidget.getSelectedIndex()).date.getYear()));
					komentarz.setText(selfReportsList.get(selfReportsListWidget.getSelectedIndex()).comment);
					
				}
					/*delete.setEnabled(true);
				else delete.setEnabled(false);*/
					
			}
			
		});
		
		reportsRevisionGridL.setWidget(0, 0, selfReportsListWidget);
		
		Button dodajRaport = new Button("Dodaj raport", new ClickListener() {

			public void onClick(Widget sender) {
				stackPanel.remove(7);
				stackPanel.add(addingReport(), "Dodawanie raportu");
				stackPanel.showStack(7);
			}
			
		});
		
		if(selfReportsList.size() > 0)
		if(((selfReportsList.get(selfReportsList.size() - 1).userType == IFunkcje.SERWISANT) && selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).doPrzodu()) ||
				((selfReportsList.get(selfReportsList.size() - 1).userType == IFunkcje.PROGRAMISTA) && !selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).doPrzodu()))
			dodajRaport.setEnabled(true);
		else dodajRaport.setEnabled(false);
		else dodajRaport.setEnabled(true);
		
		reportsRevisionGridL.setWidget(1, 0, dodajRaport);
		
		
		//notificationsRevisionGrid.setWidget(0, 1, delete);
		
		return reportsRevisionPanel;
	}

	
	
	private String przydzielanySerwisant, przydzielanyProgramista, przydzielanyTester;
	TextBox serwisant;
	TextBox programista;
	TextBox tester;
	
	public void workersAssigned() {
		// TODO Auto-generated method stub
		serwisant.setText(przydzielanySerwisant);
		programista.setText(przydzielanyProgramista);
		tester.setText(przydzielanyTester);
	}
	
	HorizontalPanel selfNotificationsRevisionInterface() {
		HorizontalPanel notificationsRevisionPanel = new HorizontalPanel();
		
		Grid notificationsRevisionGridL = new Grid(2, 2), notificationsRevisionGridR = new Grid(13, 3);
		notificationsRevisionPanel.add(notificationsRevisionGridL);
		notificationsRevisionPanel.add(notificationsRevisionGridR);
		
		notificationsRevisionGridR.setWidget(0, 0, new Label("Produkt:"));
		notificationsRevisionGridR.setWidget(1, 0, new Label("Imie zglaszajacego:"));
		notificationsRevisionGridR.setWidget(2, 0, new Label("Nazwisko zglaszajacego:"));
		notificationsRevisionGridR.setWidget(3, 0, new Label("Telefon zglaszajacego:"));
		notificationsRevisionGridR.setWidget(4, 0, new Label("Waga zglaszajacego:"));
		notificationsRevisionGridR.setWidget(5, 0, new Label("Data zgloszenia:"));
		notificationsRevisionGridR.setWidget(6, 0, new Label("PRZYDZIAL"));
		notificationsRevisionGridR.setWidget(7, 0, new Label("Serwisant:"));
		notificationsRevisionGridR.setWidget(8, 0, new Label("Projektant:"));
		notificationsRevisionGridR.setWidget(9, 0, new Label("Programista:"));
		notificationsRevisionGridR.setWidget(10, 0, new Label("Tester:"));
		
		final TextBox produkt = new TextBox();
		produkt.setEnabled(false);
		notificationsRevisionGridR.setWidget(0, 1, produkt);
		
		final TextBox imie = new TextBox();
		imie.setEnabled(false);
		notificationsRevisionGridR.setWidget(1, 1, imie);
		
		final TextBox nazwisko = new TextBox();
		nazwisko.setEnabled(false);
		notificationsRevisionGridR.setWidget(2, 1, nazwisko);
		
		final TextBox telefon = new TextBox();
		telefon.setEnabled(false);
		notificationsRevisionGridR.setWidget(3, 1, telefon);
		
		final TextBox waga = new TextBox();
		waga.setEnabled(false);
		notificationsRevisionGridR.setWidget(4, 1, waga);
		
		final Grid dataGrid = new Grid(3, 2);
		dataGrid.setWidget(0, 0, new Label("Dzien:"));
		dataGrid.setWidget(1, 0, new Label("Miesiac:"));
		dataGrid.setWidget(2, 0, new Label("Rok:"));
		
		final TextBox dzien = new TextBox();
		dzien.setEnabled(false);
		dataGrid.setWidget(0, 1, dzien);
		
		final TextBox miesiac = new TextBox();
		miesiac.setEnabled(false);
		dataGrid.setWidget(1, 1, miesiac);
		
		final TextBox rok = new TextBox();
		rok.setEnabled(false);
		dataGrid.setWidget(2, 1, rok);
		
		notificationsRevisionGridR.setWidget(5, 1, dataGrid);
		
		
		final Button przydziel = new Button("Przydziel pracownikow", new ClickListener() {
			
			public void onClick(Widget sender) {
				designerController.assignWorkers(selfNotificationsListWidget.getSelectedIndex(), przydzielanySerwisant = serwisantListWidget.getItemText(serwisantListWidget.getSelectedIndex()),
						przydzielanyProgramista = programistaListWidget.getItemText(programistaListWidget.getSelectedIndex()), przydzielanyTester = testerListWidget.getItemText(testerListWidget.getSelectedIndex()));
				
			}
		});
		
		przydziel.setEnabled(false);

		notificationsRevisionGridR.setWidget(6, 1, przydziel);
		
		
		final Button pobierzPracownikow = new Button("Pobierz pracownikow", new ClickListener() {
			
			public void onClick(Widget sender) {
				designerController.getWorkersList();
				
				serwisantListWidget.clear();
				programistaListWidget.clear();
				testerListWidget.clear();
				
				przydziel.setEnabled(true);
			}
		});
		
		//pobierzPracownikow.setEnabled(false);
		
		notificationsRevisionGridR.setWidget(6, 2, pobierzPracownikow);
		
		
		serwisant = new TextBox();
		serwisant.setEnabled(false);
		notificationsRevisionGridR.setWidget(7, 1, serwisant);
		
		serwisantListWidget = new ListBox();
		notificationsRevisionGridR.setWidget(7, 2, serwisantListWidget);
		
		final TextBox projektant = new TextBox();
		projektant.setEnabled(false);
		notificationsRevisionGridR.setWidget(8, 1, projektant);
		
		programista = new TextBox();
		programista.setEnabled(false);
		notificationsRevisionGridR.setWidget(9, 1, programista);
		
		programistaListWidget = new ListBox();
		notificationsRevisionGridR.setWidget(9, 2, programistaListWidget);
		
		tester = new TextBox();
		tester.setEnabled(false);
		notificationsRevisionGridR.setWidget(10, 1, tester);
		
		testerListWidget = new ListBox();
		notificationsRevisionGridR.setWidget(10, 2, testerListWidget);
		
		//notificationsRevisionGridR.setWidget(4, 0, new Label("Data zgloszenia:"));
		
		notificationsRevisionGridR.setWidget(11, 0, new Label("Opis problemu:"));
		
		final TextArea opis = new TextArea();
		opis.setEnabled(false);
		opis.setCharacterWidth(30);
		opis.setVisibleLines(10);
		
		notificationsRevisionGridR.setWidget(11, 1, opis);
		
		final Button raporty = new Button("Raporty", new ClickListener() {

			public void onClick(Widget sender) {
				//designerController.deleteGroup(groupsListWidget.getItemText(removedGroupNum = groupsListWidget.getSelectedIndex()));
				
				selfReportsList = selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getReports();
				
				selfReportsListWidget = new ListBox();
				
				for(int i = 0; i < selfReportsList.size(); i++)
					selfReportsListWidget.addItem(selfReportsList.get(i).userName);
				
				stackPanel.remove(7);
				stackPanel.add(selfReportsRevisionInterface(), "Przeglad raportow (i przydzielonych zgloszen)");
				stackPanel.showStack(7);
			}
		});
		
		if(selfNotificationsListWidget == null) selfNotificationsListWidget = new ListBox();
		else {
			if((selfNotificationsListWidget.getSelectedIndex() >= 0) && (selfNotificationsListWidget.getSelectedIndex() < selfNotificationsListWidget.getItemCount())) {
				
				/*if(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getReports().size() > 0) raporty.setEnabled(true);
				else raporty.setEnabled(false);*/
				
				produkt.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProdukt());
				imie.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getImieZglaszajacego());
				nazwisko.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getNazwiskoZglaszajacego());
				telefon.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getTelefonZglaszajacego());
				waga.setText(String.valueOf(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getWagaKlienta().getNrWagi()));
				
				if(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getSerwisant() != null)
					serwisant.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getSerwisant());
				else serwisant.setText("BRAK");
				
				if(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProjektant() != null)
					projektant.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProjektant());
				else projektant.setText("BRAK");
				
				if(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProgramista() != null)
					programista.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProgramista());
				else programista.setText("BRAK");
				
				if(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getTester() != null)
					tester.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getTester());
				else tester.setText("BRAK");
				
				dzien.setText(String.valueOf(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getDataZgloszenia().getDate()));
				miesiac.setText(String.valueOf(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getDataZgloszenia().getMonth()));
				rok.setText(String.valueOf(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getDataZgloszenia().getYear()));
				opis.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getOpisProblemu());
				
			}
		}
		
		/*final Button delete = new Button("Usun", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.deleteGroup(groupsListWidget.getItemText(removedGroupNum = groupsListWidget.getSelectedIndex()));
			}
		});
			
		delete.setEnabled(false);*/
		
		selfNotificationsListWidget.setVisibleItemCount(15);
		
		
		//if(selfNotificationsList != null) selfNotificationsList = new LinkedList<Zgloszenie>();
		/*if(selfNotificationsList != null)
			if(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getReports().size() > 0) raporty.setEnabled(true);
			else raporty.setEnabled(false);
		else raporty.setEnabled(false);*/
		
		notificationsRevisionGridR.setWidget(12, 0, raporty);
		
		/*final Button przejmij = new Button("Przejmij zgloszenie", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.takeNotification(takenNotification = selfNotificationsListWidget.getSelectedIndex());
			}
			
		});
		
		przejmij.setEnabled(false);
		
		notificationsRevisionGridR.setWidget(12, 1, przejmij);*/
		
		selfNotificationsListWidget.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				// TODO Auto-generated method stub
				if((selfNotificationsListWidget.getSelectedIndex() >= 0) && (selfNotificationsListWidget.getSelectedIndex() < selfNotificationsListWidget.getItemCount())) {
					
					/*if(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getReports().size() > 0) raporty.setEnabled(true);
					else raporty.setEnabled(false);*/
					
					produkt.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProdukt());
					imie.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getImieZglaszajacego());
					nazwisko.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getNazwiskoZglaszajacego());
					telefon.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getTelefonZglaszajacego());
					waga.setText(String.valueOf(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getWagaKlienta().getNrWagi()));
					
					if(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getSerwisant() != null)
						serwisant.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getSerwisant());
					else serwisant.setText("BRAK");
					
					if(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProjektant() != null)
						projektant.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProjektant());
					else projektant.setText("BRAK");
					
					if(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProgramista() != null)
						programista.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProgramista());
					else programista.setText("BRAK");
					
					if(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getTester() != null)
						tester.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getTester());
					else tester.setText("BRAK");
					
					dzien.setText(String.valueOf(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getDataZgloszenia().getDate()));
					miesiac.setText(String.valueOf(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getDataZgloszenia().getMonth()));
					rok.setText(String.valueOf(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getDataZgloszenia().getYear()));
					opis.setText(selfNotificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getOpisProblemu());
					
				}
				
				/*if(notificationsList.get(selfNotificationsListWidget.getSelectedIndex()).getProjektant() == null) przejmij.setEnabled(true);
				else przejmij.setEnabled(false);*/
				
					/*delete.setEnabled(true);
				else delete.setEnabled(false);*/
					
			}
			
		});
		
		notificationsRevisionGridL.setWidget(0, 0, selfNotificationsListWidget);
		
		Button refresh = new Button("Pobierz liste", new ClickListener() {

			public void onClick(Widget sender) {
				designerController.getSelfNotificationsList();
			}
			
		});
		
		notificationsRevisionGridL.setWidget(1, 0, refresh);
		
		
		//notificationsRevisionGrid.setWidget(0, 1, delete);
			
	

		return notificationsRevisionPanel;
	}
	
}