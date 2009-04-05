package com.dendryt.software.client.login;

import com.dendryt.software.client.IUserInterface;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LogInInterface implements IUserInterface{
	private RootPanel rootPanel;
	private LogInController logInController;
	
	public LogInInterface() {
		logInController = new LogInController();
		rootPanel = RootPanel.get();
	}
	
	public void mainInterface() {
		Window.setTitle("Dendryt - interfejs logowania");
		
		final TextBox TBname = new TextBox();
		final PasswordTextBox TBpassword = new PasswordTextBox();
		
		rootPanel.clear();
		
		rootPanel.add(new Label("Login"));
		rootPanel.add(TBname);
		rootPanel.add(new Label("Haslo"));
		rootPanel.add(TBpassword);
		
		
		

		
		
		Button Blogin=new Button("Zaloguj", new ClickListener(){

		public void onClick(Widget sender) {
			rootPanel.clear();
			rootPanel.add(new Label("Prosze czekac..."));			
			logInController.authenticate(TBname.getText(), TBpassword.getText());
			TBname.setText("");
			TBpassword.setText("");
		}
			 
		});
		 
		rootPanel.add(Blogin);
	}

}
