package com.dendryt.software.client.client;

import com.dendryt.software.client.IUserInterface;
import com.dendryt.software.client.login.LogInInterface;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class ClientInterface implements IUserInterface{
	private RootPanel rootPanel;
	private ClientController clientController;
	private ClientInterfaceTabPanel clientInterfaceMain;
	
	
	public ClientInterface() {		
		rootPanel = RootPanel.get();
		clientController = new ClientController();
		clientInterfaceMain = new ClientInterfaceTabPanel(clientController);
	}

	public void mainInterface() {
		Window.setTitle("Dendryt - Interfejs klienta");
		rootPanel.clear();
		

		Button logOut = new Button("Wyloguj", new ClickListener(){

			public void onClick(Widget sender) {
					//UM.logoff(null);
					//TODO wylogowanie !!!!!
					LogInInterface logInInterface = new LogInInterface();
					logInInterface.mainInterface();
				}
				 
			 });
		rootPanel.add(clientInterfaceMain);
		rootPanel.add(logOut);
		
		}
	

}
