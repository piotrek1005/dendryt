package com.dendryt.software.client.login;

import com.dendryt.software.client.IUserInterface;
import com.dendryt.software.client.client.ClientInterface;
import com.dendryt.software.client.client.tmp.DebugModeSetting;
import com.dendryt.software.client.designer.DesignerInterface;
import com.dendryt.software.client.programmer.ProgrammerInterface;
import com.dendryt.software.client.service.ServiceInterface;
import com.dendryt.software.client.tester.TesterInterface;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class LogInController {
	private IAuthenticateUserAsync loginService;
	private String login;
	
	
	private AsyncCallback<Integer> logInCallback=new AsyncCallback<Integer>(){

		public void onFailure(Throwable caught) {
			Window.alert("Error"+caught.toString());
			 
		}

		public void onSuccess(Integer result) {
			IUserInterface userInterface = null;
			
			//TODO - tylko na czas testowania, poki nie ma kont klienckich
			if(DebugModeSetting.ALWAYS_LOGIN_CLIENT){
				result = IFunkcje.KLIENT;				
			}
			
			
			switch(result) {
			case -1:
				userInterface = new LogInInterface();
				//TODO sprawdzic kolejnosc
				//(new LogInInterface()).mainInterface();
				Window.alert("Niepoprawny login lub haslo!");
				break;
			case IFunkcje.SERWISANT:
				userInterface = new ServiceInterface(login);
				break;
				
			case IFunkcje.PROJEKTANT:
				userInterface = new DesignerInterface(login);
				break;
				
			case IFunkcje.KLIENT:
				userInterface = new ClientInterface();
				break;
				
			case IFunkcje.PROGRAMISTA:
				userInterface = new ProgrammerInterface(login);
				break;
				
			case IFunkcje.TESTER:
				userInterface = new TesterInterface(login);
				break;
				
			default:
				System.err.println(result);
				Window.alert("Nieobsluzony typ uzytkownika!");
				return;
			}
			userInterface.mainInterface();
			
		}
		
	};
	public void setLogInCallback(AsyncCallback<Integer> as){
		logInCallback=as;
	}
	LogInController() {
		loginService=(IAuthenticateUserAsync) GWT.create(IAuthenticateUser.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) loginService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "/IAuthenticateUser");
	}

	public void authenticate(String login, String password) {
		this.login = login;
		loginService.authenticate(new Data(login, password), logInCallback);
	}
}
